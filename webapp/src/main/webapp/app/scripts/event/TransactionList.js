'use strict';
eventModule.controller('TransactionListController', function($scope, $filter, Country, Event, Transaction, Person, $stateParams,
                                                             localStorageService, ErrorPropagationService) {
    this.transactions = Transaction.transactions;
    this.transactionService = Transaction;
    this.event = Event.data;
    this.countries = Country.data;
    this.states = Person.stateStore();

    this.listBegin = 0;
    this.listEnd = 500;

    this.order = [{
        name: 'invoiceNumber',
        ascending: '-'
    }];
    this.view = {
        invoiceNumber: true,
        firstName: true,
        lastName: true,
        priceWithoutTax: true,
        tax: true,
        price: true,
        transactionStatus: true,
        paymentMethod: true,
        dateOfPayment: true
    };

    this.stringFilter = $stateParams.search || '';
    this.invoiceChangeModal = false;
    this.transactionToEdit = undefined;
    this.invoiceAddressCopy = {};

    this.config = {
        view: this.view,
        order: this.order,
        limit: this.listEnd
    };

    var scope = this;

    this.changeInvoiceAddress = function(transaction) {
        scope.transactionToEdit = transaction;
        angular.copy(transaction.invoiceAddress, scope.invoiceAddressCopy);
        scope.invoiceChangeModal = true;
    };
    this.cancelModal = function(transaction) {
        scope.invoiceChangeModal = false;
        transaction.invoiceAddress = scope.invoiceAddressCopy;
        scope.invoiceAddressCopy = {};
    };

    this.visibleTransactions = _.filter(this.transactions, scope.getFilter).length;
    this.markForCancel = function(container) {
        if(!container.cancelled) {
            container.markedForCancel = !container.markedForCancel;
        }
    };
    this.numberOfMarkedItems = function(transaction) {
        return _.filter(transaction.transaction.paymentItemContainer, function(container) {
            return container.markedForCancel;
        }).length
    };
    this.collectMarkedItems = function(transaction) {
        var filteredContainer = _.filter(transaction.transaction.paymentItemContainer, function(container) {
            return container.markedForCancel;
        });
        return _.map(filteredContainer, 'id');
    };
    this.isCompletable = function(transaction) {
        return transaction.transaction.transactionStatus == 'FIXED';
    };
    this.isChangable = function(transaction) {
        return transaction.transaction.transactionStatus == 'FIXED' ||
            transaction.transaction.transactionStatus == 'COMPLETED';
    };
    this.isCancellable = function(transaction) {
        return transaction.transaction.transactionStatus != 'CREDIT' &&
            transaction.transaction.transactionStatus != 'CANCELED' &&
            transaction.transaction.transactionStatus != 'PENDING';
    };

    if(!!localStorageService.get(this.event.identifier + '_transaction_list_config')) {
        var config = localStorageService.get(this.event.identifier + '_transaction_list_config');
        if(!!config.view) {
            this.config = localStorageService.get(this.event.identifier + '_transaction_list_config');
        }
    }

    this.setOrder = function (order) {
        var removedOrder = _.remove(this.config.order, function (o) {
            return o.name === order;
        });
        var newAscending;
        if (removedOrder.length === 1) {
            if (removedOrder[0].ascending === '+') {
                newAscending = '-';
                this.config.order.push({name: order, ascending: newAscending});
            }
        } else {
            newAscending = '+';
            this.config.order.push({name: order, ascending: newAscending});
        }
    };

    this.headerClass = function (order) {
        var found = _.find(this.config.order, function (o) {
            return o.name === order;
        });
        if (found) {
            if (found.ascending === '+') {
                return 'glyphicon-sort-by-alphabet';
            } else {
                return 'glyphicon-sort-by-alphabet-alt';
            }
        } else {
            return 'empty';
        }

    };

    this.getOrder = function () {
        return _.map(this.config.order, function (order) {
            return order.ascending + order.name
        });
    };
    this.getFilter = function (transaction) {
        var filterExpression = false;
        var filterFunction = function(arg) {
            _.forEach(arg, function(p) {
                if (p != null && _.isObject(p)) {
                    filterFunction(p);
                } else {
                    filterExpression = filterExpression || filterAnalyzer(p);
                }
            });
        };

        var filterAnalyzer = function(p) {
            var searchCriteria = [];
            if(scope.stringFilter.includes('&&')) {
                searchCriteria = scope.stringFilter.split('&&')
            } else {
                searchCriteria.push(scope.stringFilter);
            }

            var match = false;
            _.forEach(searchCriteria, function(criteria) {
                if(_.isString(p)) {
                    match = match || p.toLowerCase().includes(criteria.trim().toLowerCase());
                }
                if(_.isNumber(p) || _.isDate(p)) {
                    match = match || p == criteria.trim();
                }
            });
            return match;
        };

        filterFunction(transaction);


        if (scope.stringFilter && scope.stringFilter !== "") {
            return filterExpression;
        } else {
            return true;
        }
    };

    var processRunning = false;
    this.completeTransaction = function(transaction) {
        if(!processRunning) {
            if(confirm('Transaktion wirklich auf bezahlt setzen?')) {
                processRunning = true;
                Transaction.complete(transaction.transaction.id).then(function(updatedTransaction) {
                    transaction.transaction.transactionStatus = 'COMPLETED';
                    var localizedString = 'participant.payment.transaction.';
                    localizedString += transaction.transaction.transactionStatus.toLowerCase();
                    transaction.transaction.transactionStatusTranslated = $filter('localized')(localizedString);
                    transaction.transaction.dateOfPayment = updatedTransaction.dateOfPayment;
                    processRunning = false;
                }, function(error) {
                    ErrorPropagationService.propagateErrorResponse(error);
                    processRunning = false;
                })
            }
        }
    };
    this.cancelTransactionComplete = function (transaction) {
        if(!processRunning) {
            ErrorPropagationService.resetErrors();
            if (confirm('Transaktion wirklich komplett stornieren?')) {
                processRunning = true;
                Transaction.cancelComplete(transaction.transaction.id,
                                           transaction.newCancellationPercentage).then(function () {
                    Transaction.getTransactions(scope.event.identifier).then(function (transactions) {
                        scope.transactions = transactions;
                        processRunning = false;
                    });
                }, function (error) {
                    ErrorPropagationService.propagateErrorResponse(error);
                    processRunning = false;
                });
            }
        }
    };
    this.cancelTransactionPartly = function(transaction) {
        if(!processRunning) {
            ErrorPropagationService.resetErrors();
            if(scope.numberOfMarkedItems(transaction) > 0) {
                if(confirm('Transaktion wirklich teilweise stornieren?')) {
                    processRunning = true;
                    Transaction.cancelPartly(transaction.transaction.id, scope.collectMarkedItems(transaction),
                                             transaction.newCancellationPercentage).then(function() {
                        Transaction.getTransactions(scope.event.identifier).then(function(transactions) {
                            scope.transactions = transactions;
                            processRunning = false;
                        });
                    }, function(error) {
                        ErrorPropagationService.propagateErrorResponse(error.data);
                        processRunning = false;
                    });
                }
            }
        }
    };
    this.generateInvoice = function(transaction) {
        if(!processRunning) {
            if(confirm('Wirklich Rechnung erzeugen? Transaktion und Positionen werden fixiert!')) {
                processRunning = true;
                Transaction.generateInvoice(transaction.transaction.id).then(function() {
                    Transaction.getTransactions(scope.event.identifier).then(function(transactions) {
                        scope.transactions = transactions;
                        processRunning = false;
                    });
                }, function(error) {
                    ErrorPropagationService.propagateErrorResponse(error.data);
                    processRunning = false;
                });
            }
        }
    };
    this.submitNewInvoiceAddress = function(transaction) {
        if(!processRunning) {
            if(confirm('Rechnungsadresse wirklich ändern?')) {
                processRunning = true;
                Transaction.changeInvoiceAddress(transaction.transaction.id, transaction.invoiceAddress).then(function() {
                    Transaction.getTransactions(scope.event.identifier).then(function(transactions) {
                        scope.transactions = transactions;
                        processRunning = false;
                    });
                    scope.invoiceChangeModal = false;
                    scope.invoiceAddressCopy = {};
                }, function(error) {
                    ErrorPropagationService.propagateErrorResponse(error);
                    processRunning = true;
                });
            }
        }
    };

    $scope.$watch(function() {
        return scope.config;
    }, function(value) {
        localStorageService.set(scope.event.identifier + '_transaction_list_config', value)
    }, true);

    $scope.$watch(function() {
        return scope.stringFilter;
    }, function() {
        scope.visibleTransactions = _.filter(scope.transactions, scope.getFilter).length;
    });
});

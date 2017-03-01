'use strict';

eventModule.factory('Transaction', function ($q, $http, $filter) {

    return (function () {

        var transactionPath = function (transactionId) {
            return 'app/api/transaction/' + (transactionId ? transactionId + '/' : '');
        };

        function Transaction() {
            this.transactions = [];
            this.activeTransactions = [];
            this.transactionToExecute = undefined;
        }

        Transaction.prototype.getTransactions = function(identifier) {
            var transactionsPromise = $q.defer();
            var self = this;
            $http.get('app/api/event/' + identifier + '/transactions').then(function(transactions) {
                self.transactions = transactions.data;
                _.forEach(self.transactions, function(transaction) {
                    var localizedString = 'participant.payment.transaction.';
                    localizedString += transaction.transaction.transactionStatus.toLowerCase();
                    transaction.transaction.transactionStatusTranslated = $filter('localized')(localizedString);
                    transaction.person.fullName = transaction.person.firstName + " " + transaction.person.lastName;
                });
                transactionsPromise.resolve(transactions.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.getOrders = function() {
            var orderPromise = $q.defer();
            var self = this;
            $http.get('app/api/orders').then(function(order) {
                self.transactions = order.data;
                _.forEach(self.transactions, function(transaction) {
                    var localizedString = 'participant.payment.transaction.';
                    localizedString += transaction.transaction.transactionStatus.toLowerCase();
                    transaction.transaction.transactionStatusTranslated = $filter('localized')(localizedString);
                    transaction.person.fullName = transaction.person.firstName + " " + transaction.person.lastName;
                });
                orderPromise.resolve(order.data);
            }, function(errors) {
                orderPromise.reject(errors);
            });
            return orderPromise.promise;
        };

        Transaction.prototype.complete = function(id) {
            var transactionsPromise = $q.defer();
            $http.post('app/api/transaction/' + id + '/complete',{}).then(function(transaction) {
                transactionsPromise.resolve(transaction.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.changeMethod = function(id, method) {
            var transactionsPromise = $q.defer();
            $http.post('app/api/transaction/' + id + '/method/', { paymentMethod : method}).then(function(transaction) {
                transactionsPromise.resolve(transaction.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.getTransactionToExecute = function(id) {
            var transactionsPromise = $q.defer();
            var self = this;
            $http.get(transactionPath(id)).then(function(transaction) {
                self.transactionToExecute = transaction.data;
                transactionsPromise.resolve(transaction.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.getActiveTransactions = function() {
            var self = this;
            var transactionsPromise = $q.defer();
            $http.get(transactionPath() + 'participant').then(function(transactions) {
                self.activeTransactions = transactions.data;
                transactionsPromise.resolve(transactions.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.generateInvoice = function(transactionId) {
            var self = this;
            var transactionsPromise = $q.defer();
            $http.post(transactionPath(transactionId) + 'invoice', {}).then(function(transactions) {
                self.activeTransactions = transactions.data;
                transactionsPromise.resolve(transactions.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.cancelComplete = function(transactionId, percentage) {
            var transactionsPromise = $q.defer();
            $http.post(transactionPath(transactionId) + 'cancel/complete', {cancellationPercentage : percentage}).then(function(transaction) {
                transactionsPromise.resolve(transaction.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.cancelPartly = function(transactionId, checked, percentage) {
            var transactionsPromise = $q.defer();
            $http.post(transactionPath(transactionId) + 'cancel/partly', {cancelledItems: checked, cancellationPercentage : percentage}).then(function(transaction) {
                transactionsPromise.resolve(transaction.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        Transaction.prototype.changeInvoiceAddress = function(transactionId, invoiceAddress) {
            var transactionsPromise = $q.defer();
            $http.post(transactionPath(transactionId) + 'address', invoiceAddress).then(function(transaction) {
                transactionsPromise.resolve(transaction.data);
            }, function(errors) {
                transactionsPromise.reject(errors);
            });
            return transactionsPromise.promise;
        };

        return new Transaction();

    })();
});

eventModule.factory('Invoice', function ($q, $http) {

    return (function () {

        var invoicePath = function (invoiceNumber) {
            return 'app/api/invoice/' + invoiceNumber;
        };

        function Invoice() {
        }

        return new Invoice();

    })();
});

eventModule.factory('Payment', function ($q, $http) {

    return (function () {

        var paymentPayPalPath = function () {
            return '/app/api/paypal/participant/payment/';
        };

        var paymentCustomerPayPalPath = function () {
            return '/app/api/paypal/customer/payment/';
        };

        var paymentBankTransferPath = function () {
            return '/app/api/banktransfer/participant/payment/';
        };

        var paymentCustomerBankTransferPath = function () {
            return '/app/api/banktransfer/customer/payment/';
        };

        var paymentFeaturePath = function() {
            return '/app/api/payment/feature/';
        };

        var cancellationPath = function() {
            return '/app/api/payment/cancellation/';
        };


        function Payment() {
        }

        Payment.prototype.initPaymentViaPayPal = function(id) {
            window.location = paymentPayPalPath() + 'init?id=' + id ;
        };
        Payment.prototype.initCustomerPaymentViaPayPal = function(id, event) {
            window.location = paymentCustomerPayPalPath() + 'init?id=' + id + '&event=' + event ;
        };
        Payment.prototype.initPaymentViaBankTransfer = function(id) {
            var paymentPromise = $q.defer();
            $http.get(paymentBankTransferPath() + 'init?id=' + id).then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };
        Payment.prototype.initCustomerPaymentViaBankTransfer = function(id) {
            var paymentPromise = $q.defer();
            $http.get(paymentCustomerBankTransferPath() + 'init?id=' + id).then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.completeCustomerPayment = function() {
            var paymentPromise = $q.defer();
            $http.get(paymentCustomerPayPalPath() + 'execute').then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.completeParticipantPayment = function() {
            var paymentPromise = $q.defer();
            $http.get(paymentPayPalPath() + 'execute').then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.updatePaymentFeature = function(id, data) {
            var paymentPromise = $q.defer();
            $http.put(paymentFeaturePath() + 'general/' + id, data).then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.updateBankTransferFeature = function(id, data) {
            var paymentPromise = $q.defer();
            $http.put(paymentFeaturePath() + 'bank_transfer/' + id, data).then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.updatePayPalFeature = function(id, data) {
            var paymentPromise = $q.defer();
            $http.put(paymentFeaturePath() + 'paypal/' + id, data).then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.testPayPalFeature = function(id) {
            var paymentPromise = $q.defer();
            $http.post(paymentFeaturePath() + 'paypal/' + id + '/test', {}).then(function(response) {
                paymentPromise.resolve(response.data);
            }, function(errors) {
                paymentPromise.reject(errors.data);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.updateCancellation = function (id, data) {
            var paymentPromise = $q.defer();
            $http.put(cancellationPath() + id, data).success(function (eventFeature) {
                paymentPromise.resolve(eventFeature);
            }).error(function (errors) {
                paymentPromise.reject(errors);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.addCancellation = function (eventFeatureId) {
            var paymentPromise = $q.defer();
            $http.post(paymentFeaturePath() + 'cancellation/' + eventFeatureId, {}).success(function (eventFeature) {
                paymentPromise.resolve(eventFeature);
            }).error(function (errors) {
                paymentPromise.reject(errors);
            });
            return paymentPromise.promise;
        };

        Payment.prototype.removeCancellation = function (id) {
            var paymentPromise = $q.defer();
            $http.delete(cancellationPath() + id).success(function (eventFeature) {
                paymentPromise.resolve(eventFeature);
            }).error(function (errors) {
                paymentPromise.reject(errors);
            });
            return paymentPromise.promise;
        };

        return new Payment();

    })();
});

eventModule.controller('BankTransferFeatureController', function($scope, Event, EventFeature, Payment, ErrorPropagationService) {

    $scope.event = Event.data;

    $scope.bankTransferFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'BANK_TRANSFER' && eventFeature.eventFeatureCategory === 'PAYMENT';
    });

    $scope.updateBankTransferFeature = function() {
        ErrorPropagationService.resetErrors();
        $scope.editationSuccessful = false;
        Payment.updateBankTransferFeature($scope.bankTransferFeature.id, $scope.bankTransferFeature).then(
            function(eventFeature) {
                $scope.editationSuccessful = true;
            },
            function(errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            });
    };
});

eventModule.controller('PayPalFeatureController', function($scope, Event, EventFeature, Payment, ErrorPropagationService) {

    $scope.event = Event.data;

    $scope.paypalFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'PAYPAL' && eventFeature.eventFeatureCategory === 'PAYMENT';
    });

    $scope.markFormDirty = function() {
        $scope.formDirty = true;
    };

    $scope.updatePayPalFeature = function() {
        ErrorPropagationService.resetErrors();
        $scope.editationSuccessful = false;
        $scope.testDone = false;
        Payment.updatePayPalFeature($scope.paypalFeature.id, $scope.paypalFeature).then(
            function(eventFeature) {
                $scope.paypalFeature = eventFeature;
                $scope.formDirty = false;
                $scope.editationSuccessful = true;
            },
            function(errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            });
    };

    $scope.testPayPalFeature = function() {
        if(!$scope.formDirty) {
            $scope.testDone = false;
            $scope.editationSuccessful = false;
            Payment.testPayPalFeature($scope.paypalFeature.id).then(
                function(eventFeature) {
                    $scope.testDone = true;
                    $scope.paypalFeature = eventFeature;
                    $scope.testSuccessful = eventFeature.tested;
                },
                function(errors) {
                    ErrorPropagationService.propagateErrorResponse(errors);
                });
        }
    };

    $scope.removeEventFeature = function(eventFeatureId) {
        EventFeature.removeEventFeature(eventFeatureId).then(function(eventFeatureDeletion) {
            eventFeatureDeletion(Event.data.eventFeatures);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});

eventModule.controller('CancellationController', function($scope, Event, EventFeature, Payment, ErrorPropagationService) {

    $scope.event = Event.data;
    $scope.cancellationFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'CANCELLATION' && eventFeature.eventFeatureCategory === 'PAYMENT';
    });

    $scope.paymentFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'PAYMENT_GENERAL';
    });

    $scope.updateCancellation = function(cancellation) {
        ErrorPropagationService.resetErrors();
        Payment.updateCancellation(cancellation.id, cancellation).then(function(eventFeature) {
            $scope.cancellationFeature = eventFeature;
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.updatePaymentFeature = function(paymentFeature) {
        ErrorPropagationService.resetErrors();
        Payment.updatePaymentFeature(paymentFeature.id, paymentFeature).then(function(eventFeature) {},
            function(errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.addCancellation = function() {
        Payment.addCancellation($scope.cancellationFeature.id).then(function(eventFeature) {
            $scope.cancellationFeature = eventFeature;
        });
    };

    $scope.removeCancellation = function(cancellation) {
        Payment.removeCancellation(cancellation.id).then(function(eventFeature) {
            $scope.cancellationFeature = eventFeature;
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.removeEventFeature = function(eventFeatureId) {
        EventFeature.removeEventFeature(eventFeatureId).then(function(eventFeatureDeletion) {
            eventFeatureDeletion(Event.data.eventFeatures);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});

eventModule.controller('PaymentController', function ($scope, $state, $stateParams, EventRegistrationReminder, Event, Authorization,
                                                      ErrorPropagationService, Transaction, Payment, Person, Country) {
    $scope.person = Person;
    $scope.event = Event.data;
    $scope.countries = Country.data;
    $scope.states = Person.stateStore();
    $scope.transactions = Transaction.activeTransactions;
    $scope.bankTransferInProgress = false;
    $scope.paypalInProgress = false;

    $scope.invoiceAddressCheckModal = false;
    $scope.registerAnotherParticipantModal = false;
    $scope.differentInvoiceAddress = false;

    $scope.paypalActivated = function() {
        var paypalFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
            return eventFeature.eventFeatureType === 'PAYPAL';
        });

        if(paypalFeature) {
            return paypalFeature.tested;
        } else {
            return false;
        }
    };

    $scope.enableDifferentInvoiceAddress = function() {
        $scope.differentInvoiceAddress = true;
    };

    $scope.disableDifferentInvoiceAddress = function() {
        $scope.differentInvoiceAddress = false;
    };

    $scope.invoiceCheck = function(transaction, callback) {
        $scope.paymentToProceed = callback;
        $scope.transactionToProceed = transaction;
    };
    $scope.proceedPayment = function() {
        $scope.paymentToProceed($scope.transactionToProceed);
    };

    $scope.cancelModal = function() {
        $scope.invoiceAddressCheckModal = false;
        $scope.differentInvoiceAddress = false;
        $scope.paypalInProgress = false;
        $scope.bankTransferInProgress = false;
    };

    $scope.checkForAnotherRegistration = function() {
        if(EventRegistrationReminder.isReminded($scope.event.identifier)) {
            $scope.registerAnotherParticipantModal = true;
        }
    };

    $scope.registerAnotherParticipant = function() {
        Authorization.logout(
            function () {
                $state.go('event.registration');
            }
        );
    };

    $scope.cancelRegistrationModal = function() {
        $scope.registerAnotherParticipantModal = false;
    };

    $scope.saveInvoiceAddress = function(callback) {
        Person.edit($scope.person).then(function() {
            if(callback) callback();
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.initPaymentViaPayPal = function(transaction) {
        if(!$scope.paypalInProgress) {
            $scope.invoiceAddressCheckModal = true;
            $scope.invoiceCheck(transaction, function (transaction) {
                $scope.invoiceAddressCheckModal = false;
                $scope.paypalInProgress = true;
                Payment.initPaymentViaPayPal(transaction.id);
            });
        }
    };

    $scope.paypalInProgressGuard = function() {
        return !$scope.paypalInProgress;
    };
    $scope.bankTransferInProgressGuard = function() {
        return !$scope.bankTransferInProgress;
    };

    $scope.initPaymentViaBankTransfer = function(transaction) {
        if(!$scope.bankTransferInProgress) {
            $scope.invoiceAddressCheckModal = true;
            $scope.invoiceCheck(transaction, function () {
                $scope.invoiceAddressCheckModal = false;
                $scope.bankTransferInProgress = true;
                Payment.initPaymentViaBankTransfer(transaction.id).then(function () {
                    $scope.bankTransferInProgress = false;
                    transaction.paymentMethod = 'BANK_TRANSFER';
                    transaction.transactionStatus = 'FIXED';
                }, function (errors) {
                    $scope.bankTransferInProgress = false;
                    ErrorPropagationService.propagateErrorResponse(errors);
                });
            });
        }
    };
});

eventModule.controller('PaymentExecuteController', function ($scope, $state, $stateParams, ErrorPropagationService, Transaction, Payment) {
    $scope.transaction = Transaction.transactionToExecute;
    $scope.paymentInProgress = false;

    $scope.paymentInProgressGuard = function() {
        return !$scope.paymentInProgress;
    };

    $scope.completePayment = function() {
        if(!$scope.paymentInProgress) {
            $scope.paymentInProgress = true;
            Payment.completeParticipantPayment().then(function() {
                $scope.paymentInProgress = false;
                $state.go('event.intern.payment');
            }, function(errors) {
                alert("Bezahlung fehlgeschlagen: " + errors);
                $scope.paymentInProgress = false;
            })
        }
    };
});


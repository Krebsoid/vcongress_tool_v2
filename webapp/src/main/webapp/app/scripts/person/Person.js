'use strict';

var personModule = angular.module('PersonModule', [], function () {
});

personModule.factory('Person', function ($q, $http, User) {

    return (function () {
        var initialLoaded = false;
        var admin = false;

        var registrationPath = 'app/api/registration';
        var editPath = 'app/api/person/edit';
        var statePath = 'app/api/person';
        var registerObserver = [];

        function Person() {
            User.registerLogoutHandler(this, function () {
                this.reset();
            });
            User.registerLoginHandler(this, function () {
                this.requestState(true);
            });
        }

        Person.prototype.titleStore = function() {
            return [
                '', 'PhD', 'MD', 'PD', 'Dr.', 'Prof.', 'PD Dr.', 'Prof. Dr.'
            ];
        };

        Person.prototype.stateStore = function() {
            return [
                'Baden-Württemberg',
                'Bayern',
                'Berlin',
                'Brandenburg',
                'Bremen',
                'Hamburg',
                'Hessen',
                'Mecklenburg-Vorpommern',
                'Niedersachsen',
                'Nordrhein-Westfalen',
                'Rheinland-Pfalz',
                'Saarland',
                'Sachsen',
                'Sachsen-Anhalt',
                'Schleswig-Holstein',
                'Thüringen'
            ];
        };

        Person.prototype.reset = function() {
            initialLoaded = false;
        };

        Person.prototype.register = function (data) {
            var registerPromise = $q.defer();
            var self = this;

            $http.post(registrationPath, data).success(function (person) {
                angular.copy(person, self);
                self.email = person.user.name;
                angular.forEach(registerObserver, function (observerObject) {
                    var observer = observerObject.observer;
                    observerObject.handler.call(observer, self);
                });
                registerPromise.resolve(person);
                User.requestState(true);
            }).error(function (errors) {
                registerPromise.reject(errors);
            });

            return registerPromise.promise;
        };

        Person.prototype.edit = function (data) {
            var editPromise = $q.defer();
            var self = this;
            var path = admin ? editPath + '/' + self.id : editPath;
            $http.post(path, data).success(function (person) {
                angular.copy(person, self);
                self.email = person.user.name;
                editPromise.resolve(person);
            }).error(function (errors) {
                editPromise.reject(errors);
            });

            return editPromise.promise;
        };

        Person.prototype.editInvoiceAddress = function (data) {
            var editPromise = $q.defer();
            var self = this;
            var path = admin ? editPath + '/' + self.id : editPath;
            $http.post(path + "/invoice-address", data).success(function (person) {
                angular.copy(person, self);
                self.email = person.user.name;
                editPromise.resolve(person);
            }).error(function (errors) {
                editPromise.reject(errors);
            });

            return editPromise.promise;
        };

        Person.prototype.editAdmin = function (data) {
            var editPromise = $q.defer();
            var self = this;
            var path = editPath + '/' + self.id + '/admin';
            $http.post(path, data).success(function (person) {
                angular.copy(person, self);
                self.email = person.user.name;
                editPromise.resolve(person);
            }).error(function (errors) {
                editPromise.reject(errors);
            });

            return editPromise.promise;
        };

        Person.prototype.get = function (id) {
            var self = this;
            var personPromise = $q.defer();
            $http.get(statePath + '/' + id)
                .success(function (person) {
                    angular.copy(person, self);
                    self.email = person.user.name;
                    personPromise.resolve(self);
                    admin = true;
                })
                .error(function () {
                    personPromise.reject(self);
                    admin = true;
                });

            return personPromise.promise;
        };

        Person.prototype.requestState = function (forced) {
            var self = this;
            var personPromise = $q.defer();
            if(User.isLoggedIn) {
                if (forced || !initialLoaded) {
                    $http.get(statePath)
                        .success(function (person) {
                            angular.copy(person, self);
                            self.email = person.user.name;
                            personPromise.resolve(self);
                            initialLoaded = true;
                            admin = false;
                        })
                        .error(function () {
                            personPromise.reject(self);
                            initialLoaded = true;
                            admin = false;
                        });
                }
                else {
                    personPromise.resolve(self);
                }
            } else {
                personPromise.reject(self);
            }

            return personPromise.promise;
        };

        Person.prototype.registerRegistrationHandler = function (observer, onRegistrationHandler) {
            registerObserver.push({
                observer: observer,
                handler: onRegistrationHandler
            });
        };

        return new Person();

    })();
});


personModule.controller('RegistrationController', function ($scope, $state, Person, Country, ErrorPropagationService) {
    $scope.backlink = $state.current.data.backlink;
    $scope.countries = Country.data;
    $scope.titles = Person.titleStore();
    $scope.states = Person.stateStore();
    $scope.checkGender = function(gender) {
        return $scope.data.gender === gender ? 'selected' : '';
    };
    $scope.data = Person;


    $scope.isDifferentInvoiceAddress = function() {
        var fullName = $scope.data.title ? $scope.data.title + ' ' : '';
        fullName = fullName + $scope.data.firstName + ' ' + $scope.data.lastName;
        return $scope.data.invoiceAddress.fullName == fullName &&
            $scope.data.invoiceAddress.institute == $scope.data.occupation.institute &&
            $scope.data.invoiceAddress.street == $scope.data.address.street &&
            $scope.data.invoiceAddress.zipCode == $scope.data.address.zipCode &&
            $scope.data.invoiceAddress.city == $scope.data.address.city &&
            $scope.data.invoiceAddress.state == $scope.data.address.state &&
            $scope.data.invoiceAddress.country.id == $scope.data.address.country.id;
    };
    $scope.differentInvoiceAddress = !$scope.isDifferentInvoiceAddress();

    $scope.requestInProgress = false;
    $scope.register = function () {
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            ErrorPropagationService.resetErrors();
            Person.register($scope.person).then(
                function () {
                    $scope.registrationSuccessful = true;
                    $scope.person = {};
                    $scope.requestInProgress = false;
                },
                function (errors) {
                    $scope.registrationSuccessful = false;
                    $scope.requestInProgress = false;
                    ErrorPropagationService.propagateErrorResponse(errors);
                }
            );
        }
    };


    $scope.edit = function () {
        ErrorPropagationService.resetErrors();
        if(!$scope.differentInvoiceAddress) {
            var fullName = $scope.data.title ? $scope.data.title + ' ' : '';
            fullName = fullName + $scope.data.firstName + ' ' + $scope.data.lastName;
            $scope.data.invoiceAddress.fullName = fullName;
            $scope.data.invoiceAddress.institute = $scope.data.occupation.institute;
            $scope.data.invoiceAddress.street = $scope.data.address.street;
            $scope.data.invoiceAddress.zipCode = $scope.data.address.zipCode;
            $scope.data.invoiceAddress.city = $scope.data.address.city;
            $scope.data.invoiceAddress.state = $scope.data.address.state;
            $scope.data.invoiceAddress.country = $scope.data.address.country;
        }
        $scope.data.contact.phone = $('#phone').intlTelInput('getNumber');
        $scope.data.contact.fax = $('#fax').intlTelInput('getNumber');
        Person.edit($scope.data).then(
            function () {
                $scope.editationSuccessful = true;
                $('#phone').intlTelInput('setNumber', $scope.data.contact.phone);
                $('#fax').intlTelInput('setNumber', $scope.data.contact.fax);
            },
            function (errors) {
                $scope.editationSuccessful = false;
                ErrorPropagationService.propagateErrorResponse(errors);
            }
        );
    };

    $scope.enableDifferentInvoiceAddress = function() {
        $scope.differentInvoiceAddress = true;
        $scope.disabledDifferentInvoiceAddress = false;
    };
    $scope.disableDifferentInvoiceAddress = function() {
        $scope.differentInvoiceAddress = false;
        $scope.disabledDifferentInvoiceAddress = true;
    };
});

personModule.controller('AdminEditController', function ($scope, $state, Person, Country, ErrorPropagationService) {
    $scope.backlink = $state.current.data.backlink;
    $scope.countries = Country.data;
    $scope.titles = Person.titleStore();
    $scope.states = Person.stateStore();
    $scope.checkGender = function(gender) {
        return $scope.data.gender === gender ? 'selected' : '';
    };
    $scope.data = Person;

    $scope.edit = function () {
        ErrorPropagationService.resetErrors();
        Person.editAdmin($scope.data).then(
            function () {
                $scope.editationSuccessful = true;
            },
            function (errors) {
                $scope.editationSuccessful = false;
                ErrorPropagationService.propagateErrorResponse(errors);
            }
        );
    };
});

personModule.controller('AdminRegistrationController', function ($scope, $state, Person, Country, ErrorPropagationService) {
    $scope.backlink = $state.current.data.backlink;

    $scope.requestInProgress = false;
    $scope.register = function () {
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            ErrorPropagationService.resetErrors();
            Person.register($scope.person).then(
                function () {
                    $scope.registrationSuccessful = true;
                    $scope.person = {};
                    $scope.requestInProgress = false;
                },
                function (errors) {
                    $scope.registrationSuccessful = false;
                    $scope.requestInProgress = false;
                    ErrorPropagationService.propagateErrorResponse(errors);
                }
            );
        }
    };
});


personModule.factory('Country', function ($q, $http) {

    return (function () {
        var countryPath = 'app/api/country';

        function Country() {
            var data;
        }

        Country.prototype.getAll = function () {
            var countryPromise = $q.defer();
            var self = this;
            $http.get(countryPath).success(function (countries) {
                self.data = _.sortBy(countries, function(country) {
                    return country.name;
                });
                countryPromise.resolve(countries);
            }).error(function (errors) {
                countryPromise.reject(errors);
            });

            return countryPromise.promise;
        };

        return new Country();

    })();
});

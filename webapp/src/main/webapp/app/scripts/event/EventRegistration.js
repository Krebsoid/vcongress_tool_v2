'use strict';

eventModule.factory('EventRegistrationReminder', function(localStorageService) {
    return (function () {
        function EventRegistrationReminder() {

        }
        EventRegistrationReminder.prototype.remindRegistrationData = function (identifier, person, disclaimer) {
            person.firstName = '';
            person.lastName = '';
            person.gender = '';
            person.title = '';
            person.email = '';
            person.emailRetype = '';
            person.password = '';
            person.passwordRetype = '';
            var data = {
                person : person,
                disclaimer : disclaimer
            };
            localStorageService.set(identifier + '_registration_reminder', data);
        };

        EventRegistrationReminder.prototype.forgetRegistrationData = function (identifier) {
            localStorageService.remove(identifier + '_registration_reminder');
        };

        EventRegistrationReminder.prototype.getRegistrationData = function(identifier) {
            if(!!localStorageService.get(identifier + '_registration_reminder')) {
                return localStorageService.get(identifier + '_registration_reminder');
            }
        };

        EventRegistrationReminder.prototype.isReminded = function(identifier) {
            return !!localStorageService.get(identifier + '_registration_reminder');
        };

        return new EventRegistrationReminder();

    })();
});

eventModule.factory('EventRegistration', function ($q, $http) {

    return (function () {
        var eventPath = 'app/api/event/registration';

        function EventRegistration() {
        }

        EventRegistration.prototype.register = function (data) {
            var eventPromise = $q.defer();
            $http.post(eventPath, data).success(function (person) {
                eventPromise.resolve(person);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        EventRegistration.prototype.registerShort = function (data) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/short', data).success(function (person) {
                eventPromise.resolve(person);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        return new EventRegistration();

    })();
});

eventModule.controller('RegistrationDeadlineController', function($scope, Event) {
    $scope.event = Event.data;
    $scope.isRegistrationDeadline = function () {
        $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
            return eventFeature.eventFeatureType == 'REGISTRATION' && eventFeature.eventFeatureCategory == 'DEADLINE';
        });
        return $scope.eventFeature ? $scope.eventFeature.deadlineExpired : false;
    }
});

eventModule.controller('EventRegistrationController', function ($scope, $state, $stateParams, $filter, Person, EventRegistrationReminder,
                                                                Event, EventRegistration, Country, ErrorPropagationService) {
    $scope.person = {
        country: 'DE',
        invoiceCountry: 'DE'
    };
    $scope.titles = Person.titleStore();
    $scope.states = Person.stateStore();
    $scope.checkbox = {disclaimer1: false, disclaimer2: false, disclaimer3: false, disclaimer4: false};
    $scope.event = Event.data;
    $scope.disclaimer1 = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'DISCLAIMER' && eventFeature.index == 1
    });
    if($scope.disclaimer1.mandatory) {
        $scope.disclaimer1.content = $scope.disclaimer1.content + '*';
    }
    if(!$scope.event.shortVersion) {
        $scope.disclaimer2 = _.find($scope.event.eventFeatures, function(eventFeature) {
            return eventFeature.eventFeatureType === 'DISCLAIMER' && eventFeature.index == 2
        });
        if($scope.disclaimer2.mandatory) {
            $scope.disclaimer2.content = $scope.disclaimer2.content + '*';
        }
    }
    $scope.disclaimer3 = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'DISCLAIMER' && eventFeature.index == 3
    });
    if($scope.disclaimer3.mandatory) {
        $scope.disclaimer3.content = $scope.disclaimer3.content + '*';
    }
    $scope.disclaimer4 = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'DISCLAIMER' && eventFeature.index == 4
    });
    if($scope.disclaimer4.mandatory) {
        $scope.disclaimer4.content = $scope.disclaimer4.content + '*';
    }
    $scope.disclaimer5 = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'DISCLAIMER' && eventFeature.index == 5
    });
    $scope.disclaimer5Visible = $scope.disclaimer5.content != '' && $scope.disclaimer5.content != null;
    if($scope.disclaimer5Visible && $scope.disclaimer5.mandatory) {
        $scope.disclaimer5.content = $scope.disclaimer5.content + '*';
    }

    $scope.person.group = $scope.event.identifier;
    $scope.countries = Country.data;

    $scope.errorsInForm = false;
    $scope.requestInProgress = false;

    $scope.preventPaste = function($event) {
        alert($filter('localized')('participant.registration.security'));
        $event.preventDefault();
    };

    if(EventRegistrationReminder.isReminded($scope.event.identifier)) {
        $scope.person = EventRegistrationReminder.getRegistrationData($scope.event.identifier).person;
        $scope.checkbox = EventRegistrationReminder.getRegistrationData($scope.event.identifier).disclaimer;
        $scope.differentInvoiceAddress = true;
        $scope.disabledDifferentInvoiceAddress = false;
        $scope.groupRegistration = true;
    }

    $scope.registerAnotherParticipantInfoModal = false;
    $scope.closeGroupRegistrationInfo = function() {
        $scope.registerAnotherParticipantInfoModal = false;
    };
    $scope.openGroupRegistrationInfo = function() {
        $scope.registerAnotherParticipantInfoModal = true;
    };

    $scope.registerShort = function() {
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            ErrorPropagationService.resetErrors();
            if (!$scope.checkbox.disclaimer1) {
                ErrorPropagationService.manualError("disclaimer1", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else if (!$scope.checkbox.disclaimer3) {
                ErrorPropagationService.manualError("disclaimer3", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else if ($scope.disclaimer5Visible && !$scope.checkbox.disclaimer5) {
                ErrorPropagationService.manualError("disclaimer5", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else {
                $scope.person.notificationRequest = $scope.checkbox.disclaimer4;
                EventRegistration.registerShort($scope.person).then(
                    function () {
                        if($scope.groupRegistration) {
                            EventRegistrationReminder.remindRegistrationData($scope.event.identifier, $scope.person, $scope.checkbox);
                        } else {
                            EventRegistrationReminder.forgetRegistrationData($scope.event.identifier);
                        }
                        $scope.person = {
                            country: 'DE',
                            group: $scope.event.identifier
                        };
                        $scope.errorsInForm = false;
                        $scope.requestInProgress = false;
                        $state.go('event.registered-successfully', {event: $scope.event.identifier});
                    },
                    function (errors) {
                        $scope.errorsInForm = true;
                        $scope.requestInProgress = false;
                        ErrorPropagationService.propagateErrorResponse(errors);
                    }
                );
            }
        }
    };

    $scope.register = function () {
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            ErrorPropagationService.resetErrors();
            if (!$scope.checkbox.disclaimer1) {
                ErrorPropagationService.manualError("disclaimer1", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else if (!$scope.checkbox.disclaimer2) {
                ErrorPropagationService.manualError("disclaimer2", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else if (!$scope.checkbox.disclaimer3) {
                ErrorPropagationService.manualError("disclaimer3", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else if ($scope.disclaimer5Visible && !$scope.checkbox.disclaimer5) {
                ErrorPropagationService.manualError("disclaimer5", "Bitte stimmen Sie den Bestimmungen zu.");
                $scope.requestInProgress = false;
            } else {
                if (!$scope.differentInvoiceAddress) {
                    var fullName = $scope.person.title ? $scope.person.title + ' ' : '';
                    fullName = fullName + $scope.person.firstName + ' ' + $scope.person.lastName;
                    $scope.person.invoiceFullName = fullName;
                    $scope.person.invoiceInstitute = $scope.person.institute;
                    $scope.person.invoiceStreet = $scope.person.street;
                    $scope.person.invoiceZipCode = $scope.person.zipCode;
                    $scope.person.invoiceCity = $scope.person.city;
                    $scope.person.invoiceState = $scope.person.state;
                    $scope.person.invoiceCountry = $scope.person.country;
                }
                $scope.person.notificationRequest = $scope.checkbox.disclaimer4;
                $scope.person.phone = $('#phone').intlTelInput('getNumber');
                $scope.person.fax = $('#fax').intlTelInput('getNumber');
                EventRegistration.register($scope.person).then(
                    function () {
                        if($scope.groupRegistration) {
                            EventRegistrationReminder.remindRegistrationData($scope.event.identifier, $scope.person, $scope.checkbox);
                        } else {
                            EventRegistrationReminder.forgetRegistrationData($scope.event.identifier);
                        }

                        $scope.person = {
                            country: 'DE',
                            group: $scope.event.identifier
                        };
                        $scope.errorsInForm = false;
                        $scope.requestInProgress = false;
                        $state.go('event.intern.participation', {event: $scope.event.identifier});
                    },
                    function (errors) {
                        $scope.errorsInForm = true;
                        $scope.requestInProgress = false;
                        ErrorPropagationService.propagateErrorResponse(errors);
                    }
                );
            }
        }
    };

    $scope.checkGender = function(gender) {
        return $scope.person.gender === gender ? 'selected' : '';
    };
    $scope.enableGroupRegistration = function() {
        $scope.groupRegistration = true;
        $scope.openGroupRegistrationInfo();
    };
    $scope.disableGroupRegistration = function() {
        $scope.groupRegistration = false;
    };
    $scope.enableDifferentInvoiceAddress = function() {
        $scope.differentInvoiceAddress = true;
        $scope.disabledDifferentInvoiceAddress = false;
        $scope.person.invoiceFullName = '';
        $scope.person.invoiceInstitute = '';
        $scope.person.invoiceStreet = '';
        $scope.person.invoiceZipCode = '';
        $scope.person.invoiceCity = '';
        $scope.person.invoiceState = '';
        $scope.person.invoiceCountry = 'DE';
    };
    $scope.disableDifferentInvoiceAddress = function() {
        $scope.differentInvoiceAddress = false;
        $scope.disabledDifferentInvoiceAddress = true;
    }
});
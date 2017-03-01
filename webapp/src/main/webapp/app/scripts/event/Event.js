'use strict';

var eventModule = angular.module('EventModule', [], function () {
});

eventModule.factory('Event', function ($q, $http, $filter, FileUploader) {

    return (function () {

        var eventPath = 'app/api/event';
        var activeEvent;
        var eventList;

        function Event() {
            var data;
            var activeEvent;
            var eventListAsSummary;
            this.selectedModules = ['REGISTRATION'];
            this.selectedLicense = undefined;
        }

        Event.prototype.getAllAsSummary = function (forced) {
            var eventPromise = $q.defer();
            var self = this;
            $http.get(eventPath+'/summary').success(function (events) {
                self.eventListAsSummary = events;
                eventList = events;
                eventPromise.resolve(eventList);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.getAll = function (forced) {
            var eventPromise = $q.defer();
            $http.get(eventPath).success(function (events) {
                eventList = events;
                eventPromise.resolve(eventList);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.getNotification = function(area) {
            var result = '';
            _.forEach(this.data.eventFeatures, function(eventFeature) {
                if(eventFeature.eventFeatureCategory == 'TEXT' && eventFeature.eventFeatureType == area ) {
                    result = eventFeature.notification;
                }
            });
            return result;
        };

        Event.prototype.initModuleSelection = function() {
            this.selectedModules = ['REGISTRATION'];
        };

        Event.prototype.selectModule = function(module) {
            if(!this.isModuleSelected(module)) {
                this.selectedModules.push(module)
            } else {
                _.remove(this.selectedModules, function(m) {
                    return m == module;
                });
            }
        };

        Event.prototype.isModuleSelected = function(module) {
            return angular.isDefined(_.find(this.selectedModules, function(m) {
                return m == module;
            }));
        };

        Event.prototype.isModuleSelectedForEvent = function(event, module) {
            return angular.isDefined(_.find(event.eventModules, function(m) {
                return m == module;
            }));
        };

        Event.prototype.getEventFeature = function(id) {
            var result = '';
            _.forEach(this.data.eventFeatures, function(eventFeature) {
                if(eventFeature.id == id ) {
                    result = eventFeature;
                }
            });
            return result;
        };


        Event.prototype.logoUploader = function(id, prepare, success, error) {
            return new FileUploader({
                url : 'app/api/event/' + id + '/logo',
                queueLimit: 1,
                onBeforeUploadItem : function() {
                    prepare();
                },
                onErrorItem : function(file, errors) {
                    error(errors);
                },
                onSuccessItem : function(file, article) {
                    success(article);
                }
            });
        };

        Event.prototype.pdfUploader = function(id, prepare, success, error) {
            return new FileUploader({
                url : 'app/api/event/' + id + '/license',
                queueLimit: 1,
                onBeforeUploadItem : function() {
                    prepare();
                },
                onErrorItem : function(file, errors) {
                    error(errors);
                },
                onSuccessItem : function(file, article) {
                    success(article);
                }
            });
        };

        Event.prototype.removeEventLogo = function (id) {
            var removeEventLogoPromise = $q.defer();
            $http.delete('app/api/event/' + id + '/logo').success(function (article) {
                removeEventLogoPromise.resolve(article);
            }).error(function (errors) {
                removeEventLogoPromise.reject(errors);
            });

            return removeEventLogoPromise.promise;
        };

        Event.prototype.checkForPublic = function (identifier) {
            var eventPromise = $q.defer();
            var self = this;
            $http.get(eventPath + "/" + identifier).success(function (event) {
                    if (event.eventStatus === 'TESTMODE' || event.eventStatus === 'PUBLISHED' || event.eventStatus === 'CLOSED') {
                        self.activeEvent = event;
                        self.data = event;
                        transformParticipantStatus(self.data);
                        eventPromise.resolve(event);
                    } else {
                        eventPromise.reject();
                    }
                }
            ).error(function (errors) {
                eventPromise.reject(errors);
            });

            return eventPromise.promise;
        };

        Event.prototype.get = function (identifier, checkForPublic) {
            var eventPromise = $q.defer();
            var self = this;

            if (identifier !== '') {
                $http.get(eventPath + "/" + identifier).success(function (event) {
                    self.activeEvent = event;
                    self.data = event;
                    self.selectedModules = event.eventModules;
                    transformParticipantStatus(self.data);
                    eventPromise.resolve(event);
                }).error(function (errors) {
                    eventPromise.reject(errors);
                });
            } else {
                eventPromise.resolve(self.activeEvent);
            }

            return eventPromise.promise;
        };

        function transformParticipantStatus(event) {
            _.forEach(event.eventFeatures, function(eventFeature) {
                if(eventFeature.eventFeatureType == 'PARTICIPANT_STATUS') {
                    _.forEach(eventFeature.status, function(status) {
                        if(angular.isDefined(_.find(status.eventFlags, function(flag) {
                                return flag.name === 'EARLY_BIRD';
                            }))) {
                            status.name = status.name + ' (' + $filter('localized')('participant.participation.earlyBird') + ')'
                        }
                    });
                }
            });
        }

        Event.prototype.isShort = function() {
            return !!this.data.shortVersion;
        };

        Event.prototype.getParticipants = function () {
            var eventPromise = $q.defer();
            var self = this;
            $http.get(eventPath + '/' + self.activeEvent.identifier + '/participants').success(function (participants) {
                eventPromise.resolve(participants);
                self.data.participants = participants;
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.create = function (data) {
            var eventPromise = $q.defer();
            data.identifier = data.identifier.replace(/\s/gi, '-').toLowerCase();
            data.eventModules = this.selectedModules;
            $http.post(eventPath, data).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.createShort = function (data) {
            var eventPromise = $q.defer();
            data.identifier = data.identifier.replace(/\s/gi, '-').toLowerCase();
            data.eventModules = this.eventModules;
            $http.post(eventPath + '/short', data).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.edit = function (data) {
            var eventPromise = $q.defer();
            data.identifier = data.identifier.replace(/\s/gi, '-').toLowerCase();
            $http.put(eventPath + '/' + data.identifier, data).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.editShort = function (data) {
            var eventPromise = $q.defer();
            data.identifier = data.identifier.replace(/\s/gi, '-').toLowerCase();
            $http.put(eventPath + '/short/' + data.identifier, data).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.remove = function (data) {
            var eventPromise = $q.defer();
            data.identifier = data.identifier.replace(/\s/gi, '-').toLowerCase();
            $http.delete(eventPath + '/' + data.identifier).success(function () {
                eventPromise.resolve();
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.activateTestMode = function (identifier) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/test/on', {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.deactivateTestMode = function (identifier) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/test/off', {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.selectLicense = function(license) {
            this.selectedLicense = license;
        };
        Event.prototype.isLicenseSelected = function(license) {
            return this.selectedLicense == license;
        };
        Event.prototype.saveLicense = function (identifier, license) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/license/' + license, {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.publish = function (identifier) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/publish', {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.archive = function (data) {
            var eventPromise = $q.defer();
            data.identifier = data.identifier.replace(/\s/gi, '-').toLowerCase();
            $http.post(eventPath + '/' + data.identifier + '/archive', {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.close = function (identifier) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/close', {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.reopen = function (identifier) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/reopen', {}).success(function (event) {
                eventPromise.resolve(event);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.addEventFeature = function (identifier, data) {
            var eventPromise = $q.defer();
            $http.post(eventPath + '/' + identifier + '/eventfeature', {featureType: data}).success(function (eventFeature) {
                var modifier = function(status) {
                    return function(list) {
                        list.push(status);
                    }
                };
                eventPromise.resolve(modifier(eventFeature));
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        Event.prototype.hasEventFeature = function(type, category) {
            return !!_.find(this.data.eventFeatures, function(eventFeature) {
                return eventFeature.eventFeatureType == type && eventFeature.eventFeatureCategory == category;
            });
        };

        return new Event();

    })();
});

eventModule.directive('restrictedEvent', function (User, Event) {
    return {
        restrict: 'A',
        scope: true,
        link: function (scope, element, attrs) {
            scope.roles = [];
            if (attrs.restrictedEvent) {
                scope.roles = attrs.restrictedEvent.split(',');
            }
            scope.$watch(
                function () {
                    return User.isLoggedIn;
                },
                function () {
                    if (!User.hasPermission(Event.activeEvent.identifier, scope.roles)) {
                        element.hide();
                    }
                    else {
                        element.show();
                    }
                }
            );
        }
    };
});

eventModule.directive('eventInfo', function() {
    return {
        scope: {
            event: '=eventInfo'
        },
        template: '<div data-ng-include="\'./partials/components/event/event-info.html\'"></div>'
    };
});

eventModule.controller('AdministrationDashboardController', function ($scope, Event, User, ErrorPropagationService) {
    $scope.events = Event.eventListAsSummary;
    $scope.eventService = Event;
    $scope.user = User;

    $scope.hasPermission = function(group, role) {
        return User.hasPermission(group, role) || User.hasPermission('system', 'ADMIN') || User.hasPermission('system', 'ORGANISATION');
    };

    $scope.removeEvent = function(event) {
        confirm('Veranstaltung wirklich löschen?') ? Event.remove(event).then(function() {
            event.deleted = true;
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        }) : "";
    };

    $scope.archiveEvent = function(event) {
        confirm('Veranstaltung wirklich archivieren?') ? Event.archive(event).then(function() {
            event.deleted = true;
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        }) : "";
    };
});

eventModule.controller('ExternalEventController', function ($scope, Event) {
    $scope.event = Event.data;
    $scope.$watch(function() {
        return Event.data;
    }, function(data) {
        $scope.event = data;
    })
});

eventModule.controller('EventStateController', function ($scope, Event) {
    $scope.event = Event.data;
});

eventModule.controller('EventModuleController', function($scope, Event) {
    $scope.event = Event;
});

eventModule.controller('EventLicenseController', function($scope, $state, Event) {
    $scope.event = Event;

    $scope.saveLicense = function() {
        Event.saveLicense($scope.event.data.identifier, Event.selectedLicense).then(function() {
            $state.go('administration.event.payment', {event: $scope.event.data.identifier});
        });
    }
});

eventModule.controller('EventInfoController', function ($scope, $rootScope, $stateParams, Event, EventFeature) {
    $scope.eventService = Event;
    $scope.event = Event.data;

    $scope.addEventFeature = function(featureType) {
        Event.addEventFeature($stateParams.event, featureType).then(function(eventFeature) {
            eventFeature($scope.event.eventFeatures);
        });
    };

    $scope.removeEventFeature = function(eventFeatureId) {
        EventFeature.removeEventFeature(eventFeatureId).then(function(eventFeature) {
            eventFeature($scope.event.eventFeatures);
        });
    };

    $scope.generalInfo = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType == 'PARTICIPANT_STATUS';
    });
});


eventModule.controller('EventCreationController', function ($scope, $state, $stateParams, Event, User, ErrorPropagationService) {
    $scope.event = {
        identifier: "",
        name: ""
    };

    $scope.datepicker = {
        startDate: false,
        endDate: false,
        registrationEnd: false
    };
    $scope.dates = {};
    if($stateParams.event) {
        $scope.event = Event.data;
        $scope.dates.startDate = $scope.event.startDate ? new Date($scope.event.startDate) : '';
        $scope.dates.endDate = $scope.event.endDate ? new Date($scope.event.endDate) : '';
        $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
            return eventFeature.eventFeatureType == 'REGISTRATION' && eventFeature.eventFeatureCategory == 'DEADLINE';
        });
        $scope.dates.registrationEnd = $scope.eventFeature && $scope.eventFeature.deadline ? new Date($scope.eventFeature.deadline) : '';
    }
    $scope.editMode = !!$scope.event.identifier;

    $scope.hasPermission = function(group, role) {
        return User.hasPermission(group, role) || User.hasPermission('system', 'ADMIN') || User.hasPermission('system', 'ORGANISATION');
    };

    $scope.openStartDate = function() {
        $scope.datepicker.startDate = true;
    };
    $scope.openEndDate = function() {
        $scope.datepicker.endDate = true;
    };
    $scope.openRegistrationEnd = function() {
        $scope.datepicker.registrationEnd = true;
    };

    $scope.link = function (identifier) {
        var replacedIdentifier = identifier.replace(/\s/gi, '-').toLowerCase();
        return 'http://cms.vcongress.de/' + replacedIdentifier;
    };

    $scope.requestInProgress = false;
    $scope.create = function () {
        ErrorPropagationService.resetErrors();
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            if ($scope.dates.startDate) {
                $scope.event.startDate = $scope.dates.startDate.getFullYear() + '-' + ($scope.dates.startDate.getMonth() + 1) + '-' + $scope.dates.startDate.getDate();
            }
            if ($scope.dates.endDate) {
                $scope.event.endDate = $scope.dates.endDate.getFullYear() + '-' + ($scope.dates.endDate.getMonth() + 1) + '-' + $scope.dates.endDate.getDate();
            }
            if ($scope.dates.registrationEnd) {
                $scope.event.registrationEnd = $scope.dates.registrationEnd.getFullYear() + '-' + ($scope.dates.registrationEnd.getMonth() + 1) + '-' + $scope.dates.registrationEnd.getDate();
            }
            Event.create($scope.event).then(
                function (event) {
                    User.requestState(true).then(function() {
                        $state.go('administration.event-creation.disclaimer', {event: event.identifier});
                        $scope.requestInProgress = false;
                    });
                },
                function (errors) {
                    ErrorPropagationService.propagateErrorResponse(errors);
                    $scope.requestInProgress = false;
                }
            );
        }
    };

    $scope.createShort = function () {
        ErrorPropagationService.resetErrors();
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            if ($scope.dates.startDate) {
                $scope.event.startDate = $scope.dates.startDate.getFullYear() + '-' + ($scope.dates.startDate.getMonth() + 1) + '-' + $scope.dates.startDate.getDate();
            }
            if ($scope.dates.endDate) {
                $scope.event.endDate = $scope.dates.endDate.getFullYear() + '-' + ($scope.dates.endDate.getMonth() + 1) + '-' + $scope.dates.endDate.getDate();
            }
            if ($scope.dates.registrationEnd) {
                $scope.event.registrationEnd = $scope.dates.registrationEnd.getFullYear() + '-' + ($scope.dates.registrationEnd.getMonth() + 1) + '-' + $scope.dates.registrationEnd.getDate();
            }
            Event.createShort($scope.event).then(
                function (event) {
                    $state.go('administration.short-event-creation.disclaimer', {event: event.identifier});
                    $scope.requestInProgress = false;
                },
                function (errors) {
                    ErrorPropagationService.propagateErrorResponse(errors);
                    $scope.requestInProgress = false;
                }
            );
        }
    };

    $scope.edit = function (forward) {
        ErrorPropagationService.resetErrors();
        if($scope.dates.startDate) {
            $scope.event.startDate = $scope.dates.startDate.getFullYear() + '-' + ($scope.dates.startDate.getMonth() + 1) +  '-' + $scope.dates.startDate.getDate();
        }
        if($scope.dates.endDate) {
            $scope.event.endDate = $scope.dates.endDate.getFullYear() + '-' + ($scope.dates.endDate.getMonth() + 1) +  '-' + $scope.dates.endDate.getDate();
        }
        if ($scope.dates.registrationEnd) {
            $scope.event.registrationEnd = $scope.dates.registrationEnd.getFullYear() + '-' + ($scope.dates.registrationEnd.getMonth() + 1) + '-' + $scope.dates.registrationEnd.getDate();
        }
        Event.edit($scope.event).then(
            function (event) {
                if(!forward) {
                    $state.go('administration.event-creation.disclaimer', { event : event.identifier });
                } else {
                    $scope.editationSuccessful = true;
                }
            },
            function (errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            }
        );
    };

    $scope.editShort = function (forward) {
        ErrorPropagationService.resetErrors();
        if($scope.dates.startDate) {
            $scope.event.startDate = $scope.dates.startDate.getFullYear() + '-' + ($scope.dates.startDate.getMonth() + 1) +  '-' + $scope.dates.startDate.getDate();
        }
        if($scope.dates.endDate) {
            $scope.event.endDate = $scope.dates.endDate.getFullYear() + '-' + ($scope.dates.endDate.getMonth() + 1) +  '-' + $scope.dates.endDate.getDate();
        }
        if ($scope.dates.registrationEnd) {
            $scope.event.registrationEnd = $scope.dates.registrationEnd.getFullYear() + '-' + ($scope.dates.registrationEnd.getMonth() + 1) + '-' + $scope.dates.registrationEnd.getDate();
        }
        Event.editShort($scope.event).then(
            function (event) {
                if(!forward) {
                    $state.go('administration.short-event-creation.disclaimer', { event : event.identifier });
                } else {
                    $scope.editationSuccessful = true;
                }
            },
            function (errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            }
        );
    };
});

eventModule.controller('EventController', function ($scope, $state, $stateParams, Event, EventFeature, User, ErrorPropagationService) {
    $scope.eventService = Event;
    $scope.event = Event.data;
    $scope.user = User;
    $scope.activateTestMode = function () {
        Event.activateTestMode($stateParams.event).then(function (event) {
            $scope.event = event;
        });
    };
    $scope.deactivateTestMode = function () {
        Event.deactivateTestMode($stateParams.event).then(function (event) {
            $scope.event = event;
        });
    };
    $scope.publish = function (forward) {
        Event.publish($stateParams.event).then(function (event) {
            $scope.event = event;
            if(forward) {
                $state.go('administration.event-creation.finished');
            }
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.goToLicense = function() {
        if($scope.user.activated) {
            $state.go('administration.event.license', {event: $scope.event.identifier});
        }
    };
    $scope.revoke = function () {
        Event.revoke($stateParams.event).then(function (event) {
            $scope.event = event;
        });
    };
    $scope.close = function () {
        Event.close($stateParams.event).then(function (event) {
            $scope.event = event;
        });
    };
    $scope.reopen = function () {
        Event.reopen($stateParams.event).then(function (event) {
            $scope.event = event;
        });
    };


    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType == 'INDEX' && eventFeature.eventFeatureCategory == 'TEXT';
    });
    $scope.updateEventFeature = function() {
        ErrorPropagationService.resetErrors();
        if(!!$scope.eventFeature) {
            $scope.eventFeature.label = $scope.eventFeature.notification;
            EventFeature.updateEventFeature($scope.eventFeature.id, $scope.eventFeature).then(function(eventFeature) {

            }, function(errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            });
        }
    };
});


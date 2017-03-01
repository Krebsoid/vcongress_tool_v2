'use strict';


eventModule.factory('ParticipantStatus', function ($q, $http, $filter, Tools) {

    return (function () {

        var participationStatusPath = function (eventFeatureId, participantStatusId) {
            return 'app/api/eventfeature/' + eventFeatureId + '/participantstatus/' + (participantStatusId || '');
        };


        function ParticipantStatus() {
        }

        ParticipantStatus.prototype.getStatusList = function (eventFeatureId) {
            var statusPromise = $q.defer();
            $http.get(participationStatusPath(eventFeatureId)).success(function (statusList) {
                statusPromise.resolve(statusList);
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        ParticipantStatus.prototype.addStatus = function (eventFeatureId, data) {
            var statusPromise = $q.defer();
            data.cost = Tools.formatCurrency(data.cost);
            $http.post(participationStatusPath(eventFeatureId), data).success(function (status) {
                var modifier = function(status) {
                    return function(list) {
                        if(angular.isDefined(_.find(status.eventFlags, function(flag) {
                                return flag.name === 'EARLY_BIRD';
                            }))) {
                            status.name = status.name + ' (' + $filter('localized')('participant.participation.earlyBird') + ')';
                        }
                        list.push(status);
                    }
                };
                statusPromise.resolve(modifier(status));
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        ParticipantStatus.prototype.editStatus = function (eventFeatureId, id, data) {
            var statusPromise = $q.defer();
            data.cost = Tools.formatCurrency(data.cost);
            $http.put(participationStatusPath(eventFeatureId, id), data).success(function (status) {
                var modifier = function(status) {
                    return function(list) {
                        var index = _.findIndex(list, function (s) {
                            return s.id === status.id;
                        });
                        if(angular.isDefined(_.find(status.eventFlags, function(flag) {
                            return flag.name === 'EARLY_BIRD';
                        }))) {
                            status.name = status.name + ' (' + $filter('localized')('participant.participation.earlyBird') + ')';
                        }
                        list[index] = status;
                    }
                };
                statusPromise.resolve(modifier(status));
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        ParticipantStatus.prototype.removeStatus = function (eventFeatureId, id) {
            var statusPromise = $q.defer();
            $http.delete(participationStatusPath(eventFeatureId, id)).success(function (status) {
                var modifier = function(status) {
                    return function(list) {
                        _.remove(list, function (s) {
                            return s.id === status.id
                        });
                    }
                };
                statusPromise.resolve(modifier(status));
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        return new ParticipantStatus();

    })();
});


eventModule.controller('ParticipantStatusSelectionController', function ($scope, $stateParams, Event, $filter,
                                                                         ParticipantStatus, Participation, ErrorPropagationService) {
    $scope.statusList = [];
    $scope.event = Event.data;
    $scope.eventSelection = Participation.data($scope.eventFeatureId);
    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.id == $scope.eventFeatureId;
    });
    $scope.participation = {
        eventId: $scope.event.id,
        status: $scope.eventSelection.participantStatus ? $scope.eventSelection.participantStatus.id : undefined
    };
    angular.copy($scope.eventFeature.status, $scope.statusList);
    $scope.statusList = _.filter($scope.statusList, function(status) {
        return status.dateValid || checkForStatus(status);
    });

    $scope.disabled = function(status) {
        return !status.countValid && !checkForStatus(status);
    };
    function checkForStatus(status) {
        if($scope.eventSelection.participantStatus) {
            return $scope.eventSelection.participantStatus.id == status.id;
        } else {
            return false;
        }
    }
    $scope.isSelected = function(status) {
        return $scope.participation.status == status.id;
    };
    $scope.isBookedOut = function(status) {
        return !status.countValid && $scope.participation.status != status.id;
    };
    $scope.conditionalTooltip = function(status) {
        if($scope.isBookedOut(status)) {
            return $filter('localized')('participant.participation.bookedUp');
        } else {
            return '';
        }
    };

    $scope.fixedTooltip = function() {
        return $filter('localized')('participant.participation.fixed');
    };

    _.forEach($scope.statusList, function(status) {
        status.disabled = $scope.disabled(status);
        if(!status.countValid) {
            status.name = status.name + ' ('+ $filter('localized')('participant.participation.bookedUp')+')';
        }
    });
    $scope.countValid = _.some($scope.statusList, function(status) {
        return !status.countValid;
    });

    $scope.inProgress = false;
    $scope.saveParticipation = function (status) {
        if(!$scope.inProgress) {
            $scope.inProgress = true;
            if(!$scope.isBookedOut(status) && !$scope.eventSelection.fixed) {
                Participation.saveStatus(status.id, $scope.eventFeatureId).then(function(participation) {
                    Participation.ownData = participation;
                    $scope.eventSelection = Participation.data($scope.eventFeatureId);
                    $scope.participation.status = $scope.eventSelection.participantStatus ? $scope.eventSelection.participantStatus.id : undefined;
                    $scope.inProgress = false;
                }, function(errors) {
                    $scope.inProgress = false;
                    ErrorPropagationService.propagateErrorResponse(errors);
                });
            }
        }
    };
});


eventModule.directive('participantStatusCreation', function() {
    return {
        scope: {
            eventFeatureId: '@participantStatusCreation',
            initialOpen: '@initialOpen'
        },
        template: '<div data-ng-include="\'./partials/content/admin-area/event/event-editation/participant-status-creation.html\'"></div>'
    };
});

eventModule.directive('participantStatusSelection', function() {
    return {
        scope: {
            eventFeatureId: '@participantStatusSelection',
            participant: '@'
        },
        template: '<div data-ng-include="\'./partials/content/event/participation/participant-status.html\'"></div>'
    };
});

eventModule.directive('selectionSelection', function() {
    return {
        scope: {
            eventFeatureId: '@selectionSelection',
            participant: '@'
        },
        template: '<div data-ng-include="\'./partials/content/event/participation/selection.html\'"></div>'
    };
});

eventModule.directive('selectionCreation', function() {
    return {
        scope: {
            eventFeatureId: '@selectionCreation',
            initialOpen: '@initialOpen'
        },
        template: '<div data-ng-include="\'./partials/content/admin-area/event/event-editation/selection-creation.html\'"></div>'
    };
});


eventModule.controller('ParticipantStatusController', function ($scope, $rootScope, $stateParams, $filter, Event, EventFeature, ParticipantStatus, ErrorPropagationService) {
    $scope.eventService = Event;
    $scope.deleted = false;
    $scope.status = {name: ''};
    $scope.options = {};
    $scope.visible = false;

    $rootScope.$on('close-yourself', function() {
        $scope.visible = false;
    });

    $scope.event = Event.data;
    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.id == $scope.eventFeatureId;
    });
    $scope.statusList = $scope.eventFeature.status;

    $scope.activateTax = function() {
        $scope.tax = true;
        $scope.disableTax = false;
    };
    $scope.deactivateTax = function() {
        $scope.tax = false;
        $scope.disableTax = true;
        $scope.status.tax = undefined;
    };

    $scope.activateLimit = function() {
        $scope.limit = true;
        $scope.disableLimit = false;
    };
    $scope.deactivateLimit = function() {
        $scope.limit = false;
        $scope.disableLimit = true;
        $scope.status.limit = undefined;
    };

    $scope.addStatus = function () {
        ErrorPropagationService.resetErrors();
        ParticipantStatus.addStatus($scope.eventFeatureId, $scope.status).then(function (statusList) {
            $scope.status = {name: ''};
            statusList($scope.statusList);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.removeStatus = function (status) {
        ParticipantStatus.removeStatus($scope.eventFeatureId, status.id).then(function (statusList) {
            statusList($scope.statusList);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.editStatus = function () {
        ErrorPropagationService.resetErrors();
        ParticipantStatus.editStatus($scope.eventFeatureId, $scope.status.id, $scope.status).then(function (statusList) {
            $scope.editMode = false;
            $scope.status.name = '';
            $scope.status.cost = undefined;
            $scope.status.limit = undefined;
            statusList($scope.statusList);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.startEditStatus = function (status) {
        angular.copy(status, $scope.status);
        $scope.status.name = $scope.status.name.replace(' (' + $filter('localized')('participant.participation.earlyBird') + ')', '');
        $scope.status.earlyBird = angular.isDefined(_.find(status.eventFlags, function(flag) {
            return flag.name === 'EARLY_BIRD';
        }));
        $scope.options.earlyBird = !!(status.startDateEarlyBird && status.endDateEarlyBird);
        $scope.disableEarlyBird = !status.startDateEarlyBird && !status.endDateEarlyBird;
        if($scope.options.earlyBird) {
            $scope.dates.startDate = new Date(status.startDateEarlyBird);
            $scope.dates.endDate = new Date(status.endDateEarlyBird);
        }
        $scope.limit = !!status.limit;
        $scope.disableLimit = !(!!status.limit);
        $scope.tax = !!status.tax;
        $scope.disableTax = !(!!status.tax);
        $scope.editMode = true;
    };
    $scope.exitEditStatus = function () {
        $scope.status.name = '';
        $scope.status.earlyBird = false;
        $scope.status.cost = undefined;
        $scope.status.limit = undefined;
        $scope.editMode = false;
        $scope.newItem = false;
    };

    $scope.removeEventFeature = function(eventFeatureId) {
        EventFeature.removeEventFeature(eventFeatureId).then(function() {
            $scope.deleted = true;
        });
    };

    $scope.updateEventFeature = function(eventFeatureId, data) {
        ErrorPropagationService.resetErrors();
        EventFeature.updateParticipationEventFeature(eventFeatureId, data).then(function(eventFeature) {

        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});


eventModule.controller('ParticipantStatusCreationController', function ($scope, $rootScope, $stateParams, $filter, Event, EventFeature, ParticipantStatus, ErrorPropagationService) {
    $scope.eventService = Event;
    $scope.deleted = false;
    $scope.status = {name: '', earlyBird: false, earlyBirdActive: false};
    $scope.options = {
        earlyBird : false
    };
    $scope.dates = {};
    $scope.visible = true;

    $scope.datepicker = {
        startDate: false,
        endDate: false
    };
    $scope.openStartDate = function() {
        $scope.datepicker.startDate = true;
    };
    $scope.openEndDate = function() {
        $scope.datepicker.endDate = true;
    };

    $scope.event = Event.data;
    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType == 'PARTICIPANT_STATUS';
    });
    $scope.statusList = $scope.eventFeature.status;

    $scope.activateTax = function() {
        $scope.tax = true;
        $scope.disableTax = false;
    };
    $scope.deactivateTax = function() {
        $scope.tax = false;
        $scope.disableTax = true;
        $scope.status.tax = undefined;
    };

    $scope.activateLimit = function() {
        $scope.limit = true;
        $scope.disableLimit = false;
    };
    $scope.deactivateLimit = function() {
        $scope.limit = false;
        $scope.disableLimit = true;
        $scope.status.limit = undefined;
    };

    $scope.addStatus = function () {
        ErrorPropagationService.resetErrors();
        $scope.status.earlyBirdActive = $scope.options.earlyBird;
        if($scope.options.earlyBird) {
            $scope.status.startDate = $scope.dates.startDate.getFullYear() + '-' + ($scope.dates.startDate.getMonth() + 1) +  '-' + $scope.dates.startDate.getDate();
            $scope.status.endDate = $scope.dates.endDate.getFullYear() + '-' + ($scope.dates.endDate.getMonth() + 1) +  '-' + $scope.dates.endDate.getDate();
        }
        ParticipantStatus.addStatus($scope.eventFeature.id, $scope.status).then(function (statusList) {
            $scope.status.name = '';
            $scope.status.earlyBird = false;
            statusList($scope.statusList);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.removeStatus = function (status) {
        ParticipantStatus.removeStatus($scope.eventFeature.id, status.id).then(function (statusList) {
            statusList($scope.statusList);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.editStatus = function () {
        ErrorPropagationService.resetErrors();
        $scope.status.earlyBirdActive = $scope.options.earlyBird;
        if($scope.options.earlyBird) {
            $scope.status.startDate = $scope.dates.startDate.getFullYear() + '-' + ($scope.dates.startDate.getMonth() + 1) +  '-' + $scope.dates.startDate.getDate();
            $scope.status.endDate = $scope.dates.endDate.getFullYear() + '-' + ($scope.dates.endDate.getMonth() + 1) +  '-' + $scope.dates.endDate.getDate();
        }
        ParticipantStatus.editStatus($scope.eventFeature.id, $scope.status.id, $scope.status).then(function (statusList) {
            $scope.editMode = false;
            $scope.status.name = '';
            $scope.status.earlyBird = false;
            statusList($scope.statusList);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.startEditStatus = function (status) {
        angular.copy(status, $scope.status);
        $scope.status.name = $scope.status.name.replace(' (' + $filter('localized')('participant.participation.earlyBird') + ')', '');
        $scope.status.earlyBird = angular.isDefined(_.find(status.eventFlags, function(flag) {
            return flag.name === 'EARLY_BIRD';
        }));
        $scope.options.earlyBird = !!(status.startDateEarlyBird && status.endDateEarlyBird);
        $scope.disableEarlyBird = !status.startDateEarlyBird && !status.endDateEarlyBird;
        if($scope.options.earlyBird) {
            $scope.dates.startDate = new Date(status.startDateEarlyBird);
            $scope.dates.endDate = new Date(status.endDateEarlyBird);
        }
        $scope.limit = !!status.limit;
        $scope.disableLimit = !(!!status.limit);
        $scope.tax = !!status.tax;
        $scope.disableTax = !(!!status.tax);
        $scope.editMode = true;
    };
    $scope.exitEditStatus = function () {
        $scope.status.name = '';
        $scope.status.earlyBird = false;
        $scope.status.tax = undefined;
        $scope.status.cost = undefined;
        $scope.editMode = false;
        $scope.newItem = false;
    };

    $scope.removeEventFeature = function(eventFeatureId) {
        EventFeature.removeEventFeature($stateParams.event, eventFeatureId).then(function() {
            $scope.deleted = true;
        });
    };

    $scope.updateEventFeature = function() {
        ErrorPropagationService.resetErrors();
        EventFeature.updateParticipationEventFeature($scope.eventFeature.id, $scope.eventFeature).then(function(eventFeature) {

        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});

'use strict';

eventModule.factory('Dinner', function ($q, $http, Tools) {

    return (function () {

        var dinnerPath = function (eventFeatureId) {
            return 'app/api/eventfeature/' + eventFeatureId + '/dinner';
        };


        function Dinner() {
        }

        Dinner.prototype.getEventItem = function (eventFeatureId) {
            var statusPromise = $q.defer();
            $http.get(dinnerPath(eventFeatureId)).success(function (eventItem) {
                statusPromise.resolve(eventItem);
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        Dinner.prototype.editStatus = function (eventFeatureId, data) {
            var statusPromise = $q.defer();
            data.cost = Tools.formatCurrency(data.cost);
            $http.put(dinnerPath(eventFeatureId), data).success(function (status) {
                var modifier = function(status) {
                    return function(list) {
                        var index = _.findIndex(list, function (s) {
                            return s.id === eventFeatureId;
                        });
                        list[index].eventItem = status;
                    }
                };
                statusPromise.resolve(modifier(status));
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        return new Dinner();

    })();
});

eventModule.factory('DinnerCombo', function ($q, $http, Tools) {

    return (function () {

        var dinnerPath = function (eventFeatureId, dinnerId) {
            return 'app/api/eventfeature/' + eventFeatureId + '/dinnercombo/' + (dinnerId|| '');
        };

        function DinnerCombo() {
        }

        DinnerCombo.prototype.createDinner = function (eventFeatureId, data) {
            var statusPromise = $q.defer();
            data.cost = Tools.formatCurrency(data.cost);
            $http.post(dinnerPath(eventFeatureId), data).success(function (dinnercombo) {
                statusPromise.resolve(dinnercombo);
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        DinnerCombo.prototype.removeDinner = function (eventFeatureId, dinnerId) {
            var statusPromise = $q.defer();
            $http.delete(dinnerPath(eventFeatureId, dinnerId)).success(function (dinnercombo) {
                statusPromise.resolve(dinnercombo);
            }).error(function (errors) {
                statusPromise.reject(errors);
            });
            return statusPromise.promise;
        };

        return new DinnerCombo();

    })();
});

eventModule.controller('DinnerSelectionController', function ($scope, $stateParams, Event, Participation, ErrorPropagationService) {
    $scope.event = Event.data;
    $scope.eventSelection = Participation.data($scope.eventFeatureId);
    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.id == $scope.eventFeatureId;
    });
    $scope.participation = {
        eventId: $scope.event.id,
        checked: $scope.eventSelection.checked
    };

    $scope.checkDisabled = function() {
        return !$scope.eventFeature.eventItem.countValid && !$scope.eventSelection.checked;
    };

    $scope.inProgress = false;
    $scope.saveParticipation = function (eventFeatureId) {
        if(!$scope.inProgress) {
            $scope.inProgress = true;
            Participation.saveDinner(!$scope.participation.checked, eventFeatureId).then(function(participation) {
                Participation.ownData = participation;
                $scope.eventSelection = Participation.data($scope.eventFeatureId);
                $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
                    return eventFeature.id == $scope.eventFeatureId;
                });
                $scope.participation = {
                    eventId: $scope.event.id,
                    checked: $scope.eventSelection.checked
                };
                $scope.inProgress = false;
            }, function(errors) {
                $scope.inProgress = false;
                ErrorPropagationService.propagateErrorResponse(errors);
            });
        }
    };
});

eventModule.controller('DinnerComboSelectionController', function ($scope, $stateParams, $filter, Event, Participation, Dinner, ErrorPropagationService) {
    $scope.event = Event.data;
    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.id == $scope.eventFeatureId;
    });
    $scope.initialBookedOutAndChecked = {};

    $scope.getEventSelection = function(eventFeatureId) {
        return Participation.data(eventFeatureId);
    };

    _.forEach($scope.eventFeature.dinnerFeatures, function(dinner) {
        if(!dinner.eventItem.countValid) {
            dinner.eventItem.name = dinner.eventItem.name + ' ('+ $filter('localized')('participant.participation.bookedUp')+')';
        }
    });

    $scope.isBookedOut = function(dinner) {
        var selection = Participation.data(dinner.id);
        return !dinner.eventItem.countValid && !selection.checked;
    };
    $scope.isFixed = function(dinner) {
        return $scope.getEventSelection(dinner.id).fixed;
    };
    $scope.isChecked = function(dinner) {
        return $scope.getEventSelection(dinner.id).checked;
    };
    $scope.conditionalTooltip = function(dinner) {
        if($scope.isFixed(dinner)) {
            return $filter('localized')('participant.participation.fixed');
        } else if($scope.isBookedOut(dinner)) {
            return $filter('localized')('participant.participation.bookedUp');
        } else {
            return '';
        }
    };

    $scope.countValid = _.some($scope.eventFeature.dinnerFeatures, function(dinner) {
        return !dinner.eventItem.countValid;
    });

    $scope.inProgress = false;
    $scope.saveParticipation = function (dinner) {
        if(!$scope.inProgress) {
            $scope.inProgress = true;
            var selection = Participation.data(dinner.id);
            if(!$scope.isBookedOut(dinner) && !selection.fixed) {
                Participation.saveDinner(!selection.checked, dinner.id).then(function(participation) {
                    $scope.inProgress = false;
                    Participation.ownData = participation;
                }, function(errors) {
                    $scope.inProgress = false;
                    ErrorPropagationService.propagateErrorResponse(errors);
                });
            }
        }
    };
});


eventModule.directive('dinnerCreation', function() {
    return {
        scope: {
            eventFeatureId: '@dinnerCreation',
            initialOpen: '@initialOpen'
        },
        template: '<div data-ng-include="\'./partials/content/admin-area/event/event-editation/dinner-creation.html\'"></div>'
    };
});

eventModule.directive('dinnerSelection', function() {
    return {
        scope: {
            eventFeatureId: '@dinnerSelection',
            participant: '@'
        },
        template: '<div data-ng-include="\'./partials/content/event/participation/dinner.html\'"></div>'
    };
});
eventModule.directive('dinnerComboSelection', function() {
    return {
        scope: {
            eventFeatureId: '@dinnerComboSelection',
            participant: '@'
        },
        template: '<div data-ng-include="\'./partials/content/event/participation/dinner-combo.html\'"></div>'
    };
});


eventModule.controller('DinnerController', function ($scope, $stateParams, Event, EventFeature, Dinner, DinnerCombo, ErrorPropagationService) {
    $scope.eventService = Event;
    $scope.deleted = false;
    $scope.status = {name: ''};

    $scope.event = Event.data;
    $scope.eventFeature = _.find($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.id == $scope.eventFeatureId;
    });
    $scope.eventItem = $scope.eventFeature.eventItem;
    angular.copy($scope.eventItem, $scope.status);

    $scope.editEventItem = function () {
        ErrorPropagationService.resetErrors();
        Dinner.editEventItem($scope.eventFeatureId, $scope.status).then(function (eventItem) {
            $scope.eventItem = eventItem;
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.addStatus = function () {
        ErrorPropagationService.resetErrors();
        DinnerCombo.createDinner($scope.eventFeatureId, $scope.status).then(function(eventFeature) {
            $scope.status.name = '';
            $scope.status.description = '';
            $scope.status.cost = undefined;
            $scope.status.limit = undefined;
            $scope.eventFeature.dinnerFeatures = eventFeature.dinnerFeatures;
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.activateTax = function() {
        $scope.tax = true;
        $scope.disableTax = false;
    };
    $scope.deactivateTax = function() {
        $scope.tax = false;
        $scope.disableTax = true;
        $scope.status.tax = 0;
    };

    $scope.activateLimit = function() {
        $scope.limit = true;
        $scope.disableLimit = false;
    };
    $scope.deactivateLimit = function() {
        $scope.limit = false;
        $scope.disableLimit = true;
        $scope.status.limit = 0;
    };

    $scope.editStatus = function () {
        ErrorPropagationService.resetErrors();
        Dinner.editStatus($scope.dinnerFeatureId, $scope.status).then(function (statusList) {
            $scope.editMode = false;
            $scope.status.name = '';
            $scope.status.description = '';
            $scope.status.cost = undefined;
            $scope.status.limit = undefined;
            statusList($scope.eventFeature.dinnerFeatures);
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
    $scope.startEditStatus = function (dinnerFeature) {
        angular.copy(dinnerFeature.eventItem, $scope.status);
        $scope.dinnerFeatureId = dinnerFeature.id;
        $scope.limit = !!dinnerFeature.eventItem.limit;
        $scope.disableLimit = !(!!dinnerFeature.eventItem.limit);
        $scope.tax = !!dinnerFeature.eventItem.tax;
        $scope.disableTax = !(!!dinnerFeature.eventItem.tax);
        $scope.editMode = true;
    };
    $scope.exitEditStatus = function () {
        $scope.status.name = '';
        $scope.status.description = '';
        $scope.status.cost = undefined;
        $scope.status.limit = undefined;
        $scope.editMode = false;
        $scope.newItem = false;
    };

    $scope.removeEventFeature = function(eventFeatureId) {
        EventFeature.removeEventFeature(eventFeatureId).then(function() {
            $scope.deleted = true;
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.removeDinner = function(eventFeatureId) {
        DinnerCombo.removeDinner($scope.eventFeatureId, eventFeatureId).then(function(eventFeature) {
            $scope.eventFeature.dinnerFeatures = eventFeature.dinnerFeatures;
        }, function (errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
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

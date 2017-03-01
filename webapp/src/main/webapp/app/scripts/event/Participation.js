'use strict';

eventModule.factory('Participation', function ($q, $http) {

    return (function () {
        var participationPath = 'app/api/participation';

        var data;
        var admin = false;

        function Participation() {
            var ownData;
        }

        Participation.prototype.get = function (id) {
            var participationPromise = $q.defer();
            var path = id ? participationPath + '/' + id : participationPath;
            var self = this;
            $http.get(path).success(function (participation) {
                data = participation;
                self.ownData = participation;
                participationPromise.resolve(participation);
                admin = !!id;
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };

        Participation.prototype.saveStatus = function (eventItemId, eventFeatureId) {
            var participationPromise = $q.defer();
            var path = admin ? participationPath + '/' + data.id : participationPath;
            var self = this;
            $http.post(path + "/status", {eventItemId: eventItemId, eventFeatureId: eventFeatureId}).success(function (participation) {
                data = participation;
                self.ownData = participation;
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };

        Participation.prototype.saveDinner = function (checked, eventFeatureId) {
            var participationPromise = $q.defer();
            var path = admin ? participationPath + '/' + data.id : participationPath;
            var self = this;
            $http.post(path + "/dinner", {checked: checked, eventFeatureId: eventFeatureId}).success(function (participation) {
                data = participation;
                self.ownData = participation;
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };

        Participation.prototype.enableParticipation = function (id) {
            var participationPromise = $q.defer();
            var path = participationPath + '/' + id;
            $http.post(path + "/enable", {}).success(function (participation) {
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };
        Participation.prototype.cancelParticipation = function (id) {
            var participationPromise = $q.defer();
            var path = participationPath + '/' + id;
            $http.post(path + "/cancel", {}).success(function (participation) {
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };
        Participation.prototype.updateNote = function (id, note) {
            var participationPromise = $q.defer();
            var path = participationPath + '/' + id;
            $http.post(path + "/note", {note: note}).success(function (participation) {
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };

        Participation.prototype.calculateCost = function() {
            var cost = 0;
            var self = this;
            _.forEach(self.ownData.eventSelections, function(eventSelection) {
                if(eventSelection.eventFeature.eventFeatureType == 'PARTICIPANT_STATUS' ||
                    eventSelection.eventFeature.eventFeatureType == 'SELECTION') {
                    if(eventSelection.participantStatus && !!eventSelection.participantStatus.cost) {
                        cost += eventSelection.participantStatus.cost;
                    }
                }
                if(eventSelection.eventFeature.eventFeatureType == 'DINNER') {
                    if(eventSelection.checked && !!eventSelection.eventFeature.eventItem.cost) {
                        cost += eventSelection.eventFeature.eventItem.cost;
                    }
                }
            });
            return cost;
        };

        Participation.prototype.isComplete = function() {
            return this.ownData.complete;
        };

        Participation.prototype.saveWithCost = function (id) {
            var participationPromise = $q.defer();
            var path = participationPath + '/' + id;
            $http.post(path + "/save?method=withCost", {}).success(function (participation) {
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };
        Participation.prototype.saveWithoutCost = function (id) {
            var participationPromise = $q.defer();
            var path = participationPath + '/' + id;
            $http.post(path + "/save?method=withoutCost", {}).success(function (participation) {
                participationPromise.resolve(participation);
            }).error(function (errors) {
                participationPromise.reject(errors);
            });
            return participationPromise.promise;
        };

        Participation.prototype.data = function(eventFeatureId) {
            return _.find(data.eventSelections, function(eventSelection) {
                return eventSelection.eventFeature.id == eventFeatureId;
            });
        };

        return new Participation();

    })();
});

eventModule.controller('ParticipationFeeController', function ($scope, $stateParams, $state, Event, Participation) {
    $scope.event = Event.data;
    $scope.participant = Participation.ownData;

    $scope.onlyWithCost = function(eventSelections) {
        $scope.sumCost = 0;
        return _.filter(eventSelections, function(eventSelection) {
            if(eventSelection.eventFeature.eventFeatureType == 'PARTICIPANT_STATUS' ||
                eventSelection.eventFeature.eventFeatureType == 'SELECTION') {
                var eventItemsToPay = eventSelection.participantStatus && !!eventSelection.participantStatus.cost;
                var eventItemToShow = eventSelection.participantStatus;
                if(eventItemsToPay) {
                    $scope.sumCost += eventSelection.participantStatus.cost;
                }
                return eventItemToShow;
            }
            if(eventSelection.eventFeature.eventFeatureType == 'DINNER') {
                var eventItemsToPay = eventSelection.checked && !!eventSelection.eventFeature.eventItem.cost;
                var eventItemToShow = eventSelection.checked;
                if(eventItemsToPay) {
                    $scope.sumCost += eventSelection.eventFeature.eventItem.cost;
                }
                return eventItemToShow;
            }
        });
    };

    $scope.$watch(function() {
        return Participation.ownData;
    }, function(participation) {
        $scope.participant = participation;
    })
});

eventModule.controller('GroupRegistrationController', function($scope, $stateParams, $state,
                                                               EventRegistrationReminder, Authorization, Event) {
    $scope.event = Event.data;

    if(EventRegistrationReminder.isReminded($scope.event.identifier)) {
        $scope.registerAnotherParticipantModal = true;
    }

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
});

eventModule.controller('ParticipationInfoController', function ($scope, $stateParams, $state, EventRegistrationReminder, Authorization,
                                                                Event, Participation) {
    $scope.event = Event.data;
    $scope.participation = {
        group: $scope.event.identifier
    };
    $scope.participant = Participation.ownData;
    $scope.cost = function() {
        return Participation.calculateCost();
    };
    $scope.paid = function() {
        return $scope.participant.paid;
    };
    $scope.isRegistrationComplete = function() {
        return $scope.participant.complete;
    };


    if(EventRegistrationReminder.isReminded($scope.event.identifier)) {
        $scope.registerAnotherParticipantModal = true;
    }

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
});

eventModule.controller('ParticipationController', function ($scope, $stateParams, $state, Event, Participation) {
    $scope.backlink = $state.current.data.backlink;
    $scope.admin = !!$state.current.data.admin;
    $scope.event = Event.data;
    $scope.participation = {
        group: $scope.event.identifier
    };
    $scope.participant = Participation.ownData;
    $scope.cost = function() {
        return Participation.calculateCost();
    };

    $scope.isParticipationComplete = function() {
        return Participation.isComplete();
    };

    $scope.inProgress = false;
    $scope.saveWithCost = function() {
        if(!$scope.inProgress) {
            $scope.inProgress = true;
            Participation.saveWithCost($scope.participant.id).then(function() {
                $scope.inProgress = false;
                $state.go('event.intern.payment');
            }, function() {
                $scope.inProgress = false;
            });
        }
    };

    $scope.saveWithoutCost = function() {
        if(!$scope.inProgress) {
            $scope.inProgress = true;
            Participation.saveWithoutCost($scope.participant.id).then(function() {
                $scope.inProgress = false;
                $state.go('event.intern.dashboard');
            }, function() {
                $scope.inProgress = false;
            });
        }
    }
});

eventModule.controller('ParticipationContextController', function ParticipationContextController($scope, Participation) {
    $scope.enableParticipant = function() {
        Participation.enableParticipation($scope.participant.id).then(function(participant) {
            $scope.participant = participant;
        });
    };

    $scope.cancelParticipant = function() {
        Participation.cancelParticipation($scope.participant.id).then(function(participant) {
            $scope.participant = participant;
        });
    };
});

eventModule.directive('fixedSelectionOverlay', function() {
    return {
        scope: {
            eventSelection: '=fixedSelectionOverlay'
        },
        template: '<div data-ng-if="eventSelection.fixed" data-ng-include="\'./partials/components/event/participation/fixed-participation-selection.html\'"></div>'
    };
});
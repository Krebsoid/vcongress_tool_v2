'use strict';

eventModule.factory('Disclaimer', function ($q, $http) {

    return (function () {

        var disclaimerPath = function (eventFeatureId) {
            return 'app/api/eventfeature/' + eventFeatureId + '/disclaimer';
        };

        function Disclaimer() {
        }

        Disclaimer.prototype.createDisclaimer = function (eventFeatureId, data) {
            var disclaimerPromise = $q.defer();
            $http.put(disclaimerPath(eventFeatureId), data).success(function (eventFeature) {
                disclaimerPromise.resolve(eventFeature);
            }).error(function (errors) {
                disclaimerPromise.reject(errors);
            });
            return disclaimerPromise.promise;
        };

        Disclaimer.prototype.updateDisclaimer = function (eventFeatureId, data) {
            var disclaimerPromise = $q.defer();
            $http.put(disclaimerPath(eventFeatureId), data).success(function (eventFeature) {
                var modifier = function(eventFeature) {
                    return function(list) {
                        var index = _.findIndex(list, function (s) {
                            return s.id === eventFeature.id;
                        });
                        list[index] = eventFeature;
                    }
                };
                disclaimerPromise.resolve(modifier(eventFeature));
            }).error(function (errors) {
                disclaimerPromise.reject(errors);
            });
            return disclaimerPromise.promise;
        };

        return new Disclaimer();

    })();
});

eventModule.controller('DisclaimerController', function ($scope, $sce, Event, EventFeature, Disclaimer, ErrorPropagationService) {
    $scope.disclaimer = {content: '', mandatory: false};

    $scope.eventService = Event;
    $scope.event = Event.data;
    $scope.disclaimerList = _.filter($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureType === 'DISCLAIMER';
    });

    $scope.removeDisclaimer = function(eventFeature) {
        EventFeature.removeEventFeature(eventFeature.id).then(function() {
            $scope.deleted = true;
        });
    };

    $scope.startEditDisclaimer = function (disclaimer) {
        angular.copy(disclaimer, $scope.disclaimer);
        $scope.editMode = true;
    };
    $scope.exitEditDisclaimer = function () {
        $scope.disclaimer.content = '';
        $scope.disclaimer.mandatory = false;
        $scope.editMode = false;
    };

    $scope.trustyHtml = function(html) {
        return $sce.trustAsHtml(html);
    };

    $scope.updateDisclaimer = function() {
        ErrorPropagationService.resetErrors();
        Disclaimer.updateDisclaimer($scope.disclaimer.id, $scope.disclaimer).then(function(eventFeature) {
            $scope.exitEditDisclaimer();
            eventFeature($scope.disclaimerList);
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});

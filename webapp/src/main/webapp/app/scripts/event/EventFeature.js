'use strict';

eventModule.factory('EventFeature', function ($q, $http) {

    return (function () {

        var eventFeaturePath = 'app/api/eventfeature';

        function EventFeature() {
        }

        EventFeature.prototype.updateEventFeature = function (eventFeatureId, data) {
            var eventPromise = $q.defer();
            $http.put(eventFeaturePath + '/' + eventFeatureId, data).success(function (eventFeature) {
                eventPromise.resolve(eventFeature);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        EventFeature.prototype.updateParticipationEventFeature = function (eventFeatureId, data) {
            var eventPromise = $q.defer();
            $http.put(eventFeaturePath + '/participation/' + eventFeatureId, data).success(function (eventFeature) {
                eventPromise.resolve(eventFeature);
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        EventFeature.prototype.removeEventFeature = function (eventFeatureId) {
            var eventPromise = $q.defer();
            $http.delete(eventFeaturePath + '/' + eventFeatureId, {}).success(function (eventFeature) {
                var modifier = function(eventFeature) {
                    return function(list) {
                        _.remove(list, function (s) {
                            return s.id === eventFeature.id
                        });
                    }
                };
                eventPromise.resolve(modifier(eventFeature));
            }).error(function (errors) {
                eventPromise.reject(errors);
            });
            return eventPromise.promise;
        };

        return new EventFeature();

    })();
});

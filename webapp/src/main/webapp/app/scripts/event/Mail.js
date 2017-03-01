'use strict';

eventModule.factory('MailFeature', function ($q, $http) {

    return (function () {

        var mailFeaturePath = function (eventFeatureId) {
            return 'app/api/eventfeature/' + eventFeatureId + '/mail';
        };

        function MailFeature() {
        }

        MailFeature.prototype.createDisclaimer = function (eventFeatureId, data) {
            var mailFeaturePromise = $q.defer();
            $http.put(mailFeaturePath(eventFeatureId), data).success(function (eventFeature) {
                mailFeaturePromise.resolve(eventFeature);
            }).error(function (errors) {
                mailFeaturePromise.reject(errors);
            });
            return mailFeaturePromise.promise;
        };

        MailFeature.prototype.updateMailFeature = function (eventFeatureId, data) {
            var updateMailFeature = $q.defer();
            $http.put(mailFeaturePath(eventFeatureId), data).success(function (eventFeature) {
                var modifier = function(eventFeature) {
                    return function(list) {
                        var index = _.findIndex(list, function (s) {
                            return s.id === eventFeature.id;
                        });
                        list[index] = eventFeature;
                    }
                };
                updateMailFeature.resolve(modifier(eventFeature));
            }).error(function (errors) {
                updateMailFeature.reject(errors);
            });
            return updateMailFeature.promise;
        };

        MailFeature.prototype.testMailFeature = function(eventFeatureId, receiver) {
            var testMailFeature = $q.defer();
            $http.post(mailFeaturePath(eventFeatureId) + '/test', {receiver: receiver}).success(function () {
                testMailFeature.resolve();
            }).error(function (errors) {
                testMailFeature.reject(errors);
            });
            return testMailFeature.promise;
        };

        return new MailFeature();

    })();
});

eventModule.controller('MailController', function ($scope, $sce, User, Event, MailFeature, ErrorPropagationService) {
    $scope.mail = {content: '', applyGeneral: false};

    $scope.eventService = Event;
    $scope.event = Event.data;
    $scope.receiver = User.name;
    $scope.mailList = _.filter($scope.event.eventFeatures, function(eventFeature) {
        return eventFeature.eventFeatureCategory === 'MAIL';
    });

    $scope.startEditMail = function (mail) {
        angular.copy(mail, $scope.mail);
        $scope.editMode = true;
    };
    $scope.exitEditMail = function () {
        $scope.mail.content = '';
        $scope.mail.applyGeneral = false;
        $scope.editMode = false;
    };

    $scope.getFilter = function(mail) {
        if(mail.eventFeatureType == 'MAIL_PAYMENT_COMPLETE') {
            return $scope.eventService.isModuleSelected('ACCOUNT');
        } else if(mail.eventFeatureType == 'MAIL_CREDIT') {
            return $scope.eventService.isModuleSelected('ACCOUNT');
        } else if(mail.eventFeatureType == 'MAIL_PAYMENT') {
            return $scope.eventService.isModuleSelected('ACCOUNT');
        } else {
            return true;
        }
    };

    $scope.trustyHtml = function(html) {
        return $sce.trustAsHtml(html);
    };

    $scope.updateMail = function() {
        ErrorPropagationService.resetErrors();
        if($scope.mail.applyGeneral) {
            $scope.mail.content = '';
        }
        MailFeature.updateMailFeature($scope.mail.id, $scope.mail).then(function(eventFeature) {
            $scope.exitEditMail();
            eventFeature($scope.mailList);
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    $scope.testMail = function(eventFeatureId) {
        ErrorPropagationService.resetErrors();
        MailFeature.testMailFeature(eventFeatureId, $scope.receiver).then(function() {
            window.alert("Mail sent");
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});
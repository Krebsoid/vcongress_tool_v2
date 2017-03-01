'use strict';

var userModule = angular.module('PasswordChange', [], function () {
});

userModule.controller('PasswordChangeInitController', function ($scope, $state, $stateParams, PasswordChange, ErrorPropagationService) {
    $scope.backlink = $state.current.data.backlink;
    $scope.recover = {
        mail: "",
        group: $stateParams.event
    };
    $scope.requestInProgress = false;
    $scope.initPasswordChange = function (data) {
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            ErrorPropagationService.resetErrors();
            PasswordChange.init(data, function () {
                $scope.initSuccessful = true;
                $scope.requestInProgress = false;
            }, function (error) {
                ErrorPropagationService.propagateErrorResponse(error);
                $scope.requestInProgress = false;
            });
        }
    };
});

userModule.controller('PasswordChangeController', function ($scope, $state, $stateParams, PasswordChange, ErrorPropagationService) {
    $scope.backlink = $state.current.data.backlink;
    $scope.recover = {
        token: $stateParams.token,
        mail: $stateParams.mail,
        group: $stateParams.event
    };
    $scope.mail = $stateParams.mail;

    $scope.requestInProgress = false;
    $scope.changePassword = function (data) {
        ErrorPropagationService.resetErrors();
        if(!$scope.requestInProgress) {
            $scope.requestInProgress = true;
            PasswordChange.change(data, function () {
                $scope.changeSuccessful = true;
                $scope.recover = {};
                $scope.requestInProgress = false;
            }, function (errors) {
                $scope.requestInProgress = false;
                ErrorPropagationService.propagateErrorResponse(errors);
                angular.forEach(errors, function (error) {
                    if (error.key === "token") {
                        ErrorPropagationService.manualErrorAlert(error.value);
                    }
                });
            });
        }
    };
});

userModule.factory('PasswordChange', function ($q, $http) {

    var passwordChangePath = 'app/api/account/password/change';
    var passwordChangeInitPath = passwordChangePath + '/init';

    function PasswordChange() {

    }

    PasswordChange.prototype = {
        init: function (data, success, error) {
            $http.post(passwordChangeInitPath, data).success(success).error(function (response) {
                error(response);
            });
        },
        change: function (data, success, error) {
            $http.post(passwordChangePath, data).success(success).error(function (response) {
                error(response);
            });
        }
    };

    return new PasswordChange();
});

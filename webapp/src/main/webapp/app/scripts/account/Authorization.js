'use strict';

var loginModule = angular.module('AuthorizationModule', ['UserModule', 'PasswordChange'], function () {
});

loginModule.controller('RemoteEditingController', function ($scope, Authorization, ErrorPropagationService) {
    $scope.retrieveLink = function () {
        ErrorPropagationService.resetErrors();
        Authorization.remoteLoginLink(
            function (link) {
                $scope.link = link;
            },
            function (errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            }
        );
    };
});

loginModule.controller('LoginController', function ($scope, $state, $stateParams, Authorization, User, ErrorPropagationService) {
    $scope.user = User;
    $scope.error = {};
    $scope.group = $stateParams.event || '';
    $scope.login = function () {
        ErrorPropagationService.resetErrors();
        Authorization.login(
            $scope.user.username, $scope.user.password, $scope.group,
            function () {
                if ($scope.group === '') {
                    $state.go('administration.dashboard')
                } else {
                    $state.go('event.intern.dashboard', {event: $scope.group});
                }
            },
            function (errors) {
                ErrorPropagationService.propagateErrorResponse(errors);
            }
        );
    };
});

loginModule.controller('LogoutController', function ($scope, $state, $stateParams, Authorization) {
    $scope.group = $stateParams.event || '';
    $scope.logout = function (target) {
        Authorization.logout(
            function () {
                if (target === 'admin-area') {
                    $state.go('administration-login')
                } else {
                    $state.go('event.home', {event: $scope.group});
                }
            },
            function () {
                if (target === 'admin-area') {
                    $state.go('administration-login')
                } else {
                    $state.go('event.home', {event: $scope.group});
                }
            }
        );
    };
});

loginModule.factory('Authorization', function ($http, User) {

    return (function () {

        var logoutLocation = '/';
        var loginLocation = 'home';

        var logoutPath = 'app/api/authorization/logout';
        var loginPath = 'app/api/authorization/login';
        var autoLoginPath = 'app/api/authorization/auto-login';
        var remoteLoginPath = 'app/api/authorization/remote-login';

        var logoutRequest = function () {
            return $http.post(logoutPath, {});
        };

        var loginRequest = function (username, password, group) {
            return $http.post(loginPath, {username: username, password: password, group: group});
        };

        var autoLoginRequest = function (token) {
            return $http.post(autoLoginPath + "?token=" + token, {});
        };

        var remoteLoginRequest = function (token) {
            return $http.post(remoteLoginPath + "?token=" + token, {});
        };

        var remoteLoginLinkRequest = function (token) {
            return $http.get(remoteLoginPath);
        };

        function Authorization() {
        }

        Authorization.prototype = {
            login: function (username, password, group, success, error) {
                loginRequest(username, password, group)
                    .success(function (response) {
                        User.login(response.name, response.relationships, response.activated);
                        success(loginLocation);
                    })
                    .error(function (response) {
                        error(response);
                    });
            },
            autoLogin: function (token, success, error) {
                autoLoginRequest(token)
                    .success(function (response) {
                        User.login(response.name, response.relationships, response.activated);
                        success(loginLocation);
                    })
                    .error(function (response) {
                        error(response);
                    });
            },
            remoteLogin: function (token, success, error) {
                remoteLoginRequest(token)
                    .success(function (response) {
                        User.login(response.name, response.relationships, response.activated);
                        success(loginLocation);
                    })
                    .error(function (response) {
                        error(response);
                    });
            },
            remoteLoginLink: function (success, error) {
                remoteLoginLinkRequest()
                    .success(function (response) {
                        success(response);
                    })
                    .error(function (response) {
                        error(response);
                    });
            },
            logout: function (success, error) {
                logoutRequest()
                    .success(function () {
                        User.logout();
                        success(logoutLocation);
                    })
                    .error(function () {
                        error();
                    });
            }
        };

        return new Authorization();

    })();
});
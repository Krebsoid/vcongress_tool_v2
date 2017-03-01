'use strict';

var userModule = angular.module('UserModule', [], function () {
});

userModule.directive('restrictedLoggedIn', function (User) {
    return {
        restrict: 'A',
        link: function (scope, element) {
            scope.$watch(
                function () {
                    return User.isLoggedIn;
                },
                function (loggedIn) {
                    if (!loggedIn) {
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

userModule.directive('restrictedLoggedOut', function (User) {
    return {
        restrict: 'A',
        link: function (scope, element) {
            scope.$watch(
                function () {
                    return User.isLoggedIn;
                },
                function (loggedIn) {
                    if (loggedIn) {
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

userModule.directive('restrictedRoles', function (User, $state) {
    return {
        restrict: 'A',
        scope: {
            forward: '@forward'
        },
        link: function (scope, element, attrs) {
            scope.roles = [];
            if (attrs.restrictedRoles) {
                scope.roles = attrs.restrictedRoles.split(',');
            }
            scope.$watch(
                function () {
                    return User.isLoggedIn;
                },
                function () {
                    if (!User.hasRoles(scope.roles)) {
                        element.hide();
                        if(angular.isDefined(scope.forward)) {
                            $state.go(scope.forward);
                        }
                    }
                    else {
                        element.show();
                    }
                }
            );
        }
    };
});

userModule.controller('UserRegistrationController', function ($scope, $state, User) {
    $scope.register = function() {
        $state.go('administration-dashboard');
    };
});

userModule.controller('UserStateController', function ($scope, User) {
    $scope.user = User;
});

userModule.service('UserIsLoggedIn', function(User) {
    return User.requestState();
});

userModule.factory('User', function ($q, $http) {

    return (function () {
        var initialLoaded = false;

        var authorizationPath = 'app/api/authorization';
        var logoutObserver = [];
        var loginObserver = [];

        var userStateRequest = function () {
            return $http.get(authorizationPath);
        };

        function User() {
            this.name = undefined;
            this.relationships = undefined;
            this.isLoggedIn = undefined;
        }

        User.prototype.login = function (name, relationships, activated) {
            this.name = name;
            this.relationships = relationships;
            this.isLoggedIn = true;
            this.activated = activated;
            var self = this;
            angular.forEach(loginObserver, function (observerObject) {
                var observer = observerObject.observer;
                observerObject.handler.call(observer, self);
            });
        };

        User.prototype.logout = function () {
            this.name = undefined;
            this.relationships = [];
            this.isLoggedIn = false;
            var self = this;
            angular.forEach(logoutObserver, function (observerObject) {
                var observer = observerObject.observer;
                observerObject.handler.call(observer, self);
            });
        };

        User.prototype.registerLogoutHandler = function (observer, onLogoutHandler) {
            logoutObserver.push({
                observer: observer,
                handler: onLogoutHandler
            });
        };

        User.prototype.registerLoginHandler = function (observer, onLoginHandler) {
            loginObserver.push({
                observer: observer,
                handler: onLoginHandler
            });
        };

        User.prototype.requestState = function (forced, checkLoggedIn) {
            var self = this;
            var userPromise = $q.defer();
            if (forced || !initialLoaded) {
                userStateRequest()
                    .success(function (response) {
                        if (response.name) {
                            User.prototype.login.call(self, response.name, response.relationships, response.activated);
                            userPromise.resolve(self);
                        }
                        else {
                            User.prototype.logout.call(self);
                            if (checkLoggedIn) {
                                userPromise.reject(self);
                            }
                            else {
                                userPromise.resolve(self);
                            }
                        }
                        initialLoaded = true;
                    })
                    .error(function () {
                        User.prototype.logout.call(self);
                        userPromise.reject(self);
                        initialLoaded = true;
                    });
            }
            else {
                if(this.isLoggedIn) {
                    userPromise.resolve(self);
                }
                else {
                    userPromise.reject(self);
                }
            }
            return userPromise.promise;
        };

        User.prototype.requestRole = function(requiredRoles) {
            var rolePromise = $q.defer();
            var self = this;
            this.requestState(true).then(function() {
                if(self.hasRoles(requiredRoles)) {
                    rolePromise.resolve(this);
                } else {
                    rolePromise.reject(this);
                }
            }, function() {
                rolePromise.reject(this);
            });
            return rolePromise.promise;
        };

        User.prototype.hasRoles = function (requiredRoles) {
            var result = false;
            angular.forEach(this.relationships, function (relationship) {
                angular.forEach(requiredRoles, function (requiredRole) {
                    angular.forEach(relationship.roles, function (role) {
                        if (requiredRole == role.name) {
                            result = true;
                        }
                    });
                });
            });
            return result;
        };

        User.prototype.hasRolesAndGroup = function (requiredGroup, requiredRoles) {
            var result = false;
            angular.forEach(this.relationships, function (relationship) {
                if(relationship.group.name == requiredGroup) {
                    angular.forEach(requiredRoles, function (requiredRole) {
                        angular.forEach(relationship.roles, function (role) {
                            if (requiredRole == role.name) {
                                result = true;
                            }
                        });
                    });
                }
            });
            return result;
        };

        User.prototype.hasPermission = function (requiredGroup, requiredRoles) {
            return this.hasRolesAndGroup(requiredGroup, requiredRoles) || this.hasRolesAndGroup('system', ['ADMIN']) || this.hasRolesAndGroup('system', ['ORGANISATION'])
        };


        return new User();

    })();
});

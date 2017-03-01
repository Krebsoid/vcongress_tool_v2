'use strict';

userModule.factory('Permissions', function ($http, $q) {

    return (function () {

        var permissionsPath = 'app/api/account/permissions';

        function Permissions() {
            this.groups = undefined;
        }

        Permissions.prototype = {
            getGroups: function() {
                var groupPromise = $q.defer();
                var self = this;
                $http.get(permissionsPath + '/groups').success(function (groups) {
                    self.groups = groups;
                    groupPromise.resolve(groups);
                }).error(function (errors) {
                    groupPromise.reject(errors);
                });
                return groupPromise.promise;
            },
            addRole: function (user, group, role) {
                var userPromise = $q.defer();
                $http.put(permissionsPath + '/' + user + '/' + group + '/' + role, {}).success(function (user) {
                    userPromise.resolve(user);
                }).error(function (errors) {
                    userPromise.reject(errors);
                });
                return userPromise.promise;
            },
            removeRole: function (user, group, role) {
                var userPromise = $q.defer();
                $http.delete(permissionsPath + '/' + user + '/' + group + '/' + role).success(function (user) {
                    userPromise.resolve(user);
                }).error(function (errors) {
                    userPromise.reject(errors);
                });
                return userPromise.promise;
            }
        };

        return new Permissions();

    })();
});
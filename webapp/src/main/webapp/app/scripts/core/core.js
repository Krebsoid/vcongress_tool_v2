'use strict';

var coreModule = angular.module('CoreModule', ['LocaleModule'], function () {

});

coreModule.factory('Location', function ($location) {
    return (function () {

        function Location() {
            this.activeLocation = '';
        }

        function analyzeLocation(path) {
            var location = path.substring(1);
            return location !== '' ? location : 'home';
        }

        Location.prototype.getLocation = function() {
            return this.activeLocation;
        };

        Location.prototype.refreshLocation = function () {
            this.activeLocation = analyzeLocation($location.path());
        };

        return new Location();

    })();
});



coreModule.run(function ($rootScope, Location, Locale) {
    Locale.initLocale();
    Location.refreshLocation();

    $rootScope.$on('$routeChangeSuccess', function () {
        Location.refreshLocation();
    });
});

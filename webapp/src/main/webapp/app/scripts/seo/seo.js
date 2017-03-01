'use strict';

var seoModule = angular.module('SeoModule', ['CoreModule', 'LocaleModule'], function () {

});

seoModule.directive('seoValue', function (Locale, Location) {
    return {
        restrict: 'A',
        replace: true,
        scope: {
            seoValue: '@seoValue'
        },
        link: function (scope) {
            scope.computeOutput = function () {
                scope.location = Location.getLocation();
                scope.locale = Locale.getLocale();
                try {
                    var result = Meta[Locale.getLanguage()][scope.location][scope.seoValue];
                    return angular.isUndefined(result) ? MetaGeneral[Locale.getLanguage()][scope.seoValue] : result;
                }
                catch(error) {
                    return MetaGeneral[Locale.getLanguage()][scope.seoValue];
                }


            };

            scope.$watch(
                function () {
                    return angular.toJson({locale: Locale.getLocale(), location: Location.getLocation()});
                },
                function () {
                    scope.outputString = scope.computeOutput();
                }
            );
        },
        template: '<meta name="{{ seoValue }}" content="{{ outputString }}"/>'
    };
});

seoModule.directive('seoTitle', function (Title) {
    return {
        restrict: 'A',
        replace: true,
        link: function (scope) {
            scope.$watch(
                function () {
                    return  Title.getTitle();
                },
                function (title) {
                    scope.outputString = title;
                }
            );
        },
        template: '<title> {{ outputString }} </title>'
    };
});

seoModule.factory('Title', function ($timeout, Locale, Location) {

    return (function () {

        var _title = '',
            _dynamicTitle = '',
            _permanentTitle = '',
            _pulseTimeout,
            _pulsingTitleFunction;

        function pulseTitle() {
            _title = _title === _dynamicTitle ? _permanentTitle : _dynamicTitle;
            _pulsingTitleFunction();
        }

        function Title() {
        }

        Title.prototype = {
            getTitle: function () {
                return _title;
            },
            setTitle: function (title) {
                _permanentTitle = title;
                _title = title;
            },

            refreshTitle: function () {
                this.resetTitle();
                var location = Location.getLocation();
                var locale = Locale.getLocale();
                try {
                    _title = Meta[locale][location].title;
                }
                catch(error) {
                    _title = MetaGeneral[Locale.getLanguage()].title;
                }
                _permanentTitle = _title;
            },
            resetTitle: function () {
                this.stopPulsingTitle();
                _title = '';
                _permanentTitle = '';
                _dynamicTitle = '';
            },

            startPulsingTitle: function (pulsingTitle) {
                _dynamicTitle = pulsingTitle;
                _pulsingTitleFunction = function () {
                    _pulseTimeout = $timeout(pulseTitle.bind(this), 2000, true);
                };
                _pulsingTitleFunction();
            },
            stopPulsingTitle: function () {
                $timeout.cancel(_pulseTimeout);
                _title = _permanentTitle;
            }
        };

        return new Title();

    })();
});


seoModule.run(function ($rootScope, Title, Location, Locale) {

    $rootScope.$watch(
        function () {
            return angular.toJson({locale: Locale.getLocale(), location: Location.getLocation()});
        },
        function () {
            Title.refreshTitle();
        }
    );

});




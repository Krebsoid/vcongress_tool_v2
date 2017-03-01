'use strict';

var commonModule = angular.module('CommonModule',['FormModule'], function() {

});

commonModule.directive('naviLink', function($state) {
    return {
        scope: {
            naviLink: '@',
            naviHighlight: '@'
        },
        replace: true,
        transclude: true,
        link: function(scope, element) {

            function checkVisible(state) {
                var checkState = scope.naviHighlight || scope.naviLink;
                return _.contains(state, checkState);
            }

            var linkWidth = element.parent().find('span').width();
            scope.style = {width: linkWidth, height: 32};
            scope.visible = checkVisible($state.$current.name);
            scope.$on('$stateChangeStart', function(s, state) {
                scope.visible = checkVisible(state.name);
            });

            scope.mouseOver = function() {
                scope.visible = true;
            };
            scope.mouseOut = function() {
                if(!checkVisible($state.$current.name)) {
                    scope.visible = false;
                }
            };
        },
        template: '<div data-ng-mouseover="mouseOver()" data-ng-mouseout="mouseOut()">\n    <a ui-sref="{{ naviLink }}" class="link">\n        <span data-ng-transclude></span>\n        <img class="navi-indicator" data-ng-show="visible" data-ng-style="style" src="images/layout/DG-GT_Navi_Auszeichnung.png"/>\n    </a>\n</div>'
    };
});

commonModule.directive('onScroll', function() {
    return {
        restrict:'A',
        link: function (scope, element, attrs) {
            var offsetTop = element.context.offsetTop + 160;
            angular.element(parent.window).on('scroll', function() {
                var top = (parent.window.scrollY || parent.window.pageYOffset || parent.document.documentElement.scrollTop);
                if(top > offsetTop) {
                    element.css('margin-top', 'auto');
                    element.css('position', 'fixed');
                    element.css('top', '8px');
                } else {
                    element.css('position', 'relative');
                    element.css('margin-top', '0px');
                    element.css('top', '0px');
                }
            });
        }
    }
});

commonModule.directive('mailName', function() {
    return {
        restrict:'A',
        transclude: true,
        replace: true,
        scope: {
            'mailName' : '@',
            'mailDomain' : '@'
        },
        template: '<a href="mailto:{{mail}}" data-ng-transclude></a>',
        link:function (scope) {
            scope.mail = scope.mailName + "@" + scope.mailDomain;
        }
    };
});

commonModule.filter('shorten', function() {
    return function(input, length) {
        if(angular.isDefined(input) && input !== null && input.length > length) {
            return input.substring(0, length)+'...';
        }
        else {
            return input;
        }
    };
});

commonModule.directive('popover', function() {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            if(angular.isDefined(attrs.popover)) {
                $(element).popover({
                    trigger: 'focus'
                });
            }
        }
    };
});

commonModule.directive('tooltip', function() {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            if(angular.isDefined(attrs.tooltip)) {
                $(element).tooltip();
            }
        }
    };
});

commonModule.directive('fixElement', function() {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            $(element).fixClient({ os: ['an unknown OS','Mac','Linux'], cls: attrs.fixElement });
        }
    };
});

commonModule.directive('carousel', function() {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            if(angular.isDefined(attrs.carousel)) {
                element.carousel();
            }
        }
    };
});

commonModule.directive('fancybox', function() {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            if(angular.isDefined(attrs.fancybox)) {
                $('.'+attrs.fancybox).fancybox();
            }
        }
    };
});

commonModule.directive('waitingMessage', function() {
    return {
        restrict:'A',
        scope : {
            waitingMessage : '@',
            guard: '=waitingMessageGuard'
        },
        link:function (scope, element) {
            scope.$watch(function() {
                return scope.guard;
            }, function(guard) {
                if(guard) {
                    element.attr('disabled', 'disabled');
                    element.html(scope.waitingMessage);
                }}
            );
        }
    };
});

commonModule.directive('dropDownOnHover', function() {
    return {
        restrict:'A',
        link:function (scope, element, attrs) {
            if(angular.isDefined(attrs.dropDownOnHover)) {
                $(element).dropdownHover(true);
            }
        }
    };
});

commonModule.directive('navigationLink', function($location) {
    return {
        restrict:'A',
        transclude:true,
        replace:true,
        scope:{
            path:'@navigationLink'
        },
        link:function (scope) {

            scope.$watch(function (scope) {

                if ($location.path().contains(scope.path)) {
                    scope.visibility = 'active';
                } else {
                    scope.visibility = '';
                }

            });
        },
        template:'<li data-ng-class="visibility"><a href="/#!{{path}}" data-ng-transclude></a></li>'
    };
});

// Workaround for a DropDown Nav Item, that the item is active css-wise, when a sub item is selected
commonModule.directive('bootstrapDropDownLink', function($location) {
    return {
        restrict:'A',
        scope:{
            path:'@bootstrapDropDownLink'
        },
        link: function (scope) {
            scope.$watch(function (scope) {
                var partialUrl = '/'+$location.path().split('/')[1];
                if (partialUrl === scope.path) {
                    scope.visibility = 'active';
                } else {
                    scope.visibility = '';
                }
            });
        }
    };
});

commonModule.directive('onEnter', function() {
    return function(scope, element, attrs) {
        element.bind('keydown keypress', function(event) {
            if(event.which === 13) {
                scope.$apply(function(){
                    scope.$eval(attrs.onEnter);
                });
                element.focus();
                event.preventDefault();
            }
        });
    };
});

commonModule.directive('onTab', function() {
    return function(scope, element, attrs) {
        element.bind('keydown keypress', function(event) {
            if(event.which === 9) {
                scope.$apply(function(){
                    scope.$eval(attrs.onTab);
                });
                $(attrs.onTabNextFocus).focus();
                event.preventDefault();
            }
        });
    };
});

commonModule.directive('compile', function($compile) {
    return {
        link : function(scope, element, attrs) {
            scope.$watch(
                function(scope) {
                    return scope.$eval(attrs.compile);
                },
                function(value) {
                    element.html(value);
                    $compile(element.contents())(scope);
                }
            );
        }
    };
});

commonModule.filter('linkyWithHtml', function($filter) {
    return function(value) {
        var linked = !!value ? $filter('linky')(value) : "";
        return linked.replace(/\&gt;/g, '>').replace(/\&lt;/g, '<');
    };
});
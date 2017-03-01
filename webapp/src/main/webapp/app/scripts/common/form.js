'use strict';

var commonModule = angular.module('FormModule',[], function() {

});

commonModule.directive('phoneNumber', function() {
    return {
        scope: {
            phoneNumber: '=phoneNumber'
        },
        restrict:'A',
        require: '?ngModel',
        link:function (scope, element, attrs, ngModel) {
            if(angular.isDefined(attrs.phoneNumber)) {
                $(element).intlTelInput({
                    utilsScript: "bower_components/intl-tel-input/build/js/utils.js",
                    initialCountry: "de"
                });
                scope.$watch(function() {
                    return scope.phoneNumber;
                }, function(country) {
                    if(ngModel.$viewValue) {
                        $(element).intlTelInput("setNumber", ngModel.$viewValue);
                    } else {
                        $(element).intlTelInput("setCountry", country);
                    }
                })
            }
        }
    };
});

commonModule.directive('focusInput', function($timeout, $parse) {
    return {
        link: function (scope, element, attrs) {
            var model = $parse(attrs.focusInput);
            scope.$watch(model, function (value) {
                console.log('value=', value);
                if (value === true) {
                    $timeout(function () {
                        element[0].focus();
                        element[0].select();
                    });
                }
            });

            element.bind('blur', function () {
                console.log('blur');
                scope.$apply(model.assign(scope, false));
            });
        }
    };
});

commonModule.service('Tools', function() {
    this.formatCurrency = function(currency) {
        return currency ? currency.toString().replace(',','.') : '';
    };
});

commonModule.directive('errorAware', function($rootScope) {
    return {
        restrict:'A',
        require: '^ngModel',
        scope: {
            errorDirection: '@'
        },
        link:function (scope, element, attrs, controller) {
            scope.errorMessage = 'invalid';
            scope.error = undefined;
            scope.errorDirection = scope.errorDirection || 'top';

            $rootScope.$on('reset-errors', function(event, error) {
                element.parent().children().removeClass(error.cls);
            });
            $rootScope.$on('field-error', function (event, error) {
                var fieldNameArray = attrs.ngModel.split('.');
                var fieldName = fieldNameArray[fieldNameArray.length-1];
                if(error.key === fieldName) {
                    scope.error = error;
                    element.popover({
                        content: function () {
                            return scope.error.value;
                        },
                        trigger: 'hover focus',
                        placement: scope.errorDirection});
                    element.parent().children().addClass(error.cls);
                }
            });
            scope.$watch(
                function(){return controller.$viewValue;},
                function(){
                    if(scope.error) {
                        element.parent().children().removeClass(scope.error.cls);
                    }
                }
            );
        }
    };
});

commonModule.directive('optionsClass', function ($parse) {
    return {
        require: 'select',
        link: function(scope, elem, attrs, ngSelect) {
            // get the source for the items array that populates the select.
            var optionsSourceStr = attrs.ngOptions.split(' ').pop(),
            // use $parse to get a function from the options-class attribute
            // that you can use to evaluate later.
                getOptionsClass = $parse(attrs.optionsClass);

            scope.$watch(optionsSourceStr, function(items) {
                // when the options source changes loop through its items.
                angular.forEach(items, function(item, index) {
                    // evaluate against the item to get a mapping object for
                    // for your classes.
                    var classes = getOptionsClass(item),
                    // also get the option you're going to need. This can be found
                    // by looking for the option with the appropriate index in the
                    // value attribute.
                        option = elem.find('option[value=\'number:' + (index+1) + '\']');

                    // now loop through the key/value pairs in the mapping object
                    // and apply the classes that evaluated to be truthy.
                    angular.forEach(classes, function(add, className) {
                        if(add) {
                            angular.element(option).addClass(className);
                        }
                    });
                });
            });
        }
    };
});

commonModule.directive('remoteErrorAware', function($rootScope) {
    return {
        restrict:'A',
        scope: {
            errorDirection: '@',
            fieldName: '@remoteErrorAware',
            remoteErrorModel: '='
        },
        link:function (scope, element) {
            scope.errorMessage = 'invalid';
            scope.error = undefined;
            scope.errorDirection = scope.errorDirection || 'top';

            $rootScope.$on('reset-errors', function(event, error) {
                element.removeClass(error.cls);
                element.popover('destroy');
            });
            $rootScope.$on('field-error', function (event, error) {
                if(error.key === scope.fieldName) {
                    scope.error = error;
                    element.popover({content: scope.error.value, trigger: 'hover focus', placement: scope.errorDirection});
                    element.addClass(error.cls);
                }
            });
            scope.$watch(
                function(){return scope.remoteErrorModel;},
                function(){
                    if(scope.error) {
                        element.removeClass(scope.error.cls);
                    }
                }
            );
        }
    };
});

commonModule.factory('ErrorPropagationService', function($rootScope) {
    var resetEvent = 'reset-errors';
    var propagateError = 'field-error';
    var invalidBorderCls = 'invalid';

    function resetErrors() {
        var errorReset = {
            cls : invalidBorderCls
        };
        $rootScope.$broadcast(resetEvent,errorReset);
    }

    function manualError(key, value) {
        var error = {
            cls : invalidBorderCls,
            key : key,
            value : value
        };
        $rootScope.$broadcast(propagateError, error);
    }

    function propagateErrorResponse(errors) {
        if(!errors.error) {
            angular.forEach(errors, function(error) {
                manualError(error.key, error.value);
            });
        }
        else {
            alert(errors.error);
        }
    }

    function manualErrorAlert(errorMessage) {
        alert(errorMessage);
    }

    return {
        resetErrors : function() {
            resetErrors();
        },
        manualError : function(key, value) {
            manualError(key, value);
        },
        manualErrorAlert : function(message) {
            manualErrorAlert(message);
        },
        propagateErrorResponse : function(errors) {
            propagateErrorResponse(errors);
        }
    };
});

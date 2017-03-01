'use strict';

//noinspection JSValidateTypes
angular.module('main', ['angular-loading-bar', 'ui.router', 'ui.bootstrap', 'ngAnimate', 'ngContextMenu', 'ngSanitize',
        'AuthorizationModule', 'CoreModule', 'SeoModule', 'angularFileUpload',
        'CommonModule', 'LocalStorageModule', 'AdminDashboardModule',
        'PersonModule', 'PersonListModule', 'EventModule'],
    function ($stateProvider, $urlRouterProvider, $locationProvider, $tooltipProvider, LocaleProvider) {
        $locationProvider.hashPrefix('!');
        $locationProvider.html5Mode(true);

        $tooltipProvider.options( { appendToBody: true } );

        var _availableLocales = [
            {name: 'English', value: 'en_GB'}
        ];

        LocaleProvider.setAvailableLocales(_availableLocales);
        LocaleProvider.setDefaultLocale('en_GB');

        $stateProvider
            .state('password-recovery-init', {
                url: '/konto/passwort-zuruecksetzen',
                template: '<div data-ng-include="\'./partials/content/password-recovery-init.html\'"></div>',
                data: {
                    backlink: 'administration-login'
                }
            })
            .state('password-recovery-execute', {
                url: '/konto/passwort-aendern?token&mail',
                template: '<div data-ng-include="\'./partials/content/password-recovery-execute.html\'"></div>',
                data: {
                    backlink: 'administration-login'
                }
            })

            .state('administration-login', {
                url: '/administration',
                template: '<div data-ng-include="\'./partials/content/login-administration.html\'"></div>'
            })
            .state('administration-registration', {
                url: '/administration/registrierung',
                template: '<div data-ng-include="\'./partials/content/registration.html\'"></div>',
                data: {
                    backlink: 'administration-login'
                }
            })


            .state('home', {
                url: '/',
                controller: function($state) {
                    $state.go('administration-login');
                }
            })
            .state('administration', {
                url: '/administration',
                template: '<div data-ng-include="\'./partials/content/admin-area/admin-area.html\'"></div>',
                abstract: true
            })
            .state('administration.home', {
                url: '/home',
                template: '<div data-ng-include="\'./partials/content/admin-area/home.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User) {
                        return User.requestState();
                    }
                }
            })
            .state('administration.dashboard', {
                url: '/dashboard',
                template: '<div data-ng-include="\'./partials/content/admin-area/dashboard.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event) {
                        return User.requestState().then(function() {
                            return Event.getAllAsSummary();
                        });
                    }
                }
            })
            .state('administration.user-list', {
                url: '/benutzer',
                template: '<div data-ng-include="\'./partials/content/admin-area/user-list.html\'"></div>',
                resolve: {
                    userLoggedIn : function($q, User, Permissions, PersonList) {
                        return User.requestRole(['ADMIN']).then(function() {
                            return $q.all([
                                Permissions.getGroups(),
                                PersonList.getAllPersons()
                            ]);
                        });
                    }
                }
            })
            .state('administration.customer-list', {
                url: '/kunden',
                template: '<div data-ng-include="\'./partials/content/admin-area/customer-list.html\'"></div>',
                resolve: {
                    userLoggedIn : function($q, User, Permissions, Customers) {
                        return User.requestRole(['ADMIN']).then(function() {
                            return $q.all([
                                Permissions.getGroups(),
                                Customers.getCustomers()
                            ]);
                        });
                    }
                }
            })
            .state('administration.order-list-customer', {
                url: '/bestellungen',
                template: '<div data-ng-include="\'./partials/content/admin-area/order-list.html\'"></div>',
                resolve: {
                    userLoggedIn : function($q, User, Order) {
                        return User.requestRole(['CUSTOMER']).then(function() {
                            return $q.all([
                                Order.getActiveOrders()
                            ]);
                        });
                    }
                }
            })
            .state('administration.order-list-admin', {
                url: '/rechnungen?search',
                template: '<div data-ng-include="\'./partials/content/admin-area/transaction-list.html\'"></div>',
                resolve: {
                    userLoggedIn : function($q, User, Transaction) {
                        return User.requestRole(['ADMIN']).then(function() {
                            return $q.all([
                                Transaction.getOrders()
                            ]);
                        });
                    }
                }
            })
            .state('administration.event-list', {
                url: '/veranstaltungen',
                template: '<div data-ng-include="\'./partials/content/admin-area/event-list.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User) {
                        return User.requestState();
                    }
                }
            })
            .state('administration.event-creation-selection', {
                url: '/veranstaltungen/neu/auswahl',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-selection.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User) {
                        return User.requestState();
                    }
                }
            })
            .state('administration.event-creation-modules', {
                url: '/veranstaltungen/neu/module',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-modules.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User) {
                        return User.requestState();
                    }
                }
            })
            .state('administration.event-creation-license', {
                url: '/veranstaltungen/neu/module',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-modules.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User) {
                        return User.requestState();
                    }
                }
            })
            .state('administration.event-creation-1', {
                url: '/veranstaltungen/neu/schritt-1',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation.html\'"></div>'
            })
            .state('administration.short-event-creation-1', {
                url: '/veranstaltungen/neu/kurz',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/short-event-creation.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User) {
                        return User.requestState();
                    }
                }
            })
            .state('administration.short-event-creation', {
                url: '/veranstaltungen/neu/kurz/:event',
                template: '<div data-ui-view=""></div>',
                abstract: true
            })
            .state('administration.short-event-creation.general', {
                url: '/schritt-1',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/short-event-creation.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.short-event-creation.disclaimer', {
                url: '/schritt-2',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/short-event-creation-disclaimer.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.short-event-creation.publication', {
                url: '/schritt-3',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/short-event-creation-publication.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })


            .state('administration.event-creation', {
                url: '/veranstaltungen/neu/:event',
                template: '<div data-ui-view=""></div>',
                abstract: true
            })
            .state('administration.event-creation.general', {
                url: '/schritt-1',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.event-creation.disclaimer', {
                url: '/schritt-2',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-disclaimer.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.event-creation.participation', {
                url: '/schritt-3',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-participation.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.event-creation.additional-program', {
                url: '/schritt-4',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-additional-program.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.event-creation.publication', {
                url: '/schritt-5',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-publication.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                            return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.event.license', {
                url: '/lizenz',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-payment/license.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, Order, Person, Country, $q, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event);
                        });
                    }
                }
            })
            .state('administration.event.payment', {
                url: '/bezahlung',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-payment/payment.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, Order, Person, Country, $q, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event).then(function() {
                                return $q.all([
                                    Person.requestState(),
                                    Order.getOrderByEvent($stateParams.event),
                                    Country.getAll()
                                ]);
                            });
                        });
                    }
                }
            })
            .state('administration.event.complete-payment', {
                url: '/bezahlung/abschließen?payment-method&token&transaction',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-payment/payment-complete.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, Order, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event).then(function() {
                                return Order.getOrderToExecute($stateParams.transaction);
                            });
                        });
                    }
                }
            })
            .state('administration.event-creation.finished', {
                url: '/abschluss',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-creation-finished.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return Event.get($stateParams.event);
                    }
                }
            })
            .state('administration.event', {
                url: '/veranstaltungen/:event',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-area.html\'"></div>',
                abstract: true
            })
            .state('administration.event.event-editation', {
                url: '/bearbeiten',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/event-editation-panel.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event);
                        });
                    }
                }
            })
            .state('administration.event.participant-list', {
                url: '/teilnehmer?search',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/participant-list.html\'"></div>',
                resolve: {
                    participantList : function(Event, User, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event).then(function() {
                                return Event.getParticipants();
                            });
                        });
                    }
                }
            })
            .state('administration.event.transaction-list', {
                url: '/transaktionen?search',
                template: '<div data-ng-include="\'./partials/content/admin-area/event/transaction-list.html\'"></div>',
                resolve: {
                    participantList : function(Event, User, Transaction, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event).then(function() {
                                return Transaction.getTransactions($stateParams.event);
                            });
                        });
                    },
                    countries : function(Country) {
                        return Country.getAll();
                    }
                }
            })
            .state('administration.event.person-edit', {
                url: '/person/:person',
                template: '<div data-ng-include="\'./partials/content/event/user-edit.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Person, $stateParams) {
                        return User.requestState().then(function() {
                            return Person.get($stateParams.person);
                        });
                    },
                    countries : function(Country) {
                        return Country.getAll();
                    }
                },
                data: {
                    backlink: 'administration.event.participant-list',
                    admin: true
                }
            })
            .state('administration.event.participation-edit', {
                url: '/teilnahme/:participant',
                template: '<div data-ng-include="\'./partials/content/event/participation.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Event, Participation, $stateParams) {
                        return User.requestState().then(function() {
                            return Event.get($stateParams.event).then(function() {
                                return Participation.get($stateParams.participant);
                            });
                        });
                    }
                },
                data: {
                    backlink: 'administration.event.participant-list',
                    admin: true
                }
            })
            .state('administration.user-edit', {
                url: '/konto',
                template: '<div data-ng-include="\'./partials/content/admin-area/user-edit.html\'"></div>',
                resolve: {
                    userLoggedIn : function(User, Person) {
                        return User.requestState().then(function() {
                            return Person.requestState();
                        });
                    },
                    countries : function(Country) {
                        return Country.getAll();
                    }
                },
                data: {
                    backlink: 'administration.dashboard'
                }
            })
            .state('event-not-available', {
                url: '/veranstaltung-nicht-verfuegbar',
                template: '<div data-ng-include="\'./partials/content/event/not-available.html\'"></div>'
            })
            .state('auto-login', {
                url: '/auto-login?token',
                controller: function($stateParams, $state, Authorization, ErrorPropagationService) {
                    Authorization.autoLogin($stateParams.token, function() {
                        $state.go('administration.dashboard')
                    }, function(errors) {
                        ErrorPropagationService.propagateErrorResponse(errors);
                    })
                }
            })
            .state('remote-login', {
                url: '/remote-login?token',
                controller: function($stateParams, $state, Authorization, ErrorPropagationService) {
                    Authorization.remoteLogin($stateParams.token, function() {
                        $state.go('administration.dashboard')
                    }, function(errors) {
                        ErrorPropagationService.propagateErrorResponse(errors);
                    })
                }
            })



            .state('event', {
                url: '/:event',
                template: '<div data-ui-view=""></div>'
            })
            .state('event.home', {
                url: '/home',
                template: '<div data-ng-include="\'./partials/content/event/home.html\'"></div>',
                resolve: {
                    eventLoad : function(Event, $stateParams) {
                        return Event.checkForPublic($stateParams.event);
                    }
                }
            })
            .state('event.contact', {
                url: '/kontakt',
                template: '<div data-ng-include="\'./partials/content/contact.html\'"></div>'
            })
            .state('event.imprint', {
                url: '/impressum',
                template: '<div data-ng-include="\'./partials/content/imprint.html\'"></div>'
            })
            .state('event.registration', {
                url: '/registrierung',
                template: '<div data-ng-include="registrationTemplate"></div>',
                resolve: {
                    countries : function(Country) {
                        return Country.getAll();
                    },
                    eventLoad : function(Event, $stateParams) {
                        return Event.checkForPublic($stateParams.event);
                    }
                },
                controller: function($scope, Event) {
                    if(Event.isShort()) {
                        $scope.registrationTemplate = './partials/content/event/registration-short.html';
                    } else {
                        $scope.registrationTemplate = './partials/content/event/registration.html';
                    }
                }
            })
            .state('event.login', {
                url: '/login',
                template: '<div data-ng-include="\'./partials/content/event/login.html\'"></div>',
                resolve: {
                    eventLoad : function(Event, $stateParams) {
                        return Event.checkForPublic($stateParams.event);
                    }
                }
            })
            .state('event.password-recovery-init', {
                url: '/konto/passwort-zuruecksetzen',
                template: '<div data-ng-include="\'./partials/content/password-recovery-init.html\'"></div>',
                data: {
                    backlink: 'event.home'
                },
                resolve: {
                    eventLoad : function(Event, $stateParams) {
                        return Event.checkForPublic($stateParams.event);
                    }
                }
            })
            .state('event.password-recovery-execute', {
                url: '/konto/passwort-aendern?token&mail',
                template: '<div data-ng-include="\'./partials/content/password-recovery-execute.html\'"></div>',
                data: {
                    backlink: 'event.home'
                },
                resolve: {
                    eventLoad : function(Event, $stateParams) {
                        return Event.checkForPublic($stateParams.event);
                    }
                }
            })
            .state('event.registered-successfully', {
                url: '/erfolgreich-registriert',
                template: '<div data-ng-include="\'./partials/content/event/registered-successfully.html\'"></div>',
                resolve: {
                    eventLoad : function(Event, $stateParams) {
                        return Event.checkForPublic($stateParams.event);
                    }
                }
            })

            .state('event.intern', {
                abstract: true,
                template: '<div data-ng-include="\'./partials/content/event/event-area.html\'"></div>'
            })
            .state('event.intern.dashboard', {
                url: '/dashboard',
                template: '<div data-ng-include="\'./partials/content/event/dashboard.html\'"></div>',
                resolve: {
                    isParticipant: function ($q, User, Event, Participation, $stateParams) {
                        return User.requestRole(['TEILNEHMER']).then(function () {
                                return Event.checkForPublic($stateParams.event).then(function () {
                                    return $q.all([
                                        Participation.get()
                                    ]);
                                });
                            });
                    }
                }
            })
            .state('event.intern.participation', {
                url: '/teilnahme',
                template: '<div data-ng-include="\'./partials/content/event/participation.html\'"></div>',
                data: {
                    backlink: 'event.intern.dashboard'
                },
                resolve: {
                    isParticipant : function(User, Event, Participation, $stateParams) {
                        return User.requestRole(['TEILNEHMER']).then(function(){
                            return Event.checkForPublic($stateParams.event).then(function() {
                                return Participation.get();
                            });
                        });
                    }
                }
            })
            .state('event.intern.payment', {
                url: '/bezahlung',
                template: '<div data-ng-include="\'./partials/content/event/payment.html\'"></div>',
                data: {
                    backlink: 'event.intern.dashboard'
                },
                resolve: {
                    isParticipant : function(User, Event, Participation, Country, Person, Transaction, $stateParams, $q) {
                        return User.requestRole(['TEILNEHMER']).then(function(){
                            return Event.checkForPublic($stateParams.event).then(function() {
                                return $q.all([
                                    Person.requestState(),
                                    Transaction.getActiveTransactions(),
                                    Country.getAll()
                                ]);
                            });
                        });
                    }
                }
            })
            .state('event.intern.complete-payment', {
                url: '/bezahlung/abschließen?payment-method&token&transaction',
                template: '<div data-ng-include="\'./partials/content/event/payment-complete.html\'"></div>',
                resolve: {
                    isParticipant : function(User, Event, Participation, Transaction, $stateParams) {
                        return User.requestRole(['TEILNEHMER']).then(function(){
                            return Event.checkForPublic($stateParams.event).then(function() {
                                return Transaction.getTransactionToExecute($stateParams.transaction);
                            });
                        });
                    }
                }
            })
            .state('event.intern.user-edit', {
                url: '/benutzer',
                template: '<div data-ng-include="\'./partials/content/event/user-edit.html\'"></div>',
                resolve: {
                    isParticipant : function(User, Event, Person, $stateParams) {
                        return User.requestRole(['TEILNEHMER']).then(function(){
                            return Event.checkForPublic($stateParams.event).then(function() {
                                return Person.requestState();
                            });
                        });
                    },
                    countries : function(Country) {
                        return Country.getAll();
                    }
                },
                data: {
                    backlink: 'event.intern.dashboard'
                }
            })
    });

angular.element(document).ready(function () {
    return angular.bootstrap(document, ['main']);
});

angular.module('main').run(function($rootScope, $location, $state) {
    $rootScope.$on('$stateChangeSuccess', function (e, toState, toParams) {
        if(toState.name === 'event') {
            if(toParams.event !== '') {
                $state.go('event.home', {event: toParams.event});
            }
        }
    });
    $rootScope.$on('$stateChangeError', function (e, toState, toParams) {
        if(toState.name === 'event.home') {
            $state.go('event-not-available');
        } else if(toState.name.includes('event')) {
            $state.go('event', {event: toParams.event});
        }

     if(toState.name.includes('administration')) {
            $state.go('administration-login');
        }
    });
});

if (!String.prototype.includes) {
    String.prototype.includes = function() {'use strict';
        return String.prototype.indexOf.apply(this, arguments) !== -1;
    };
}

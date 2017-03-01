'use strict';

eventModule.controller('ParticipantListController', function ($scope, $stateParams, Event, ErrorPropagationService,
                                                              Participation, localStorageService) {
    this.order = [{
        name: 'person.lastName',
        ascending: '+'
    }];
    this.listBegin = 0;
    this.listEnd = 500;

    this.stringFilter = $stateParams.search || '';
    this.cancelled = true;
    this.view = {
        firstName: true,
        lastName: true,
        gender: true,
        email: true,
        registration: true,
        participation: true
    };

    this.config = {
        view: this.view,
        order: this.order,
        limit: this.listEnd
    };

    var scope = this;
    this.event = Event.data;
    this.participants = this.event.participants;

    if(!!localStorageService.get(this.event.identifier + '_participant_list_config')) {
        this.config = localStorageService.get(this.event.identifier + '_participant_list_config');
    }

    this.visibleParticipants = _.filter(this.participants, scope.getFilter).length;

    this.onlyParticipationColumns = function(eventFeatures) {
        return _.filter(eventFeatures, function(eventFeature) {
            return eventFeature.eventFeatureCategory === 'PARTICIPATION';
        })
    };

    this.checkVisibilityForEventFeature = function(id) {
        var feature = Event.getEventFeature(id);
        if(feature.eventFeatureType == 'DINNER') {
            return this.config.view[feature.eventItem.name];
        }
        if(feature.eventFeatureType == 'PARTICIPANT_STATUS' || feature.eventFeatureType == 'SELECTION') {
            return this.config.view[feature.label];
        }
    };

    this.setOrder = function (order) {
        var removedOrder = _.remove(this.config.order, function (o) {
            return o.name === order;
        });
        var newAscending;
        if (removedOrder.length === 1) {
            if (removedOrder[0].ascending === '+') {
                newAscending = '-';
                this.config.order.push({name: order, ascending: newAscending});
            }
        } else {
            newAscending = '+';
            this.config.order.push({name: order, ascending: newAscending});
        }
    };

    this.headerClass = function (order) {
        var found = _.find(this.config.order, function (o) {
            return o.name === order;
        });
        if (found) {
            if (found.ascending === '+') {
                return 'glyphicon-sort-by-alphabet';
            } else {
                return 'glyphicon-sort-by-alphabet-alt';
            }
        } else {
            return 'empty';
        }

    };


    this.getOrder = function () {
        return _.map(this.config.order, function (order) {
            return order.ascending + order.name
        });
    };
    this.getFilter = function (participant) {
        var filterExpression = false;
        var filterFunction = function(arg) {
            _.forEach(arg, function(p) {
                if (p != null && _.isObject(p)) {
                    filterFunction(p);
                } else if (_.isString(p)) {
                    filterExpression = filterExpression || p.toLowerCase().includes(scope.stringFilter.toLowerCase());
                }
            });
        };
        var eventSelectionFunction = function(selection) {
            _.forEach(selection, function(s) {
                var eventFeature = Event.getEventFeature(s.eventFeatureId);
                if((s.eventFeature === 'PARTICIPANT_STATUS' && s.participantStatus) ||
                    (s.eventFeature === 'SELECTION' && s.participantStatus)) {
                    filterExpression = filterExpression || s.value.toLowerCase().includes(scope.stringFilter.toLowerCase());
                }
                if(s.eventFeature === 'DINNER') {
                    if(scope.stringFilter && scope.stringFilter.substr(0, 1) === '!') {
                        filterExpression = filterExpression || (eventFeature.eventItem.name.toLowerCase().includes(scope.stringFilter.toLowerCase().replace('!','')) && !s.checked);
                    } else {
                        filterExpression = filterExpression || (eventFeature.eventItem.name.toLowerCase().includes(scope.stringFilter.toLowerCase()) && s.checked);
                    }
                }
            });
        };

        filterExpression = filterExpression || participant.dateOfRegistration.includes(scope.stringFilter);
        filterExpression = filterExpression || participant.note.includes(scope.stringFilter);
        filterFunction(participant.person);
        eventSelectionFunction(participant.eventSelections);
        if(scope.cancelled) {
            filterExpression = filterExpression && participant.active;
        }


        if ((scope.stringFilter && scope.stringFilter !== "") || scope.cancelled) {
            return filterExpression;
        } else {
            return true;
        }
    };

    $scope.$watch(function() {
        return scope.config;
    }, function(value) {
        localStorageService.set(scope.event.identifier + '_participant_list_config', value)
    }, true);

    $scope.$watch(function() {
        return scope.stringFilter;
    }, function() {
        scope.visibleParticipants = _.filter(scope.participants, scope.getFilter).length;
    });

    this.enableParticipant = function(participant) {
        Participation.enableParticipation(participant.id).then(function() {
            participant.active = true;
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    this.cancelParticipant = function(participant) {
        Participation.cancelParticipation(participant.id).then(function() {
            participant.active = false;
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };

    this.updateNote = function(participant) {
        Participation.updateNote(participant.id, participant.note).then(function() {
        }, function(errors) {
            ErrorPropagationService.propagateErrorResponse(errors);
        });
    };
});
'use strict';


eventModule.controller('EventListController', function ($scope, $stateParams, Event) {
    this.order = [{
        name: 'event.name',
        ascending: '+'
    }];
    this.listBegin = 0;
    this.listEnd = 10;

    this.setOrder = function (order) {
        var removedOrder = _.remove(this.order, function (o) {
            return o.name === order;
        });
        var newAscending;
        if (removedOrder.length === 1) {
            if (removedOrder[0].ascending === '+') {
                newAscending = '-';
                this.order.push({name: order, ascending: newAscending});
            }
        } else {
            newAscending = '+';
            this.order.push({name: order, ascending: newAscending});
        }
    };

    this.headerClass = function (order) {
        var found = _.find(this.order, function (o) {
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
        return _.map(this.order, function (order) {
            return order.ascending + order.name
        });
    };
    this.getFilter = function (event) {
        var filterExpression = (event.name.includes(scope.stringFilter) ||
        event.startDate.includes(scope.stringFilter) ||
        event.regNumber.includes(scope.stringFilter));
        if (scope.stringFilter && scope.stringFilter !== "") {
            return filterExpression;
        } else {
            return true;
        }
    };

    var scope = this;
    Event.getAll(true).then(function(events) {
        scope.events = events;
    });
});
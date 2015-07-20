'use strict';

angular.module('jhipsterApp')
    .factory('Stock_airwaybill', function ($resource, DateUtils) {
        return $resource('api/stock_airwaybills/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

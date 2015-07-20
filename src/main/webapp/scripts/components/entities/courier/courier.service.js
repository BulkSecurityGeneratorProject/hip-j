'use strict';

angular.module('jhipsterApp')
    .factory('Courier', function ($resource, DateUtils) {
        return $resource('api/couriers/:id', {}, {
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

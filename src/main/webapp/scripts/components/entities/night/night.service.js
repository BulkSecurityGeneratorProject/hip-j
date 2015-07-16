'use strict';

angular.module('jhipsterApp')
    .factory('Night', function ($resource, DateUtils) {
        return $resource('api/nights/:id', {}, {
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

'use strict';

angular.module('jhipsterApp')
    .factory('Five', function ($resource, DateUtils) {
        return $resource('api/fives/:id', {}, {
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

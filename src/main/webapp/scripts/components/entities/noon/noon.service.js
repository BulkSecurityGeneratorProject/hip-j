'use strict';

angular.module('jhipsterApp')
    .factory('Noon', function ($resource, DateUtils) {
        return $resource('api/noons/:id', {}, {
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

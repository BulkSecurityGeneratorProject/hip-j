'use strict';

angular.module('jhipsterApp')
    .factory('Test03', function ($resource, DateUtils) {
        return $resource('api/test03s/:id', {}, {
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

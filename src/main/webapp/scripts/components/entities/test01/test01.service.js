'use strict';

angular.module('jhipsterApp')
    .factory('Test01', function ($resource, DateUtils) {
        return $resource('api/test01s/:id', {}, {
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

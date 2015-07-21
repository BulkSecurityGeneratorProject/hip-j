'use strict';

angular.module('jhipsterApp')
    .factory('Test02', function ($resource, DateUtils) {
        return $resource('api/test02s/:id', {}, {
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

'use strict';

angular.module('jhipsterApp')
    .factory('Three', function ($resource, DateUtils) {
        return $resource('api/threes/:id', {}, {
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

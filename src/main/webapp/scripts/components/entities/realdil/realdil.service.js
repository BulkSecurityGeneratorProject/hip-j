'use strict';

angular.module('jhipsterApp')
    .factory('Realdil', function ($resource, DateUtils) {
        return $resource('api/realdils/:id', {}, {
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

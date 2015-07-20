'use strict';

angular.module('jhipsterApp')
    .factory('Courier_preferences', function ($resource, DateUtils) {
        return $resource('api/courier_preferencess/:id', {}, {
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

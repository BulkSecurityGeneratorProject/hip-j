'use strict';

angular.module('jhipsterApp')
    .factory('Morn', function ($resource, DateUtils) {
        return $resource('api/morns/:id', {}, {
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

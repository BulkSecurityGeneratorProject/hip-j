'use strict';

angular.module('jhipsterApp')
    .factory('Hoohaa', function ($resource, DateUtils) {
        return $resource('api/hoohaas/:id', {}, {
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

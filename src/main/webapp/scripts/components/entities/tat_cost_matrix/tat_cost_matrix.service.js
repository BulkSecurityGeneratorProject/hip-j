'use strict';

angular.module('jhipsterApp')
    .factory('Tat_cost_matrix', function ($resource, DateUtils) {
        return $resource('api/tat_cost_matrixs/:id', {}, {
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

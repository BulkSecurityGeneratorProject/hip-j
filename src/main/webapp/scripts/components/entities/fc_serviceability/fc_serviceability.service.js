'use strict';

angular.module('jhipsterApp')
    .factory('Fc_serviceability', function ($resource, DateUtils) {
        return $resource('api/fc_serviceabilitys/:id', {}, {
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

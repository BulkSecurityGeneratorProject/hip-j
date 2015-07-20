'use strict';

angular.module('jhipsterApp')
    .factory('Fulfillment_center', function ($resource, DateUtils) {
        return $resource('api/fulfillment_centers/:id', {}, {
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

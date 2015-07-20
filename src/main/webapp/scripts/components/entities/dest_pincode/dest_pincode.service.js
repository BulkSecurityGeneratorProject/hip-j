'use strict';

angular.module('jhipsterApp')
    .factory('Dest_pincode', function ($resource, DateUtils) {
        return $resource('api/dest_pincodes/:id', {}, {
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

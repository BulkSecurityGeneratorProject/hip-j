'use strict';

angular.module('jhipsterApp')
    .factory('Courier_pincode_serviceability', function ($resource, DateUtils) {
        return $resource('api/courier_pincode_serviceabilitys/:id', {}, {
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

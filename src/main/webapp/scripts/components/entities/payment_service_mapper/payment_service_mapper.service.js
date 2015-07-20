'use strict';

angular.module('jhipsterApp')
    .factory('Payment_service_mapper', function ($resource, DateUtils) {
        return $resource('api/payment_service_mappers/:id', {}, {
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

'use strict';

angular.module('jhipsterApp')
    .factory('Payment_type', function ($resource, DateUtils) {
        return $resource('api/payment_types/:id', {}, {
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

'use strict';

angular.module('jhipsterApp')
    .factory('Shipment_transactions', function ($resource, DateUtils) {
        return $resource('api/shipment_transactionss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.time = DateUtils.convertDateTimeFromServer(data.time);
                    data.in_scan_time = DateUtils.convertDateTimeFromServer(data.in_scan_time);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

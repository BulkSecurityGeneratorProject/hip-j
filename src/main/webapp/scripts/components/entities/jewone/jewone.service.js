'use strict';

angular.module('jhipsterApp')
    .factory('Jewone', function ($resource, DateUtils) {
        return $resource('api/jewones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationTime = DateUtils.convertDateTimeFromServer(data.creationTime);
                    data.handoverTime = DateUtils.convertDateTimeFromServer(data.handoverTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

'use strict';

angular.module('jhipsterApp')
    .factory('Newone', function ($resource, DateUtils) {
        return $resource('api/newones/:id', {}, {
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

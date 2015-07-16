'use strict';

angular.module('jhipsterApp')
    .factory('Sssss', function ($resource, DateUtils) {
        return $resource('api/ssssss/:id', {}, {
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

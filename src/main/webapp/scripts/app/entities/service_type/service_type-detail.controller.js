'use strict';

angular.module('jhipsterApp')
    .controller('Service_typeDetailController', function ($scope, $stateParams, Service_type) {
        $scope.service_type = {};
        $scope.load = function (id) {
            Service_type.get({id: id}, function(result) {
              $scope.service_type = result;
            });
        };
        $scope.load($stateParams.id);
    });

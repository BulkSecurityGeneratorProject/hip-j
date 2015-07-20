'use strict';

angular.module('jhipsterApp')
    .controller('Courier_pincode_serviceabilityDetailController', function ($scope, $stateParams, Courier_pincode_serviceability, Courier, Payment_service_mapper) {
        $scope.courier_pincode_serviceability = {};
        $scope.load = function (id) {
            Courier_pincode_serviceability.get({id: id}, function(result) {
              $scope.courier_pincode_serviceability = result;
            });
        };
        $scope.load($stateParams.id);
    });

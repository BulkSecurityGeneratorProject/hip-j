'use strict';

angular.module('jhipsterApp')
    .controller('Payment_service_mapperDetailController', function ($scope, $stateParams, Payment_service_mapper, Payment_type, Service_type) {
        $scope.payment_service_mapper = {};
        $scope.load = function (id) {
            Payment_service_mapper.get({id: id}, function(result) {
              $scope.payment_service_mapper = result;
            });
        };
        $scope.load($stateParams.id);
    });

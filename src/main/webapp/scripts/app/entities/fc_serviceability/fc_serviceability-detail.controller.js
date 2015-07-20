'use strict';

angular.module('jhipsterApp')
    .controller('Fc_serviceabilityDetailController', function ($scope, $stateParams, Fc_serviceability, Fulfillment_center, Payment_service_mapper) {
        $scope.fc_serviceability = {};
        $scope.load = function (id) {
            Fc_serviceability.get({id: id}, function(result) {
              $scope.fc_serviceability = result;
            });
        };
        $scope.load($stateParams.id);
    });

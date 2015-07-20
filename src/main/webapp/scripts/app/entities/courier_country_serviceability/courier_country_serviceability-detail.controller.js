'use strict';

angular.module('jhipsterApp')
    .controller('Courier_country_serviceabilityDetailController', function ($scope, $stateParams, Courier_country_serviceability, Courier, Payment_service_mapper, Country) {
        $scope.courier_country_serviceability = {};
        $scope.load = function (id) {
            Courier_country_serviceability.get({id: id}, function(result) {
              $scope.courier_country_serviceability = result;
            });
        };
        $scope.load($stateParams.id);
    });

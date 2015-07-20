'use strict';

angular.module('jhipsterApp')
    .controller('CourierDetailController', function ($scope, $stateParams, Courier) {
        $scope.courier = {};
        $scope.load = function (id) {
            Courier.get({id: id}, function(result) {
              $scope.courier = result;
            });
        };
        $scope.load($stateParams.id);
    });

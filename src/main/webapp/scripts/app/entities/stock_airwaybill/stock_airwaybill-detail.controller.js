'use strict';

angular.module('jhipsterApp')
    .controller('Stock_airwaybillDetailController', function ($scope, $stateParams, Stock_airwaybill, Courier, Payment_type) {
        $scope.stock_airwaybill = {};
        $scope.load = function (id) {
            Stock_airwaybill.get({id: id}, function(result) {
              $scope.stock_airwaybill = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('Payment_typeDetailController', function ($scope, $stateParams, Payment_type) {
        $scope.payment_type = {};
        $scope.load = function (id) {
            Payment_type.get({id: id}, function(result) {
              $scope.payment_type = result;
            });
        };
        $scope.load($stateParams.id);
    });

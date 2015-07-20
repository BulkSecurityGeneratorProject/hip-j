'use strict';

angular.module('jhipsterApp')
    .controller('Tat_cost_matrixDetailController', function ($scope, $stateParams, Tat_cost_matrix, City, Courier, Payment_service_mapper) {
        $scope.tat_cost_matrix = {};
        $scope.load = function (id) {
            Tat_cost_matrix.get({id: id}, function(result) {
              $scope.tat_cost_matrix = result;
            });
        };
        $scope.load($stateParams.id);
    });

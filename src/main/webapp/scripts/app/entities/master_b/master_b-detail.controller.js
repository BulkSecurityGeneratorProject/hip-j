'use strict';

angular.module('jhipsterApp')
    .controller('Master_bDetailController', function ($scope, $stateParams, Master_b, Courier) {
        $scope.master_b = {};
        $scope.load = function (id) {
            Master_b.get({id: id}, function(result) {
              $scope.master_b = result;
            });
        };
        $scope.load($stateParams.id);
    });

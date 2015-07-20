'use strict';

angular.module('jhipsterApp')
    .controller('Master_roshiDetailController', function ($scope, $stateParams, Master_roshi, Courier) {
        $scope.master_roshi = {};
        $scope.load = function (id) {
            Master_roshi.get({id: id}, function(result) {
              $scope.master_roshi = result;
            });
        };
        $scope.load($stateParams.id);
    });

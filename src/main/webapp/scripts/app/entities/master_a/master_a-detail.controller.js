'use strict';

angular.module('jhipsterApp')
    .controller('Master_aDetailController', function ($scope, $stateParams, Master_a, Courier) {
        $scope.master_a = {};
        $scope.load = function (id) {
            Master_a.get({id: id}, function(result) {
              $scope.master_a = result;
            });
        };
        $scope.load($stateParams.id);
    });

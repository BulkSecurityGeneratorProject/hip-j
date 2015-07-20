'use strict';

angular.module('jhipsterApp')
    .controller('Master_cDetailController', function ($scope, $stateParams, Master_c, Courier) {
        $scope.master_c = {};
        $scope.load = function (id) {
            Master_c.get({id: id}, function(result) {
              $scope.master_c = result;
            });
        };
        $scope.load($stateParams.id);
    });

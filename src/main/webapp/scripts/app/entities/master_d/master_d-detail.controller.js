'use strict';

angular.module('jhipsterApp')
    .controller('Master_dDetailController', function ($scope, $stateParams, Master_d, Courier) {
        $scope.master_d = {};
        $scope.load = function (id) {
            Master_d.get({id: id}, function(result) {
              $scope.master_d = result;
            });
        };
        $scope.load($stateParams.id);
    });

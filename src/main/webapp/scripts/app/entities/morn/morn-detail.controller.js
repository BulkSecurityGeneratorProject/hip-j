'use strict';

angular.module('jhipsterApp')
    .controller('MornDetailController', function ($scope, $stateParams, Morn) {
        $scope.morn = {};
        $scope.load = function (id) {
            Morn.get({id: id}, function(result) {
              $scope.morn = result;
            });
        };
        $scope.load($stateParams.id);
    });

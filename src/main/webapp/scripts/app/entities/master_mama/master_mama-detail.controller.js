'use strict';

angular.module('jhipsterApp')
    .controller('Master_mamaDetailController', function ($scope, $stateParams, Master_mama, Courier) {
        $scope.master_mama = {};
        $scope.load = function (id) {
            Master_mama.get({id: id}, function(result) {
              $scope.master_mama = result;
            });
        };
        $scope.load($stateParams.id);
    });

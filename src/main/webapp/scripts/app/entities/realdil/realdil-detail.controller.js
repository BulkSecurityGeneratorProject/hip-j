'use strict';

angular.module('jhipsterApp')
    .controller('RealdilDetailController', function ($scope, $stateParams, Realdil) {
        $scope.realdil = {};
        $scope.load = function (id) {
            Realdil.get({id: id}, function(result) {
              $scope.realdil = result;
            });
        };
        $scope.load($stateParams.id);
    });

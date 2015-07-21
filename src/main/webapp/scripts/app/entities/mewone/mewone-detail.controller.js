'use strict';

angular.module('jhipsterApp')
    .controller('MewoneDetailController', function ($scope, $stateParams, Mewone, Courier) {
        $scope.mewone = {};
        $scope.load = function (id) {
            Mewone.get({id: id}, function(result) {
              $scope.mewone = result;
            });
        };
        $scope.load($stateParams.id);
    });

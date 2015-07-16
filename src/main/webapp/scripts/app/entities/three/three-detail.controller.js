'use strict';

angular.module('jhipsterApp')
    .controller('ThreeDetailController', function ($scope, $stateParams, Three) {
        $scope.three = {};
        $scope.load = function (id) {
            Three.get({id: id}, function(result) {
              $scope.three = result;
            });
        };
        $scope.load($stateParams.id);
    });

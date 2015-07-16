'use strict';

angular.module('jhipsterApp')
    .controller('FourDetailController', function ($scope, $stateParams, Four) {
        $scope.four = {};
        $scope.load = function (id) {
            Four.get({id: id}, function(result) {
              $scope.four = result;
            });
        };
        $scope.load($stateParams.id);
    });

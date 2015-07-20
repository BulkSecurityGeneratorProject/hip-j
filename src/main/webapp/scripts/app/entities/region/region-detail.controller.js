'use strict';

angular.module('jhipsterApp')
    .controller('RegionDetailController', function ($scope, $stateParams, Region) {
        $scope.region = {};
        $scope.load = function (id) {
            Region.get({id: id}, function(result) {
              $scope.region = result;
            });
        };
        $scope.load($stateParams.id);
    });

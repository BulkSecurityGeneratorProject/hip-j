'use strict';

angular.module('jhipsterApp')
    .controller('NoonDetailController', function ($scope, $stateParams, Noon) {
        $scope.noon = {};
        $scope.load = function (id) {
            Noon.get({id: id}, function(result) {
              $scope.noon = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('TwoDetailController', function ($scope, $stateParams, Two) {
        $scope.two = {};
        $scope.load = function (id) {
            Two.get({id: id}, function(result) {
              $scope.two = result;
            });
        };
        $scope.load($stateParams.id);
    });

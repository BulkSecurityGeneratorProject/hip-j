'use strict';

angular.module('jhipsterApp')
    .controller('Test02DetailController', function ($scope, $stateParams, Test02) {
        $scope.test02 = {};
        $scope.load = function (id) {
            Test02.get({id: id}, function(result) {
              $scope.test02 = result;
            });
        };
        $scope.load($stateParams.id);
    });

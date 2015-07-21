'use strict';

angular.module('jhipsterApp')
    .controller('Test05DetailController', function ($scope, $stateParams, Test05) {
        $scope.test05 = {};
        $scope.load = function (id) {
            Test05.get({id: id}, function(result) {
              $scope.test05 = result;
            });
        };
        $scope.load($stateParams.id);
    });

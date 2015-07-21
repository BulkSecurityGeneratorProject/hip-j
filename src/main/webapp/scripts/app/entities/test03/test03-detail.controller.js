'use strict';

angular.module('jhipsterApp')
    .controller('Test03DetailController', function ($scope, $stateParams, Test03) {
        $scope.test03 = {};
        $scope.load = function (id) {
            Test03.get({id: id}, function(result) {
              $scope.test03 = result;
            });
        };
        $scope.load($stateParams.id);
    });

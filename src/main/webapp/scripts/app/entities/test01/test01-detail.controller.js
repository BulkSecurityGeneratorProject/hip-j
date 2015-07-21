'use strict';

angular.module('jhipsterApp')
    .controller('Test01DetailController', function ($scope, $stateParams, Test01) {
        $scope.test01 = {};
        $scope.load = function (id) {
            Test01.get({id: id}, function(result) {
              $scope.test01 = result;
            });
        };
        $scope.load($stateParams.id);
    });

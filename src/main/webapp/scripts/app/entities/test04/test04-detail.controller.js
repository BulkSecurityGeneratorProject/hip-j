'use strict';

angular.module('jhipsterApp')
    .controller('Test04DetailController', function ($scope, $stateParams, Test04) {
        $scope.test04 = {};
        $scope.load = function (id) {
            Test04.get({id: id}, function(result) {
              $scope.test04 = result;
            });
        };
        $scope.load($stateParams.id);
    });

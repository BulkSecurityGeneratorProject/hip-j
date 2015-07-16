'use strict';

angular.module('jhipsterApp')
    .controller('FiveDetailController', function ($scope, $stateParams, Five) {
        $scope.five = {};
        $scope.load = function (id) {
            Five.get({id: id}, function(result) {
              $scope.five = result;
            });
        };
        $scope.load($stateParams.id);
    });

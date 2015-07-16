'use strict';

angular.module('jhipsterApp')
    .controller('OneDetailController', function ($scope, $stateParams, One) {
        $scope.one = {};
        $scope.load = function (id) {
            One.get({id: id}, function(result) {
              $scope.one = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('NewoneDetailController', function ($scope, $stateParams, Newone, Courier) {
        $scope.newone = {};
        $scope.load = function (id) {
            Newone.get({id: id}, function(result) {
              $scope.newone = result;
            });
        };
        $scope.load($stateParams.id);
    });

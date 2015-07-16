'use strict';

angular.module('jhipsterApp')
    .controller('SssssDetailController', function ($scope, $stateParams, Sssss) {
        $scope.sssss = {};
        $scope.load = function (id) {
            Sssss.get({id: id}, function(result) {
              $scope.sssss = result;
            });
        };
        $scope.load($stateParams.id);
    });

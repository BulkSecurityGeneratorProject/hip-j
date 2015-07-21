'use strict';

angular.module('jhipsterApp')
    .controller('JewoneDetailController', function ($scope, $stateParams, Jewone, Courier) {
        $scope.jewone = {};
        $scope.load = function (id) {
            Jewone.get({id: id}, function(result) {
              $scope.jewone = result;
            });
        };
        $scope.load($stateParams.id);
    });

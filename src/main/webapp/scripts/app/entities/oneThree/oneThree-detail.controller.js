'use strict';

angular.module('jhipsterApp')
    .controller('OneThreeDetailController', function ($scope, $stateParams, OneThree) {
        $scope.oneThree = {};
        $scope.load = function (id) {
            OneThree.get({id: id}, function(result) {
              $scope.oneThree = result;
            });
        };
        $scope.load($stateParams.id);
    });

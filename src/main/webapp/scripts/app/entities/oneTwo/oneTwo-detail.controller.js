'use strict';

angular.module('jhipsterApp')
    .controller('OneTwoDetailController', function ($scope, $stateParams, OneTwo) {
        $scope.oneTwo = {};
        $scope.load = function (id) {
            OneTwo.get({id: id}, function(result) {
              $scope.oneTwo = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('HoohaaDetailController', function ($scope, $stateParams, Hoohaa) {
        $scope.hoohaa = {};
        $scope.load = function (id) {
            Hoohaa.get({id: id}, function(result) {
              $scope.hoohaa = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('Master_chachaDetailController', function ($scope, $stateParams, Master_chacha, Courier) {
        $scope.master_chacha = {};
        $scope.load = function (id) {
            Master_chacha.get({id: id}, function(result) {
              $scope.master_chacha = result;
            });
        };
        $scope.load($stateParams.id);
    });

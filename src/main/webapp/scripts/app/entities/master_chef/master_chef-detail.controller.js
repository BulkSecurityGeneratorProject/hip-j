'use strict';

angular.module('jhipsterApp')
    .controller('Master_chefDetailController', function ($scope, $stateParams, Master_chef, Courier) {
        $scope.master_chef = {};
        $scope.load = function (id) {
            Master_chef.get({id: id}, function(result) {
              $scope.master_chef = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('Master_bagDetailController', function ($scope, $stateParams, Master_bag, Courier) {
        $scope.master_bag = {};
        $scope.load = function (id) {
            Master_bag.get({id: id}, function(result) {
              $scope.master_bag = result;
            });
        };
        $scope.load($stateParams.id);
    });

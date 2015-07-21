'use strict';

angular.module('jhipsterApp')
    .controller('Master_baggDetailController', function ($scope, $stateParams, Master_bagg, Courier) {
        $scope.master_bagg = {};
        $scope.load = function (id) {
            Master_bagg.get({id: id}, function(result) {
              $scope.master_bagg = result;
            });
        };
        $scope.load($stateParams.id);
    });

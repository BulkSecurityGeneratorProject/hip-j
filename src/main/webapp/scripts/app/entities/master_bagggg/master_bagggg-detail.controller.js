'use strict';

angular.module('jhipsterApp')
    .controller('Master_baggggDetailController', function ($scope, $stateParams, Master_bagggg, Courier) {
        $scope.master_bagggg = {};
        $scope.load = function (id) {
            Master_bagggg.get({id: id}, function(result) {
              $scope.master_bagggg = result;
            });
        };
        $scope.load($stateParams.id);
    });

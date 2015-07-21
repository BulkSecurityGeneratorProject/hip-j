'use strict';

angular.module('jhipsterApp')
    .controller('Master_bagggDetailController', function ($scope, $stateParams, Master_baggg, Courier) {
        $scope.master_baggg = {};
        $scope.load = function (id) {
            Master_baggg.get({id: id}, function(result) {
              $scope.master_baggg = result;
            });
        };
        $scope.load($stateParams.id);
    });

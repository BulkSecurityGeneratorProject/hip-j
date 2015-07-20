'use strict';

angular.module('jhipsterApp')
    .controller('Fulfillment_centerDetailController', function ($scope, $stateParams, Fulfillment_center, City, State, Cluster) {
        $scope.fulfillment_center = {};
        $scope.load = function (id) {
            Fulfillment_center.get({id: id}, function(result) {
              $scope.fulfillment_center = result;
            });
        };
        $scope.load($stateParams.id);
    });

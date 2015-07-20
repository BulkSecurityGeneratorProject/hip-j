'use strict';

angular.module('jhipsterApp')
    .controller('Dest_pincodeDetailController', function ($scope, $stateParams, Dest_pincode, City, State, Region, Country, Cluster) {
        $scope.dest_pincode = {};
        $scope.load = function (id) {
            Dest_pincode.get({id: id}, function(result) {
              $scope.dest_pincode = result;
            });
        };
        $scope.load($stateParams.id);
    });

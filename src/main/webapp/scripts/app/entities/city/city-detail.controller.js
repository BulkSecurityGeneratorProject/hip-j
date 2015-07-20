'use strict';

angular.module('jhipsterApp')
    .controller('CityDetailController', function ($scope, $stateParams, City, Region) {
        $scope.city = {};
        $scope.load = function (id) {
            City.get({id: id}, function(result) {
              $scope.city = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('jhipsterApp')
    .controller('CountryDetailController', function ($scope, $stateParams, Country) {
        $scope.country = {};
        $scope.load = function (id) {
            Country.get({id: id}, function(result) {
              $scope.country = result;
            });
        };
        $scope.load($stateParams.id);
    });

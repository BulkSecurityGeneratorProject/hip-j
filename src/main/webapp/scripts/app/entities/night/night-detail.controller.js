'use strict';

angular.module('jhipsterApp')
    .controller('NightDetailController', function ($scope, $stateParams, Night) {
        $scope.night = {};
        $scope.load = function (id) {
            Night.get({id: id}, function(result) {
              $scope.night = result;
            });
        };
        $scope.load($stateParams.id);
    });

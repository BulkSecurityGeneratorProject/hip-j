'use strict';

angular.module('jhipsterApp')
    .controller('StateDetailController', function ($scope, $stateParams, State) {
        $scope.state = {};
        $scope.load = function (id) {
            State.get({id: id}, function(result) {
              $scope.state = result;
            });
        };
        $scope.load($stateParams.id);
    });

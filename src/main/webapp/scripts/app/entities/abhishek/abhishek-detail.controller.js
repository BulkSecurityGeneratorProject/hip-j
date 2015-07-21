'use strict';

angular.module('jhipsterApp')
    .controller('AbhishekDetailController', function ($scope, $stateParams, Abhishek) {
        $scope.abhishek = {};
        $scope.load = function (id) {
            Abhishek.get({id: id}, function(result) {
              $scope.abhishek = result;
            });
        };
        $scope.load($stateParams.id);
    });

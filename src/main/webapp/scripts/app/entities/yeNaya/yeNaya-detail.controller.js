'use strict';

angular.module('jhipsterApp')
    .controller('YeNayaDetailController', function ($scope, $stateParams, YeNaya) {
        $scope.yeNaya = {};
        $scope.load = function (id) {
            YeNaya.get({id: id}, function(result) {
              $scope.yeNaya = result;
            });
        };
        $scope.load($stateParams.id);
    });

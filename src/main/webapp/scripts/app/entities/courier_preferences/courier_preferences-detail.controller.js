'use strict';

angular.module('jhipsterApp')
    .controller('Courier_preferencesDetailController', function ($scope, $stateParams, Courier_preferences, Cluster, Payment_service_mapper, Courier) {
        $scope.courier_preferences = {};
        $scope.load = function (id) {
            Courier_preferences.get({id: id}, function(result) {
              $scope.courier_preferences = result;
            });
        };
        $scope.load($stateParams.id);
    });

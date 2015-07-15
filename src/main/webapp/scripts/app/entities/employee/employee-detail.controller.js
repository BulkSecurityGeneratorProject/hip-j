'use strict';

angular.module('jhipsterApp')
    .controller('EmployeeDetailController', function ($scope, $stateParams, Employee) {
        $scope.employee = {};
        $scope.load = function (id) {
            Employee.get({id: id}, function(result) {
              $scope.employee = result;
            });
        };
        $scope.load($stateParams.id);
    });

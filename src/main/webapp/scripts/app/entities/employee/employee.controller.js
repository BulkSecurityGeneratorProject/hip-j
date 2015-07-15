'use strict';

angular.module('jhipsterApp')
  .controller('EmployeeController', function($scope, Employee) {
    window.scope = $scope;

    $scope.users = [{
      id: 1,
      name: 'awesome user1',
      status: 2,
      group: 4,
      groupName: 'admin'
    }, {
      id: 2,
      name: 'awesome user2',
      status: undefined,
      group: 3,
      groupName: 'vip'
    }, {
      id: 3,
      name: 'awesome user3',
      status: 2,
      group: null
    }];

    $scope.statuses = [{
      value: 1,
      text: 'status1'
    }, {
      value: 2,
      text: 'status2'
    }, {
      value: 3,
      text: 'status3'
    }, {
      value: 4,
      text: 'status4'
    }];

    $scope.employees = [];
    $scope.loadAll = function() {
      Employee.query(function(result) {
        $scope.employees = result;
      });
    };
    $scope.loadAll();

    $scope.user = {
      firstname: null,
      lastname: null,
      age: null,
      email: null,
      id: null
    };

    $scope.showUpdate = function(id) {
      Employee.get({
        id: id
      }, function(result) {
        $scope.employee = result;
        $('#saveEmployeeModal').modal('show');
      });
    };

    $scope.saveUser = function(a, b) {
      Employee.update(a,
        function() {
          $scope.refresh();
        });
      var x = 1;
    };

    $scope.save = function() {
      if ($scope.user.id != null) {
        Employee.update($scope.user,
          function() {
            $scope.refresh();
          });
      } else {
        var newObj = $scope.user;
        Employee.save(newObj,
          function() {
            $scope.refresh();
          });
      }
    };
    // $scope.save = function() {
    //   if ($scope.employee.id != null) {
    //     Employee.update($scope.employee,
    //       function() {
    //         $scope.refresh();
    //       });
    //   } else {
    //     var newObj = $scope.employee;
    //     Employee.save(newObj,
    //       function() {
    //         $scope.refresh();
    //       });
    //     // Employee.save($scope.employee,
    //     //   function() {
    //     //     $scope.refresh();
    //     //   });
    //   }
    // };

    $scope.save2 = function() {
      if ($scope.employee.id != null) {
        Employee.update($scope.employee,
          function() {
            $scope.refresh();
          });
      } else {
        Employee.save($scope.employee,
          function() {
            $scope.refresh();
          });
      }
    };

    $scope.delete = function(id) {
      Employee.get({
        id: id
      }, function(result) {
        $scope.employee = result;
        $('#deleteEmployeeConfirmation').modal('show');
      });
    };

    $scope.confirmDelete = function(id) {
      Employee.delete({
          id: id
        },
        function() {
          $scope.loadAll();
          $('#deleteEmployeeConfirmation').modal('hide');
          $scope.clear();
        });
    };

    $scope.refresh = function() {
      $scope.loadAll();
      $('#saveEmployeeModal').modal('hide');
      $scope.clear();
    };

    $scope.clear = function() {
      $scope.employee = {
        firstname: null,
        lastname: null,
        age: null,
        email: null,
        id: null
      };
      $scope.editForm.$setPristine();
      $scope.editForm.$setUntouched();
    };
  });

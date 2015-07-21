'use strict';

angular.module('jhipsterApp')
    .controller('EmployeeController', function($http, $scope, Employee) {

        $scope.employees = [];
        $scope.loadAll = function() {
            Employee.query(function(result) {
                $scope.employees = result;
            });
        };
        $scope.loadAll();

        $scope.gridOptions = {
            enableFiltering: true,
            data: 'employees',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true
            }, {
                field: "firstname",
                displayName: "First Name",
                headerCellClass: $scope.highlightFilteredHeader
            }, {
                field: "lastname",
                displayName: "Last Name"
            }, {
                field: "age",
                displayName: "Age"
            }, {
                field: "email",
                displayName: "Email"
            }, {
                name: 'asdf',
                displayName: "Delete",
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity.id)" >Delete</button> '
            }],
        };

        $scope.edit = function(id) {
            console.log(id);
            Employee.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $scope.clear();
                });
        }

        $scope.gridOptions.onRegisterApi = function(gridApi) {
            $scope.gridApi = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                Employee.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

        $scope.employee = {
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
            if ($scope.employee.id != null) {
                Employee.update($scope.employee,
                    function() {
                        $scope.refresh();
                    });
            } else {
                var newObj = $scope.employee;
                Employee.save(newObj,
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

        $scope.highlightFilteredHeader = function(row, rowRenderIndex, col, colRenderIndex) {
            if (col.filters[0].term) {
                return 'header-filtered';
            } else {
                return '';
            }
        };


    });

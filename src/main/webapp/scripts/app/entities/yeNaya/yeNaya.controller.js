'use strict';

angular.module('jhipsterApp')
    .controller('YeNayaController', function ($scope, YeNaya) {
        $scope.yeNayas = [];
        $scope.loadAll = function() {
            YeNaya.query(function(result) {
               $scope.yeNayas = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            YeNaya.get({id: id}, function(result) {
                $scope.yeNaya = result;
                $('#saveYeNayaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.yeNaya.id != null) {
                YeNaya.update($scope.yeNaya,
                    function () {
                        $scope.refresh();
                    });
            } else {
                YeNaya.save($scope.yeNaya,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            YeNaya.get({id: id}, function(result) {
                $scope.yeNaya = result;
                $('#deleteYeNayaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            YeNaya.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteYeNayaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveYeNayaModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yeNaya = {firstname: null, lastname: null, age: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'yeNayas',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, field: "firstname",displayName: "First Name"

            }, field: "lastname",displayName: "First Name"

            }, field: "age",displayName: "First Name"

            }, field: "email",displayName: "First Name"

            }, {
                name: '',
                displayName: "Delete",
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity.id)" >Delete</button> '
            }],
        };

        $scope.gridOptions.onRegisterApi = function(gridApi) {
            $scope.gridApi = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                Employee.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

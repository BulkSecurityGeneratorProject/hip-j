'use strict';

angular.module('jhipsterApp')
    .controller('OneController', function ($scope, One) {
        $scope.ones = [];
        $scope.loadAll = function() {
            One.query(function(result) {
               $scope.ones = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            One.get({id: id}, function(result) {
                $scope.one = result;
                $('#saveOneModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.one.id != null) {
                One.update($scope.one,
                    function () {
                        $scope.refresh();
                    });
            } else {
                One.save($scope.one,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            One.get({id: id}, function(result) {
                $scope.one = result;
                $('#deleteOneConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            One.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOneConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveOneModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.one = {a: null, b: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'ones',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "a",
                displayName: "First Name"

            }, {
                field: "b",
                displayName: "First Name"

            }, {
                name: 'asdf',
                displayName: "Delete",
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)" >Delete</button> '
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

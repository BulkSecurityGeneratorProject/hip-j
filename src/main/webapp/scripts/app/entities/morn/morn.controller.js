'use strict';

angular.module('jhipsterApp')
    .controller('MornController', function ($scope, Morn) {
        $scope.morns = [];
        $scope.loadAll = function() {
            Morn.query(function(result) {
               $scope.morns = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Morn.get({id: id}, function(result) {
                $scope.morn = result;
                $('#saveMornModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.morn.id != null) {
                Morn.update($scope.morn,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Morn.save($scope.morn,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Morn.get({id: id}, function(result) {
                $scope.morn = result;
                $('#deleteMornConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Morn.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMornConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMornModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.morn = {first: null, last: null, age: null, email: null, papa: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'morns',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "first",
                displayName: "First Name"

            }, {
                field: "last",
                displayName: "First Name"

            }, {
                field: "age",
                displayName: "First Name"

            }, {
                field: "email",
                displayName: "First Name"

            }, {
                field: "papa",
                displayName: "First Name"

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

'use strict';

angular.module('jhipsterApp')
    .controller('SssssController', function ($scope, Sssss) {
        $scope.ssssss = [];
        $scope.loadAll = function() {
            Sssss.query(function(result) {
               $scope.ssssss = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Sssss.get({id: id}, function(result) {
                $scope.sssss = result;
                $('#saveSssssModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.sssss.id != null) {
                Sssss.update($scope.sssss,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Sssss.save($scope.sssss,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Sssss.get({id: id}, function(result) {
                $scope.sssss = result;
                $('#deleteSssssConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Sssss.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSssssConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveSssssModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.sssss = {wwewwe: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'ssssss',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {field:
    fields[fieldId].fieldName,displayName: "First Name"
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

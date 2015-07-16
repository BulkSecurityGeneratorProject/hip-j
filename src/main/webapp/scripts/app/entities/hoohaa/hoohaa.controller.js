'use strict';

angular.module('jhipsterApp')
    .controller('HoohaaController', function ($scope, Hoohaa) {
        $scope.hoohaas = [];
        $scope.loadAll = function() {
            Hoohaa.query(function(result) {
               $scope.hoohaas = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Hoohaa.get({id: id}, function(result) {
                $scope.hoohaa = result;
                $('#saveHoohaaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.hoohaa.id != null) {
                Hoohaa.update($scope.hoohaa,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Hoohaa.save($scope.hoohaa,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Hoohaa.get({id: id}, function(result) {
                $scope.hoohaa = result;
                $('#deleteHoohaaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Hoohaa.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHoohaaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveHoohaaModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hoohaa = {a: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'hoohaas',
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

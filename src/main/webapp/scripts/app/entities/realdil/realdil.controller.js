'use strict';

angular.module('jhipsterApp')
    .controller('RealdilController', function ($scope, Realdil) {
        $scope.realdils = [];
        $scope.loadAll = function() {
            Realdil.query(function(result) {
               $scope.realdils = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Realdil.get({id: id}, function(result) {
                $scope.realdil = result;
                $('#saveRealdilModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.realdil.id != null) {
                Realdil.update($scope.realdil,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Realdil.save($scope.realdil,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Realdil.get({id: id}, function(result) {
                $scope.realdil = result;
                $('#deleteRealdilConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Realdil.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRealdilConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveRealdilModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.realdil = {firstname: null, lastname: null, age: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'realdils',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, field:
    fields[fieldId].fieldName,displayName: "First Name"
            }, {field:
    fields[fieldId].fieldName,displayName: "First Name"
            }, {field:
    fields[fieldId].fieldName,displayName: "First Name"
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

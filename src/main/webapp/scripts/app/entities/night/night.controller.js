'use strict';

angular.module('jhipsterApp')
    .controller('NightController', function ($scope, Night) {
        window.scope = $scope;
        $scope.nights = [];
        $scope.loadAll = function() {
            Night.query(function(result) {
               $scope.nights = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Night.get({id: id}, function(result) {
                $scope.night = result;
                $('#saveNightModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.night.id != null) {
                Night.update($scope.night,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Night.save($scope.night,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Night.get({id: id}, function(result) {
                $scope.night = result;
                $('#deleteNightConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Night.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNightConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveNightModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.night = {first: null, last: null, age: null, email: null, mummy: null, number: null, id: null};
            // $scope.editForm.$setPristine();
            // $scope.editForm.$setUntouched();
        };

        $scope.clear();
        $scope.gridOptions = {
            data: 'nights',
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
                field: "mummy",
                displayName: "First Name"

            }, {
                field: "number",
                displayName: "First Name"

            }, {
                name: 'asdf',
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

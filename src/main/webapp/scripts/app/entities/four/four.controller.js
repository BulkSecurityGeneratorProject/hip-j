'use strict';

angular.module('jhipsterApp')
    .controller('FourController', function ($scope, Four) {
        $scope.fours = [];
        $scope.loadAll = function() {
            Four.query(function(result) {
               $scope.fours = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Four.get({id: id}, function(result) {
                $scope.four = result;
                $('#saveFourModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.four.id != null) {
                Four.update($scope.four,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Four.save($scope.four,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Four.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveFourModal').modal('hide');
            $scope.clear();
        };

        $scope.four = {firstname: null, lastname: null, age: null, email: null, country: null, id: null};

        $scope.clear = function () {
            $scope.four = {firstname: null, lastname: null, age: null, email: null, country: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'fours',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "firstname",
                displayName: "First Name"

            }, {
                field: "lastname",
                displayName: "First Name"

            }, {
                field: "age",
                displayName: "First Name"

            }, {
                field: "email",
                displayName: "First Name"

            }, {
                field: "country",
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
                four.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

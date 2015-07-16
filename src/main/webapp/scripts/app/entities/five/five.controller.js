'use strict';

angular.module('jhipsterApp')
    .controller('FiveController', function ($scope, Five) {
        $scope.fives = [];
        $scope.loadAll = function() {
            Five.query(function(result) {
               $scope.fives = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Five.get({id: id}, function(result) {
                $scope.five = result;
                $('#saveFiveModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.five.id != null) {
                Five.update($scope.five,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Five.save($scope.five,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Five.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveFiveModal').modal('hide');
            $scope.clear();
        };

        $scope.five = {a: null, s: null, d: null, f: null, id: null};

        $scope.clear = function () {
            $scope.five = {a: null, s: null, d: null, f: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'fives',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "a",
                displayName: "First Name"

            }, {
                field: "s",
                displayName: "First Name"

            }, {
                field: "d",
                displayName: "First Name"

            }, {
                field: "f",
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
                Five.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

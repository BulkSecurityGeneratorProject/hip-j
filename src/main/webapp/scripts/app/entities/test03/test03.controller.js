'use strict';

angular.module('jhipsterApp')
    .controller('Test03Controller', function ($scope, Test03) {
        $scope.test03s = [];
        $scope.loadAll = function() {
            Test03.query(function(result) {
               $scope.test03s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test03.get({id: id}, function(result) {
                $scope.test03 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test03.id != null) {
                Test03.update($scope.test03,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test03.save($scope.test03,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Test03.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test03 = {asdf: null, id: null};

        $scope.clear = function () {
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'test03s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test03.update(rowEntity,
                        function() {
                            $scope.refresh();
                        });
                });
            },
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "asdf",
                displayName: "asdf"


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                enableFiltering: false,
                cellTemplate: '<button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button>'
            }],
        };
    });

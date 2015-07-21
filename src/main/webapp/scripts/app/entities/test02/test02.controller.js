'use strict';

angular.module('jhipsterApp')
    .controller('Test02Controller', function ($scope, Test02) {
        $scope.test02s = [];
        $scope.loadAll = function() {
            Test02.query(function(result) {
               $scope.test02s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test02.get({id: id}, function(result) {
                $scope.test02 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test02.id != null) {
                Test02.update($scope.test02,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test02.save($scope.test02,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Test02.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test02 = {asdf: null, zxcv: null, qwer: null, id: null};

        $scope.clear = function () {
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'test02s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test02.update(rowEntity,
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
                field: "zxcv",
                displayName: "zxcv"

            }, {
                field: "qwer",
                displayName: "qwer"


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

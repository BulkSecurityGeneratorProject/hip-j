'use strict';

angular.module('jhipsterApp')
    .controller('Test01Controller', function ($scope, Test01) {
        $scope.test01s = [];
        $scope.loadAll = function() {
            Test01.query(function(result) {
               $scope.test01s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test01.get({id: id}, function(result) {
                $scope.test01 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test01.id != null) {
                Test01.update($scope.test01,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test01.save($scope.test01,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Test01.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test01 = {asdf: null, qwer: null, id: null};

        $scope.clear = function () {
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'test01s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test01.update(rowEntity,
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
                field: "qwer",
                displayName: "qwer"


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                cellTemplate: '<button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button>'
            }],
        };
    });

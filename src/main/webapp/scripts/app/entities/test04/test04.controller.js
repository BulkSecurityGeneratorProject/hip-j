'use strict';

angular.module('jhipsterApp')
    .controller('Test04Controller', function ($scope, Test04) {
        $scope.test04s = [];
        $scope.loadAll = function() {
            Test04.query(function(result) {
               $scope.test04s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test04.get({id: id}, function(result) {
                $scope.test04 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test04.id != null) {
                Test04.update($scope.test04,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test04.save($scope.test04,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test04 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test04.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test04 = {qwer: null, asdf: null, zxcv: null, id: null};

        $scope.clear = function () {
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'test04s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test04.update(rowEntity,
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
                field: "qwer",
                displayName: "qwer"

            }, {
                field: "asdf",
                displayName: "asdf"

            }, {
                field: "zxcv",
                displayName: "zxcv"


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

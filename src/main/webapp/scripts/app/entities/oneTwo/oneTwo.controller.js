'use strict';

angular.module('jhipsterApp')
    .controller('OneTwoController', function ($scope, OneTwo) {
        $scope.oneTwos = [];
        $scope.loadAll = function() {
            OneTwo.query(function(result) {
               $scope.oneTwos = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            OneTwo.get({id: id}, function(result) {
                $scope.oneTwo = result;
                $('#saveOneTwoModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.oneTwo.id != null) {
                OneTwo.update($scope.oneTwo,
                    function () {
                        $scope.refresh();
                    });
            } else {
                OneTwo.save($scope.oneTwo,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            OneTwo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveOneTwoModal').modal('hide');
            $scope.clear();
        };

        $scope.oneTwo = {one: null, id: null};

        $scope.clear = function () {
            $scope.oneTwo = {one: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'oneTwos',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "one",
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
                OneTwo.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

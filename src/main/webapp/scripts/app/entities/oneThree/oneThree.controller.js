'use strict';

angular.module('jhipsterApp')
    .controller('OneThreeController', function ($scope, OneThree) {
        $scope.oneThrees = [];
        $scope.loadAll = function() {
            OneThree.query(function(result) {
               $scope.oneThrees = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            OneThree.get({id: id}, function(result) {
                $scope.oneThree = result;
                $('#saveOneThreeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.oneThree.id != null) {
                OneThree.update($scope.oneThree,
                    function () {
                        $scope.refresh();
                    });
            } else {
                OneThree.save($scope.oneThree,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            OneThree.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveOneThreeModal').modal('hide');
            $scope.clear();
        };

        $scope.oneThree = {threeFour: null, id: null};

        $scope.clear = function () {
            $scope.oneThree = {threeFour: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'oneThrees',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "threeFour",
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
                OneThree.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

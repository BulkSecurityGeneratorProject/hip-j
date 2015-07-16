'use strict';

angular.module('jhipsterApp')
    .controller('ThreeController', function ($scope, Three) {
        $scope.threes = [];
        $scope.loadAll = function() {
            Three.query(function(result) {
               $scope.threes = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Three.get({id: id}, function(result) {
                $scope.three = result;
                $('#saveThreeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.three.id != null) {
                Three.update($scope.three,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Three.save($scope.three,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Three.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveThreeModal').modal('hide');
            $scope.clear();
        };

        $scope.three = {name: null, country: null, age: null, matches: null, catches: null, email: null, id: null};

        $scope.clear = function () {
            $scope.three = {name: null, country: null, age: null, matches: null, catches: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'threes',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "name",
                displayName: "First Name"

            }, {
                field: "country",
                displayName: "First Name"

            }, {
                field: "age",
                displayName: "First Name"

            }, {
                field: "matches",
                displayName: "First Name"

            }, {
                field: "catches",
                displayName: "First Name"

            }, {
                field: "email",
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
                Employee.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

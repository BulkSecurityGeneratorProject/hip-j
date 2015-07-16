'use strict';

angular.module('jhipsterApp')
    .controller('NoonController', function ($scope, Noon) {
        window.scope = $scope;
        $scope.noons = [];
        $scope.loadAll = function() {
            Noon.query(function(result) {
               $scope.noons = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Noon.get({id: id}, function(result) {
                $scope.noon = result;
                $('#saveNoonModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.noon.id != null) {
                Noon.update($scope.noon,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Noon.save($scope.noon,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Noon.get({id: id}, function(result) {
                $scope.noon = result;
                $('#deleteNoonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Noon.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNoonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveNoonModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.noon = {first: null, last: null, age: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.clear();
        $scope.gridOptions = {
            data: 'noons',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "first",
                displayName: "First Name"
            }

            }, {
                field: "last",
                displayName: "First Name"
            }

            }, {
                field: "age",
                displayName: "First Name"
            }

            }, {
                field: "email",
                displayName: "First Name"
            }

            }, {
                name: '',
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

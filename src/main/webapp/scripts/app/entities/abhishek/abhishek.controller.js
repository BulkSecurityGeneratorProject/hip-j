'use strict';

angular.module('jhipsterApp')
    .controller('AbhishekController', function($scope, Abhishek) {
        $scope.abhisheks = [];
        $scope.loadAll = function() {
            Abhishek.query(function(result) {
                $scope.abhisheks = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function(id) {
            Abhishek.get({
                id: id
            }, function(result) {
                $scope.abhishek = result;
                $('#saveAbhishekModal').modal('show');
            });
        };

        $scope.save = function() {
            if ($scope.abhishek.id != null) {
                Abhishek.update($scope.abhishek,
                    function() {
                        $scope.refresh();
                    });
            } else {
                Abhishek.save($scope.abhishek,
                    function() {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function(id) {
            Abhishek.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function() {
            $scope.loadAll();
            $('#saveAbhishekModal').modal('hide');
            $scope.clear();
        };

        $scope.abhishek = {
            firstname: null,
            lastname: null,
            age: null,
            id: null
        };

        $scope.clear = function() {
            $scope.abhishek = {
                firstname: null,
                lastname: null,
                age: null,
                id: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            enableFiltering: true,
            data: 'abhisheks',
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
                Abhishek.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

'use strict';

angular.module('jhipsterApp')
    .controller('TwoController', function ($scope, Two) {
        $scope.twos = [];
        $scope.loadAll = function() {
            Two.query(function(result) {
               $scope.twos = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Two.get({id: id}, function(result) {
                $scope.two = result;
                $('#saveTwoModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.two.id != null) {
                Two.update($scope.two,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Two.save($scope.two,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Two.get({id: id}, function(result) {
                $scope.two = result;
                $('#deleteTwoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Two.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTwoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveTwoModal').modal('hide');
            $scope.clear();
        };

        $scope.two = {first: null, last: null, age: null, email: null, oye: null, eyo: null, id: null};

        $scope.clear = function () {
            $scope.two = {first: null, last: null, age: null, email: null, oye: null, eyo: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'twos',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "first",
                displayName: "First Name"

            }, {
                field: "last",
                displayName: "First Name"

            }, {
                field: "age",
                displayName: "First Name"

            }, {
                field: "email",
                displayName: "First Name"

            }, {
                field: "oye",
                displayName: "First Name"

            }, {
                field: "eyo",
                displayName: "First Name"

            }, {
                name: 'asdf',
                displayName: "Delete",
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.confirmDelete(row.entity.id)" >Delete</button> '
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

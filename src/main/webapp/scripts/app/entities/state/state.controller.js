'use strict';

angular.module('jhipsterApp')
    .controller('StateController', function ($scope, State, ParseLinks) {
        $scope.states = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            State.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.states = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            State.get({id: id}, function(result) {
                $scope.state = result;
                $('#saveStateModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.state.id != null) {
                State.update($scope.state,
                    function () {
                        $scope.refresh();
                    });
            } else {
                State.save($scope.state,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            State.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveStateModal').modal('hide');
            $scope.clear();
        };

        $scope.state = {code: null, id: null};

        $scope.clear = function () {
            $scope.state = {code: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'states',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "code",
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
                State.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

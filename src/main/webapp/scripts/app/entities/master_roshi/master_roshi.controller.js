'use strict';

angular.module('jhipsterApp')
    .controller('Master_roshiController', function ($scope, Master_roshi, Courier, ParseLinks) {
        $scope.master_roshis = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_roshi.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_roshis = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_roshi.get({id: id}, function(result) {
                $scope.master_roshi = result;
                $('#saveMaster_roshiModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.master_roshi.id != null) {
                Master_roshi.update($scope.master_roshi,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_roshi.save($scope.master_roshi,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Master_roshi.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMaster_roshiModal').modal('hide');
            $scope.clear();
        };

        $scope.master_roshi = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_roshi = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'master_roshis',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "code",
                displayName: "First Name"

            }, {
                field: "creationTime",
                displayName: "First Name"

            }, {
                field: "handoverTime",
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
                Master_roshi.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

'use strict';

angular.module('jhipsterApp')
    .controller('RegionController', function ($scope, Region, ParseLinks) {
        $scope.regions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Region.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.regions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Region.get({id: id}, function(result) {
                $scope.region = result;
                $('#saveRegionModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.region.id != null) {
                Region.update($scope.region,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Region.save($scope.region,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Region.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveRegionModal').modal('hide');
            $scope.clear();
        };

        $scope.region = {code: null, name: null, promise_date_buffer: null, id: null};

        $scope.clear = function () {
            $scope.region = {code: null, name: null, promise_date_buffer: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'regions',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "code",
                displayName: "First Name"

            }, {
                field: "name",
                displayName: "First Name"

            }, {
                field: "promise_date_buffer",
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
                Region.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

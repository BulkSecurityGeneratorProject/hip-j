'use strict';

angular.module('jhipsterApp')
    .controller('ClusterController', function ($scope, Cluster, ParseLinks) {
        $scope.clusters = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Cluster.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.clusters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Cluster.get({id: id}, function(result) {
                $scope.cluster = result;
                $('#saveClusterModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.cluster.id != null) {
                Cluster.update($scope.cluster,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Cluster.save($scope.cluster,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Cluster.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveClusterModal').modal('hide');
            $scope.clear();
        };

        $scope.cluster = {name: null, description: null, type: null, id: null};

        $scope.clear = function () {
            $scope.cluster = {name: null, description: null, type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'clusters',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "name",
                displayName: "First Name"

            }, {
                field: "description",
                displayName: "First Name"

            }, {
                field: "type",
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
                Cluster.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

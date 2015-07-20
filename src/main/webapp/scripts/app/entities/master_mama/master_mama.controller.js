'use strict';

angular.module('jhipsterApp')
    .controller('Master_mamaController', function ($scope, Master_mama, Courier, ParseLinks) {
        window.scope = $scope;
        $scope.master_mamas = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_mama.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_mamas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_mama.get({id: id}, function(result) {
                $scope.master_mama = result;
                $('#saveMaster_mamaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.master_mama.id != null) {
                Master_mama.update($scope.master_mama,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_mama.save($scope.master_mama,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Master_mama.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMaster_mamaModal').modal('hide');
            $scope.clear();
        };

        $scope.master_mama = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_mama = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'master_mamas',
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
                field: "courier.name",
                displayName: "ssssssssss"

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
                Master_mama.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

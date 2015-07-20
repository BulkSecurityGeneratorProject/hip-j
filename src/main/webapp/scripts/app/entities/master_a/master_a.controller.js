'use strict';

angular.module('jhipsterApp')
    .controller('Master_aController', function ($scope, Master_a, Courier, ParseLinks) {
        $scope.master_as = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_a.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_as = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_a.get({id: id}, function(result) {
                $scope.master_a = result;
                $('#saveMaster_aModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.master_a.id != null) {
                Master_a.update($scope.master_a,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_a.save($scope.master_a,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Master_a.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMaster_aModal').modal('hide');
            $scope.clear();
        };

        $scope.master_a = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_a = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'master_as',
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
                field: "0",
                displayName: "Dat Name"
      

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
                Master_a.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

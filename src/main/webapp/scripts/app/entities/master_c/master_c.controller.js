'use strict';

angular.module('jhipsterApp')
    .controller('Master_cController', function ($scope, Master_c, Courier, ParseLinks) {
        $scope.master_cs = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_c.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_cs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_c.get({id: id}, function(result) {
                $scope.master_c = result;
                $('#saveMaster_cModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.master_c.id != null) {
                Master_c.update($scope.master_c,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_c.save($scope.master_c,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Master_c.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMaster_cModal').modal('hide');
            $scope.clear();
        };

        $scope.master_c = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_c = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'master_cs',
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
                field: "name",
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
                Master_c.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

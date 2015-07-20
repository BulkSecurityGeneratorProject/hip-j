'use strict';

angular.module('jhipsterApp')
    .controller('Master_bagController', function ($scope, Master_bag, Courier, ParseLinks) {
        $scope.master_bags = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_bag.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_bags = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_bag.get({id: id}, function(result) {
                $scope.master_bag = result;
                $('#saveMaster_bagModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.master_bag.id != null) {
                Master_bag.update($scope.master_bag,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_bag.save($scope.master_bag,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Master_bag.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMaster_bagModal').modal('hide');
            $scope.clear();
        };

        $scope.master_bag = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_bag = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'master_bags',
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
                Master_bag.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

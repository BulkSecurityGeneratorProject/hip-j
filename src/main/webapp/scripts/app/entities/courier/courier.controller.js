'use strict';

angular.module('jhipsterApp')
    .controller('CourierController', function ($scope, Courier, ParseLinks) {
        $scope.couriers = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Courier.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.couriers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Courier.get({id: id}, function(result) {
                $scope.courier = result;
                $('#saveCourierModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.courier.id != null) {
                Courier.update($scope.courier,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Courier.save($scope.courier,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Courier.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCourierModal').modal('hide');
            $scope.clear();
        };

        $scope.courier = {name: null, daily_capacity: null, color_code: null, is_enabled: null, id: null};

        $scope.clear = function () {
            $scope.courier = {name: null, daily_capacity: null, color_code: null, is_enabled: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'couriers',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "name",
                displayName: "First Name"

            }, {
                field: "daily_capacity",
                displayName: "First Name"

            }, {
                field: "color_code",
                displayName: "First Name"

            }, {
                field: "is_enabled",
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
                Courier.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

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

        $scope.edit = function (entity) {
            $scope.courier = entity;
            $scope.showForm = true;
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
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Courier.update(rowEntity,
                        function() {
                            $scope.refresh();
                        });
                });
            },
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "name",
                displayName: "name"

            }, {
                field: "daily_capacity",
                displayName: "daily_capacity"

            }, {
                field: "color_code",
                displayName: "color_code"

            }, {
                field: "is_enabled",
                displayName: "is_enabled"


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                enableFiltering: false,
                cellTemplate: '<button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button>'
            }],
        };
    });

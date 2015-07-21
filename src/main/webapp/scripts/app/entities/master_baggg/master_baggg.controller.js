'use strict';

angular.module('jhipsterApp')
    .controller('Master_bagggController', function ($timeout, $q, $log, $scope, Master_baggg, Courier, ParseLinks) {
        window.scope = $scope;
        $scope.master_bagggs = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_baggg.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_bagggs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_baggg.get({id: id}, function(result) {
                $scope.master_baggg = result;
            });
        };

        $scope.save = function () {
            if ($scope.master_baggg.id != null) {
                Master_baggg.update($scope.master_baggg,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_baggg.save($scope.master_baggg,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.master_baggg = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Master_baggg.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.master_baggg = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_baggg = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
            $scope.ctrl.searchText = null;
            $scope.master_baggg.courier = null;
        };

        $scope.gridOptions = {
            data: 'master_bagggs',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Master_baggg.update(rowEntity,
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
                field: "code",
                displayName: "code"

            }, {
                field: "creationTime",
                displayName: "creationTime"

            }, {
                field: "handoverTime",
                displayName: "handoverTime"


            }, {
                field: "courier.name",
                displayName: "courier"

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

        $scope.ctrl = {};
        $scope.ctrl.simulateQuery = false;
        $scope.ctrl.isDisabled = false;
        $scope.ctrl.states = $scope.couriers;
        $scope.ctrl.querySearch = querySearch;

        function querySearch(query) {
            var results = query ? $scope.couriers.filter(createFilterFor(query)) : $scope.couriers;
            var results = query ? $scope.ctrl.states.filter(createFilterFor(query)) : $scope.ctrl.states;
            return results;
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.name.indexOf(lowercaseQuery) === 0);
            };
        }

    });

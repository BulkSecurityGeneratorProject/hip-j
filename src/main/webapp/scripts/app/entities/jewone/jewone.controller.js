'use strict';

angular.module('jhipsterApp')
    .controller('JewoneController', function ($scope, Jewone, Courier, ParseLinks) {
        window.scope = $scope;
        $scope.jewones = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Jewone.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jewones = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Jewone.get({id: id}, function(result) {
                $scope.jewone = result;
            });
        };

        $scope.save = function () {
            if ($scope.jewone.id != null) {
                Jewone.update($scope.jewone,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Jewone.save($scope.jewone,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.jewone = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Jewone.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.jewone = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.jewone = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

            $scope.ctrl.searchTextCourier = null;
            $scope.jewone.courier = null;
    
        };

        $scope.gridOptions = {
            data: 'jewones',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Jewone.update(rowEntity,
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
        $scope.ctrl.querySearch = querySearch;

        function querySearch(query, entity) {
            var results = query ? $scope[entity].filter(createFilterFor(query)) : $scope[entity];
            return results;
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.name.indexOf(lowercaseQuery) === 0);
            };
        }
    });

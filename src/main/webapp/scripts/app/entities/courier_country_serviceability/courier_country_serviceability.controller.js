'use strict';

angular.module('jhipsterApp')
    .controller('Courier_country_serviceabilityController', function ($scope, Courier_country_serviceability, Courier, Payment_service_mapper, Country, ParseLinks) {
        window.scope = $scope;
        $scope.courier_country_serviceabilitys = [];
        $scope.couriers = Courier.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.countrys = Country.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Courier_country_serviceability.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courier_country_serviceabilitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Courier_country_serviceability.get({id: id}, function(result) {
                $scope.courier_country_serviceability = result;
            });
        };

        $scope.save = function () {
            if ($scope.courier_country_serviceability.id != null) {
                Courier_country_serviceability.update($scope.courier_country_serviceability,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Courier_country_serviceability.save($scope.courier_country_serviceability,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.courier_country_serviceability = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Courier_country_serviceability.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.courier_country_serviceability = {is_enabled: null, shipment_capacity: null, id: null};

        $scope.clear = function () {
            $scope.courier_country_serviceability = {is_enabled: null, shipment_capacity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

            $scope.ctrl.searchTextCourier = null;
            $scope.courier_country_serviceability.courier = null;
    
            $scope.ctrl.searchTextPayment_service_mapper = null;
            $scope.courier_country_serviceability.payment_service_mapper = null;
    
            $scope.ctrl.searchTextCountry = null;
            $scope.courier_country_serviceability.country = null;
    
        };

        $scope.gridOptions = {
            data: 'courier_country_serviceabilitys',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Courier_country_serviceability.update(rowEntity,
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
                field: "is_enabled",
                displayName: "is_enabled"

            }, {
                field: "shipment_capacity",
                displayName: "shipment_capacity"


            }, {
                field: "courier.name",
                displayName: "courier"
    
            }, {
                field: "payment_service_mapper.description",
                displayName: "payment_service_mapper"
    
            }, {
                field: "country.name",
                displayName: "country"
    
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

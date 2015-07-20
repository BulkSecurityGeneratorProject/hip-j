'use strict';

angular.module('jhipsterApp')
    .controller('Courier_country_serviceabilityController', function($timeout, $q, $log, $scope, Courier_country_serviceability, Courier, Payment_service_mapper, Country, ParseLinks) {
        $scope.courier_country_serviceabilitys = [];
        $scope.couriers = Courier.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.countrys = Country.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Courier_country_serviceability.query({
                page: $scope.page,
                per_page: 20
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courier_country_serviceabilitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function(id) {
            Courier_country_serviceability.get({
                id: id
            }, function(result) {
                $scope.courier_country_serviceability = result;
                $('#saveCourier_country_serviceabilityModal').modal('show');
            });
        };

        $scope.save = function() {
            if ($scope.courier_country_serviceability.id != null) {
                Courier_country_serviceability.update($scope.courier_country_serviceability,
                    function() {
                        $scope.refresh();
                    });
            } else {
                Courier_country_serviceability.save($scope.courier_country_serviceability,
                    function() {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function(id) {
            Courier_country_serviceability.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function() {
            $scope.loadAll();
            $('#saveCourier_country_serviceabilityModal').modal('hide');
            $scope.clear();
        };

        $scope.courier_country_serviceability = {
            is_enabled: null,
            shipment_capacity: null,
            id: null
        };

        $scope.clear = function() {
            $scope.courier_country_serviceability = {
                is_enabled: null,
                shipment_capacity: null,
                id: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'courier_country_serviceabilitys',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "is_enabled",
                displayName: "First Name"

            }, {
                field: "shipment_capacity",
                displayName: "First Name"






            }, {
                field: "courier.name",
                displayName: "Dat Name"

            }, {
                field: "payment_service_mapper.description",
                displayName: "Dat Name"

            }, {
                field: "country.name",
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
                Courier_country_serviceability.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };
        var self = this;
        self.simulateQuery = false;
        self.isDisabled = false;
        // list of `state` value/display objects
        self.states = loadAll();
        self.querySearch = querySearch;
        self.selectedItemChange = selectedItemChange;
        self.searchTextChange = searchTextChange;
        // ******************************
        // Internal methods
        // ******************************
        /**
         * Search for states... use $timeout to simulate
         * remote dataservice call.
         */
        function querySearch(query) {
            var results = query ? self.states.filter(createFilterFor(query)) : self.states,
                deferred;
            if (self.simulateQuery) {
                deferred = $q.defer();
                $timeout(function() {
                    deferred.resolve(results);
                }, Math.random() * 1000, false);
                return deferred.promise;
            } else {
                return results;
            }
        }

        function searchTextChange(text) {
            $log.info('Text changed to ' + text);
        }

        function selectedItemChange(item) {
            $log.info('Item changed to ' + JSON.stringify(item));
        }
        /**
         * Build `states` list of key/value pairs
         */
        function loadAll() {
            var allStates = 'Alabama, Alaska, Arizona, Arkansas, California, Colorado, Connecticut, Delaware,\
              Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Louisiana,\
              Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana,\
              Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina,\
              North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina,\
              South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, West Virginia,\
              Wisconsin, Wyoming';
            return allStates.split(/, +/g).map(function(state) {
                return {
                    value: state.toLowerCase(),
                    display: state
                };
            });
        }
        /**
         * Create filter function for a query string
         */
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.value.indexOf(lowercaseQuery) === 0);
            };
        }




    });

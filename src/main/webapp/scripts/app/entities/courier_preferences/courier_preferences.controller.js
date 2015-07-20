'use strict';

angular.module('jhipsterApp')
    .controller('Courier_preferencesController', function ($scope, Courier_preferences, Cluster, Payment_service_mapper, Courier, ParseLinks) {
        $scope.courier_preferencess = [];
        $scope.clusters = Cluster.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Courier_preferences.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courier_preferencess = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Courier_preferences.get({id: id}, function(result) {
                $scope.courier_preferences = result;
                $('#saveCourier_preferencesModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.courier_preferences.id != null) {
                Courier_preferences.update($scope.courier_preferences,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Courier_preferences.save($scope.courier_preferences,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Courier_preferences.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCourier_preferencesModal').modal('hide');
            $scope.clear();
        };

        $scope.courier_preferences = {priority: null, shipment_limit: null, is_enabled: null, id: null};

        $scope.clear = function () {
            $scope.courier_preferences = {priority: null, shipment_limit: null, is_enabled: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'courier_preferencess',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "priority",
                displayName: "First Name"

            }, {
                field: "shipment_limit",
                displayName: "First Name"

            }, {
                field: "is_enabled",
                displayName: "First Name"






            }, {
                field: "cluster.name",
                displayName: "Dat Name"
      
            }, {
                field: "cluster.name",
                displayName: "Dat Name"
      
            }, {
                field: "payment_service_mapper.description",
                displayName: "Dat Name"
      
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
                Courier_preferences.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

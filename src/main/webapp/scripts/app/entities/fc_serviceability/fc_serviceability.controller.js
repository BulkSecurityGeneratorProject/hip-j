'use strict';

angular.module('jhipsterApp')
    .controller('Fc_serviceabilityController', function ($scope, Fc_serviceability, Fulfillment_center, Payment_service_mapper, ParseLinks) {
        $scope.fc_serviceabilitys = [];
        $scope.fulfillment_centers = Fulfillment_center.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Fc_serviceability.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fc_serviceabilitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Fc_serviceability.get({id: id}, function(result) {
                $scope.fc_serviceability = result;
                $('#saveFc_serviceabilityModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.fc_serviceability.id != null) {
                Fc_serviceability.update($scope.fc_serviceability,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Fc_serviceability.save($scope.fc_serviceability,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Fc_serviceability.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveFc_serviceabilityModal').modal('hide');
            $scope.clear();
        };

        $scope.fc_serviceability = {is_enabled: null, shipment_cutoff_time: null, id: null};

        $scope.clear = function () {
            $scope.fc_serviceability = {is_enabled: null, shipment_cutoff_time: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'fc_serviceabilitys',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "is_enabled",
                displayName: "First Name"

            }, {
                field: "shipment_cutoff_time",
                displayName: "First Name"






            }, {
                field: "fulfillment_center.name",
                displayName: "Dat Name"
      
            }, {
                field: "payment_service_mapper.description",
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
                Fc_serviceability.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

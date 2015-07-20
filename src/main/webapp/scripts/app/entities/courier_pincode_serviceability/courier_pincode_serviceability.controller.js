'use strict';

angular.module('jhipsterApp')
    .controller('Courier_pincode_serviceabilityController', function ($scope, Courier_pincode_serviceability, Courier, Payment_service_mapper, ParseLinks) {
        $scope.courier_pincode_serviceabilitys = [];
        $scope.couriers = Courier.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Courier_pincode_serviceability.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courier_pincode_serviceabilitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Courier_pincode_serviceability.get({id: id}, function(result) {
                $scope.courier_pincode_serviceability = result;
                $('#saveCourier_pincode_serviceabilityModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.courier_pincode_serviceability.id != null) {
                Courier_pincode_serviceability.update($scope.courier_pincode_serviceability,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Courier_pincode_serviceability.save($scope.courier_pincode_serviceability,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Courier_pincode_serviceability.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCourier_pincode_serviceabilityModal').modal('hide');
            $scope.clear();
        };

        $scope.courier_pincode_serviceability = {pincode: null, capacity: null, id: null};

        $scope.clear = function () {
            $scope.courier_pincode_serviceability = {pincode: null, capacity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'courier_pincode_serviceabilitys',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "pincode",
                displayName: "First Name"

            }, {
                field: "capacity",
                displayName: "First Name"






            }, {
                field: "courier.name",
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
                Courier_pincode_serviceability.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

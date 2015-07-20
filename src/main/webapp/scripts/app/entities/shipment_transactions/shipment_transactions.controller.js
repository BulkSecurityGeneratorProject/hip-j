'use strict';

angular.module('jhipsterApp')
    .controller('Shipment_transactionsController', function ($scope, Shipment_transactions, Fulfillment_center, Dest_pincode, Courier, Payment_service_mapper, Master_bag, ParseLinks) {
        $scope.shipment_transactionss = [];
        $scope.fulfillment_centers = Fulfillment_center.query();
        $scope.dest_pincodes = Dest_pincode.query();
        $scope.couriers = Courier.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.master_bags = Master_bag.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Shipment_transactions.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shipment_transactionss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Shipment_transactions.get({id: id}, function(result) {
                $scope.shipment_transactions = result;
                $('#saveShipment_transactionsModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.shipment_transactions.id != null) {
                Shipment_transactions.update($scope.shipment_transactions,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Shipment_transactions.save($scope.shipment_transactions,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Shipment_transactions.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveShipment_transactionsModal').modal('hide');
            $scope.clear();
        };

        $scope.shipment_transactions = {orderRef: null, invoiceId: null, shipmentId: null, amount: null, awb: null, status: null, time: null, lbh: null, in_scan_time: null, description: null, cost: null, id: null};

        $scope.clear = function () {
            $scope.shipment_transactions = {orderRef: null, invoiceId: null, shipmentId: null, amount: null, awb: null, status: null, time: null, lbh: null, in_scan_time: null, description: null, cost: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'shipment_transactionss',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "orderRef",
                displayName: "First Name"

            }, {
                field: "invoiceId",
                displayName: "First Name"

            }, {
                field: "shipmentId",
                displayName: "First Name"

            }, {
                field: "amount",
                displayName: "First Name"

            }, {
                field: "awb",
                displayName: "First Name"

            }, {
                field: "status",
                displayName: "First Name"

            }, {
                field: "time",
                displayName: "First Name"

            }, {
                field: "lbh",
                displayName: "First Name"

            }, {
                field: "in_scan_time",
                displayName: "First Name"

            }, {
                field: "description",
                displayName: "First Name"

            }, {
                field: "cost",
                displayName: "First Name"






            }, {
                field: "fulfillment_center.name",
                displayName: "Dat Name"
      
            }, {
                field: "dest_pincode.pincode",
                displayName: "Dat Name"
      
            }, {
                field: "courier.name",
                displayName: "Dat Name"
      
            }, {
                field: "payment_service_mapper.description",
                displayName: "Dat Name"
      
            }, {
                field: "master_bag.code",
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
                Shipment_transactions.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

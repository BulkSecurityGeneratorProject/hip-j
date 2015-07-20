'use strict';

angular.module('jhipsterApp')
    .controller('Stock_airwaybillController', function ($scope, Stock_airwaybill, Courier, Payment_type, ParseLinks) {
        $scope.stock_airwaybills = [];
        $scope.couriers = Courier.query();
        $scope.payment_types = Payment_type.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Stock_airwaybill.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.stock_airwaybills = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Stock_airwaybill.get({id: id}, function(result) {
                $scope.stock_airwaybill = result;
                $('#saveStock_airwaybillModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.stock_airwaybill.id != null) {
                Stock_airwaybill.update($scope.stock_airwaybill,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Stock_airwaybill.save($scope.stock_airwaybill,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Stock_airwaybill.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveStock_airwaybillModal').modal('hide');
            $scope.clear();
        };

        $scope.stock_airwaybill = {awb: null, status: null, id: null};

        $scope.clear = function () {
            $scope.stock_airwaybill = {awb: null, status: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'stock_airwaybills',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "awb",
                displayName: "First Name"

            }, {
                field: "status",
                displayName: "First Name"






            }, {
                field: "courier.name",
                displayName: "Dat Name"
      
            }, {
                field: "payment_type.name",
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
                Stock_airwaybill.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

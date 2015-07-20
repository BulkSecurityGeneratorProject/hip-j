'use strict';

angular.module('jhipsterApp')
    .controller('Payment_service_mapperController', function ($scope, Payment_service_mapper, Payment_type, Service_type, ParseLinks) {
        $scope.payment_service_mappers = [];
        $scope.payment_types = Payment_type.query();
        $scope.service_types = Service_type.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Payment_service_mapper.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.payment_service_mappers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Payment_service_mapper.get({id: id}, function(result) {
                $scope.payment_service_mapper = result;
                $('#savePayment_service_mapperModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.payment_service_mapper.id != null) {
                Payment_service_mapper.update($scope.payment_service_mapper,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Payment_service_mapper.save($scope.payment_service_mapper,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Payment_service_mapper.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePayment_service_mapperModal').modal('hide');
            $scope.clear();
        };

        $scope.payment_service_mapper = {description: null, id: null};

        $scope.clear = function () {
            $scope.payment_service_mapper = {description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'payment_service_mappers',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "description",
                displayName: "First Name"






            }, {
                field: "payment_type.name",
                displayName: "Dat Name"
      
            }, {
                field: "service_type.name",
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
                Payment_service_mapper.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

'use strict';

angular.module('jhipsterApp')
    .controller('Tat_cost_matrixController', function ($scope, Tat_cost_matrix, City, Courier, Payment_service_mapper, ParseLinks) {
        $scope.tat_cost_matrixs = [];
        $scope.citys = City.query();
        $scope.couriers = Courier.query();
        $scope.payment_service_mappers = Payment_service_mapper.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Tat_cost_matrix.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tat_cost_matrixs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Tat_cost_matrix.get({id: id}, function(result) {
                $scope.tat_cost_matrix = result;
                $('#saveTat_cost_matrixModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.tat_cost_matrix.id != null) {
                Tat_cost_matrix.update($scope.tat_cost_matrix,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Tat_cost_matrix.save($scope.tat_cost_matrix,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Tat_cost_matrix.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveTat_cost_matrixModal').modal('hide');
            $scope.clear();
        };

        $scope.tat_cost_matrix = {basic_cost: null, fixed_addon_cost: null, variable_addon_percent: null, tat: null, id: null};

        $scope.clear = function () {
            $scope.tat_cost_matrix = {basic_cost: null, fixed_addon_cost: null, variable_addon_percent: null, tat: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'tat_cost_matrixs',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "basic_cost",
                displayName: "First Name"

            }, {
                field: "fixed_addon_cost",
                displayName: "First Name"

            }, {
                field: "variable_addon_percent",
                displayName: "First Name"

            }, {
                field: "tat",
                displayName: "First Name"






            }, {
                field: "city.name",
                displayName: "Dat Name"
      
            }, {
                field: "city.name",
                displayName: "Dat Name"
      
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
                Tat_cost_matrix.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

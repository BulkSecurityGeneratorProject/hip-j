'use strict';

angular.module('jhipsterApp')
    .controller('Payment_typeController', function ($scope, Payment_type, ParseLinks) {
        $scope.payment_types = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Payment_type.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.payment_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Payment_type.get({id: id}, function(result) {
                $scope.payment_type = result;
                $('#savePayment_typeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.payment_type.id != null) {
                Payment_type.update($scope.payment_type,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Payment_type.save($scope.payment_type,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Payment_type.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePayment_typeModal').modal('hide');
            $scope.clear();
        };

        $scope.payment_type = {name: null, description: null, id: null};

        $scope.clear = function () {
            $scope.payment_type = {name: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'payment_types',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "name",
                displayName: "First Name"

            }, {
                field: "description",
                displayName: "First Name"







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
                Payment_type.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

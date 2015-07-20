'use strict';

angular.module('jhipsterApp')
    .controller('Service_typeController', function ($scope, Service_type, ParseLinks) {
        $scope.service_types = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Service_type.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.service_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Service_type.get({id: id}, function(result) {
                $scope.service_type = result;
                $('#saveService_typeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.service_type.id != null) {
                Service_type.update($scope.service_type,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Service_type.save($scope.service_type,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Service_type.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveService_typeModal').modal('hide');
            $scope.clear();
        };

        $scope.service_type = {name: null, description: null, id: null};

        $scope.clear = function () {
            $scope.service_type = {name: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'service_types',
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
                Service_type.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

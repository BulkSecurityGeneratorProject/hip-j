'use strict';

angular.module('jhipsterApp')
    .controller('CityController', function ($scope, City, Region, ParseLinks) {
        $scope.citys = [];
        $scope.regions = Region.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            City.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.citys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            City.get({id: id}, function(result) {
                $scope.city = result;
                $('#saveCityModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.city.id != null) {
                City.update($scope.city,
                    function () {
                        $scope.refresh();
                    });
            } else {
                City.save($scope.city,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            City.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCityModal').modal('hide');
            $scope.clear();
        };

        $scope.city = {code: null, name: null, id: null};

        $scope.clear = function () {
            $scope.city = {code: null, name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'citys',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "code",
                displayName: "First Name"

            }, {
                field: "name",
                displayName: "First Name"






            }, {
                field: "region.name",
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
                City.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

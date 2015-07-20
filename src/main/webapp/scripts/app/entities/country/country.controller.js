'use strict';

angular.module('jhipsterApp')
    .controller('CountryController', function ($scope, Country, ParseLinks) {
        $scope.countrys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Country.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.countrys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Country.get({id: id}, function(result) {
                $scope.country = result;
                $('#saveCountryModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.country.id != null) {
                Country.update($scope.country,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Country.save($scope.country,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Country.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCountryModal').modal('hide');
            $scope.clear();
        };

        $scope.country = {code: null, name: null, id: null};

        $scope.clear = function () {
            $scope.country = {code: null, name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'countrys',
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
                Country.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

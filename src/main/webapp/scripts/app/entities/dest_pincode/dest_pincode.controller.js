'use strict';

angular.module('jhipsterApp')
    .controller('Dest_pincodeController', function ($scope, Dest_pincode, City, State, Region, Country, Cluster, ParseLinks) {
        $scope.dest_pincodes = [];
        $scope.citys = City.query();
        $scope.states = State.query();
        $scope.regions = Region.query();
        $scope.countrys = Country.query();
        $scope.clusters = Cluster.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Dest_pincode.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dest_pincodes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Dest_pincode.get({id: id}, function(result) {
                $scope.dest_pincode = result;
                $('#saveDest_pincodeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.dest_pincode.id != null) {
                Dest_pincode.update($scope.dest_pincode,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Dest_pincode.save($scope.dest_pincode,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Dest_pincode.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveDest_pincodeModal').modal('hide');
            $scope.clear();
        };

        $scope.dest_pincode = {pincode: null, district: null, id: null};

        $scope.clear = function () {
            $scope.dest_pincode = {pincode: null, district: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'dest_pincodes',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "pincode",
                displayName: "First Name"

            }, {
                field: "district",
                displayName: "First Name"






            }, {
                field: "city.name",
                displayName: "Dat Name"
      
            }, {
                field: "state.code",
                displayName: "Dat Name"
      
            }, {
                field: "region.name",
                displayName: "Dat Name"
      
            }, {
                field: "country.name",
                displayName: "Dat Name"
      
            }, {
                field: "cluster.name",
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
                Dest_pincode.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

'use strict';

angular.module('jhipsterApp')
    .controller('Fulfillment_centerController', function ($scope, Fulfillment_center, City, State, Cluster, ParseLinks) {
        $scope.fulfillment_centers = [];
        $scope.citys = City.query();
        $scope.states = State.query();
        $scope.clusters = Cluster.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Fulfillment_center.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fulfillment_centers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Fulfillment_center.get({id: id}, function(result) {
                $scope.fulfillment_center = result;
                $('#saveFulfillment_centerModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.fulfillment_center.id != null) {
                Fulfillment_center.update($scope.fulfillment_center,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Fulfillment_center.save($scope.fulfillment_center,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Fulfillment_center.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveFulfillment_centerModal').modal('hide');
            $scope.clear();
        };

        $scope.fulfillment_center = {fcid: null, name: null, description: null, fc_type: null, franchise: null, address1: null, address2: null, address3: null, pincode: null, o2s_tat: null, is_enabled: null, id: null};

        $scope.clear = function () {
            $scope.fulfillment_center = {fcid: null, name: null, description: null, fc_type: null, franchise: null, address1: null, address2: null, address3: null, pincode: null, o2s_tat: null, is_enabled: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'fulfillment_centers',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "fcid",
                displayName: "First Name"

            }, {
                field: "name",
                displayName: "First Name"

            }, {
                field: "description",
                displayName: "First Name"

            }, {
                field: "fc_type",
                displayName: "First Name"

            }, {
                field: "franchise",
                displayName: "First Name"

            }, {
                field: "address1",
                displayName: "First Name"

            }, {
                field: "address2",
                displayName: "First Name"

            }, {
                field: "address3",
                displayName: "First Name"

            }, {
                field: "pincode",
                displayName: "First Name"

            }, {
                field: "o2s_tat",
                displayName: "First Name"

            }, {
                field: "is_enabled",
                displayName: "First Name"






            }, {
                field: "city.name",
                displayName: "Dat Name"
      
            }, {
                field: "state.code",
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
                Fulfillment_center.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

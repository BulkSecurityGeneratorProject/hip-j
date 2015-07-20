'use strict';

angular.module('jhipsterApp')
    .controller('Master_chachaController', function ($scope, Master_chacha, Courier, ParseLinks) {
        $scope.master_chachas = [];
        $scope.couriers = Courier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Master_chacha.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.master_chachas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Master_chacha.get({id: id}, function(result) {
                $scope.master_chacha = result;
                $('#saveMaster_chachaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.master_chacha.id != null) {
                Master_chacha.update($scope.master_chacha,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Master_chacha.save($scope.master_chacha,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Master_chacha.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMaster_chachaModal').modal('hide');
            $scope.clear();
        };

        $scope.master_chacha = {code: null, creationTime: null, handoverTime: null, id: null};

        $scope.clear = function () {
            $scope.master_chacha = {code: null, creationTime: null, handoverTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.gridOptions = {
            data: 'master_chachas',
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true



            }, {
                field: "code",
                displayName: "First Name"

            }, {
                field: "creationTime",
                displayName: "First Name"

            }, {
                field: "handoverTime",
                displayName: "First Name"






            }, {
                field: "",
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
                Master_chacha.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
            });
        };

    });

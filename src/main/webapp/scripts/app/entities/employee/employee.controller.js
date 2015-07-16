// 'use strict';

angular.module('jhipsterApp')
    .controller('EmployeeController', function($http, $scope, Employee) {
        window.scope = $scope;
        // Start: ui-grid example
        $scope.gridOptions = {};

        $scope.getTableHeight = function() {
            var rowHeight = 30; // your row height
            var headerHeight = 30; // your header height
            return {
                height: ($scope.gridOptions2.data.length * rowHeight + headerHeight) + "px"
            };
        };

        $scope.storeFile = function(gridRow, gridCol, files) {
            // ignore all but the first file, it can only select one anyway
            // set the filename into this column
            gridRow.entity.filename = files[0].name;

            // read the file and set it into a hidden column, which we may do stuff with later
            var setFile = function(fileContent) {
                gridRow.entity.file = fileContent.currentTarget.result;
                // put it on scope so we can display it - you'd probably do something else with it
                $scope.lastFile = fileContent.currentTarget.result;
                $scope.$apply();
            };
            var reader = new FileReader();
            reader.onload = setFile;
            reader.readAsText(files[0]);
        };

        $scope.gridOptions.columnDefs = [{
            name: 'id',
            enableCellEdit: false,
            width: '10%'
        }, {
            name: 'name',
            displayName: 'Name (editable)',
            width: '20%'
        }, {
            name: 'age',
            displayName: 'Age',
            type: 'number',
            width: '10%'
        }, {
            name: 'gender',
            displayName: 'Gender',
            editableCellTemplate: 'ui-grid/dropdownEditor',
            width: '20%',
            cellFilter: 'mapGender',
            editDropdownValueLabel: 'gender',
            editDropdownOptionsArray: [{
                id: 1,
                gender: 'male'
            }, {
                id: 2,
                gender: 'female'
            }]
        }, {
            name: 'registered',
            displayName: 'Registered',
            type: 'date',
            cellFilter: 'date:"yyyy-MM-dd"',
            width: '20%'
        }, {
            name: 'address',
            displayName: 'Address',
            type: 'object',
            cellFilter: 'address',
            width: '30%'
        }, {
            name: 'address.city',
            displayName: 'Address (even rows editable)',
            width: '20%',
            cellEditableCondition: function($scope) {
                return $scope.rowRenderIndex % 2
            }
        }, {
            name: 'isActive',
            displayName: 'Active',
            type: 'boolean',
            width: '10%'
        }, {
            name: 'pet',
            displayName: 'Pet',
            width: '20%',
            editableCellTemplate: 'ui-grid/dropdownEditor',
            editDropdownRowEntityOptionsArrayPath: 'foo.bar[0].options',
            editDropdownIdLabel: 'value'
        }, {
            name: 'filename',
            displayName: 'File',
            width: '20%',
            editableCellTemplate: 'ui-grid/fileChooserEditor',
            editFileChooserCallback: $scope.storeFile
        }];

        $scope.msg = {};


        $scope.gridOptions.onRegisterApi = function(gridApi) {
            //set gridApi on scope
            $scope.gridApi = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                $scope.msg.lastCellEdited = 'edited row id:' + rowEntity.id + ' Column:' + colDef.name + ' newValue:' + newValue + ' oldValue:' + oldValue;
                $scope.$apply();
            });
        };

        $http.get('/data/500_complex.json')
            .success(function(data) {
                for (i = 0; i < data.length; i++) {
                    data[i].registered = new Date(data[i].registered);
                    data[i].gender = data[i].gender === 'male' ? 1 : 2;
                    if (i % 2) {
                        data[i].pet = 'fish'
                        data[i].foo = {
                            bar: [{
                                baz: 2,
                                options: [{
                                    value: 'fish'
                                }, {
                                    value: 'hamster'
                                }]
                            }]
                        }
                    } else {
                        data[i].pet = 'dog'
                        data[i].foo = {
                            bar: [{
                                baz: 2,
                                options: [{
                                    value: 'dog'
                                }, {
                                    value: 'cat'
                                }]
                            }]
                        }
                    }
                }
                $scope.gridOptions.data = data;
            });


        // End: ui-grid example

        $scope.myData = [{
            "firstName": "Cox",
            "lastName": "Carney",
            "company": "Enormo",
            "employed": true
        }, {
            "firstName": "Lorraine",
            "lastName": "Wise",
            "company": "Comveyer",
            "employed": false
        }, {
            "firstName": "Nancy",
            "lastName": "Waters",
            "company": "Fuelton",
            "employed": false
        }];

        $scope.users = [{
            id: 1,
            name: 'awesome user1',
            status: 2,
            group: 4,
            groupName: 'admin'
        }, {
            id: 2,
            name: 'awesome user2',
            status: undefined,
            group: 3,
            groupName: 'vip'
        }, {
            id: 3,
            name: 'awesome user3',
            status: 2,
            group: null
        }];

        $scope.statuses = [{
            value: 1,
            text: 'status1'
        }, {
            value: 2,
            text: 'status2'
        }, {
            value: 3,
            text: 'status3'
        }, {
            value: 4,
            text: 'status4'
        }];

        $scope.employees = [];
        $scope.loadAll = function() {
            Employee.query(function(result) {
                $scope.employees = result;
            });
        };
        $scope.loadAll();
        $scope.gridOptions2 = {
            data: $scope.employees
        };

        $scope.myData = [{
            name: "Moroni",
            age: 50
        }, {
            name: "Tiancum",
            age: 43
        }, {
            name: "Jacob",
            age: 27
        }, {
            name: "Nephi",
            age: 29
        }, {
            name: "Enos",
            age: 34
        }];

        $scope.gridOptions2 = {
            data: 'employees',
            columnDefs: [{
                headerCellClass: "center",
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true
            }, {
                headerCellClass: "center",
                field: "firstname",
                displayName: "First Name"
            }, {
                headerCellClass: "center",
                field: "lastname",
                displayName: "Last Name"
            }, {
                headerCellClass: "center",
                field: "age",
                displayName: "Age"
            }, {
                headerCellClass: "center",
                field: "email",
                displayName: "Email"
            }, {
                headerCellClass: "center",
                // width: '*',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                name: 'asdf',
                displayName: "Delete",
                // cellTemplate: '<md-button ng-click="showForm=true" class="delButton">Create</md-button>'
                cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity.id)" >Delete</button> '
                    // cellTemplate: '<button id="editBtn" type="button" class="btn btn-primary" ng-click="grid.appScope.edit(row.entity.id)" >Delete</button> '
            }],
        };
        $scope.edit = function(id) {
            console.log(id);
            Employee.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $scope.clear();
                });
        }

        $scope.gridOptions3 = {
            data: 'myData',
            columnDefs: [{
                field: "name",
                displayName: "NAME"
            }, {
                field: "age",
                displayName: "AGE"
            }],
            multiSelect: true
        };

        $scope.gridOptions2.onRegisterApi = function(gridApi) {
            //set gridApi on scope
            $scope.gridApi = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                Employee.update(rowEntity,
                    function() {
                        $scope.refresh();
                    });
                $scope.msg.lastCellEdited = 'edited row id:' + rowEntity.id + ' Column:' + colDef.name + ' newValue:' + newValue + ' oldValue:' + oldValue;
                $scope.$apply();
            });
        };

        $scope.user = {
            firstname: null,
            lastname: null,
            age: null,
            email: null,
            id: null
        };

        $scope.showUpdate = function(id) {
            Employee.get({
                id: id
            }, function(result) {
                $scope.employee = result;
                $('#saveEmployeeModal').modal('show');
            });
        };

        $scope.saveUser = function(a, b) {
            Employee.update(a,
                function() {
                    $scope.refresh();
                });
            var x = 1;
        };

        $scope.save = function() {
            if ($scope.user.id != null) {
                Employee.update($scope.user,
                    function() {
                        $scope.refresh();
                    });
            } else {
                var newObj = $scope.user;
                Employee.save(newObj,
                    function() {
                        $scope.refresh();
                    });
            }
        };
        // $scope.save = function() {
        //   if ($scope.employee.id != null) {
        //     Employee.update($scope.employee,
        //       function() {
        //         $scope.refresh();
        //       });
        //   } else {
        //     var newObj = $scope.employee;
        //     Employee.save(newObj,
        //       function() {
        //         $scope.refresh();
        //       });
        //     // Employee.save($scope.employee,
        //     //   function() {
        //     //     $scope.refresh();
        //     //   });
        //   }
        // };

        $scope.save2 = function() {
            if ($scope.employee.id != null) {
                Employee.update($scope.employee,
                    function() {
                        $scope.refresh();
                    });
            } else {
                Employee.save($scope.employee,
                    function() {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function(id) {
            Employee.get({
                id: id
            }, function(result) {
                $scope.employee = result;
                $('#deleteEmployeeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(id) {
            Employee.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $('#deleteEmployeeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function() {
            $scope.loadAll();
            $('#saveEmployeeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function() {
            // $scope.employee = {
            $scope.user = {
                firstname: null,
                lastname: null,
                age: null,
                email: null,
                id: null
            };
            $scope.editForm1.$setPristine();
            $scope.editForm1.$setUntouched();
        };
    }).filter('mapGender', function() {
        var genderHash = {
            1: 'male',
            2: 'female'
        };

        return function(input) {
            if (!input) {
                return '';
            } else {
                return genderHash[input];
            }
        };
    });

angular.module('addressFormatter', []).filter('address', function() {
    return function(input) {
        return input.street + ', ' + input.city + ', ' + input.state + ', ' + input.zip;
    };
});

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employee', {
                parent: 'entity',
                url: '/employee',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.employee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employee/employees.html',
                        controller: 'EmployeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeDetail', {
                parent: 'entity',
                url: '/employee/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.employee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employee/employee-detail.html',
                        controller: 'EmployeeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        return $translate.refresh();
                    }]
                }
            });
    });

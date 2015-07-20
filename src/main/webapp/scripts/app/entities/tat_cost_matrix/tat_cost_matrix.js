'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tat_cost_matrix', {
                parent: 'entity',
                url: '/tat_cost_matrix',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.tat_cost_matrix.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tat_cost_matrix/tat_cost_matrixs.html',
                        controller: 'Tat_cost_matrixController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tat_cost_matrix');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tat_cost_matrixDetail', {
                parent: 'entity',
                url: '/tat_cost_matrix/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.tat_cost_matrix.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tat_cost_matrix/tat_cost_matrix-detail.html',
                        controller: 'Tat_cost_matrixDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tat_cost_matrix');
                        return $translate.refresh();
                    }]
                }
            });
    });

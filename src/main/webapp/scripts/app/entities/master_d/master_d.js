'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_d', {
                parent: 'entity',
                url: '/master_d',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_d.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_d/master_ds.html',
                        controller: 'Master_dController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_d');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_dDetail', {
                parent: 'entity',
                url: '/master_d/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_d.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_d/master_d-detail.html',
                        controller: 'Master_dDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_d');
                        return $translate.refresh();
                    }]
                }
            });
    });

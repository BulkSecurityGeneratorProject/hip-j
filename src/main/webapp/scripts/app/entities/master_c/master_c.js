'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_c', {
                parent: 'entity',
                url: '/master_c',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_c.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_c/master_cs.html',
                        controller: 'Master_cController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_c');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_cDetail', {
                parent: 'entity',
                url: '/master_c/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_c.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_c/master_c-detail.html',
                        controller: 'Master_cDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_c');
                        return $translate.refresh();
                    }]
                }
            });
    });

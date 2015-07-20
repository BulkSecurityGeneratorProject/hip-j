'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_b', {
                parent: 'entity',
                url: '/master_b',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_b.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_b/master_bs.html',
                        controller: 'Master_bController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_b');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_bDetail', {
                parent: 'entity',
                url: '/master_b/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_b.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_b/master_b-detail.html',
                        controller: 'Master_bDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_b');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_roshi', {
                parent: 'entity',
                url: '/master_roshi',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_roshi.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_roshi/master_roshis.html',
                        controller: 'Master_roshiController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_roshi');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_roshiDetail', {
                parent: 'entity',
                url: '/master_roshi/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_roshi.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_roshi/master_roshi-detail.html',
                        controller: 'Master_roshiDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_roshi');
                        return $translate.refresh();
                    }]
                }
            });
    });

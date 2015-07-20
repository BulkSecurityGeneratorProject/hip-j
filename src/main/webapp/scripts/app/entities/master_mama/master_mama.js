'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_mama', {
                parent: 'entity',
                url: '/master_mama',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_mama.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_mama/master_mamas.html',
                        controller: 'Master_mamaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_mama');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_mamaDetail', {
                parent: 'entity',
                url: '/master_mama/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_mama.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_mama/master_mama-detail.html',
                        controller: 'Master_mamaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_mama');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('morn', {
                parent: 'entity',
                url: '/morn',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.morn.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/morn/morns.html',
                        controller: 'MornController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('morn');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mornDetail', {
                parent: 'entity',
                url: '/morn/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.morn.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/morn/morn-detail.html',
                        controller: 'MornDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('morn');
                        return $translate.refresh();
                    }]
                }
            });
    });

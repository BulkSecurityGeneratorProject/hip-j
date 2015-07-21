'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mewone', {
                parent: 'entity',
                url: '/mewone',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.mewone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mewone/mewones.html',
                        controller: 'MewoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mewone');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mewoneDetail', {
                parent: 'entity',
                url: '/mewone/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.mewone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mewone/mewone-detail.html',
                        controller: 'MewoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mewone');
                        return $translate.refresh();
                    }]
                }
            });
    });

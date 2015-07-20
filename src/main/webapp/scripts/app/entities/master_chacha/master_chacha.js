'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_chacha', {
                parent: 'entity',
                url: '/master_chacha',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_chacha.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_chacha/master_chachas.html',
                        controller: 'Master_chachaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_chacha');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_chachaDetail', {
                parent: 'entity',
                url: '/master_chacha/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_chacha.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_chacha/master_chacha-detail.html',
                        controller: 'Master_chachaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_chacha');
                        return $translate.refresh();
                    }]
                }
            });
    });

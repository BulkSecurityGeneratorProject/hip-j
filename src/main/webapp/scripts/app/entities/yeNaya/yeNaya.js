'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yeNaya', {
                parent: 'entity',
                url: '/yeNaya',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.yeNaya.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yeNaya/yeNayas.html',
                        controller: 'YeNayaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('yeNaya');
                        return $translate.refresh();
                    }]
                }
            })
            .state('yeNayaDetail', {
                parent: 'entity',
                url: '/yeNaya/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.yeNaya.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yeNaya/yeNaya-detail.html',
                        controller: 'YeNayaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('yeNaya');
                        return $translate.refresh();
                    }]
                }
            });
    });

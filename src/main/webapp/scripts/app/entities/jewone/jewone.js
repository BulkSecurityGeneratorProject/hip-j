'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jewone', {
                parent: 'entity',
                url: '/jewone',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.jewone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jewone/jewones.html',
                        controller: 'JewoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jewone');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jewoneDetail', {
                parent: 'entity',
                url: '/jewone/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.jewone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jewone/jewone-detail.html',
                        controller: 'JewoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jewone');
                        return $translate.refresh();
                    }]
                }
            });
    });

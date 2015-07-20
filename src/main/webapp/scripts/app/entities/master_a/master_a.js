'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_a', {
                parent: 'entity',
                url: '/master_a',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_a.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_a/master_as.html',
                        controller: 'Master_aController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_a');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_aDetail', {
                parent: 'entity',
                url: '/master_a/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_a.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_a/master_a-detail.html',
                        controller: 'Master_aDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_a');
                        return $translate.refresh();
                    }]
                }
            });
    });

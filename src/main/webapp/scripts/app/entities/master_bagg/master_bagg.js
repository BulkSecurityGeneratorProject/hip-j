'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_bagg', {
                parent: 'entity',
                url: '/master_bagg',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bagg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bagg/master_baggs.html',
                        controller: 'Master_baggController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bagg');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_baggDetail', {
                parent: 'entity',
                url: '/master_bagg/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bagg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bagg/master_bagg-detail.html',
                        controller: 'Master_baggDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bagg');
                        return $translate.refresh();
                    }]
                }
            });
    });

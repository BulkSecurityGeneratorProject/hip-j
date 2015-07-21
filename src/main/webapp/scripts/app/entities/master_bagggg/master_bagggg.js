'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_bagggg', {
                parent: 'entity',
                url: '/master_bagggg',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bagggg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bagggg/master_baggggs.html',
                        controller: 'Master_baggggController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bagggg');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_baggggDetail', {
                parent: 'entity',
                url: '/master_bagggg/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bagggg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bagggg/master_bagggg-detail.html',
                        controller: 'Master_baggggDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bagggg');
                        return $translate.refresh();
                    }]
                }
            });
    });

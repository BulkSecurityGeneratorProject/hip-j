'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_baggg', {
                parent: 'entity',
                url: '/master_baggg',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_baggg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_baggg/master_bagggs.html',
                        controller: 'Master_bagggController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_baggg');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_bagggDetail', {
                parent: 'entity',
                url: '/master_baggg/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_baggg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_baggg/master_baggg-detail.html',
                        controller: 'Master_bagggDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_baggg');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('region', {
                parent: 'entity',
                url: '/region',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.region.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/region/regions.html',
                        controller: 'RegionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('region');
                        return $translate.refresh();
                    }]
                }
            })
            .state('regionDetail', {
                parent: 'entity',
                url: '/region/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.region.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/region/region-detail.html',
                        controller: 'RegionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('region');
                        return $translate.refresh();
                    }]
                }
            });
    });

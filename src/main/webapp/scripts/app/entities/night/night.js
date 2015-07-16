'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('night', {
                parent: 'entity',
                url: '/night',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.night.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/night/nights.html',
                        controller: 'NightController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('night');
                        return $translate.refresh();
                    }]
                }
            })
            .state('nightDetail', {
                parent: 'entity',
                url: '/night/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.night.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/night/night-detail.html',
                        controller: 'NightDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('night');
                        return $translate.refresh();
                    }]
                }
            });
    });

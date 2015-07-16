'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hoohaa', {
                parent: 'entity',
                url: '/hoohaa',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.hoohaa.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hoohaa/hoohaas.html',
                        controller: 'HoohaaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hoohaa');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hoohaaDetail', {
                parent: 'entity',
                url: '/hoohaa/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.hoohaa.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hoohaa/hoohaa-detail.html',
                        controller: 'HoohaaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hoohaa');
                        return $translate.refresh();
                    }]
                }
            });
    });

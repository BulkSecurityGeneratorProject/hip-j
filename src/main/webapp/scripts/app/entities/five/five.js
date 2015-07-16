'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('five', {
                parent: 'entity',
                url: '/five',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.five.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/five/fives.html',
                        controller: 'FiveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('five');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fiveDetail', {
                parent: 'entity',
                url: '/five/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.five.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/five/five-detail.html',
                        controller: 'FiveDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('five');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('four', {
                parent: 'entity',
                url: '/four',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.four.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/four/fours.html',
                        controller: 'FourController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('four');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fourDetail', {
                parent: 'entity',
                url: '/four/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.four.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/four/four-detail.html',
                        controller: 'FourDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('four');
                        return $translate.refresh();
                    }]
                }
            });
    });

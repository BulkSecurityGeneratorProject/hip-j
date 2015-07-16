'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('noon', {
                parent: 'entity',
                url: '/noon',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.noon.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/noon/noons.html',
                        controller: 'NoonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('noon');
                        return $translate.refresh();
                    }]
                }
            })
            .state('noonDetail', {
                parent: 'entity',
                url: '/noon/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.noon.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/noon/noon-detail.html',
                        controller: 'NoonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('noon');
                        return $translate.refresh();
                    }]
                }
            });
    });

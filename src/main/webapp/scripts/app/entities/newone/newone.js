'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('newone', {
                parent: 'entity',
                url: '/newone',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.newone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/newone/newones.html',
                        controller: 'NewoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('newone');
                        return $translate.refresh();
                    }]
                }
            })
            .state('newoneDetail', {
                parent: 'entity',
                url: '/newone/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.newone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/newone/newone-detail.html',
                        controller: 'NewoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('newone');
                        return $translate.refresh();
                    }]
                }
            });
    });

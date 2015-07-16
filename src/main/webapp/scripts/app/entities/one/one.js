'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('one', {
                parent: 'entity',
                url: '/one',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.one.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/one/ones.html',
                        controller: 'OneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('one');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneDetail', {
                parent: 'entity',
                url: '/one/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.one.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/one/one-detail.html',
                        controller: 'OneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('one');
                        return $translate.refresh();
                    }]
                }
            });
    });

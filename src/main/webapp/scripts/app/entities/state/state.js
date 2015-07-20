'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('state', {
                parent: 'entity',
                url: '/state',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.state.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/state/states.html',
                        controller: 'StateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('state');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stateDetail', {
                parent: 'entity',
                url: '/state/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.state.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/state/state-detail.html',
                        controller: 'StateDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('state');
                        return $translate.refresh();
                    }]
                }
            });
    });

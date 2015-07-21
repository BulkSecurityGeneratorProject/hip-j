'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test04', {
                parent: 'entity',
                url: '/test04',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test04.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test04/test04s.html',
                        controller: 'Test04Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test04');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test04Detail', {
                parent: 'entity',
                url: '/test04/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test04.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test04/test04-detail.html',
                        controller: 'Test04DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test04');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test03', {
                parent: 'entity',
                url: '/test03',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test03.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test03/test03s.html',
                        controller: 'Test03Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test03');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test03Detail', {
                parent: 'entity',
                url: '/test03/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test03.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test03/test03-detail.html',
                        controller: 'Test03DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test03');
                        return $translate.refresh();
                    }]
                }
            });
    });

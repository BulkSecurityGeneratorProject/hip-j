'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test05', {
                parent: 'entity',
                url: '/test05',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test05.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test05/test05s.html',
                        controller: 'Test05Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test05');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test05Detail', {
                parent: 'entity',
                url: '/test05/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test05.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test05/test05-detail.html',
                        controller: 'Test05DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test05');
                        return $translate.refresh();
                    }]
                }
            });
    });

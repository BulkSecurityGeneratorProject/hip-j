'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test01', {
                parent: 'entity',
                url: '/test01',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test01.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test01/test01s.html',
                        controller: 'Test01Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test01');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test01Detail', {
                parent: 'entity',
                url: '/test01/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test01.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test01/test01-detail.html',
                        controller: 'Test01DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test01');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test02', {
                parent: 'entity',
                url: '/test02',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test02.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test02/test02s.html',
                        controller: 'Test02Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test02');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test02Detail', {
                parent: 'entity',
                url: '/test02/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test02.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test02/test02-detail.html',
                        controller: 'Test02DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test02');
                        return $translate.refresh();
                    }]
                }
            });
    });

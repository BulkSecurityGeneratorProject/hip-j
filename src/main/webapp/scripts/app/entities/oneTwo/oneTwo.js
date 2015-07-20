'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oneTwo', {
                parent: 'entity',
                url: '/oneTwo',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneTwo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneTwo/oneTwos.html',
                        controller: 'OneTwoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneTwo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneTwoDetail', {
                parent: 'entity',
                url: '/oneTwo/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneTwo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneTwo/oneTwo-detail.html',
                        controller: 'OneTwoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneTwo');
                        return $translate.refresh();
                    }]
                }
            });
    });

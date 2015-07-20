'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oneThree', {
                parent: 'entity',
                url: '/oneThree',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneThree.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneThree/oneThrees.html',
                        controller: 'OneThreeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneThree');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneThreeDetail', {
                parent: 'entity',
                url: '/oneThree/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneThree.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneThree/oneThree-detail.html',
                        controller: 'OneThreeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneThree');
                        return $translate.refresh();
                    }]
                }
            });
    });

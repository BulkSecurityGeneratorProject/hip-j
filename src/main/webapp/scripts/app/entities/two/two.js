'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('two', {
                parent: 'entity',
                url: '/two',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.two.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/two/twos.html',
                        controller: 'TwoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('two');
                        return $translate.refresh();
                    }]
                }
            })
            .state('twoDetail', {
                parent: 'entity',
                url: '/two/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.two.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/two/two-detail.html',
                        controller: 'TwoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('two');
                        return $translate.refresh();
                    }]
                }
            });
    });

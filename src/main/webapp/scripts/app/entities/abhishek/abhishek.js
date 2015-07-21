'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('abhishek', {
                parent: 'entity',
                url: '/abhishek',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.abhishek.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/abhishek/abhisheks.html',
                        controller: 'AbhishekController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('abhishek');
                        return $translate.refresh();
                    }]
                }
            })
            .state('abhishekDetail', {
                parent: 'entity',
                url: '/abhishek/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.abhishek.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/abhishek/abhishek-detail.html',
                        controller: 'AbhishekDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('abhishek');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sssss', {
                parent: 'entity',
                url: '/sssss',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.sssss.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sssss/ssssss.html',
                        controller: 'SssssController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sssss');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sssssDetail', {
                parent: 'entity',
                url: '/sssss/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.sssss.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sssss/sssss-detail.html',
                        controller: 'SssssDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sssss');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('three', {
                parent: 'entity',
                url: '/three',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.three.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/three/threes.html',
                        controller: 'ThreeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('three');
                        return $translate.refresh();
                    }]
                }
            })
            .state('threeDetail', {
                parent: 'entity',
                url: '/three/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.three.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/three/three-detail.html',
                        controller: 'ThreeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('three');
                        return $translate.refresh();
                    }]
                }
            });
    });

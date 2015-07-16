'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('realdil', {
                parent: 'entity',
                url: '/realdil',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.realdil.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/realdil/realdils.html',
                        controller: 'RealdilController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('realdil');
                        return $translate.refresh();
                    }]
                }
            })
            .state('realdilDetail', {
                parent: 'entity',
                url: '/realdil/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.realdil.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/realdil/realdil-detail.html',
                        controller: 'RealdilDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('realdil');
                        return $translate.refresh();
                    }]
                }
            });
    });

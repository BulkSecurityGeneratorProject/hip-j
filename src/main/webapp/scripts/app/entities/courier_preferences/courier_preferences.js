'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courier_preferences', {
                parent: 'entity',
                url: '/courier_preferences',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier_preferences.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier_preferences/courier_preferencess.html',
                        controller: 'Courier_preferencesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier_preferences');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courier_preferencesDetail', {
                parent: 'entity',
                url: '/courier_preferences/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier_preferences.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier_preferences/courier_preferences-detail.html',
                        controller: 'Courier_preferencesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier_preferences');
                        return $translate.refresh();
                    }]
                }
            });
    });

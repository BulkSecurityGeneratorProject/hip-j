'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('city', {
                parent: 'entity',
                url: '/city',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.city.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/city/citys.html',
                        controller: 'CityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('city');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cityDetail', {
                parent: 'entity',
                url: '/city/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.city.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/city/city-detail.html',
                        controller: 'CityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('city');
                        return $translate.refresh();
                    }]
                }
            });
    });

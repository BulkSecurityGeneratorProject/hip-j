'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('country', {
                parent: 'entity',
                url: '/country',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.country.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/countrys.html',
                        controller: 'CountryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }]
                }
            })
            .state('countryDetail', {
                parent: 'entity',
                url: '/country/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.country.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/country-detail.html',
                        controller: 'CountryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }]
                }
            });
    });

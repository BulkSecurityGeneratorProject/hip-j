'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courier_country_serviceability', {
                parent: 'entity',
                url: '/courier_country_serviceability',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier_country_serviceability.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier_country_serviceability/courier_country_serviceabilitys.html',
                        controller: 'Courier_country_serviceabilityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier_country_serviceability');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courier_country_serviceabilityDetail', {
                parent: 'entity',
                url: '/courier_country_serviceability/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier_country_serviceability.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier_country_serviceability/courier_country_serviceability-detail.html',
                        controller: 'Courier_country_serviceabilityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier_country_serviceability');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fc_serviceability', {
                parent: 'entity',
                url: '/fc_serviceability',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.fc_serviceability.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fc_serviceability/fc_serviceabilitys.html',
                        controller: 'Fc_serviceabilityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fc_serviceability');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fc_serviceabilityDetail', {
                parent: 'entity',
                url: '/fc_serviceability/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.fc_serviceability.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fc_serviceability/fc_serviceability-detail.html',
                        controller: 'Fc_serviceabilityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fc_serviceability');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courier_pincode_serviceability', {
                parent: 'entity',
                url: '/courier_pincode_serviceability',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier_pincode_serviceability.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier_pincode_serviceability/courier_pincode_serviceabilitys.html',
                        controller: 'Courier_pincode_serviceabilityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier_pincode_serviceability');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courier_pincode_serviceabilityDetail', {
                parent: 'entity',
                url: '/courier_pincode_serviceability/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier_pincode_serviceability.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier_pincode_serviceability/courier_pincode_serviceability-detail.html',
                        controller: 'Courier_pincode_serviceabilityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier_pincode_serviceability');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courier', {
                parent: 'entity',
                url: '/courier',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier/couriers.html',
                        controller: 'CourierController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courierDetail', {
                parent: 'entity',
                url: '/courier/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier/courier-detail.html',
                        controller: 'CourierDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier');
                        return $translate.refresh();
                    }]
                }
            });
    });

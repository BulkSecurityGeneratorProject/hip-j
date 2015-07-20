'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payment_service_mapper', {
                parent: 'entity',
                url: '/payment_service_mapper',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.payment_service_mapper.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payment_service_mapper/payment_service_mappers.html',
                        controller: 'Payment_service_mapperController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payment_service_mapper');
                        return $translate.refresh();
                    }]
                }
            })
            .state('payment_service_mapperDetail', {
                parent: 'entity',
                url: '/payment_service_mapper/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.payment_service_mapper.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payment_service_mapper/payment_service_mapper-detail.html',
                        controller: 'Payment_service_mapperDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payment_service_mapper');
                        return $translate.refresh();
                    }]
                }
            });
    });

'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payment_type', {
                parent: 'entity',
                url: '/payment_type',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.payment_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payment_type/payment_types.html',
                        controller: 'Payment_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payment_type');
                        return $translate.refresh();
                    }]
                }
            })
            .state('payment_typeDetail', {
                parent: 'entity',
                url: '/payment_type/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.payment_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payment_type/payment_type-detail.html',
                        controller: 'Payment_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payment_type');
                        return $translate.refresh();
                    }]
                }
            });
    });

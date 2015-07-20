'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shipment_transactions', {
                parent: 'entity',
                url: '/shipment_transactions',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.shipment_transactions.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipment_transactions/shipment_transactionss.html',
                        controller: 'Shipment_transactionsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipment_transactions');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shipment_transactionsDetail', {
                parent: 'entity',
                url: '/shipment_transactions/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.shipment_transactions.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipment_transactions/shipment_transactions-detail.html',
                        controller: 'Shipment_transactionsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipment_transactions');
                        return $translate.refresh();
                    }]
                }
            });
    });

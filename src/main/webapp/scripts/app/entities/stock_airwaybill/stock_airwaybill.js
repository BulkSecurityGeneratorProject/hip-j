'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stock_airwaybill', {
                parent: 'entity',
                url: '/stock_airwaybill',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.stock_airwaybill.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stock_airwaybill/stock_airwaybills.html',
                        controller: 'Stock_airwaybillController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stock_airwaybill');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stock_airwaybillDetail', {
                parent: 'entity',
                url: '/stock_airwaybill/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.stock_airwaybill.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stock_airwaybill/stock_airwaybill-detail.html',
                        controller: 'Stock_airwaybillDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stock_airwaybill');
                        return $translate.refresh();
                    }]
                }
            });
    });

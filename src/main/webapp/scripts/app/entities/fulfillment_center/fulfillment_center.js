'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fulfillment_center', {
                parent: 'entity',
                url: '/fulfillment_center',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.fulfillment_center.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fulfillment_center/fulfillment_centers.html',
                        controller: 'Fulfillment_centerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fulfillment_center');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fulfillment_centerDetail', {
                parent: 'entity',
                url: '/fulfillment_center/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.fulfillment_center.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fulfillment_center/fulfillment_center-detail.html',
                        controller: 'Fulfillment_centerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fulfillment_center');
                        return $translate.refresh();
                    }]
                }
            });
    });

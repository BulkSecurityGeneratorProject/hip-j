'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dest_pincode', {
                parent: 'entity',
                url: '/dest_pincode',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.dest_pincode.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dest_pincode/dest_pincodes.html',
                        controller: 'Dest_pincodeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dest_pincode');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dest_pincodeDetail', {
                parent: 'entity',
                url: '/dest_pincode/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.dest_pincode.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dest_pincode/dest_pincode-detail.html',
                        controller: 'Dest_pincodeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dest_pincode');
                        return $translate.refresh();
                    }]
                }
            });
    });

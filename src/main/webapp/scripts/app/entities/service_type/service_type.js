'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('service_type', {
                parent: 'entity',
                url: '/service_type',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.service_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/service_type/service_types.html',
                        controller: 'Service_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('service_type');
                        return $translate.refresh();
                    }]
                }
            })
            .state('service_typeDetail', {
                parent: 'entity',
                url: '/service_type/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.service_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/service_type/service_type-detail.html',
                        controller: 'Service_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('service_type');
                        return $translate.refresh();
                    }]
                }
            });
    });

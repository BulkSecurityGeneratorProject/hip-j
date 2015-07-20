'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_bag', {
                parent: 'entity',
                url: '/master_bag',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bag.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bag/master_bags.html',
                        controller: 'Master_bagController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bag');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_bagDetail', {
                parent: 'entity',
                url: '/master_bag/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_bag.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_bag/master_bag-detail.html',
                        controller: 'Master_bagDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_bag');
                        return $translate.refresh();
                    }]
                }
            });
    });

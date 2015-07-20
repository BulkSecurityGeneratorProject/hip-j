'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('master_chef', {
                parent: 'entity',
                url: '/master_chef',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_chef.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_chef/master_chefs.html',
                        controller: 'Master_chefController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_chef');
                        return $translate.refresh();
                    }]
                }
            })
            .state('master_chefDetail', {
                parent: 'entity',
                url: '/master_chef/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.master_chef.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/master_chef/master_chef-detail.html',
                        controller: 'Master_chefDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('master_chef');
                        return $translate.refresh();
                    }]
                }
            });
    });

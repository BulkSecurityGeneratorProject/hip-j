'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cluster', {
                parent: 'entity',
                url: '/cluster',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.cluster.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cluster/clusters.html',
                        controller: 'ClusterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cluster');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clusterDetail', {
                parent: 'entity',
                url: '/cluster/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.cluster.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cluster/cluster-detail.html',
                        controller: 'ClusterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cluster');
                        return $translate.refresh();
                    }]
                }
            });
    });

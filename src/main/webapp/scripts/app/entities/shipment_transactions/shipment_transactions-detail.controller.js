'use strict';

angular.module('jhipsterApp')
    .controller('Shipment_transactionsDetailController', function ($scope, $stateParams, Shipment_transactions, Fulfillment_center, Dest_pincode, Courier, Payment_service_mapper, Master_bag) {
        $scope.shipment_transactions = {};
        $scope.load = function (id) {
            Shipment_transactions.get({id: id}, function(result) {
              $scope.shipment_transactions = result;
            });
        };
        $scope.load($stateParams.id);
    });

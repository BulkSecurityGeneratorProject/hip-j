'use strict';

angular.module('jhipsterApp')
    .controller('AuthorController', function ($scope, Author) {
        $scope.authors = [];
        $scope.loadAll = function() {
            Author.query(function(result) {
               $scope.authors = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
                $('#saveAuthorModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.author.id != null) {
                Author.update($scope.author,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Author.save($scope.author,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
                $('#deleteAuthorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAuthorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveAuthorModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.author = {firstname: null, lastname: null, email: null, age: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

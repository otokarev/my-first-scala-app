define([
    './module'
], function(controllers) {
  'use strict';

    controllers.controller('SubscribersCtrl', ['$scope', 'NgTableParams', '$resource', 'Subscribers', function($scope, NgTableParams, $resource, Subscribers) {
        var self = this;
        $scope.hideErrorMessage = true;
        $scope.showItemDialog = false;

        $scope.tableParams = new NgTableParams({}, {
            getData: function(params) {
                // TODO: error handling
                return Subscribers.query().$promise.then(function(data) {
                    params.total(data.items.length);
                    return data.items;
                }, function (error) {
                });
            }
         });

        $scope.addItem = function () {
            $scope.showItemDialog = true;
            $scope.titleDialog = "Add new Subscriber";
            $scope.item = {title: '', actorClass: ''};
            $scope.onSaveItemDialog = function (item) {
                return Subscribers.save(item).$promise.finally(function (result) {
                    $scope.tableParams.reload();
                });
            };
        };

        $scope.updateItem = function (item) {
            $scope.showItemDialog = true;
            $scope.titleDialog = "Update Subscriber";
            $scope.item = item;
            $scope.onSaveItemDialog = function (item) {
                return Subscribers.update(item).$promise.finally(function (result) {
                    $scope.tableParams.reload();
                });
            };
        };

         $scope.removeItem = function (v) {
            Subscribers.delete({id: v.id}).$promise.then(function (data) {
                $scope.tableParams.reload();
            }, function (error) {
                showError(error);
            });
         };

         var showError = function (err) {
            $scope.errorMessage = err.statusText;
            $scope.hideErrorMessage = false;

         };

         $scope.closeError = function () {
            $scope.hideErrorMessage = true;
         };
    }]);
});


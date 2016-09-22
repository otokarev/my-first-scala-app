define([
    './module'
], function(controllers) {
  'use strict';

    controllers.controller('ChannelSubscribersCtrl', ['$scope', 'NgTableParams', '$resource', 'ChannelSubscribers', function($scope, NgTableParams, $resource, ChannelSubscribers) {
        var self = this;
        $scope.hideErrorMessage = true;
        $scope.showItemDialog = false;

        $scope.tableParams = new NgTableParams({}, {
            getData: function(params) {
                // TODO: error handling
                return ChannelSubscribers.query().$promise.then(function(data) {
                    params.total(data.items.length);
                    return data.items;
                }, function (error) {
                });
            }
         });

        $scope.addItem = function () {
            $scope.showItemDialog = true;
            $scope.titleDialog = "Add new Subscriber's Channel";
            $scope.item = {title: '', actorClass: ''};
            $scope.onSaveItemDialog = function (item) {
                return ChannelSubscribers.save(item).$promise.finally(function (result) {
                    $scope.tableParams.reload();
                });
            };
        };

        $scope.updateItem = function (item) {
            $scope.showItemDialog = true;
            $scope.titleDialog = "Update Subscriber's Channel";
            $scope.item = item;
            $scope.onSaveItemDialog = function (item) {
                return ChannelSubscribers.update(item).$promise.finally(function (result) {
                    $scope.tableParams.reload();
                });
            };
        };

         $scope.removeItem = function (v) {
            ChannelSubscribers.delete({id: v.id}).$promise.then(function (data) {
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

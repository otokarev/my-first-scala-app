define([
    './module'
], function(controllers) {
  'use strict';

    controllers.controller('ChannelsCtrl', ['$scope', 'NgTableParams', '$resource', 'Channels', function($scope, NgTableParams, $resource, Channels) {
        var self = this;
        $scope.hideErrorMessage = true;
        $scope.showItemDialog = false;

        $scope.tableParams = new NgTableParams({}, {
            getData: function(params) {
                // TODO: error handling
                return Channels.query().$promise.then(function(data) {
                    params.total(data.items.length);
                    return data.items;
                }, function (error) {
                });
            }
         });

        $scope.addItem = function () {
            $scope.showItemDialog = true;
            $scope.titleDialog = "Add new Channel";
            $scope.item = {title: '', actorClass: ''};
            $scope.onSaveItemDialog = function (item) {
                return Channels.save(item).$promise.finally(function (result) {
                    $scope.tableParams.reload();
                });
            };
        };

        $scope.updateItem = function (item) {
            $scope.showItemDialog = true;
            $scope.titleDialog = "Update Channel";
            $scope.item = item;
            $scope.onSaveItemDialog = function (item) {
                return Channels.update(item).$promise.finally(function (result) {
                    $scope.tableParams.reload();
                });
            };
        };

         $scope.removeItem = function (v) {
            Channels.delete({id: v.id}).$promise.then(function (data) {
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

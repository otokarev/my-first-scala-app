define([
    './module'
], function(controllers) {
  'use strict';

    controllers.controller('ChannelsCtrl', ['$scope', 'NgTableParams', '$resource', 'Channels', function($scope, NgTableParams, $resource, Channels) {
        var self = this;
        $scope.hideErrorMessage = true;
        $scope.showAddItemDialog = false;
        $scope.showUpdateItemDialog = false;

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
            $scope.showAddItemDialog = true;
//            Channels.save({title: $scope.title, actorClass: $scope.actorClass}).$promise.then(function (data) {
//                $scope.title = $scope.actorClass = '';showAddItemDialog
//            }, function (error) {
//                showError(error);
//            });
         };

         $scope.removeItem = function (v) {
            Channels.delete({id: v.id}).$promise.then(function (data) {
                $scope.tableParams.reload();
            }, function (error) {
                showError(error);
            });
         };

         $scope.editItem = function () {
            $scope.showUpdateItemDialog = true;
//            Channels.save({id: $scope.itemId, title: $scope.title, actorClass: $scope.actorClass}).$promise.then(function (data) {
//                $scope.title = $scope.actorClass = '';
//            }, function (error) {
//                showError(error);
//            });
         };

         var showError = function (err) {
            console.log("Show error");
            console.log(err.statusText);
            $scope.errorMessage = err.statusText;
            $scope.hideErrorMessage = false;

         };

         $scope.closeError = function () {
            $scope.hideErrorMessage = true;
         };
    }]);
});

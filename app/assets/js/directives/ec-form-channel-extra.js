define([
    './module',
], function (module) {
  'use strict';

  module.directive('ecFormChannelExtra', function () {
      return {
        restrict: 'A',
        transclude: true,
        scope: {
        },
        controller: ['$scope', '$element', 'Channels', function($scope, $element, Channels) {
            $scope.$parent.getChannelOptions = function () {
                return Channels.query().$promise.then(function (data) {return data.items;});
            };
            $scope.$parent.onChannelSelect = function ($item, $model, $label, $event) {
                $scope.$parent.item.channelId = $item.id;
            };
        }]
      };
    });
});

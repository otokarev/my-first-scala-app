define([
    './module',
], function (module) {
  'use strict';

  module.directive('ecFormSubscriberChannelExtra', function () {
      return {
        restrict: 'A',
        transclude: true,
        scope: {
        },
        controller: [
            '$scope', '$element', 'Channels', 'Subscribers', 'filterFilter', 'orderByFilter',
            function($scope, $element, Channels, Subscribers, filterFilter, orderByFilter) {
                // Channel
                $scope.$parent.getChannelList = function ($viewValue) {
                    return Channels.query().$promise.then(
                        function (data) {
                            return orderByFilter(filterFilter(data.items, $viewValue), 'title', true);
                        },
                        function (er) {}
                    );
                };
                $scope.$parent.onChannelSelect = function ($item, $model, $label, $event) {
                    $scope.$parent.item.channelId = $item.id;
                };

                // Subscriber
                $scope.$parent.getSubscriberList = function ($viewValue) {
                    return Subscribers.query().$promise.then(
                        function (data) {
                            return orderByFilter(filterFilter(data.items, $viewValue), 'title', true);
                        },
                        function (er) {}
                    );
                };
                $scope.$parent.onSubscriberSelect = function ($item, $model, $label, $event) {
                    $scope.$parent.item.subscriberId = $item.id;
                };
            }
        ]
      };
    });
});

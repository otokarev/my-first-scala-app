define([], function() {
  'use strict';

    var ChannelsCtrl = function($scope, Channels, NgTableParams, $resource) {
        var self = this;
        $scope.data = [
            {id: 1, title: "ngTable test 1"},
            {id: 2, title: "ngTable test 2"},
            {id: 3, title: "ngTable test 3"},
            {id: 4, title: "ngTable test 4"}
        ];

        $scope.tableParams = new NgTableParams({
            page: 1,
            count: 10
        }, {
            counts: [10, 25, 50],
            total: $scope.data.length,
            dataset: $scope.data
//              getData: function(params) {
//                return $resource("/channel/").get(params.url()).$promise.then(function(data) {
//                  params.total(data.inlineCount);
//                  return data.results;
//                });
//              }
         });

    };
  ChannelsCtrl.$inject = [ '$scope', 'Channels', 'NgTableParams', '$resource' ];

  return ChannelsCtrl;
});

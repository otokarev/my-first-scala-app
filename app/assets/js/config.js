define([], function() {
  'use strict';

  var config = function($routeProvider) {
    $routeProvider
      .when('/main',  {templateUrl: '/assets/partials/index.html',  controller: controllers.DashboardCtrl})
      .when('/channels',  {templateUrl: '/assets/partials/channels.html',  controller: 'ChannelsCtrl'})
      .when('/subscribers',  {templateUrl: '/assets/partials/subscribers.html',  controller: controllers.SubscribersCtrl})
      .when('/channel-subscribers',  {templateUrl: '/assets/partials/channel-subscribers.html',  controller: controllers.ChannelSubscribersCtrl})
      .when('/event-streams',  {templateUrl: '/assets/partials/event-streams.html',  controller: controllers.EventsCtrl})
      .otherwise({redirectTo: '/main'});
  };
  config.$inject = ['$routeProvider'];

  return config;
});

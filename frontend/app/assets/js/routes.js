define(['./app'], function(app) {
  'use strict';

  app.config(function($logProvider){
      $logProvider.debugEnabled(true);
  });

  return app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/main',  {templateUrl: '/assets/partials/index.html',  controller: 'DashboardCtrl'})
      .when('/channels',  {templateUrl: '/assets/partials/channels.html',  controller: 'ChannelsCtrl'})
      .when('/subscribers',  {templateUrl: '/assets/partials/subscribers.html',  controller: 'SubscribersCtrl'})
      .when('/channel-subscribers',  {templateUrl: '/assets/partials/channel-subscribers.html',  controller: 'ChannelSubscribersCtrl'})
      .when('/event-streams',  {templateUrl: '/assets/partials/event-streams.html',  controller: 'EventsCtrl'})
      .otherwise({redirectTo: '/main'});
  }]);
});

define([], function() {
  'use strict';

  var config = function($routeProvider) {
    $routeProvider
      .when('/',  {templateUrl: '/assets/partials/index.html',  controller: 'DashboardCtrl'})
      .when('/channels',  {templateUrl: '/assets/partials/channels.html',  controller: 'ChannelsCtrl'})
      .when('/subscribers',  {templateUrl: '/assets/partials/subscribers.html',  controller: 'SubscribersCtrl'})
      .when('/event-streams',  {templateUrl: '/assets/partials/event-streams.html',  controller: 'EventsCtrl'})
      .otherwise({redirectTo: '/'});
  };
  config.$inject = ['$routeProvider'];

  return config;
});

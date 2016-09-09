define([
    'angular',
    'config',
    'controllers/HeaderController',
    'controllers/ChannelsController',
    'controllers/ChannelSubscribersController',
    'controllers/SubscribersController',
    'controllers/DashboardController',
    'controllers/EventsController'
], function(angular,
    config,
    HeaderController,
    ChannelsController,
    ChannelSubscribersController,
    SubscribersController,
    DashboardController,
    EventsController
) {
  'use strict';

  var m = angular.module('app', ['ngRoute']);
  m.config(config);
  m.controller('HeaderCtrl', HeaderController);
  m.controller('DashboardCtrl', DashboardController);
  m.controller('EventsCtrl', EventsController);
  m.controller('SubscribersCtrl', SubscribersController);
  m.controller('ChannelsCtrl', ChannelsController);
  m.controller('ChannelSubscribersCtrl', ChannelSubscribersController);
  return m;
});

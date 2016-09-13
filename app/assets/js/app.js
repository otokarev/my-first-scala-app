define([
    'angular',
    'config',
    'services/Channels',
    'services/Subscribers',
    'ctrls/HeaderCtrl',
    'ctrls/ChannelsCtrl',
    'ctrls/ChannelSubscribersCtrl',
    'ctrls/SubscribersCtrl',
    'ctrls/DashboardCtrl',
    'ctrls/EventsCtrl',
    'ng-table'
], function(angular,
    config,
    Channels,
    Subscribers,
    HeaderCtrl,
    ChannelsCtrl,
    ChannelSubscribersCtrl,
    SubscribersCtrl,
    DashboardCtrl,
    EventsCtrl
) {
  'use strict';

  var m = angular.module('app', ['ngRoute', 'ngTable']);
  m.constant("utility", {
    baseAddress: "http://localhost:9000/"
  });
  m.config(config);
  m.factory('Channels', Channels);
  m.factory('Subscribers', Subscribers);
  m.controller('HeaderCtrl', HeaderCtrl);
  m.controller('DashboardCtrl', DashboardCtrl);
  m.controller('EventsCtrl', EventsCtrl);
  m.controller('SubscribersCtrl', SubscribersCtrl);
  m.controller('ChannelsCtrl', ChannelsCtrl);
  m.controller('ChannelSubscribersCtrl', ChannelSubscribersCtrl);

  return m;
});

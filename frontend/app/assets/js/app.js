define([
    'angular',
    'controllers/index',
    'directives/index',
    'services/index',
    'ng-table',
    'angular-ui-bootstrap',
    'angular-ui-bootstrap-tpls',
    'angular-resource'
], function(angular) {
  'use strict';
  console.log("APP!");

  return angular.module('app', [
    'app.directives',
    'app.services',
    'ngRoute',
    'ngTable',
    'ui.bootstrap.typeahead',
    'ui.bootstrap.tpls',
    'ngResource',
    'app.controllers'
  ]);
});

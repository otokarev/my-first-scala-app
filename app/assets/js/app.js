define([
    'angular',
    'controllers/index',
    'directives/index',
    'services/index',
    'ng-table',
    'angular-resource'
], function(angular) {
  'use strict';
  console.log("APP!");

  return angular.module('app', [
    'app.directives',
    'app.services',
    'ngRoute',
    'ngTable',
    'ngResource',
    'app.controllers'
  ]);
});

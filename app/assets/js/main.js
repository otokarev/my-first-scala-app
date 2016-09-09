(function (requirejs) {
  'use strict';

  requirejs.config({
    packages: ['common', 'home', 'dashboard', 'services'],
    shim: {
      'jsRoutes': {
        deps: [],
        exports: 'jsRoutes'
      },
      'angular': {
        deps: ['jquery'],
        exports: 'angular'
      },
      'angular-route': ['angular'],
      'angular-cookies': ['angular'],
      'bootstrap': ['jquery']
    },
    paths: {
      'requirejs': ['../lib/requirejs/require'],
      'underscore' : ['../lib/underscorejs/underscore'],
      'jquery': ['../lib/jquery/jquery'],
      'angular': ['../lib/angularjs/angular'],
      'angular-route': ['../lib/angularjs/angular-route'],
      'angular-cookies': ['../lib/angularjs/angular-cookies'],
      'bootstrap': ['../lib/bootstrap/js/bootstrap'],
      'd3' : ['../lib/d3js/d3'],
      'jsRoutes': ['/jsroutes']
    }
  });

  requirejs.onError = function (err) {
    console.log(err);
  };

  require([
    'angular',
    'angular-cookies',
    'angular-route',
    'jquery',
    'bootstrap',
    'd3',
    'app'
   ],
    function (angular) {
      angular.bootstrap(document, ['app']);
    }
  );
})(requirejs);

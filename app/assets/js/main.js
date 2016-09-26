(function (requirejs) {
  'use strict';

  requirejs.config({
    packages: [],
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
      'angular-resource': ['angular'],
      'angular-ui-bootstrap': ['angular'],
      'angular-ui-bootstrap-tpls': ['angular-ui-bootstrap'],
      'ng-table': ['angular'],
      'bootstrap': ['jquery']
    },
    paths: {
      'requirejs': ['../lib/requirejs/require'],
      'underscore' : ['../lib/underscorejs/underscore'],
      'jquery': ['../lib/jquery/jquery'],
      'angular': ['../lib/angularjs/angular'],
      'angular-route': ['../lib/angularjs/angular-route'],
      'angular-cookies': ['../lib/angularjs/angular-cookies'],
      'angular-resource': ['../lib/angularjs/angular-resource'],
      'angular-ui-bootstrap': ['../lib/angular-ui-bootstrap/dist/ui-bootstrap'],
      'angular-ui-bootstrap-tpls': ['../lib/angular-ui-bootstrap/dist/ui-bootstrap-tpls'],
      'ng-table': ['../lib/ng-table/dist/ng-table'],
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
    'angular-resource',
    'angular-ui-bootstrap',
    'angular-ui-bootstrap-tpls',
    'ng-table',
    'jquery',
    'bootstrap',
    'd3',
    'app',
    'routes'
   ],
    function (angular) {
      angular.bootstrap(document, ['app']);
    }
  );
})(requirejs);

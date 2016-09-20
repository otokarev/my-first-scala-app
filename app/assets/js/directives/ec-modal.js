define(['./module'], function (module) {
  'use strict';
  console.log("Directive OK!");

  module.directive('ecModal', function () {
      return {
        template:
          '<div class="modal">' +
            '<div class="modal-dialog">' +
              '<div class="modal-content">' +
                '<div class="modal-header">' +
                  '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                  '<h4 class="modal-title">{{title}}</h4>' +
                '</div>' +
                '<div class="modal-body" ng-transclude></div>' +
                '<div class="modal-footer">' +
                  '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>' +
                  '<button type="button" class="btn btn-primary">Save changes</button>' +
                '</div>' +
              '</div>' +
            '</div>' +
          '</div>',
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            title: '@title'
        },
        link: function postLink(scope, element, attrs) {
            //scope.$watch(attrs.visible, function(value) {
            //    console.log("visible: "+value);
            //    $(element).modal(value? 'show' : 'hide');
            //});
            attrs.$observe('visible', function(value) {
                console.log("Observe visible: "+value);
                $(element).modal(value ? 'show' : 'hide');
            });

        }
      };
    });
});
define(['./module'], function (module) {
  'use strict';

  module.directive('ecChannelForm', function () {
      return {
        template:
            '<form class="form-horizontal">' +
                '<fieldset>' +
                    '<div class="form-group">' +
                        '<label for="title" class="col-lg-2 control-label">Title</label>' +
                        '<div class="col-lg-10">' +
                            '<input class="form-control" id="title" placeholder="Title" type="text">' +
                        '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                        '<label for="actorClass" class="col-lg-2 control-label">Actor Class</label>' +
                        '<div class="col-lg-10">' +
                            '<input class="form-control" id="actorClass" placeholder="Actor Class" type="text">' +
                        '</div>' +
                    '</div>' +
                    '<!--div class="form-group" >' +
                        '<div class="col-lg-10 col-lg-offset-2">' +
                            '<button type="reset" class="btn btn-default">Cancel</button>' +
                            '<button type="submit" class="btn btn-primary">Submit</button>' +
                        '</div>' +
                    '</div-->' +
                '</fieldset>' +
            '</form>',
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: true,
        link: function postLink(scope, element, attrs) {
        }
      };
    });
});

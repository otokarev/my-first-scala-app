define(['./module'], function (module) {
  'use strict';

  module.directive('ecChannelModal', function () {
      return {
        template:
          '<div class="modal">' +
            '<div class="modal-dialog">' +
              '<div class="modal-content">' +
                '<div class="modal-header">' +
                  '<button type="button" class="close" ng-click="close()" aria-hidden="true">&times;</button>' +
                  '<h4 class="modal-title">{{title}}</h4>' +
                '</div>' +
                '<div class="modal-body" ng-transclude>' +
                    '<form class="form-horizontal">' +
                        '<fieldset>' +
                            '<div class="form-group">' +
                                '<label for="title" class="col-lg-2 control-label">Title</label>' +
                                '<div class="col-lg-10">' +
                                    '<input class="form-control" id="title" placeholder="Title" type="text" value="{{item.title}}">' +
                                '</div>' +
                            '</div>' +
                            '<div class="form-group">' +
                                '<label for="actorClass" class="col-lg-2 control-label">Actor Class</label>' +
                                '<div class="col-lg-10">' +
                                    '<input class="form-control" id="actorClass" placeholder="Actor Class" type="text" value="{{item.actorClass}}">' +
                                '</div>' +
                            '</div>' +
                        '</fieldset>' +
                    '</form>' +
                '</div>' +
                '<div class="modal-footer">' +
                  '<button type="button" class="btn btn-default" ng-click="close()">Close</button>' +
                  '<button type="button" class="btn btn-primary" ng-click="save()">Save changes</button>' +
                '</div>' +
              '</div>' +
            '</div>' +
          '</div>',
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            title: '=',
            item: '=',
            visible: '=',
            onSave: '='
        },
        controller: function($scope, $element) {
            $scope.close = function () {
                console.log("Close: ");
                console.log($scope.item);
                $($element).modal('hide');
                $scope.visible = false;
                $scope.item = undefined;
            };
            $scope.save = function () {
                $scope.onSave($scope.item).then(function (data) {
                    console.log("Save: ");
                    console.log($scope.item);
                    $($element).modal('hide');
                    $scope.visible = false;
                }, function (error) {
                    console.log("Save (error): "+error);
                });
            };
        },
        link: function(scope, element, attrs) {
            scope.$watch('visible', function(value) {
                console.log("Visible: "+value);
                if (value === true) {
                    $(element).modal('show');
                } else {
                    $(element).modal('hide');
                }
            });

        }
      };
    });
});
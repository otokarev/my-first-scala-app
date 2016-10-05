define(['./module'], function (module) {
  'use strict';

  module.directive('ecFormModal', function () {
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
            visible: '='
        },
        controller: function($scope, $element) {
            $scope.close = function () {
                $($element).modal('hide');
                $scope.visible = false;
            };
            $scope.save = function () {
                console.log($scope.item);
                $scope.onChildFormSubmit().then(function (data) {
                    $($element).modal('hide');
                    $scope.visible = false;
                });
            };
        },
        link: function(scope, element, attrs) {
            scope.$watch('visible', function(value) {
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

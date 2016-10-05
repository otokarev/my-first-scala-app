define(['./module'], function (module) {
  'use strict';

  module.directive('ecItemForm', function () {
      return {
        templateUrl: function (el, attrs) {
            return '/assets/partials/form-'+attrs.type+'.html';
        },
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            item: '=',
            // Method with name below will be created in parent controller
            // Default: onChildFormSubmit
            onSubmitMethodName: '@',
            // Name to be called after all form fields validation to submit data
            onSubmit: '='
        },
        controller: function($scope, $element) {
            $scope.submit = function () {
                return $scope.onSubmit($scope.item).then(function (data) {
                    // TODO: show success message
                    console.log("Form submitted");
                }, function (error) {
                    // TODO: show error message
                    console.log("Cannot submit form");
                });
            };
        },
        link: function(scope, element, attrs) {
            var parentClkName = scope.onSubmitMethodName ? scope.onSubmitMethodName : "onChildFormSubmit";
            scope.$parent.$parent[parentClkName] = scope.submit;
        }
      };
    });
});

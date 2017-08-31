'use strict';
define(['clickerQuest'], function(clickerQuest) {

	clickerQuest.controller('HomeCtrl', ['$scope', function($scope) {
    $scope.spice = 'very';

    $scope.chiliSpicy = function() {
      $scope.spice = 'chili';
    };

    $scope.jalapenoSpicy = function() {
      $scope.spice = 'jalapeño';
    };
  }]);
});

define(['clickerQuest', 'services/UserService'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('userRanking', function($scope, UserService) {

      $scope.page = getParameterByName("page", window.location.href);
      if (!$scope.page) $scope.page = 1;

      UserService.getAllUsersByPage($scope.page, 20).then(
        function (response) {
          $scope.users = response.data.elements;
          $scope.totalPages = response.data.total_pages;

        }
      );



    });

});

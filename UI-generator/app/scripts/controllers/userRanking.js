define(['clickerQuest', 'services/UserService'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('userRanking', function($scope, UserService) {

      $scope.page = parseInt(getParameterByName("page", window.location.href));
      if (!$scope.page) $scope.page = 1;

      $scope.itemsPerPage = 10;

      UserService.getAllUsersByPage($scope.page, $scope.itemsPerPage).then(
        function (response) {
          $scope.users = response.data.elements;
          $scope.users.forEach(function (u) {
            u.score = formatDecimal(u.score, 2);
          });

          $scope.totalPages = response.data.total_pages;
        }
      );



    });

});

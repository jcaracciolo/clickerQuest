define(['clickerQuest', 'services/ClanService'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('clanRanking', function($scope, ClanService) {

      $scope.page = getParameterByName("page", window.location.href);
      if (!$scope.page) $scope.page = 1;

      ClanService.getAllClansByPage($scope.page, 20).then(
        function (response) {
          $scope.clans = response.data.elements;
          $scope.clans.forEach(function (c) {
            c.image_url = getClanImage(c.id);
          });

          $scope.totalPages = response.data.total_pages;
        }
      );


    });

});

define(['clickerQuest'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('clanCtrl', function($scope) {
      $scope.clan = {
        id: 0,
        name: "MY CLAN",
        score: 654,
        ranking: 12,
        cantBattlesWon: 5,
        cantBattles: 8
      };

      $scope.battle = {
        versus: {
                      id: 2,
                      name: "EVIL CLAN",
                      score: 548,
                      ranking: 18
                    },
        myInitialScore: 666,
        versusInitialScore: 546
      };

      $scope.members = [
        {name: "USER1", id: 0, ranking: 44, score: 112},
        {name: "USER2", id: 1, ranking: 4, score: 532},
        {name: "USER3", id: 2, ranking: 188, score: 52}
      ]

    });

});

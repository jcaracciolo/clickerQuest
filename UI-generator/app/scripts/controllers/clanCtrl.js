define(['clickerQuest', 'services/UserService','services/ClanService'], function(clickerQuest) {

  'use strict';
  clickerQuest.controller('clanCtrl', function($scope, ClanService) {

    $scope.clan = {
      id: null,
      name: null,
      image: null, // TODO: put default profile image path
      score: null,
      rank: null,
      wins: null,
      battles: null
    };

    ClanService.getClan(80).then(
      function(response) {
        $scope.clan.id = response.data.id;
        $scope.clan.name = response.data.name;
        $scope.clan.image = response.data.image;
        $scope.clan.score = response.data.score;
        $scope.clan.wins = response.data.wins;
        $scope.clan.battles = response.data.battles;
      }
    );

    ClanService.getClanRank(80).then(
      function (response) {
        $scope.clan.rank = response.data.rank;
      }
    );

    $scope.battle = {
      versus: {
        id: null,
        name: null,
        image: null,
        score: null
      },
      myInitialScore: null,
      versusInitialScore: null
    };

    ClanService.getClanBattle(80).then(
      function (response) {
        $scope.battle.myInitialScore = response.data.initial_score;
        $scope.battle.versusInitialScore = response.data.opponent_initial_score;

      //  TODO: GET VERSUS CLAN WITH CLANID
      });


    $scope.members = [ ];

    ClanService.getClanUsers(80).then(
      function(response) {
        var users = response.data.users;
        users.forEach(function (u) {
          $scope.members.push({name: u.username, image: u.profile_image_url ,score: u.score});
        });

        $scope.members.sort(function (u1, u2) {
          return u2.score - u1.score;
        })
      }
    )

  });

});

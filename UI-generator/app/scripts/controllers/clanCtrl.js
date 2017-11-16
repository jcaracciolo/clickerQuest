define(['clickerQuest', 'services/UserService','services/ClanService'], function(clickerQuest) {

  'use strict';
  clickerQuest.controller('clanCtrl', function($scope, $http, ClanService) {

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
        score: null,
      },
      myInitialScore: null,
      versusInitialScore: null
    };

    ClanService.getClanBattle(80).then(
      function (response) {
        $scope.battle.myInitialScore = response.data.initial_score;
        $scope.battle.versusInitialScore = response.data.opponent_initial_score;

        var versusbattle = $http.get(response.data.opponent_clan_battl_url);

        versusbattle.then(
          function (response) {
            var versusClan = $http.get(response.data.clan_url);

            versusClan.then(
              function (response) {
                $scope.battle.versus.id = response.data.id;
                $scope.battle.versus.name = response.data.name;
                $scope.battle.versus.image = response.data.image;
                $scope.battle.versus.score = response.data.score;
              });
          });
      });


    $scope.members = [
      {name: "USER1", id: 0, ranking: 44, score: 112},
      {name: "USER2", id: 1, ranking: 4, score: 532},
      {name: "USER3", id: 2, ranking: 188, score: 52}
    ];

    ClanService.getClanUsers(80).then(
      function(response) {
        var users = response.data.users;
        users.forEach(function (u) {
          $scope.members.push({name: u.username, rank: "-", image: u.profile_image_url ,score: u.score});

        })
      }
    )

  });

});

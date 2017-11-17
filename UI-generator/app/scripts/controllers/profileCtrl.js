define(['clickerQuest', 'services/UserService', 'services/ClanService'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('profileCtrl', function($scope, UserService, ClanService) {
      $scope.user = {
        username: "Jorgito",
        score: "55.12",
        ranking: 65,
        clan: {
                id: "0",
                name: "MY CLAN"
              }
      };

      UserService.getUser(1).then(
        function (response) {
          $scope.user.username = response.data.username;
          $scope.user.score = response.data.score;

          ClanService.getClan(response.data.clan_id).then(
            function (response) {
              $scope.user.clan.id = response.data.id;
              $scope.user.clan.name = response.data.name;
            }
          );

          UserService.getRank(response.data.id).then(
            function (response) {
              $scope.user.ranking = response.data.rank;
            }
          )
        }
      );


      UserService.getUserFactories(1).then(
        function (response) {
          $scope.factories = response.data.factories;
          $scope.factories.sort(function (f1, f2) {
            return f2.type_id - f1.type_id;
          })
        }
      );


      // It's important to respect id and position in array
      $scope.resources = [
        {name:'Power', id: 0, storage: 25, production: 50.2},
        {name:'Plastic', id: 1, storage: 25, production: 50.2},
        {name:'Gold', id: 2, storage: 25, production: 50.2},
        {name:'Money', id: 3, storage: 25, production: 50.2},
        {name:'People', id: 4, storage: 25, production: 50.2},
        {name:'Iron', id: 5, storage: 25, production: 50.2},
        {name:'Tires', id: 6, storage: 25, production: 50.2},
        {name:'Rubber', id: 7, storage: 25, production: 50.2},
        {name:'Metal Scrap', id:8, storage: 25, production: 50.2},
        {name:'Copper', id: 9, storage: 25, production: 50.2},
        {name:'Copper Cable', id: 10, storage: 25, production: 50.2},
        {name:'Cardboard', id: 11, storage: 25, production: 50.2},
        {name:'Circuits', id: 12, storage: 25, production: 50.2}
      ];

      // TODO: Wait for API update
      // UserService.getWealth(1).then(
      //   function (response) {
      //     $scope.resources = []
      //     var storage = response.data.storage
      //     var productions = response.data.productions
      //     for (var property in storage) {
      //       $scope.resources.push({name:property, id: 0, stock: storage[property][0], production: productions[property][0]})
      //     }
      //   }
      // );

    });

});

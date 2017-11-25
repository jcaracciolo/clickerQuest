define(['clickerQuest','services/UserService', 'services/factoryService'], function(clickerQuest) {

  'use strict';
  clickerQuest.controller('GameCtrl', function($scope, UserService, factoryService) {

    $scope.user = {
      username: null,
      score: null,
      image: null,  // TODO: DEFAULT IMAGE
      ranking: null,
      clan: {
        id: null,
        name: null
      }
    };

    UserService.getUser(1).then(
      function(response) {
        $scope.user.username = response.data.username;
        $scope.user.score = response.data.score;
        $scope.user.image = response.data.profile_image_url;
    },
      function(response) {
        //TODO error
        console.log(response)
      }
    );

    UserService.getRank(1).then(
      function (response) {
        $scope.user.ranking = response.data.rank;
      }
    );


    $scope.resources = [];

    UserService.getWealth(1).then(
      function (response) {
        response.data.resources.forEach(function(res){
          $scope.resources.push({ name: res.name,
                                  id: res.id,
                                  storage: formatDecimal(res.storage, 2),
                                  production: formatDecimal(res.production, 2)})
        });
      }
    );


    $scope.activeFactories = [];

    UserService.getFactories(1).then(
      function (response) {
        $scope.activeFactories = response.data.factories;
        $scope.activeFactories.sort(function (f1, f2) {
          return f1.id - f2.id;
        })
      }
    );


    $scope.factories = [];

    factoryService.getAllFactories().then(
      function (response) {
        response.data.factories.forEach(function (f) {
          factoryService.getFactoryRecipe(f.id).then(
            function (response) {
              $scope.factories.push(response.data);
              $scope.factories.sort(function (f1, f2) {
                return f1.factory_id - f2.factory_id;
              })
            }
          )
        });
      }
    );

  });

});

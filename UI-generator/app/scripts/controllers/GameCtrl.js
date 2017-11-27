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


    $scope.factories = [];

    factoryService.getAllFactories().then(
      function (response) {
        response.data.factories.forEach(function (f) {
          factoryService.getFactoryRecipe(f.id).then(
            function (response) {
              var factInfo = { factory_id: response.data.factory_id,
                                factory_type: response.data.factory_type,
                                cost: [],
                                recipe: { input: [],
                                          output: []
                                        }
                            };
              response.data.recipe.forEach(function (elem) {
                if (elem.storage != null) {
                  factInfo.cost.push(elem);
                }
                if (elem.production < 0) {
                  factInfo.recipe.input.push(elem);
                }
                if (elem.production > 0) {
                  factInfo.recipe.output.push(elem);
                }
              });
              $scope.factories.push(factInfo);
              $scope.factories.sort(function (f1, f2) {
                return f1.factory_id - f2.factory_id;
              })
            }
          )
        });
      }
    );

    $scope.activeFactories = [];

    UserService.getFactories(1).then(
      function (response) {
        response.data.factories.forEach(function (f) {
          if (f.amount != 0) {
            $scope.activeFactories.push(f);
          }
        });
        $scope.activeFactories.sort(function (f1, f2) {
          return f1.id - f2.id;
        })
      }
    );

  });



});

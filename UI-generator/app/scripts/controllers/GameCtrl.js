define(['clickerQuest','services/UserService'], function(clickerQuest) {

  'use strict';
  clickerQuest.controller('GameCtrl', function($scope, UserService) {

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
        console.log($scope.resources)
      }
    );



    // It's important to respect id and position in array
    $scope.factories = [
      {name:'Stock Investment',
        id: 0,
        cantActive: 5,
        cost: [ {id: 3, cant: 100},
                {id: 0, cant: 100} ],
        recipe: {input: [ {id: 1, cant: 2},
                          {id: 2, cant: 2} ],
          output: [ {id: 3, cant: 1},
                    {id: 0, cant:1} ]},
        upgrade: {type: 1, cost: 600}
      },
      {name:'People Recruitment Base',
        id: 1,
        cantActive: 3,
        cost: [ {id: 6, cant: 50} ],
        recipe: {input: [ {id: 4, cant: 3},
                          {id: 1, cant: 1} ],
          output: [ {id: 3, cant: 1} ]},
        upgrade: {type: 1, cost: 115}
      },
      {name:'Junk Collector',
        id: 2,
        cantActive: 0,
        cost: [ {id: 7, cant: 160}],
        recipe: {input: [ ],
          output: [ {id: 12, cant: 105} ]},
        upgrade: {type: 1, cost: 34}
      }
    ];

  });

});

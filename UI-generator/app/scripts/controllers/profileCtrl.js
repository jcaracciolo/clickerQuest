define(['clickerQuest'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('profileCtrl', function($scope) {
      $scope.user = {
        username: "Jorgito",
        score: "55.12",
        ranking: 65,
        clan: {
                id: "0",
                name: "MY CLAN"
              }
      };

      // It's important to respect id and position in array
      $scope.resources = [
        {name:'Power', id: 0, stock: 25, production: 50.2},
        {name:'Plastic', id: 1, stock: 25, production: 50.2},
        {name:'Gold', id: 2, stock: 25, production: 50.2},
        {name:'Money', id: 3, stock: 25, production: 50.2},
        {name:'People', id: 4, stock: 25, production: 50.2},
        {name:'Iron', id: 5, stock: 25, production: 50.2},
        {name:'Tires', id: 6, stock: 25, production: 50.2},
        {name:'Rubber', id: 7, stock: 25, production: 50.2},
        {name:'Metal Scrap', id:8, stock: 25, production: 50.2},
        {name:'Copper', id: 9, stock: 25, production: 50.2},
        {name:'Copper Cable', id: 10, stock: 25, production: 50.2},
        {name:'Cardboard', id: 11, stock: 25, production: 50.2},
        {name:'Circuits', id: 12, stock: 25, production: 50.2}
      ];

      // It's important to respect id and position in array
      $scope.factories = [
        {name:'Stock Investment',
          id:'0',
          cantActive:'5',
          cost: [ {id:'3', cant:'100'},
            {id:'0', cant:'100'} ],
          recipe: {input: [ {id:'1', cant:'2'},
            {id:'2', cant:'2'} ],
            output: [ {id:'3', cant:'1'},
              {id:'0', cant:'1'} ]},
          upgrade: {type:'1', cost:'XXXX'}
        },
        {name:'People Recruitment Base',
          id:'1',
          cantActive:'3',
          cost: [ {id:'6', cant:'50'} ],
          recipe: {input: [ {id:'4', cant:'3'},
            {id:'1', cant:'1'} ],
            output: [ {id:'3', cant:'1'} ]},
          upgrade: {type:'1', cost:'XXXX'}
        },
        {name:'Junk Collector',
          id:'2',
          cantActive:'0',
          cost: [ {id:'7', cant:'160'}],
          recipe: {input: [ ],
            output: [ {id:'12', cant:'1'} ]},
          upgrade: {type:'1', cost:'XXXX'}
        }
      ];
    });

});

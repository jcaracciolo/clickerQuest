define(['clickerQuest'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('mainScreenCtrl', function($scope) {
      $scope.resources = [
        {name:'Circuits', id:'12'},
        {name:'Cardboard', id:'11'},
        {name:'Copper Cable', id:'10'},
        {name:'Copper', id:'9'},
        {name:'Metal Scrap', id:'8'},
        {name:'Rubber', id:'7'},
        {name:'Tires', id:'6'},
        {name:'Iron', id:'5'},
        {name:'People', id:'4'},
        {name:'Money', id:'3'},
        {name:'Gold', id:'2'},
        {name:'Plastic', id:'1'},
        {name:'Power', id:'0'}
      ];

      $scope.activeFactories = [
        {name:'Stock Investment',
          id:'0',
          amount:'XXX',
          consuming:[ ],
          producing:[ {name:'Money', id:'3', cant:'987654321'} ]
        },
        {name:'People Recruiting Base',
          id:'1',
          amount:'XXX',
          consuming:[ {name:'Circuits', id:'12', cant:'123456789'} ],
          producing:[ {name:'Money', id:'3', cant:'987654321'} ]
        },
        {name:'Stock Investment',
          id:'0',
          amount:'XXX',
          consuming:[ {name:'Circuits', id:'12', cant:'123456789'} ],
          producing:[ {name:'Money', id:'3', cant:'987654321'} ]
        }
      ];
    });

});

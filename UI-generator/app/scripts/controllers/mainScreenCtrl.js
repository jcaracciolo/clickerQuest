define(['clickerQuest'], function(clickerQuest) {

  'use strict';
  clickerQuest.controller('mainScreenCtrl', function($scope) {

    // It's important to respect id and position in array
    $scope.resources = [
      {name:'Power', id:'0'},
      {name:'Plastic', id:'1'},
      {name:'Gold', id:'2'},
      {name:'Money', id:'3'},
      {name:'People', id:'4'},
      {name:'Iron', id:'5'},
      {name:'Tires', id:'6'},
      {name:'Rubber', id:'7'},
      {name:'Metal Scrap', id:'8'},
      {name:'Copper', id:'9'},
      {name:'Copper Cable', id:'10'},
      {name:'Cardboard', id:'11'},
      {name:'Circuits', id:'12'}
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

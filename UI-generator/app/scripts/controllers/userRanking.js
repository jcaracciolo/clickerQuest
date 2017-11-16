define(['clickerQuest'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('userRanking', function($scope) {
        $scope.users = [
          {name: "Jorgito", id: 1, ranking: 1, score: 123}
        ]
    });

});

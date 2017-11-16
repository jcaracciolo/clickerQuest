define(['clickerQuest'], function(clickerQuest) {

    'use strict';
    clickerQuest.controller('userRanking', function($scope) {
        $scope.users = [
          {username: "Jorgito", id: 1, ranking: 1, score: 123}
        ]
    });

});

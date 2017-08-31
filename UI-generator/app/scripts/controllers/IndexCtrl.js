'use strict';
define(['clickerQuest'], function(clickerQuest) {

	clickerQuest.controller('IndexCtrl', function($scope, $http) {
    $http.get('https://api.github.com').
      then(function(response) {
        $scope.greeting = response.data["followers_url"];
      });
	});
});

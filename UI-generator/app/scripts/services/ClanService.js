define(['clickerQuest'], function(clickerquest) {

  'use strict';
  clickerquest.service('ClanService', function($http) {
    this.getAllClans = function() {
      return $http.get("http://localhost:8080/api/v1/clans/all");
    };

    this.getClan = function(clanID) {
      return $http.get("http://localhost:8080/api/v1/clans/" + clanID);
    };

    this.getClanUsers = function(clanID) {
      return $http.get("http://localhost:8080/api/v1/clans/" + clanID + "/users");
    };

    this.getClanBattle = function(clanID) {
      return $http.get("http://localhost:8080/api/v1/clans/" + clanID + "/battle");
    };

    this.getClanRank = function(clanID) {
      return $http.get("http://localhost:8080/api/v1/clans/" + clanID + "/rank");
    };

    this.getClan = function(clanID) {
      return $http.get("http://localhost:8080/api/v1/clans/" + clanID);
    };

  });

});

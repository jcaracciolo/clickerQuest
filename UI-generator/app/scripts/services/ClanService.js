define(['clickerQuest'], function(clickerquest) {

  'use strict';
  clickerquest.service('ClanService', function($http) {

    this.url = "http://localhost:8080/api/v1/clans/";

    this.getAllClans = function() {
      return $http.get(this.url + "all");
    };

    this.getAllClansByPage = function(page, pageSize) {
      return $http.get(this.url + "all?page=" + page + "&pageSize=" + pageSize);
    };

    this.getClan = function(clanID) {
      return $http.get(this.url + clanID);
    };

    this.getClanUsers = function(clanID) {
      return $http.get(this.url + clanID + "/users");
    };

    this.getClanBattle = function(clanID) {
      return $http.get(this.url + clanID + "/battle");
    };

    this.getClanRank = function(clanID) {
      return $http.get(this.url + clanID + "/rank");
    };

    this.getClan = function(clanID) {
      return $http.get(this.url + clanID);
    };

  });

});

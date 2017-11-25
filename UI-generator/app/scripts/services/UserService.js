define(['clickerQuest'], function(clickerquest) {

    'use strict';
    clickerquest.service('UserService', function($http) {

      this.url = "http://localhost:8080/api/v1/users/";

      this.getUser = function(userId) {
        return $http.get(this.url + userId);
      }

      this.getAllUsers = function() {
        return $http.get(this.url + "all");
      }

      this.getAllUsersByPage = function(page, pageSize) {
        return $http.get(this.url + "all?page=" + page + "&pageSize=" + pageSize);
      }

      this.getWealth = function(userId) {
        return $http.get(this.url + userId + "/wealth");
      }

      this.getUserFactories = function(userId) {
        return $http.get(this.url + userId + "/factories");
      }

      this.getFactories = function(userId) {
        return $http.get(this.url + userId + "/factories");
      }

      this.getFactory = function(userId, factoryID) {
        return $http.get(this.url + userId + "/factories/" + factoryID);
      }

      this.getFactoryUpgrade = function(userId, factoryID) {
        return $http.get(this.url + userId + "/factories/" + factoryID + "/upgrade");
      }

      this.getRank = function(userId) {
        return $http.get(this.url + userId + "/rank");
      }


    });

});

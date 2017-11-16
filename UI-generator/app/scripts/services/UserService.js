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

      this.getWealth = function(userId) {
        return $http.get(this.url + userId + "/wealth");
      }

      this.getUserFactories = function(userId) {
        return $http.get(this.url + userId + "/factories");
      }

      this.getFactories = function(userId, factoryID) {
        return $http.get(this.url + userId + "/factories/" + factoryID);
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

define(['clickerQuest'], function(clickerquest) {

    'use strict';
    clickerquest.service('factoryService', function($http) {

      this.url = "http://localhost:8080/api/v1/factories/";

      this.getAllFactories = function () {
        return $http.get(this.url + "all");
      };

      this.getFactory = function (factoryId) {
        return $http.get(this.url + factoryId);
      };

      this.getFactoryRecipe = function (factoryId) {
        return $http.get(this.url + factoryId + "/recipe");
      };

    });

});

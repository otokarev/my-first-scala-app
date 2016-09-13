define(['underscore'], function (app) {
    'use strict';

    var Subscribers = function ($http, utility) {
         var url = utility.baseAddress + "channel";
         return {
             getList: function () {
                 return $http.get(url);
             },
             get: function (v) {
                 return $http.get(url + "/" + v.id);
             },
             add: function (v) {
                 return $http.post(url, v);
             },
             delete: function (v) {
                 return $http.delete(url + "/" + v.id);
             },
             update: function (v) {
                 return $http.put(url + "/" + v.id, v);
             }
         };
    };
    Subscribers.$inject = ['$http', 'utility'];

    return Subscribers;
});

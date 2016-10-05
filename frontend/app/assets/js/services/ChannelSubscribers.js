define(['./module'], function (module) {
  'use strict';

    module.factory('ChannelSubscribers', ['$resource', '$http', function ($resource, $http) {
        $http.defaults.headers.common['Content-Type']= 'application/json; charset=UTF-8';
        return $resource('/channel-subscriber/:id', {id: '@id'},
            {
                'query': {method: 'GET'},
                'update': {method: 'PUT'}
            });
    }]);
});


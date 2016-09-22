define(['./module'], function (module) {
  'use strict';

    module.factory('Channels', ['$resource', function ($resource) {
        return $resource('/channel/:id', {id: '@id'},
            {
                'get': {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                },
                'query': {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                },
                'update': {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                },
                'save': {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                },
                'delete': {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }
            });
    }]);
});
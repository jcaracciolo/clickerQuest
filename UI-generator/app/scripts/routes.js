'use strict';

define([], function() {
    return {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: '/views/home.html',
                controller: 'SpicyController'
            },
            '/game': {
                templateUrl: '/views/game/mainScreenCtrl.html',
                controller: 'mainScreenCtrl'
            },
            '/profile': {
                templateUrl: '/views/profile/profileCtrl.html',
                controller: 'profileCtrl'
            },
            '/clan': {
                templateUrl: '/views/clan/clanCtrl.html',
                controller: 'clanCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});

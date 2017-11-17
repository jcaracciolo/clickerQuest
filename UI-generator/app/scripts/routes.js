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
                templateUrl: '/views/game/GameCtrl.html',
                controller: 'GameCtrl'
            },
            '/profile': {
                templateUrl: '/views/profile/profileCtrl.html',
                controller: 'profileCtrl'
            },
            '/clan': {
                templateUrl: '/views/clan/clanCtrl.html',
                controller: 'clanCtrl'
            },
            '/userRanking': {
                templateUrl: '/views/userRanking/userRanking.html',
                controller: 'userRanking'
            },
            '/clanRanking': {
                templateUrl: '/views/clanRanking/clanRanking.html',
                controller: 'clanRanking'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});

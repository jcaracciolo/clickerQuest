---
title: API Reference

language_tabs: # must be one of https://git.io/vQNgJ
  - javascript
  - shell

toc_footers:
  - <a href='https://github.com/lord/slate'>Documentation Powered by Slate</a>

includes:
  - errors

search: true
---

# Introduction

Welcome to the ClickerQuest API! Here you can see how to access all of the ClickerQuest API in order to be able to play the game.

We have language bindings in Javascript and Shell! You can view code examples in the dark area to the right, and you can switch the programming language of the examples with the tabs in the top right.

This API documentation page was created with [Slate](https://github.com/lord/slate).

# Authentication

> To authorize, use this code:

```shell
# With shell, you can just pass the correct header with each request
curl "api_endpoint_here"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
```

> Make sure to replace `meowmeowmeow` with your token.

ClickerQuest uses a Token Authorization method. In order to login and get your token do the following.
`Authorization: meowmeowmeow`

<aside class="notice">
You must replace <code>meowmeowmeow</code> with your personal API key.
</aside>

# Users

## Get All Users

```shell
curl "http://example.com/api/v1/users/all"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let kittens = api.kittens.get();
```

> The above command returns JSON structured like this:

```json
{
    "page": 1,
    "items_per_page": 20,
    "total_pages": 1,
    "total_items": 2,
    "elements": [
        {
            "type": "User",
            "id": 1,
            "username": "Wololo",
            "profile_image_url": "http://localhost:8080/api/resources/profile_images/10.jpg",
            "score": 86035162.84607725,
            "clan_id": 20,
            "clan_url": "http://localhost:8080/api/v1/clans/20",
            "factories_url": "http://localhost:8080/api/v1/users/1/factories",
            "wealth_url": "http://localhost:8080/api/v1/users/1/wealth",
            "rank_url": "http://localhost:8080/api/v1/users/1/rank"
        },
        {
            "type": "User",
            "id": 2,
            "username": "aaaa",
            "profile_image_url": "http://localhost:8080/api/resources/profile_images/2.jpg",
            "score": 1895.770193289029,
            "clan_id": 13,
            "clan_url": "http://localhost:8080/api/v1/clans/13",
            "factories_url": "http://localhost:8080/api/v1/users/2/factories",
            "wealth_url": "http://localhost:8080/api/v1/users/2/wealth",
            "rank_url": "http://localhost:8080/api/v1/users/2/rank"
        }
    ]
}
```

This endpoint retrieves all kittens.

### HTTP Request

`GET http://pawserver.it.itba.edu.ar/paw-2017a-4/api/v1/users/all`

### Query Parameters

Parameter | Default | Description
--------- | ------- | -----------
page | 1 | Number of page of user to be provided.
pageSize | 20 | Number of users per page.

## Get a Specific User

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "id": 1,
    "username": "Wololo",
    "profile_image_url": "http://localhost:8080/api/resources/profile_images/10.jpg",
    "score": 86035162.84607725,
    "clan_id": 20,
    "clan_url": "http://localhost:8080/api/v1/clans/20",
    "factories_url": "http://localhost:8080/api/v1/users/1/factories",
    "wealth_url": "http://localhost:8080/api/v1/users/1/wealth",
    "rank_url": "http://localhost:8080/api/v1/users/1/rank"
}
```

This endpoint retrieves a specific user.

### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the user to retrieve

## Get an User's Wealth

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "user_id": 1,
    "resources": [
        {
            "id": 12,
            "name": "CIRCUITS",
            "storage": 19534118,
            "production": 2
        },
        {
            "id": 11,
            "name": "CARDBOARD",
            "storage": 200555196884,
            "production": 14326.523556775875
        },
        {
            "id": 10,
            "name": "COPPER_CABLE",
            "storage": 12981876,
            "production": 1.4000000000000004
        },
        {
            "id": 9,
            "name": "COPPER",
            "storage": 492084727,
            "production": 28.800000000000004
        },
        {
            "id": 8,
            "name": "METAL_SCRAP",
            "storage": 60404333766,
            "production": 4451.802047317245
        },
        {
            "id": 7,
            "name": "RUBBER",
            "storage": 192637803,
            "production": 3.200000000000003
        },
        {
            "id": 6,
            "name": "TIRES",
            "storage": 20342383775,
            "production": 1499.0099999999998
        },
        {
            "id": 5,
            "name": "IRON",
            "storage": 1066041490,
            "production": 79.2
        },
        {
            "id": 4,
            "name": "PEOPLE",
            "storage": 226940578509,
            "production": 16818.48
        },
        {
            "id": 3,
            "name": "MONEY",
            "storage": 115751950518337,
            "production": 8580025.549999999
        },
        {
            "id": 2,
            "name": "GOLD",
            "storage": 0,
            "production": 0
        },
        {
            "id": 1,
            "name": "PLASTIC",
            "storage": 123,
            "production": 0
        },
        {
            "id": 0,
            "name": "POWER",
            "storage": 1043750483,
            "production": 96.35000000000002
        }
    ]
}
```

This endpoint returns a given user's wealth.

### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/wealth`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the wealth's user to retrieve

## Get an User's factories

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "user_id": 1,
    "factories": [
        {
            "id": 1,
            "type": "PEOPLE_RECRUITER",
            "amount": 53392,
            "level": 1,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/1/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/1/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/1/recipe"
        },
        {
            "id": 6,
            "type": "BOILER",
            "amount": 187,
            "level": 2,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/6/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/6/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/6/recipe"
        },
        {
            "id": 5,
            "type": "CABLE_MAKER",
            "amount": 8,
            "level": 0,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/5/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/5/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/5/recipe"
        },
        {
            "id": 7,
            "type": "CIRCUIT_MAKER",
            "amount": 1,
            "level": 0,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/7/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/7/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/7/recipe"
        },
        {
            "id": 0,
            "type": "STOCK_INVESTOR",
            "amount": 5917259,
            "level": 17,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/0/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/0/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/0/recipe"
        },
        {
            "id": 2,
            "type": "JUNK_COLLECTOR",
            "amount": 6987,
            "level": 5,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/2/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/2/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/2/recipe"
        },
        {
            "id": 3,
            "type": "METAL_SEPARATOR",
            "amount": 132,
            "level": 1,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/3/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/3/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/3/recipe"
        },
        {
            "id": 4,
            "type": "RUBBER_SHREDDER",
            "amount": 54,
            "level": 0,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/4/upgrade",
            "buyLimits_url": "http://localhost:8080/users/1/factories/4/buyLimits",
            "recipe_url": "http://localhost:8080/api/v1/users/1/factories/4/recipe"
        }
    ]
}
```

This endpoint returns a given user's factories.

### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/factories`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the factories' user to retrieve

## Get an User's factory

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "id": 1,
    "type": "PEOPLE_RECRUITER",
    "amount": 53392,
    "level": 1,
    "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/1/upgrade",
    "buyLimits_url": "http://localhost:8080/users/1/factories/1/buyLimits",
    "recipe_url": "http://localhost:8080/api/v1/users/1/factories/1/recipe"
}
```

This endpoint returns a given user's specific factory.

### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/factories/<FactoryID>`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the factory's user to retrieve
FactoryID | The ID of the factoryType to retrieve

## Get an User's factory's recipe

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "user_id": 1,
    "factory_id": 0,
    "recipe": [
        {
            "id": 3,
            "name": "MONEY",
            "storage": 3379992545,
            "production": 1.45
        }
    ]
}
```

This endpoint returns a given user's specific factory's upgrade.
### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/factories/<FactoryID>/recipe`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the factory's user to retrieve
FactoryID | The ID of the factoryType to retrieve


## Get an User's factory's upgrade

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "type": 1,
    "factory_type_id": 0,
    "factory_type": "STOCK_INVESTOR",
    "level": 18,
    "description": "Upgrade n°18",
    "cost": 5400
}
```

This endpoint returns a given user's specific factory's upgrade.
### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/factories/<FactoryID>/upgrade`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the factory's user to retrieve
FactoryID | The ID of the factoryType to retrieve

## Get an User's Buying Limits

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "user_id": 1,
    "factory_id": 0,
    "max": 34147,
    "cost_max": [
        {
            "id": 3,
            "name": "MONEY",
            "storage": 115749615600134,
            "production": 34147
        }
    ],
    "cost_1": [
        {
            "id": 3,
            "name": "MONEY",
            "storage": 3379992545,
            "production": 1
        }
    ],
    "cost_10": [
        {
            "id": 3,
            "name": "MONEY",
            "storage": 33799951155,
            "production": 10
        }
    ],
    "cost_100": [
        {
            "id": 3,
            "name": "MONEY",
            "storage": 338002081999,
            "production": 100
        }
    ]
}
```

This endpoint retrieves the maximun amount of factories of type <factoryID> which the user can buy with the current resources, and whether or no (and the cost) of buying 1, 10 and 100 of the given factory.
### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/factories/<factoryID>/buyLimits`

### Path Parameters

Parameter | Description
--------- | -----------
ID | The ID of the factory's user to retrieve
FactoryID | The ID of the factoryType to retrieve



## Get an User's rank

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "user_id": 1,
    "user_url": "http://localhost:8080/api/v1/users/1",
    "rank": 1
}
```

This endpoint returns a given user's specific factory.

### HTTP Request

`GET http://localhost:8080/api/v1/users/<ID>/rank`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the rank's user to retrieve


# Clans

## Get All Clans

```shell
curl "http://example.com/api/v1/users/all"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let kittens = api.kittens.get();
```

> The above command returns JSON structured like this:

```json
{
    "page": 1,
    "items_per_page": 20,
    "total_pages": 1,
    "total_items": 2,
    "elements": [
        {
            "type": "Clan",
            "id": 20,
            "name": "wololox",
            "score": 86035162.84607725,
            "users_url": "http://localhost:8080/api/v1/clans/20/users",
            "wins": 7,
            "battles": 3646,
            "image": "http://localhost:8080/api/v1/resources/group_icons/1.jpg",
            "battle_url": "http://localhost:8080/api/v1/clans/20/battle",
            "clan_rank_url": "http://localhost:8080/api/v1/clans/20/rank"
        },
        {
            "type": "Clan",
            "id": 13,
            "name": "a",
            "score": 1895.770193289029,
            "users_url": "http://localhost:8080/api/v1/clans/13/users",
            "wins": 2,
            "battles": 4133,
            "image": "http://localhost:8080/api/v1/resources/group_icons/1.jpg",
            "battle_url": "http://localhost:8080/api/v1/clans/13/battle",
            "clan_rank_url": "http://localhost:8080/api/v1/clans/13/rank"
        }
    ]
}
```

This endpoint retrieves all kittens.

### HTTP Request

`GET http://pawserver.it.itba.edu.ar/paw-2017a-4/api/v1/clans/all`

### Query Parameters

Parameter | Default | Description
--------- | ------- | -----------
page | 1 | Number of page of user to be provided.
pageSize | 20 | Number of users per page.

## Get a Specific Clan

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "id": 20,
    "name": "wololox",
    "score": 86035162.84607725,
    "users_url": "http://localhost:8080/api/v1/clans/20/users",
    "wins": 7,
    "battles": 3646,
    "image": "http://localhost:8080/api/v1/resources/group_icons/1.jpg",
    "battle_url": "http://localhost:8080/api/v1/clans/20/battle",
    "clan_rank_url": "http://localhost:8080/api/v1/clans/20/rank"
}
```

This endpoint retrieves a specific clan.

### HTTP Request

`GET http://localhost:8080/api/v1/clans/<ID>`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the clan to retrieve

## Get a Clan's users

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "clan_id": 20,
    "members": 1,
    "users": [
        {
            "id": 1,
            "username": "Wololo",
            "profile_image_url": "http://localhost:8080/api/v1/resources/profile_images/10.jpg",
            "score": 86035162.84607725,
            "clan_id": 20,
            "clan_url": "http://localhost:8080/api/v1/clans/20",
            "factories_url": "http://localhost:8080/api/v1/users/1/factories",
            "wealth_url": "http://localhost:8080/api/v1/users/1/wealth",
            "rank_url": "http://localhost:8080/api/v1/users/1/rank"
        }
    ]
}
```

This endpoint returns a given clan's users.

### HTTP Request

`GET http://localhost:8080/api/v1/clans/<ID>/users`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the users' clan to retrieve

## Get a Clan's battle

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "clan_url": "http://localhost:8080/api/v1/clans/20",
    "initial_score": 86035162.84607725,
    "opponent_initial_score": 1895.770193289029,
    "opponent_clan_battl_url": "http://localhost:8080/api/v1/clans/13/battle"
}
```

This endpoint returns a given clan's battle.

### HTTP Request

`GET http://localhost:8080/api/v1/clans/<ID>/battle`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the battle's clan to retrieve

## Get a Clan's rank

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "clan_id": 20,
    "clan_url": "http://localhost:8080/api/v1/clans/20",
    "rank": 1
}
```

This endpoint returns a given clan's battle.

### HTTP Request

`GET http://localhost:8080/api/v1/clans/<ID>/rank`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the rank's clan to retrieve


## Join a Clan

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "id": 20,
    "name": "WololoClan",
    "score": 86035162.84607725,
    "users_url": "http://localhost:8080/api/v1/clans/20/users",
    "wins": 7,
    "battles": 3646,
    "image": "http://localhost:8080/api/v1/resources/group_icons/1.jpg",
    "battle_url": "http://localhost:8080/api/v1/clans/20/battle"
}
```

This endpoint joins the user to a clan.

### HTTP Request

`POST http://localhost:8080/api/v1/clans/join`

### Body Parameters

Parameter | Description
--------- | -----------
id | The ID of the clan to join.

### Errors
Code | Description
--------- | -----------
419 | User is already part of a clan.

## Create a Clan

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command returns JSON structured like this:

```json
{
    "id": 20,
    "name": "WololoClan",
    "score": 86035162.84607725,
    "users_url": "http://localhost:8080/api/v1/clans/20/users",
    "wins": 0,
    "battles": 0,
    "image": "http://localhost:8080/api/v1/resources/group_icons/1.jpg",
}
```

This endpoint returns a given clan's battle.

### HTTP Request

`POST http://localhost:8080/api/v1/clans/create`

### Body Parameters

Parameter | Description
--------- | -----------
name | The new clan's name

### Errors
Code | Description
--------- | -----------
419 | User is already part of a clan.
419 | Clan with name <name> already exists.

## Leave a Clan

```shell
curl "http://example.com/api/kittens/2"
  -X DELETE
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.delete(2);
```

> The above command does not return a body

This endpoint removes an user from a clan, deletes de clan if is empty.

### HTTP Request

`DELETE http://localhost:8080/api/v1/clans/leave`

### Errors
Code | Description
--------- | -----------
419 | User is no part of a clan.

# Factories

## Get All factories

```shell
curl "http://example.com/api/v1/users/all"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let kittens = api.kittens.get();
```

> The above command returns JSON structured like this:

```json
{
    "factories": [
        {
            "id": 0,
            "type": "STOCK_INVESTOR",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "PEOPLE_RECRUITER",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "JUNK_COLLECTOR",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "METAL_SEPARATOR",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "RUBBER_SHREDDER",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "CABLE_MAKER",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "BOILER",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        },
        {
            "id": 0,
            "type": "CIRCUIT_MAKER",
            "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
        }
    ]
}
```

This endpoint retrieves all factories.

### HTTP Request

`GET http://pawserver.it.itba.edu.ar/paw-2017a-4/api/v1/factories/all`

## Get a Specific Factory

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "id": 0,
    "type": "STOCK_INVESTOR",
    "recipe_url": "http://localhost:8080/api/v1/factories/0/recipe"
}
```

This endpoint retrieves a specific factory.

### HTTP Request

`GET http://localhost:8080/api/v1/factories/<factoryId>`

### URL Parameters

Parameter | Description
--------- | -----------
factoryId | The Id of the factory to retrieve

## Get a Factory's recipe

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "factory_id": 0,
    "factory_type": "STOCK_INVESTOR",
    "recipe": [
        {
            "id": 3,
            "name": "MONEY",
            "storage": 1000,
            "production": 1
        }
    ]
}
```

This endpoint retrieves a specific factory's recipe.

### HTTP Request

`GET http://localhost:8080/api/v1/factories/<factoryId>/recipe`

### URL Parameters

Parameter | Description
--------- | -----------
factoryId | The Id of the recipe's factory to retrieve


## Purchase Factories

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command does not return a body

This endpoint attempts to purhcase a factory with the 
### HTTP Request

`POST http://localhost:8080/api/v1/factories/purchase`

### Body Parameters

Parameter | Description
--------- | -----------
id | The factory's ID to be purchased
amount | The amount of factories desired to be purchased

### Errors
Code | Description
--------- | -----------
419 | User does not have either resources or production required to make the purchase.


## Upgrade a Factory

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command does not return a body

This endpoint attempts to upgrade an user's factory. 
### HTTP Request

`POST http://localhost:8080/api/v1/factories/purchase`

### Body Parameters

Parameter | Description
--------- | -----------
id | The factory's ID to be upgraded

### Errors
Code | Description
--------- | -----------
419 | User does not have the money required to upgrade the factory.

# Market

## Get the Market's prices

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "market": [
        {
            "id": 12,
            "name": "CIRCUITS",
            "price": 52
        },
        {
            "id": 11,
            "name": "CARDBOARD",
            "price": 1
        },
        {
            "id": 10,
            "name": "COPPER_CABLE",
            "price": 10
        },
        {
            "id": 9,
            "name": "COPPER",
            "price": 1
        },
        {
            "id": 8,
            "name": "METAL_SCRAP",
            "price": 12
        },
        {
            "id": 7,
            "name": "RUBBER",
            "price": 6
        },
        {
            "id": 6,
            "name": "TIRES",
            "price": 9
        },
        {
            "id": 5,
            "name": "IRON",
            "price": 6
        },
        {
            "id": 4,
            "name": "PEOPLE",
            "price": 10
        },
        {
            "id": 3,
            "name": "MONEY",
            "price": 10
        },
        {
            "id": 2,
            "name": "GOLD",
            "price": 21
        },
        {
            "id": 1,
            "name": "PLASTIC",
            "price": 42
        },
        {
            "id": 0,
            "name": "POWER",
            "price": 46
        }
    ]
}
```

This endpoint retrieves the maximun amount of factories of type <factoryID> which the user can buy with the current resources, and whether or no (and the cost) of buying 1, 10 and 100 of the given factory.
### HTTP Request

`GET http://localhost:8080/api/v1/market/prices`

## Purchase Resources

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command does not return a body

This endpoint attempts to upgrade an user's factory. 
### HTTP Request

`POST http://localhost:8080/api/v1/maket/purchase`

### Body Parameters

Parameter | Description
--------- | -----------
resource_type | The resourceType codeName to be purchased
amount | The amount of resources desired to be purchased


### Errors
Code | Description
--------- | -----------
419 | User does not have the money required to buy that amount of resources.

## Sell Resources

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command does not return a body

This endpoint attempts to upgrade an user's factory. 
### HTTP Request

`POST http://localhost:8080/api/v1/maket/sell`

### Body Parameters

Parameter | Description
--------- | -----------
resource_type | The resourceType codeName to be sold
amount | The amount of resources desired to be sold


### Errors
Code | Description
--------- | -----------
419 | User does not have that amount of resources to sell.

# Search

## Search for by user's o clan's name

```shell
curl "http://example.com/api/kittens/2"
  -H "Authorization: meowmeowmeow"
```

```javascript
const kittn = require('kittn');

let api = kittn.authorize('meowmeowmeow');
let max = api.kittens.get(2);
```

> The above command returns JSON structured like this:

```json
{
    "results": [
        {
            "type": "user",
            "name": "wasabu",
            "url": "http://localhost:8080/api/v1/users/3"
        },
        {
            "type": "user",
            "name": "aaaa",
            "url": "http://localhost:8080/api/v1/users/2"
        },
        {
            "type": "clan",
            "name": "a",
            "url": "http://localhost:8080/api/v1/clans/13"
        },
        {
            "type": "clan",
            "name": "AAA",
            "url": "http://localhost:8080/api/v1/clans/21"
        }
    ]
}
```

This endpoint retrieves a list of users and clans which name contains the query parameter.

### HTTP Request

`GET http://localhost:8080/api/v1/search`

### Query Parameters

Parameter | Description
--------- | -----------
query | String included in the interested username o clan's name


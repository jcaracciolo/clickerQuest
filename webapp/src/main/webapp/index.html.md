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
        "profile_image_url": "http://localhost:8080/resources/profile_images/10.jpg",
        "score": 86035162.84607725,
        "clan_id": 20,
        "clan_url": "http://localhost:8080/clans/20",
        "factories_url": "http://localhost:8080/users/1/factories",
        "wealth_url": "http://localhost:8080/users/1/wealth"
    },
    {
        "type": "User",
        "id": 2,
        "username": "aaaa",
        "profile_image_url": "http://localhost:8080/resources/profile_images/2.jpg",
        "score": 1895.770193289029,
        "factories_url": "http://localhost:8080/users/2/factories",
        "wealth_url": "http://localhost:8080/users/2/wealth"
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
    "profile_image_url": "http://localhost:8080/api/v1/resources/profile_images/10.jpg",
    "score": 86035162.84607725,
    "clan_id": 20,
    "clan_url": "http://localhost:8080/api/v1/clans/20",
    "factories_url": "http://localhost:8080/api/v1/users/1/factories",
    "wealth_url": "http://localhost:8080/api/v1/users/1/wealth"
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
    "storage": {
        "CIRCUITS": 17975792,
        "CARDBOARD": 189392499810,
        "COPPER_CABLE": 11891047,
        "COPPER": 469644832,
        "METAL_SCRAP": 56935654327,
        "RUBBER": 190144481,
        "TIRES": 19174410647,
        "IRON": 1004331781,
        "PEOPLE": 213836241177,
        "MONEY": 109066712070722,
        "GOLD": 0,
        "PLASTIC": 123,
        "POWER": 968678128
    },
    "productions": {
        "CIRCUITS": 2,
        "CARDBOARD": 14326.523556775875,
        "COPPER_CABLE": 1.4000000000000004,
        "COPPER": 28.800000000000004,
        "METAL_SCRAP": 4451.802047317245,
        "RUBBER": 3.200000000000003,
        "TIRES": 1499.0099999999998,
        "IRON": 79.2,
        "PEOPLE": 16818.48,
        "MONEY": 8580025.549999999,
        "GOLD": 0,
        "PLASTIC": 0,
        "POWER": 96.35000000000002
    }
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
            "type_id": 1,
            "type": "PEOPLE_RECRUITER",
            "amount": 53392,
            "input_reduction": 1,
            "output_multiplier": 1.05,
            "cost_reduction": 1,
            "level": 1,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/1/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/1/buyLimits"
        },
        {
            "type_id": 6,
            "type": "BOILER",
            "amount": 187,
            "input_reduction": 0.9323938199059483,
            "output_multiplier": 1.05,
            "cost_reduction": 1,
            "level": 2,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/6/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/6/buyLimits"
        },
        {
            "type_id": 5,
            "type": "CABLE_MAKER",
            "amount": 8,
            "input_reduction": 1,
            "output_multiplier": 1,
            "cost_reduction": 1,
            "level": 0,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/5/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/5/buyLimits"
        },
        {
            "type_id": 7,
            "type": "CIRCUIT_MAKER",
            "amount": 1,
            "input_reduction": 1,
            "output_multiplier": 1,
            "cost_reduction": 1,
            "level": 0,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/7/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/7/buyLimits"
        },
        {
            "type_id": 0,
            "type": "STOCK_INVESTOR",
            "amount": 5917259,
            "input_reduction": 1,
            "output_multiplier": 1.45,
            "cost_reduction": 0.5712090638488149,
            "level": 17,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/0/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/0/buyLimits"
        },
        {
            "type_id": 2,
            "type": "JUNK_COLLECTOR",
            "amount": 6987,
            "input_reduction": 1,
            "output_multiplier": 1.15,
            "cost_reduction": 0.8693582353988059,
            "level": 5,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/2/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/2/buyLimits"
        },
        {
            "type_id": 3,
            "type": "METAL_SEPARATOR",
            "amount": 132,
            "input_reduction": 0.9323938199059483,
            "output_multiplier": 1,
            "cost_reduction": 1,
            "level": 1,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/3/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/3/buyLimits"
        },
        {
            "type_id": 4,
            "type": "RUBBER_SHREDDER",
            "amount": 54,
            "input_reduction": 1,
            "output_multiplier": 1,
            "cost_reduction": 1,
            "level": 0,
            "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/4/upgrade",
            "buyLimits_url": "http://localhost:8080/api/v1/factories/4/buyLimits"
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
    "type_id": 0,
    "type": "STOCK_INVESTOR",
    "amount": 5917259,
    "input_reduction": 1,
    "output_multiplier": 1.45,
    "cost_reduction": 0.5712090638488149,
    "level": 17,
    "upgradeURL": "http://localhost:8080/api/v1/users/1/factories/0/upgrade",
    "buyLimits_url": "http://localhost:8080/api/v1/factories/0/buyLimits"
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
            "battle_url": "http://localhost:8080/api/v1/clans/20/battle"
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
            "battle_url": "http://localhost:8080/api/v1/clans/13/battle"
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
    "battle_url": "http://localhost:8080/api/v1/clans/20/battle"
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
    "users": [
        {
            "id": 1,
            "username": "Wololo",
            "profile_image_url": "http://localhost:8080/api/v1/resources/profile_images/10.jpg",
            "score": 86035162.84607725,
            "clan_id": 20,
            "clan_url": "http://localhost:8080/api/v1/clans/20",
            "factories_url": "http://localhost:8080/api/v1/users/1/factories",
            "wealth_url": "http://localhost:8080/api/v1/users/1/wealth"
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

`POST http://localhost:8080/api/v1/clans/<ID>/join`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the clan to join.

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

### POST Parameters

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

## Get Your buying limits

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
    "factory_id": 6,
    "max": 205,
    "cost_max": {
        "COPPER_CABLE": 11890000,
        "IRON": 29725000,
        "PEOPLE": 1189000
    },
    "cost_1": {
        "COPPER_CABLE": 37600,
        "IRON": 94000,
        "PEOPLE": 3760
    },
    "cost_10": {
        "COPPER_CABLE": 385000,
        "IRON": 962500,
        "PEOPLE": 38500
    },
    "cost_100": {
        "COPPER_CABLE": 4750000,
        "IRON": 11875000,
        "PEOPLE": 475000
    }
}
```

This endpoint retrieves the maximun amount of factories of type <factoryID> which the user can buy with the current resources, and whether or no (and the cost) of buying 1, 10 and 100 of the given factory.
### HTTP Request

`GET http://localhost:8080/api/v1/factories/<factoryID>/buyLimits`

### Query Parameters

Parameter | Description
--------- | -----------
query | String included in the interested username o clan's name


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


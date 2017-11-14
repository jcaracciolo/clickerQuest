---
title: API Reference

language_tabs: # must be one of https://git.io/vQNgJ
  - javascript
  - shell
  - ruby
  - python


toc_footers:
  - <a href='#'>Sign Up for a Developer Key</a>
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
itemsPerPage | 20 | Number of users per page.

## Get a Specific Kitten

```ruby
require 'kittn'

api = Kittn::APIClient.authorize!('meowmeowmeow')
api.kittens.get(2)
```

```python
import kittn

api = kittn.authorize('meowmeowmeow')
api.kittens.get(2)
```

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
  "id": 2,
  "name": "Max",
  "breed": "unknown",
  "fluffiness": 5,
  "cuteness": 10
}
```

This endpoint retrieves a specific kitten.

<aside class="warning">Inside HTML code blocks like this one, you can't use Markdown, so use <code>&lt;code&gt;</code> blocks to denote code.</aside>

### HTTP Request

`GET http://example.com/kittens/<ID>`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the kitten to retrieve

## Delete a Specific Kitten

```ruby
require 'kittn'

api = Kittn::APIClient.authorize!('meowmeowmeow')
api.kittens.delete(2)
```

```python
import kittn

api = kittn.authorize('meowmeowmeow')
api.kittens.delete(2)
```

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
  "id": 2,
  "deleted" : ":("
}
```

This endpoint deletes a specific kitten.

### HTTP Request

`DELETE http://example.com/kittens/<ID>`

### URL Parameters

Parameter | Description
--------- | -----------
ID | The ID of the kitten to delete


# Java Request Catcher

[![Build Status](https://travis-ci.org/tpolishchuk/jrcatcher.svg?branch=master)](https://travis-ci.org/tpolishchuk/jrcatcher)

Java Request Catcher (JRC) is tiny Java-based project to help you with webhooks testing.

JRC has simple UI to organize your endpoints and log caught requests.

## Key features
* Can be deployed to any environment
* Supports connection to a custom database to log your requests
* You can set up as much endpoints for data listening as you wish
* You can clean request logs for selected endpoint and then reuse it for the next testing round
* You can filter request logs data by content in case if you need to analyze it deeply
* You can clean all data in one click

## Default setup

To perform default setup clone this project and run the following command in the root directory:

```
docker-compose up
```

In result you will obtain two new containers in your Docker infrastructure: jrc_app and jrc_mysql.
To access JRC web interface use [http://localhost:7070/](http://localhost:7070/) by default.

If you want to amend project settings, feel free to change .env file and then restart the environment.

## Custom database setup

If you do not want to use jrc_mysql container, specify your MySQL settings in .env.custom.db file and run `run-on-custom-db.sh` script in the root directory. 

In result jrc_app will store requests in your database and all data will be accessible on web interface.

[**Details and workflows**: to be updated in [JRC Project Wiki](https://github.com/tpolishchuk/jrcatcher/wiki) soon]


#!/bin/bash

ENV_FILE=.env.custom.db

print_help() {
    echo "Usage: run-on-custom-db.sh [--help | -h][--env-file-path | -e <file path>][--create-database | -d][--create-user | -u]

    Options:
  --help, -h                        Print help
  --env-file-path, -e <file path>   Use custom environment file, .env.custom.db is a default value
  --create-database, -d             Drop if exists and then create a MySQL database required in environment file
  --create-user, -u                 Drop if exists and then create a MySQL user required in environment file

  Example of usage:

    run-on-custom-db.sh -u -d

  will create a MySQL database and a user and then run Java Request Catcher. All data will be recorded to a recently created database
  "
}

create_database() {
    echo Creating '${MYSQL_DATABASE}' database
    mysql -uroot -p${MYSQL_ROOT_PASSWORD} --host=${MYSQL_HOST} << EOF
    DROP DATABASE IF EXISTS ${MYSQL_DATABASE};
    CREATE DATABASE ${MYSQL_DATABASE};
EOF
}

seed_database() {
    echo Seeding '${MYSQL_DATABASE}' database
    mysql -u${MYSQL_USER} -p${MYSQL_PASSWORD} --host=${MYSQL_HOST} --database=${MYSQL_DATABASE} < ./src/main/resources/sql/db_init.sql
}

create_user() {
    echo Creating user '${MYSQL_USER}' for database '${MYSQL_DATABASE}'
    mysql -uroot -p${MYSQL_ROOT_PASSWORD} --host=${MYSQL_HOST} << EOF
    DROP USER IF EXISTS '${MYSQL_USER}'@'localhost';
    CREATE USER '${MYSQL_USER}'@'localhost' identified by '${MYSQL_PASSWORD}';
    GRANT ALL on ${MYSQL_DATABASE}.* to '${MYSQL_USER}'@'localhost';
    FLUSH PRIVILEGES;
EOF
}

set_env_file_path() {
    ENV_FILE=$1

    if [[ -z ${ENV_FILE} ]]
    then
          echo "Custom environment file path is empty!"
          exit 1
    fi

    if [[ ! -f ${ENV_FILE} ]]; then
        echo "File ${ENV_FILE} is not found!"
        exit 1
    fi

    echo Set $ENV_FILE as an environment file for processing
}

parse_env_file() {
    while IFS='=' read -r key value; do
        key=$(echo $key | tr .-/ _ | tr -cd 'A-Za-z0-9_')

        if [[ ${#key} -gt 0 ]]; then
            eval ${key}=\${value}
        fi
    done < "$ENV_FILE"

    replace_mysql_localhost_path
}

replace_mysql_localhost_path() {
    if [[ ${MYSQL_HOST} == "host.docker.internal" ]]; then
        MYSQL_HOST=localhost;
    fi
}

parse_env_file

for opt in $@
do
	case $opt in
		--help | -h)
			print_help
			exit
			;;
		--env-file-path | -e)
			set_env_file_path $2
			parse_env_file
			;;
		--create-database | -d)
			create_database
			seed_database
			;;
		--create-user | -u)
			create_user
			;;
	esac
	shift
done

docker-compose -f docker-compose-custom-db.yml up

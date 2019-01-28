CREATE TABLE IF NOT EXISTS `endpoints` (
	`id` int NOT NULL AUTO_INCREMENT,
	`path` char(255) NOT NULL UNIQUE,
	`active` bool NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `requests` (
	`id` int NOT NULL AUTO_INCREMENT,
	`endpoint_id` int NOT NULL,
	`url` varchar(255) NOT NULL,
	`body` text NOT NULL,
	`request_method` varchar(255) NOT NULL,
	`received_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_requests_endpointid_idx` (`endpoint_id`),
	CONSTRAINT `requests_fk0` FOREIGN KEY (`endpoint_id`) REFERENCES `endpoints`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `request_cookies` (
	`id` int NOT NULL AUTO_INCREMENT,
	`request_id` int NOT NULL,
	`name` varchar(255) NOT NULL,
	`value` text,
	`domain` varchar(255),
	`path` varchar(255),
	`max_age` int NOT NULL,
	`http_only` bool NOT NULL,
	`secure` bool NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_requestcookies_requestid_idx` (`request_id`),
	CONSTRAINT `request_cookies_fk0` FOREIGN KEY (`request_id`) REFERENCES `requests`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `request_headers` (
	`id` int NOT NULL AUTO_INCREMENT,
	`request_id` int NOT NULL,
	`name` varchar(255) NOT NULL,
	`value` varchar(255),
	PRIMARY KEY (`id`),
	KEY `fk_requestheaders_requestid_idx` (`request_id`),
	CONSTRAINT `request_headers_fk0` FOREIGN KEY (`request_id`) REFERENCES `requests`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

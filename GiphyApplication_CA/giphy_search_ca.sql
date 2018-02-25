create database giphy_data;

use giphy_data;

create table favorite_giphy
(
id int(64) AUTO_INCREMENT NOT NULL,
userid varchar(256) NOT NULL,
url  varchar(256) NOT NULL,
title  varchar(256) DEFAULT NULL,
PRIMARY KEY (`id`)
);

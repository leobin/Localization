# ---------------------------------------------------------------------- #
# Script generated with: DeZign for Databases v6.2.1                     #
# Target DBMS:           MySQL 5                                         #
# Project file:          database_v0.5.dez                               #
# Project name:                                                          #
# Author:                                                                #
# Script type:           Database creation script                        #
# Created on:            2010-12-29 20:41                                #
# ---------------------------------------------------------------------- #


# ---------------------------------------------------------------------- #
# Tables                                                                 #
# ---------------------------------------------------------------------- #

# ---------------------------------------------------------------------- #
# Add table "location"                                                   #
# ---------------------------------------------------------------------- #

CREATE TABLE `location` (
    `location_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT ,
    `algorithm_id` BIGINT UNSIGNED, 
    `location_name` VARCHAR(50),
    `location_description` TEXT,
    `parent_location_id` BIGINT DEFAULT 0,
    `google_location_id` VARCHAR(256),
    `content_zoom` VARCHAR(40),
    CONSTRAINT `PK_location` PRIMARY KEY (`location_id`)
);

# ---------------------------------------------------------------------- #
# Add table "mac_address_location"                                       #
# ---------------------------------------------------------------------- #

CREATE TABLE `mac_address_location` (
    `mac_address_id` BIGINT NOT NULL,
    `location_id` BIGINT NOT NULL,
    `algorithm_id` BIGINT UNSIGNED ,
    CONSTRAINT `PK_mac_address_location` PRIMARY KEY (`mac_address_id`, `location_id`, `algorithm_id`)
);

# ---------------------------------------------------------------------- #
# Add table "point"                                                      #
# ---------------------------------------------------------------------- #

CREATE TABLE `point` (
    `point_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `coordinate_x` INTEGER,
    `coordinate_y` INTEGER,
    `location_id` BIGINT UNSIGNED NOT NULL,
    CONSTRAINT `PK_point` PRIMARY KEY (`point_id`)
);

# ---------------------------------------------------------------------- #
# Add table "mac_address"                                                #
# ---------------------------------------------------------------------- #

CREATE TABLE `mac_address` (
    `mac_address_id` BIGINT NOT NULL,
    CONSTRAINT `PK_mac_address` PRIMARY KEY (`mac_address_id`)
);

# ---------------------------------------------------------------------- #
# Add table "user"                                                       #
# ---------------------------------------------------------------------- #

CREATE TABLE `user` (
    `user_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_type` ENUM('client', 'mapbuilder', 'algorithm creator', 'admin') NOT NULL,
    `user_name` VARCHAR(100),
    `password` VARCHAR(100),
    CONSTRAINT `PK_user` PRIMARY KEY (`user_id`)
);

# ---------------------------------------------------------------------- #
# Add table "data_type"                                                  #
# ---------------------------------------------------------------------- #

CREATE TABLE `data_type` (
    `data_type_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `data_type_name` VARCHAR(100),
    `data_type_description` VARCHAR(100),
    `data_type_class_name` VARCHAR(100),
    CONSTRAINT `PK_data_type` PRIMARY KEY (`data_type_id`)
);

# ---------------------------------------------------------------------- #
# Add table "algorithm"                                                  #
# ---------------------------------------------------------------------- #

CREATE TABLE `algorithm` (
    `algorithm_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `algorithm_description` VARCHAR(100),
    `algorithm_name` VARCHAR(40),
    `algorithm_class_name` VARCHAR(100),
    `data_type_id` BIGINT UNSIGNED NOT NULL,
    CONSTRAINT `PK_algorithm` PRIMARY KEY (`algorithm_id`)
);

# ---------------------------------------------------------------------- #
# Foreign key constraints                                                #
# ---------------------------------------------------------------------- #

ALTER TABLE `location` ADD CONSTRAINT `user_location` 
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

ALTER TABLE `location` ADD CONSTRAINT `algorithm_location` 
    FOREIGN KEY (`algorithm_id`) REFERENCES `algorithm` (`algorithm_id`);

ALTER TABLE `mac_address_location` ADD CONSTRAINT `location_mac_address_location` 
    FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `mac_address_location` ADD CONSTRAINT `mac_address_mac_address_location` 
    FOREIGN KEY (`mac_address_id`) REFERENCES `mac_address` (`mac_address_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `point` ADD CONSTRAINT `location_point` 
    FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`);

ALTER TABLE `algorithm` ADD CONSTRAINT `data_type_algorithm` 
    FOREIGN KEY (`data_type_id`) REFERENCES `data_type` (`data_type_id`);

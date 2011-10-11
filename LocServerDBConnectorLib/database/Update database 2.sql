CREATE TABLE IF NOT EXISTS `location_mac_address_mapping` (
  `client_mac_address_id` bigint(20) NOT NULL,
  `build_mac_address_id` bigint(20) NOT NULL,
  `a` double NOT NULL,
  `b` double NOT NULL,
  PRIMARY KEY (`client_mac_address_id`,`build_mac_address_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE  `location` ADD  `build_mac_address_id` INT NOT NULL
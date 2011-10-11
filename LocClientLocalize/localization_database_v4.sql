-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 21, 2011 at 02:14 AM
-- Server version: 5.1.53
-- PHP Version: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `localization_database_v4`
--

-- --------------------------------------------------------

--
-- Table structure for table `algorithm`
--

CREATE TABLE IF NOT EXISTS `algorithm` (
  `algorithm_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `algorithm_description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `algorithm_name` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `algorithm_class_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `data_type_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`algorithm_id`),
  KEY `data_type_algorithm` (`data_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `algorithm`
--


-- --------------------------------------------------------

--
-- Table structure for table `data_type`
--

CREATE TABLE IF NOT EXISTS `data_type` (
  `data_type_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_type_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `data_type_description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `data_type_class_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`data_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Dumping data for table `data_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `algorithm_id` bigint(20) unsigned DEFAULT NULL,
  `location_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location_description` text COLLATE utf8_unicode_ci,
  `parent_location_id` bigint(20) DEFAULT '0',
  `google_location_id` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content_zoom` varchar(40) COLLATE utf8_unicode_ci DEFAULT '1',
  `location_data_path` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`location_id`),
  KEY `user_location` (`user_id`),
  KEY `algorithm_location` (`algorithm_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=14 ;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`location_id`, `user_id`, `algorithm_id`, `location_name`, `location_description`, `parent_location_id`, `google_location_id`, `content_zoom`, `location_data_path`) VALUES
(3, 2, NULL, 'Location 1', 'this is just a test location', NULL, NULL, '1', NULL),
(4, 2, NULL, 'Sub Location 1', 'This is a sub location 1', NULL, NULL, '1', NULL),
(5, 2, NULL, 'SubLocation2', 'This Is Sub location 2', NULL, NULL, '1', NULL),
(13, 2, NULL, 'test location', 'test location', 3, NULL, '1', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `mac_address`
--

CREATE TABLE IF NOT EXISTS `mac_address` (
  `mac_address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`mac_address_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `mac_address`
--


-- --------------------------------------------------------

--
-- Table structure for table `mac_address_location`
--

CREATE TABLE IF NOT EXISTS `mac_address_location` (
  `mac_address_id` bigint(20) NOT NULL,
  `location_id` bigint(20) NOT NULL,
  `algorithm_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`mac_address_id`,`location_id`,`algorithm_id`),
  KEY `location_mac_address_location` (`location_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `mac_address_location`
--


-- --------------------------------------------------------

--
-- Table structure for table `point`
--

CREATE TABLE IF NOT EXISTS `point` (
  `point_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `coordinate_x` int(11) DEFAULT NULL,
  `coordinate_y` int(11) DEFAULT NULL,
  `location_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`point_id`),
  KEY `location_point` (`location_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=57 ;

--
-- Dumping data for table `point`
--

INSERT INTO `point` (`point_id`, `coordinate_x`, `coordinate_y`, `location_id`) VALUES
(1, 1000, 0, 1),
(2, 1000, 1000, 1),
(3, 0, 1000, 1),
(4, 0, 0, 1),
(5, 0, 100, 2),
(6, 0, 0, 2),
(7, 100, 100, 2),
(8, 100, 0, 2),
(9, 0, 200, 3),
(10, 200, 200, 3),
(11, 200, 0, 3),
(12, 0, 0, 3),
(13, 34, 42, 4),
(14, 138, 95, 4),
(15, 66, 108, 4),
(16, 137, 44, 4),
(17, 137, 44, 4),
(18, 138, 95, 4),
(19, 66, 108, 4),
(20, 34, 42, 4),
(21, 18, 42, 5),
(22, 74, 34, 5),
(23, 58, 124, 5),
(24, 124, 105, 5),
(25, 58, 124, 5),
(26, 124, 105, 5),
(27, 74, 34, 5),
(28, 18, 42, 5),
(29, 123, 113, 6),
(30, 33, 16, 6),
(31, 109, 37, 6),
(32, 46, 140, 6),
(40, 117, 127, 11),
(39, 43, 121, 11),
(38, 138, 44, 11),
(37, 56, 35, 11),
(41, 138, 44, 11),
(42, 117, 127, 11),
(43, 43, 121, 11),
(44, 56, 35, 11),
(45, 40, 52, 12),
(46, 57, 152, 12),
(47, 158, 120, 12),
(48, 119, 14, 12),
(49, 40, 52, 12),
(50, 57, 152, 12),
(51, 158, 120, 12),
(52, 119, 14, 12),
(53, 48, 49, 13),
(54, 57, 103, 13),
(55, 109, 129, 13),
(56, 157, 60, 13);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_type` enum('client','mapbuilder','algorithm creator','admin') COLLATE utf8_unicode_ci NOT NULL,
  `user_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_type`, `user_name`, `password`) VALUES
(2, 'admin', 'admin', 'demo124');

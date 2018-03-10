-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.22-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table hni.activation_codes
DROP TABLE IF EXISTS `activation_codes`;
CREATE TABLE IF NOT EXISTS `activation_codes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activation_code` varchar(128) NOT NULL,
  `organization_id` int(11) NOT NULL,
  `meals_authorized` int(11) DEFAULT NULL,
  `meals_remaining` int(11) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL COMMENT 'true/false whether this voucher can be used',
  `comments` varchar(255) DEFAULT NULL,
  `created` varchar(45) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT 'the user who “owns” this voucher',
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.addresses
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zip` varchar(15) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `timezone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.board_members
DROP TABLE IF EXISTS `board_members`;
CREATE TABLE IF NOT EXISTS `board_members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.brand_partners
DROP TABLE IF EXISTS `brand_partners`;
CREATE TABLE IF NOT EXISTS `brand_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `company` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.client
DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL DEFAULT '0',
  `race` int(11) NOT NULL DEFAULT '0',
  `ethinicity` int(11) DEFAULT '0',
  `bday` date DEFAULT NULL,
  `been_arrested` tinyint(4) DEFAULT '0',
  `ngo_id` int(11) DEFAULT '0',
  `been_convicted` tinyint(4) DEFAULT '0',
  `has_smart_phone` tinyint(4) DEFAULT '0',
  `service_provider` varchar(50) DEFAULT '0',
  `model` varchar(50) DEFAULT '0',
  `have_monthly_plan` tinyint(4) DEFAULT '0',
  `monthly_plan_minute` varchar(50) DEFAULT '0',
  `monthly_plan_data` varchar(50) DEFAULT '0',
  `monthly_plan_cost` varchar(50) DEFAULT '0',
  `alt_monthly_plan` int(11) DEFAULT '0',
  `alt_monthly_plan_together` varchar(50) DEFAULT '0',
  `sliblings` int(11) DEFAULT '0',
  `kids` int(11) DEFAULT '0',
  `live_at_home` tinyint(4) DEFAULT '0',
  `sheltered` int(11) DEFAULT '0',
  `live_with` int(4) DEFAULT '0',
  `parent_education` int(11) DEFAULT '0',
  `education` int(11) DEFAULT '0',
  `enrollment_status` int(11) DEFAULT '0',
  `enrollment_location` varchar(50) DEFAULT '0',
  `work_status` int(11) DEFAULT '0',
  `time_to_workplace` int(11) DEFAULT '0',
  `no_of_job` int(11) DEFAULT '0',
  `employer` varchar(50) DEFAULT '0',
  `job_title` varchar(50) DEFAULT '0',
  `duration_of_employement` int(11) DEFAULT '0',
  `unemployment_benfits` tinyint(4) DEFAULT '0',
  `reason_unemployment_benefits` varchar(100) DEFAULT '0',
  `total_income` double DEFAULT '0',
  `rate_amount` int(11) DEFAULT '0',
  `rate_type` int(11) DEFAULT '0',
  `avg_hours_per_week` varchar(255) DEFAULT '0',
  `resident_status` int(11) DEFAULT '0',
  `dollar_spend_food` int(11) DEFAULT '0',
  `dollar_spend_clothes` int(11) DEFAULT '0',
  `dollar_spend_entertainment` int(11) DEFAULT '0',
  `dollar_spend_transport` int(11) DEFAULT '0',
  `dollar_spend_savings` int(11) DEFAULT '0',
  `meals_per_day` int(11) DEFAULT '0',
  `food_preference` varchar(200) DEFAULT '0',
  `food_source` varchar(50) DEFAULT '0',
  `cook` tinyint(4) DEFAULT '0',
  `travel_for_food_distance` int(11) DEFAULT '0',
  `traval_for_food_time` int(11) DEFAULT '0',
  `sub_food_program` tinyint(4) DEFAULT '0',
  `sub_food_program_entity` varchar(50) DEFAULT '0',
  `sub_food_program_duration` int(11) DEFAULT '0',
  `sub_food_program_renew` int(11) DEFAULT '0',
  `sub_food_program_exp` varchar(256) DEFAULT '0',
  `allergies` varchar(256) DEFAULT '0',
  `addiction` tinyint(4) DEFAULT '0',
  `addiction_type` varchar(50) DEFAULT '0',
  `mental_health_issue` varchar(250) DEFAULT '0',
  `mental_health_issue_history` varchar(256) DEFAULT '0',
  `height` varchar(50) DEFAULT '0',
  `weight` varchar(50) DEFAULT '0',
  `exercise_per_week` int(11) DEFAULT '0',
  `last_visit_doctor` int(11) DEFAULT '0',
  `last_visit_dentist` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.dependents
DROP TABLE IF EXISTS `dependents`;
CREATE TABLE IF NOT EXISTS `dependents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `age` int(11) DEFAULT '0',
  `client_id` int(11) DEFAULT '0',
  `relation` varchar(255) DEFAULT NULL,
  `gender` varchar(5) DEFAULT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '0',
  `eligible_for_meal` tinyint(4) DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.education
DROP TABLE IF EXISTS `education`;
CREATE TABLE IF NOT EXISTS `education` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `education_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.endrosement
DROP TABLE IF EXISTS `endrosement`;
CREATE TABLE IF NOT EXISTS `endrosement` (
  `id` int(11) NOT NULL,
  `ngo_id` int(11) NOT NULL,
  `endrosement` varchar(100),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.event_state
DROP TABLE IF EXISTS `event_state`;
CREATE TABLE IF NOT EXISTS `event_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eventname` varchar(255) NOT NULL,
  `phoneno` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phoneno` (`phoneno`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.food_bank
DROP TABLE IF EXISTS `food_bank`;
CREATE TABLE IF NOT EXISTS `food_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `food_bank_name` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.food_services
DROP TABLE IF EXISTS `food_services`;
CREATE TABLE IF NOT EXISTS `food_services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `service_type` int(11) NOT NULL,
  `weekdays` varchar(150) NOT NULL,
  `total_count` int(11) NOT NULL,
  `other` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.hni_services
DROP TABLE IF EXISTS `hni_services`;
CREATE TABLE IF NOT EXISTS `hni_services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `service_name` varchar(75) NOT NULL,
  `service_path` varchar(100) NOT NULL,
  `service_img` varchar(500) DEFAULT NULL,
  `active` varchar(1) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.hni_templates
DROP TABLE IF EXISTS `hni_templates`;
CREATE TABLE IF NOT EXISTS `hni_templates` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `created_by` int(5) NOT NULL DEFAULT '1',
  `template` varchar(1000) NOT NULL,
  `type` varchar(25) NOT NULL,
  `last_modified` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.income
DROP TABLE IF EXISTS `income`;
CREATE TABLE IF NOT EXISTS `income` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `income_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.invitation
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE IF NOT EXISTS `invitation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL,
  `invite_code` varchar(50) NOT NULL,
  `activated` int(2) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL,
  `invited_by` int(11) NOT NULL,
  `ngo_id` int(11) DEFAULT NULL,
  `token_expire_date` date NOT NULL,
  `phone` varchar(17) DEFAULT NULL,
  `dependants_count` int(2) DEFAULT '0',
  `message` varchar(500) DEFAULT NULL,
  `name` varchar(250) DEFAULT NULL,
  `invitation_type` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `created_date` date NOT NULL,
  `data` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.local_partners
DROP TABLE IF EXISTS `local_partners`;
CREATE TABLE IF NOT EXISTS `local_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `company` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.marital_status
DROP TABLE IF EXISTS `marital_status`;
CREATE TABLE IF NOT EXISTS `marital_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marital_status_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.meal_donation_sources
DROP TABLE IF EXISTS `meal_donation_sources`;
CREATE TABLE IF NOT EXISTS `meal_donation_sources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `source` varchar(255) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `frequency` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.meal_funding_sources
DROP TABLE IF EXISTS `meal_funding_sources`;
CREATE TABLE IF NOT EXISTS `meal_funding_sources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `source` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.menus
DROP TABLE IF EXISTS `menus`;
CREATE TABLE IF NOT EXISTS `menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `provider_id` int(11) NOT NULL,
  `start_hour` int(11) DEFAULT NULL COMMENT 'starting hour item is available in 24hr',
  `end_hour` int(11) DEFAULT NULL COMMENT 'ending hour item is available in 24hr',
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.menu_items
DROP TABLE IF EXISTS `menu_items`;
CREATE TABLE IF NOT EXISTS `menu_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `price` decimal(10,2) DEFAULT NULL,
  `expires` datetime DEFAULT NULL,
  `calories` int(11) DEFAULT NULL,
  `protien` int(11) DEFAULT NULL,
  `fat` int(11) DEFAULT NULL,
  `carbs` int(11) DEFAULT NULL,
  `active` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.ngo
DROP TABLE IF EXISTS `ngo`;
CREATE TABLE IF NOT EXISTS `ngo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `website` varchar(255) NOT NULL,
  `contact_name` varchar(255) NOT NULL,
  `fte` int(11) NOT NULL,
  `overview` varchar(2048) NOT NULL,
  `mission` varchar(2048) NOT NULL,
  `monthly_budget` int(11) NOT NULL,
  `operating_cost` int(11) DEFAULT NULL,
  `personal_cost` int(11) DEFAULT NULL,
  `kitchen_volunteers` int(11) DEFAULT NULL,
  `food_stamp_assist` tinyint(1) NOT NULL,
  `food_bank` tinyint(1) NOT NULL,
  `resources_to_clients` varchar(200) DEFAULT NULL,
  `ind_serv_daily` int(11) NOT NULL,
  `ind_serv_monthly` int(11) NOT NULL,
  `ind_serv_annual` int(11) NOT NULL,
  `client_info` tinyint(1) NOT NULL,
  `store_client_info` varchar(255) DEFAULT NULL,
  `clients_unsheltered` int(11) NOT NULL,
  `clients_employed` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.ngo_funding_sources
DROP TABLE IF EXISTS `ngo_funding_sources`;
CREATE TABLE IF NOT EXISTS `ngo_funding_sources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `source` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.orders
DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `provider_location_id` int(11) NOT NULL,
  `order_date` datetime NOT NULL,
  `ready_date` datetime DEFAULT NULL,
  `pickup_date` datetime DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `tax` decimal(10,2) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL COMMENT 'surrogate to users',
  `status_id` int(11) NOT NULL,
  `completed_by` int(11) DEFAULT NULL,
  `confirmation_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.order_items
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `menu_item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.order_payments
DROP TABLE IF EXISTS `order_payments`;
CREATE TABLE IF NOT EXISTS `order_payments` (
  `order_id` int(11) NOT NULL,
  `payment_instrument_id` int(11) NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `status` varchar(5) NOT NULL,
  PRIMARY KEY (`order_id`,`payment_instrument_id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.organizations
DROP TABLE IF EXISTS `organizations`;
CREATE TABLE IF NOT EXISTS `organizations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.organization_addresses
DROP TABLE IF EXISTS `organization_addresses`;
CREATE TABLE IF NOT EXISTS `organization_addresses` (
  `organization_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`organization_id`,`address_id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.partial_orders
DROP TABLE IF EXISTS `partial_orders`;
CREATE TABLE IF NOT EXISTS `partial_orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `provider_location_id` int(11) DEFAULT NULL,
  `menu_item_id` int(11) DEFAULT NULL,
  `order_item_id` int(11) DEFAULT NULL,
  `chosen_provider_id` int(11) DEFAULT NULL,
  `transaction_phase` varchar(45) DEFAULT NULL,
  `address` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.partial_orders_menu_items
DROP TABLE IF EXISTS `partial_orders_menu_items`;
CREATE TABLE IF NOT EXISTS `partial_orders_menu_items` (
  `id` int(11) NOT NULL,
  `menu_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.partial_orders_menu_selections
DROP TABLE IF EXISTS `partial_orders_menu_selections`;
CREATE TABLE IF NOT EXISTS `partial_orders_menu_selections` (
  `id` int(11) NOT NULL,
  `menu_item_id` int(11) NOT NULL
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.partial_orders_order_items
DROP TABLE IF EXISTS `partial_orders_order_items`;
CREATE TABLE IF NOT EXISTS `partial_orders_order_items` (
  `id` int(11) NOT NULL,
  `order_item_id` int(11) NOT NULL
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.partial_orders_provider_locations
DROP TABLE IF EXISTS `partial_orders_provider_locations`;
CREATE TABLE IF NOT EXISTS `partial_orders_provider_locations` (
  `id` int(11) NOT NULL,
  `provider_location_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.participant_dependent_relation
DROP TABLE IF EXISTS `participant_dependent_relation`;
CREATE TABLE IF NOT EXISTS `participant_dependent_relation` (
  `client_id` int(11) DEFAULT NULL,
  `dependent_id` int(11) DEFAULT NULL) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.payment_instruments
DROP TABLE IF EXISTS `payment_instruments`;
CREATE TABLE IF NOT EXISTS `payment_instruments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) DEFAULT NULL,
  `card_type` varchar(45) DEFAULT NULL,
  `card_serial_id` varchar(255) DEFAULT NULL,
  `card_number` varchar(45) DEFAULT NULL COMMENT 'hashed value',
  `status` varchar(45) DEFAULT NULL COMMENT 'activated or not',
  `orginal_balance` decimal(10,2) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `last_used_datetime` datetime DEFAULT NULL,
  `state_code` varchar(50) DEFAULT NULL,
  `allow_topup` tinyint(4) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `pin_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.providers
DROP TABLE IF EXISTS `providers`;
CREATE TABLE IF NOT EXISTS `providers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `menu_id` int(11) NOT NULL,
  `website_url` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.provider_locations
DROP TABLE IF EXISTS `provider_locations`;
CREATE TABLE IF NOT EXISTS `provider_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `provider_id` int(11) NOT NULL,
  `address_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `is_active` tinyint(4) DEFAULT NULL,
  `last_updated_by` int(11) DEFAULT NULL,
  `last_updated` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.provider_location_hours
DROP TABLE IF EXISTS `provider_location_hours`;
CREATE TABLE IF NOT EXISTS `provider_location_hours` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_location_id` int(11) NOT NULL,
  `dow` varchar(3) DEFAULT NULL,
  `open_hour` int(11) DEFAULT NULL COMMENT 'open hour in 24hr',
  `close_hour` int(11) DEFAULT NULL COMMENT 'close hour in 24hr',
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.race
DROP TABLE IF EXISTS `race`;
CREATE TABLE IF NOT EXISTS `race` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `race_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.registration_state
DROP TABLE IF EXISTS `registration_state`;
CREATE TABLE IF NOT EXISTS `registration_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eventname` varchar(255) NOT NULL,
  `phoneno` varchar(45) NOT NULL,
  `payload` varchar(255) DEFAULT NULL,
  `eventstate` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.reports
DROP TABLE IF EXISTS `reports`;
CREATE TABLE IF NOT EXISTS `reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_path` varchar(50) NOT NULL,
  `label` varchar(50) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.security_permissions
DROP TABLE IF EXISTS `security_permissions`;
CREATE TABLE IF NOT EXISTS `security_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.security_roles
DROP TABLE IF EXISTS `security_roles`;
CREATE TABLE IF NOT EXISTS `security_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.security_role_permissions
DROP TABLE IF EXISTS `security_role_permissions`;
CREATE TABLE IF NOT EXISTS `security_role_permissions` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `all_instances` int(11) DEFAULT '0',
  PRIMARY KEY (`role_id`,`permission_id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.sms_provider
DROP TABLE IF EXISTS `sms_provider`;
CREATE TABLE IF NOT EXISTS `sms_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider` varchar(100) NOT NULL,
  `long_code` varchar(20) DEFAULT NULL,
  `short_code` varchar(20) DEFAULT NULL,
  `state_code` varchar(2) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `created` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `gender_code` varchar(1) DEFAULT NULL,
  `mobile_phone` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `hashed_secret` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `active` tinyint(4) DEFAULT '0',
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_uniq_idx` (`email`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.user_address
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE IF NOT EXISTS `user_address` (
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`address_id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.user_organization_role
DROP TABLE IF EXISTS `user_organization_role`;
CREATE TABLE IF NOT EXISTS `user_organization_role` (
  `user_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`organization_id`,`role_id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.user_profile_tmp
DROP TABLE IF EXISTS `user_profile_tmp`;
CREATE TABLE IF NOT EXISTS `user_profile_tmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `data` blob NOT NULL,
  `created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.user_provider_role
DROP TABLE IF EXISTS `user_provider_role`;
CREATE TABLE IF NOT EXISTS `user_provider_role` (
  `user_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`provider_id`,`role_id`)) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.user_status
DROP TABLE IF EXISTS `user_status`;
CREATE TABLE IF NOT EXISTS `user_status` (
  `user_id` int(11) NOT NULL,
  `status` varchar(2) NOT NULL
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.volunteer
DROP TABLE IF EXISTS `volunteer`;
CREATE TABLE IF NOT EXISTS `volunteer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `created` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `sex` char(1) NOT NULL,
  `race` int(11) NOT NULL,
  `education` int(11) NOT NULL,
  `marital_status` int(11) NOT NULL,
  `income` int(11) NOT NULL,
  `kids` int(11) NOT NULL,
  `employer` varchar(100) NOT NULL,
  `non_profit` char(1) NOT NULL COMMENT 'Yes or No',
  `available_for_place_order` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
-- Dumping structure for table hni.volunteer_availability
DROP TABLE IF EXISTS `volunteer_availability`;
CREATE TABLE IF NOT EXISTS `volunteer_availability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `volunteer_id` int(11) NOT NULL,
  `timeline` int(11) NOT NULL COMMENT 'It should be a list of time rages, constants',
  `weekday` varchar(200) NOT NULL COMMENT 'sunday, monday, etc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

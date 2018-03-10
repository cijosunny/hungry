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

-- Dumping data for table hni.activation_codes: ~8 rows (approximately)
DELETE FROM `activation_codes`;
/*!40000 ALTER TABLE `activation_codes` DISABLE KEYS */;
INSERT INTO `activation_codes` (`id`, `activation_code`, `organization_id`, `meals_authorized`, `meals_remaining`, `enabled`, `comments`, `created`, `user_id`) VALUES
	(1, '1234567890', 2, 10, 10, 1, NULL, '2017-05-07 12:58:18', 9),
	(2, '7h-1234567890', 2, 10, 10, 1, 'freddy has activated this', '2017-05-07 12:58:18', 2),
	(3, '123456', 2, 10, 10, 1, NULL, '2017-05-07 12:58:18', NULL),
	(4, '987654', 2, 10, 10, 0, NULL, '2017-05-07 12:58:18', NULL),
	(21, '987655', 1, 180, 180, 1, NULL, '2017-06-15 18:22:38', 12),
	(22, '987656', 1, 180, 180, 1, NULL, '2017-06-15 18:22:38', 12),
	(23, '987657', 1, 180, 180, 1, NULL, '2017-06-15 18:22:38', 12),
	(24, '987658', 1, 180, 180, 1, NULL, '2017-06-15 18:22:38', 12);
/*!40000 ALTER TABLE `activation_codes` ENABLE KEYS */;

-- Dumping data for table hni.addresses: ~177 rows (approximately)
DELETE FROM `addresses`;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` (`id`, `name`, `address_line1`, `address_line2`, `city`, `state`, `zip`, `longitude`, `latitude`, `timezone`) VALUES
	(14, 'office', 'Test address', 'Test 2', 'Nova', 'FL', '123456', NULL, NULL, NULL),
	(16, 'office', 'Test Address', 'Test Address 3', 'Novato', 'PA', '124562', NULL, NULL, NULL),
	(17, 'office', 'Test Address for Rahul', NULL, 'Notea', 'MO', '12345', NULL, NULL, NULL),
	(20, 'subway corp addr', '1251 Phoenician way', '', 'columbus', 'oh', '43240', -82.98402279999999, 40.138686, 'etc'),
	(193, 'Subway', 'Subway 4963 Natural Bridge Ave', 'St Louis', 'St Louis', 'MO', '63115', -90.250207, 38.675969, 'etc');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;

-- Dumping data for table hni.board_members: ~1 rows (approximately)
DELETE FROM `board_members`;
/*!40000 ALTER TABLE `board_members` DISABLE KEYS */;
INSERT INTO `board_members` (`id`, `ngo_id`, `first_name`, `last_name`, `company`, `created`, `created_by`) VALUES
	(3, 2, '1', '', '2', '2017-05-07 13:31:58', 9);
/*!40000 ALTER TABLE `board_members` ENABLE KEYS */;

-- Dumping data for table hni.brand_partners: ~1 rows (approximately)
DELETE FROM `brand_partners`;
/*!40000 ALTER TABLE `brand_partners` DISABLE KEYS */;
INSERT INTO `brand_partners` (`id`, `ngo_id`, `phone`, `company`, `created`, `created_by`) VALUES
	(2, 2, '0', '32', '2017-05-07 13:31:58', 9);
/*!40000 ALTER TABLE `brand_partners` ENABLE KEYS */;

-- Dumping data for table hni.client: ~2 rows (approximately)
DELETE FROM `client`;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id`, `user_id`, `created_by`, `race`, `ethinicity`, `bday`, `been_arrested`, `ngo_id`, `been_convicted`, `has_smart_phone`, `service_provider`, `model`, `have_monthly_plan`, `monthly_plan_minute`, `monthly_plan_data`, `monthly_plan_cost`, `alt_monthly_plan`, `alt_monthly_plan_together`, `sliblings`, `kids`, `live_at_home`, `sheltered`, `live_with`, `parent_education`, `education`, `enrollment_status`, `enrollment_location`, `work_status`, `time_to_workplace`, `no_of_job`, `employer`, `job_title`, `duration_of_employement`, `unemployment_benfits`, `reason_unemployment_benefits`, `total_income`, `rate_amount`, `rate_type`, `avg_hours_per_week`, `resident_status`, `dollar_spend_food`, `dollar_spend_clothes`, `dollar_spend_entertainment`, `dollar_spend_transport`, `dollar_spend_savings`, `meals_per_day`, `food_preference`, `food_source`, `cook`, `travel_for_food_distance`, `traval_for_food_time`, `sub_food_program`, `sub_food_program_entity`, `sub_food_program_duration`, `sub_food_program_renew`, `sub_food_program_exp`, `allergies`, `addiction`, `addiction_type`, `mental_health_issue`, `mental_health_issue_history`, `height`, `weight`, `exercise_per_week`, `last_visit_doctor`, `last_visit_dentist`) VALUES
	(1, 10, 1, 0, 0, NULL, 0, 2, 0, 0, '0', '0', 0, '0', '0', '0', 0, '0', 0, 0, 0, 1, 0, 0, 0, 0, '0', 0, 0, 0, '0', '0', 0, 0, '0', 0, 0, 0, '0', 0, 0, 0, 0, 0, 0, 0, '0', '0', 0, 0, 0, 0, '0', 0, 0, '0', '0', 0, '0', '0', '0', '0', '0', 0, 0, 0),
	(3, 12, 1, 0, NULL, NULL, 0, NULL, 0, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;

-- Dumping data for table hni.dependents: ~0 rows (approximately)
DELETE FROM `dependents`;
/*!40000 ALTER TABLE `dependents` DISABLE KEYS */;
/*!40000 ALTER TABLE `dependents` ENABLE KEYS */;

-- Dumping data for table hni.education: ~0 rows (approximately)
DELETE FROM `education`;
/*!40000 ALTER TABLE `education` DISABLE KEYS */;
/*!40000 ALTER TABLE `education` ENABLE KEYS */;

-- Dumping data for table hni.endrosement: ~0 rows (approximately)
DELETE FROM `endrosement`;
/*!40000 ALTER TABLE `endrosement` DISABLE KEYS */;
/*!40000 ALTER TABLE `endrosement` ENABLE KEYS */;

-- Dumping data for table hni.event_state: ~1 rows (approximately)
DELETE FROM `event_state`;
/*!40000 ALTER TABLE `event_state` DISABLE KEYS */;
INSERT INTO `event_state` (`id`, `eventname`, `phoneno`) VALUES
	(1, 'MEAL', '1232322334');
/*!40000 ALTER TABLE `event_state` ENABLE KEYS */;

-- Dumping data for table hni.food_bank: ~1 rows (approximately)
DELETE FROM `food_bank`;
/*!40000 ALTER TABLE `food_bank` DISABLE KEYS */;
INSERT INTO `food_bank` (`id`, `ngo_id`, `food_bank_name`, `created`, `created_by`) VALUES
	(2, 2, 'Test Food bank', '2017-05-07 13:31:58', 9);
/*!40000 ALTER TABLE `food_bank` ENABLE KEYS */;

-- Dumping data for table hni.food_services: ~1 rows (approximately)
DELETE FROM `food_services`;
/*!40000 ALTER TABLE `food_services` DISABLE KEYS */;
INSERT INTO `food_services` (`id`, `ngo_id`, `service_type`, `weekdays`, `total_count`, `other`, `created`, `created_by`) VALUES
	(2, 2, 1, 'Sunday', 10, '', '2017-05-07 13:32:43', 9);
/*!40000 ALTER TABLE `food_services` ENABLE KEYS */;

-- Dumping data for table hni.hni_services: ~25 rows (approximately)
DELETE FROM `hni_services`;
/*!40000 ALTER TABLE `hni_services` DISABLE KEYS */;
INSERT INTO `hni_services` (`id`, `org_id`, `role_id`, `service_name`, `service_path`, `service_img`, `active`, `created`) VALUES
	(1, 2, 1, 'NGO Invitation', 'ngoInvitation', '', 'Y', '2017-05-02 13:16:58'),
	(2, 2, 1, 'Client Invitation', 'clientInvitation', '', 'Y', '2017-05-02 13:16:58'),
	(3, 2, 1, 'Volunteer Invitation', 'inviteVolunteer', '', 'Y', '2017-05-02 13:16:59'),
	(4, 2, 1, 'Reports', 'reports', '', 'Y', '2017-05-02 13:16:59'),
	(5, 2, 1, 'Settings', 'settings', '', 'Y', '2017-05-02 13:16:59'),
	(6, 2, 6, 'Add NGO', 'addNgo', '', 'Y', '2017-05-02 13:16:59'),
	(7, 2, 6, 'Client Invitation', 'clientInvitation', '', 'Y', '2017-05-02 13:16:59'),
	(8, 2, 6, 'Volunteer Invitaion', 'inviteVolunteer', '', 'Y', '2017-05-02 13:16:59'),
	(9, 2, 6, 'View Profile', 'profile', '', 'Y', '2017-05-02 13:16:59'),
	(10, 2, 6, 'Change Password', 'change-password', '', 'Y', '2017-05-02 13:16:59'),
	(11, 2, 6, 'View Customers', 'view-customers', '', 'Y', '2017-05-02 13:16:59'),
	(12, 2, 7, 'Client Invitation', 'clientInvitation', '', 'Y', '2017-05-02 13:16:59'),
	(13, 2, 7, 'Clients', 'clients', '', 'Y', '2017-05-02 13:16:59'),
	(14, 2, 7, 'Volunteer Invitaion', 'inviteVolunteer', '', 'Y', '2017-05-02 13:16:59'),
	(15, 2, 7, 'View Profile', 'profile', '', 'Y', '2017-05-02 13:16:59'),
	(16, 2, 7, 'Change Password', 'change-password', '', 'Y', '2017-05-02 13:16:59'),
	(17, 2, 7, 'Add Resturants', 'add-resturants', '', 'Y', '2017-05-02 13:16:59'),
	(18, 2, 3, 'Place Order', 'order', '', 'Y', '2017-05-02 13:16:59'),
	(19, 2, 3, 'My Orders', 'my-orders', '', 'Y', '2017-05-02 13:16:59'),
	(20, 2, 3, 'View Profile', 'profile', '', 'Y', '2017-05-02 13:16:59'),
	(21, 2, 3, 'Change Password', 'change-password', '', 'Y', '2017-05-02 13:16:59'),
	(22, 2, 4, 'Request Food', 'hungry', '', 'Y', '2017-05-02 13:16:59'),
	(23, 2, 4, 'My Orders', 'my-orders', '', 'Y', '2017-05-02 13:16:59'),
	(24, 2, 4, 'View Profile', 'user-profile', '', 'Y', '2017-05-02 13:16:59'),
	(25, 2, 4, 'Change Password', 'change-password', '', 'Y', '2017-05-02 13:16:59');
/*!40000 ALTER TABLE `hni_services` ENABLE KEYS */;

-- Dumping data for table hni.hni_templates: ~2 rows (approximately)
DELETE FROM `hni_templates`;
/*!40000 ALTER TABLE `hni_templates` DISABLE KEYS */;
INSERT INTO `hni_templates` (`id`, `created_by`, `template`, `type`, `last_modified`) VALUES
	(1, 1, 'Dear Participant, Order %s sucessfully placed, you can now pick up your order from the store : %s. You confirmation id is : %s";', 'POCN', '2017-07-28'),
	(2, 1, 'Hi! Apologies for the inconvenience! Your order has been cancelled. Please contact HungerNotImpossible for more information.', 'OCUND', '2017-08-21');
/*!40000 ALTER TABLE `hni_templates` ENABLE KEYS */;

-- Dumping data for table hni.income: ~0 rows (approximately)
DELETE FROM `income`;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
/*!40000 ALTER TABLE `income` ENABLE KEYS */;

-- Dumping data for table hni.invitation: ~3 rows (approximately)
DELETE FROM `invitation`;
/*!40000 ALTER TABLE `invitation` DISABLE KEYS */;
INSERT INTO `invitation` (`id`, `org_id`, `invite_code`, `activated`, `email`, `invited_by`, `ngo_id`, `token_expire_date`, `phone`, `dependants_count`, `message`, `name`, `invitation_type`, `state`, `created_date`, `data`) VALUES
	(1, 8, 'eff1229845f84253946506d350f14f36', 1, 'U45914@ust-global.com', 1, NULL, '2017-05-12', NULL, 0, NULL, NULL, NULL, NULL, '2017-05-07', NULL),
	(2, 2, 'd1bbfc77180643eebb25b868196bc882', 0, 'Rahul.Nair111@ust-global.com', 1, NULL, '2017-05-27', NULL, 0, NULL, NULL, NULL, NULL, '2017-05-22', '{"name":"Rahul","phone":"953-946-9293","email":"Rahul.Nair@ust-global.com","website":"NA","dependants":"2"}'),
	(3, 2, 'c98f129777574a478c4cd9ed188e268f', 1, 'Rahul.Nair@ust-global.com', 1, NULL, '2017-05-28', NULL, 0, NULL, NULL, NULL, NULL, '2017-05-23', '{"name":"Rahul","phone":"215-302-0127","email":"Rahul.Nair@ust-global.com","invitationMessage":"Welcome Rahul"}');
/*!40000 ALTER TABLE `invitation` ENABLE KEYS */;

-- Dumping data for table hni.local_partners: ~1 rows (approximately)
DELETE FROM `local_partners`;
/*!40000 ALTER TABLE `local_partners` DISABLE KEYS */;
INSERT INTO `local_partners` (`id`, `ngo_id`, `phone`, `company`, `created`, `created_by`) VALUES
	(2, 2, '0', 'Res', '2017-05-07 13:31:58', 9);
/*!40000 ALTER TABLE `local_partners` ENABLE KEYS */;

-- Dumping data for table hni.marital_status: ~0 rows (approximately)
DELETE FROM `marital_status`;
/*!40000 ALTER TABLE `marital_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `marital_status` ENABLE KEYS */;

-- Dumping data for table hni.meal_donation_sources: ~2 rows (approximately)
DELETE FROM `meal_donation_sources`;
/*!40000 ALTER TABLE `meal_donation_sources` DISABLE KEYS */;
INSERT INTO `meal_donation_sources` (`id`, `ngo_id`, `source`, `quantity`, `frequency`, `created`, `created_by`) VALUES
	(3, 2, 'Need to check', NULL, '23', '2017-05-07 13:34:41', 9),
	(4, 2, 'Rest', NULL, '24', '2017-05-07 13:34:41', 9);
/*!40000 ALTER TABLE `meal_donation_sources` ENABLE KEYS */;

-- Dumping data for table hni.meal_funding_sources: ~2 rows (approximately)
DELETE FROM `meal_funding_sources`;
/*!40000 ALTER TABLE `meal_funding_sources` DISABLE KEYS */;
INSERT INTO `meal_funding_sources` (`id`, `ngo_id`, `amount`, `source`, `created`, `created_by`) VALUES
	(3, 2, 32, 'Test', '2017-05-07 13:34:45', 9),
	(4, 2, 34, 'Res', '2017-05-07 13:34:45', 9);
/*!40000 ALTER TABLE `meal_funding_sources` ENABLE KEYS */;

-- Dumping data for table hni.menus: ~36 rows (approximately)
DELETE FROM `menus`;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` (`id`, `name`, `provider_id`, `start_hour`, `end_hour`) VALUES
	(1, 'Subway Missouri Menu', 1, 10, 23),
	(2, 'Chipotle Missouri Menu', 2, 10, 23),
	(3, 'Taco Bell Missouri Menu', 3, 10, 23),
	(4, 'Panera Missouri Menu', 4, 10, 23),
	(5, 'Taco Bell Menu Oregon', 3, 16, 1),
	(6, 'Taco Bell Menu Oregon', 3, 16, 1),
	(7, 'Taco Bell Menu Oregon', 3, 16, 1),
	(8, 'Subway Menu Oregon', 1, 16, 1),
	(9, 'Jalapeno Menu Oregon', 5, 16, 1);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;

-- Dumping data for table hni.menu_items: ~147 rows (approximately)
DELETE FROM `menu_items`;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` (`id`, `menu_id`, `name`, `description`, `price`, `expires`, `calories`, `protien`, `fat`, `carbs`, `active`) VALUES
	(1, 1, 'Turkey Breast no cheese add avocado', '9-Grain Wheat, Swiss Cheese, Cucumber, Green Pepper, Lettuce, Spinach, Tomato', 8.03, NULL, 560, 36, 7, 92, 0),
	(2, 1, 'Veggie Delight', '9-Grain Wheat, Swiss Cheese, Cucumber, Green Pepper, Lettuce, Spinach, Tomato', 6.82, NULL, 460, 16, 5, 88, 0),
	(147, 36, 'Southwestern Chicken Salad', 'Grilled chicken with chimichurri, mixed greens, corn and black bean salsa, Cheddar and crispy tortilla strips with a creamy house-made cilantro ranch dressing', 10.99, NULL, 0, 0, 0, 0, 1);
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;

-- Dumping data for table hni.ngo: ~1 rows (approximately)
DELETE FROM `ngo`;
/*!40000 ALTER TABLE `ngo` DISABLE KEYS */;
INSERT INTO `ngo` (`id`, `user_id`, `website`, `contact_name`, `fte`, `overview`, `mission`, `monthly_budget`, `operating_cost`, `personal_cost`, `kitchen_volunteers`, `food_stamp_assist`, `food_bank`, `resources_to_clients`, `ind_serv_daily`, `ind_serv_monthly`, `ind_serv_annual`, `client_info`, `store_client_info`, `clients_unsheltered`, `clients_employed`, `created`, `created_by`) VALUES
	(2, 9, 'http://www.ust-global.com', '', 1, 'I love working in IT', 'Save people', 1, 0, 2, 3, 1, 1, '1', 12, 12, 23, 1, 'In Database', 13, 4, '2017-05-07 13:31:58', 1);
/*!40000 ALTER TABLE `ngo` ENABLE KEYS */;

-- Dumping data for table hni.ngo_funding_sources: ~2 rows (approximately)
DELETE FROM `ngo_funding_sources`;
/*!40000 ALTER TABLE `ngo_funding_sources` DISABLE KEYS */;
INSERT INTO `ngo_funding_sources` (`id`, `ngo_id`, `amount`, `source`, `created`, `created_by`) VALUES
	(3, 2, 122, 'UST', '2017-05-07 13:34:47', 9),
	(4, 2, 234, 'Dev', '2017-05-07 13:34:47', 9);
/*!40000 ALTER TABLE `ngo_funding_sources` ENABLE KEYS */;

-- Dumping data for table hni.orders: ~1 rows (approximately)
DELETE FROM `orders`;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`id`, `user_id`, `provider_location_id`, `order_date`, `ready_date`, `pickup_date`, `subtotal`, `tax`, `created_by`, `status_id`, `completed_by`, `confirmation_id`) VALUES
	(3, 10, 15, '2017-07-28 13:20:02', NULL, NULL, 10.00, NULL, NULL, 1, NULL, NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dumping data for table hni.order_items: ~6 rows (approximately)
DELETE FROM `order_items`;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` (`id`, `order_id`, `menu_item_id`, `quantity`, `amount`) VALUES
	(1, 1, 1, 1, 6.99),
	(2, 2, 2, 1, 7.99),
	(3, 2, 3, 1, 8.99),
	(14, 14, 1, 1, 6.99),
	(15, 2, 21, 1, 10.00),
	(16, 3, 22, 1, 10.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;

-- Dumping data for table hni.order_payments: ~2 rows (approximately)
DELETE FROM `order_payments`;
/*!40000 ALTER TABLE `order_payments` DISABLE KEYS */;
INSERT INTO `order_payments` (`order_id`, `payment_instrument_id`, `amount`, `created_by`, `created_date`, `status`) VALUES
	(1, 1, 8.00, 1, '2017-07-20 14:07:01', '0'),
	(1, 2, 1.00, 1, '2017-07-20 14:08:16', '0');
/*!40000 ALTER TABLE `order_payments` ENABLE KEYS */;

-- Dumping data for table hni.organizations: ~4 rows (approximately)
DELETE FROM `organizations`;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` (`id`, `name`, `phone`, `email`, `website`, `logo`, `created`, `created_by`) VALUES
	(1, 'Not Impossible', 'phone', 'ni@email.net', 'website', 'logo', '2017-05-07 12:58:13', 1),
	(2, 'Samaritan House', 'phone', 'emailsam@samhouse.net', 'website', 'logo', '2017-05-07 12:58:14', 1),
	(3, 'Volunteer Organization', 'phone', 'noreply@nowhere.net', 'website', 'logo', '2017-05-07 12:58:14', 1),
	(8, 'UST', '123-456-3435', 'U45914@ust-global.com', 'http://www.ust-global.com', NULL, '2017-05-07 13:15:40', 1);
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;

-- Dumping data for table hni.organization_addresses: ~5 rows (approximately)
DELETE FROM `organization_addresses`;
/*!40000 ALTER TABLE `organization_addresses` DISABLE KEYS */;
INSERT INTO `organization_addresses` (`organization_id`, `address_id`) VALUES
	(4, 10),
	(5, 11),
	(6, 12),
	(7, 13),
	(8, 14);
/*!40000 ALTER TABLE `organization_addresses` ENABLE KEYS */;

-- Dumping data for table hni.partial_orders: ~0 rows (approximately)
DELETE FROM `partial_orders`;
/*!40000 ALTER TABLE `partial_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `partial_orders` ENABLE KEYS */;

-- Dumping data for table hni.partial_orders_menu_items: ~0 rows (approximately)
DELETE FROM `partial_orders_menu_items`;
/*!40000 ALTER TABLE `partial_orders_menu_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `partial_orders_menu_items` ENABLE KEYS */;

-- Dumping data for table hni.partial_orders_menu_selections: ~0 rows (approximately)
DELETE FROM `partial_orders_menu_selections`;
/*!40000 ALTER TABLE `partial_orders_menu_selections` DISABLE KEYS */;
/*!40000 ALTER TABLE `partial_orders_menu_selections` ENABLE KEYS */;

-- Dumping data for table hni.partial_orders_order_items: ~0 rows (approximately)
DELETE FROM `partial_orders_order_items`;
/*!40000 ALTER TABLE `partial_orders_order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `partial_orders_order_items` ENABLE KEYS */;

-- Dumping data for table hni.partial_orders_provider_locations: ~0 rows (approximately)
DELETE FROM `partial_orders_provider_locations`;
/*!40000 ALTER TABLE `partial_orders_provider_locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `partial_orders_provider_locations` ENABLE KEYS */;

-- Dumping data for table hni.participant_dependent_relation: ~0 rows (approximately)
DELETE FROM `participant_dependent_relation`;
/*!40000 ALTER TABLE `participant_dependent_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `participant_dependent_relation` ENABLE KEYS */;

-- Dumping data for table hni.payment_instruments: ~10 rows (approximately)
DELETE FROM `payment_instruments`;
/*!40000 ALTER TABLE `payment_instruments` DISABLE KEYS */;
INSERT INTO `payment_instruments` (`id`, `provider_id`, `card_type`, `card_serial_id`, `card_number`, `status`, `orginal_balance`, `balance`, `last_used_datetime`, `state_code`, `allow_topup`, `created_by`, `modified_by`, `created_date`, `pin_number`) VALUES
	(1, 1, 'gift', '1', '1000-0000-0000-0001', 'A', 10.00, 0.00, NULL, 'MO', NULL, NULL, NULL, NULL, '1234'),
	(2, 1, 'gift', '2', '2000-0000-0000-0001', 'A', 10.00, 10.00, NULL, 'MO', NULL, NULL, NULL, NULL, '1234'),
	(3, 1, 'gift', '3', '3000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(4, 1, 'gift', '4', '4000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(5, 1, 'gift', '5', '5000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(6, 1, 'gift', '6', '6000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(7, 1, 'gift', '7', '7000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(8, 1, 'gift', '8', '8000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(9, 1, 'gift', '9', '9000-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234'),
	(10, 1, 'gift', '10', '1100-0000-0000-0001', 'A', 10.00, 10.00, NULL, NULL, NULL, NULL, NULL, NULL, '1234');
/*!40000 ALTER TABLE `payment_instruments` ENABLE KEYS */;

-- Dumping data for table hni.providers: ~26 rows (approximately)
DELETE FROM `providers`;
/*!40000 ALTER TABLE `providers` DISABLE KEYS */;
INSERT INTO `providers` (`id`, `name`, `address_id`, `menu_id`, `website_url`, `created`, `created_by`) VALUES
	(1, 'Subway', 21, 1, 'http://www.subway.com', '2017-05-29 17:00:46', 1),
	(2, 'Chipotle', 57, 2, 'https://order.chipotle.com', '2017-05-29 17:00:52', 1),
	(3, 'Taco Bell', 70, 3, 'https://www.tacobell.com/locations', '2017-05-29 17:00:55', 1),
	(4, 'Panera', 113, 4, 'https://delivery.panerabread.com', '2017-05-29 17:01:00', 1),
	(5, 'Jalapeno', 150, 9, '', '2017-06-28 20:39:27', 1);
/*!40000 ALTER TABLE `providers` ENABLE KEYS */;

-- Dumping data for table hni.provider_locations: ~166 rows (approximately)
DELETE FROM `provider_locations`;
/*!40000 ALTER TABLE `provider_locations` DISABLE KEYS */;
INSERT INTO `provider_locations` (`id`, `name`, `provider_id`, `address_id`, `menu_id`, `created`, `created_by`, `is_active`, `last_updated_by`, `last_updated`) VALUES
	(1, 'Subway #22', 1, 22, 4, '2017-05-29 17:00:47', 1, NULL, NULL, NULL),
	(2, 'Subway #23', 1, 23, 4, '2017-05-29 17:00:47', 1, NULL, NULL, NULL),
	(3, 'Subway #24', 1, 24, 4, '2017-05-29 17:00:47', 1, NULL, NULL, NULL),
	(4, 'Subway #25', 1, 25, 4, '2017-05-29 17:00:47', 1, NULL, NULL, NULL),
	(5, 'Subway #26', 1, 26, 4, '2017-05-29 17:00:47', 1, NULL, NULL, NULL),
	(6, 'Subway #27', 1, 27, 4, '2017-05-29 17:00:47', 1, NULL, NULL, NULL),
	(7, 'Subway #28', 1, 28, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(8, 'Subway #29', 1, 29, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(9, 'Subway #30', 1, 30, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(10, 'Subway #31', 1, 31, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(11, 'Subway #32', 1, 32, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(12, 'Subway #33', 1, 33, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(13, 'Subway #34', 1, 34, 4, '2017-05-29 17:00:48', 1, NULL, NULL, NULL),
	(14, 'Subway #35', 1, 35, 4, '2017-05-29 17:00:49', 1, NULL, NULL, NULL),
	(15, 'Subway #36', 1, 36, 4, '2017-05-29 17:00:49', 1, NULL, NULL, NULL),
	(16, 'Subway #37', 1, 37, 4, '2017-05-29 17:00:49', 1, NULL, NULL, NULL),
	(17, 'Subway #38', 1, 38, 4, '2017-05-29 17:00:49', 1, NULL, NULL, NULL),
	(18, 'Subway #39', 1, 39, 4, '2017-05-29 17:00:49', 1, NULL, NULL, NULL),
	(19, 'Subway #40', 1, 40, 4, '2017-05-29 17:00:49', 1, NULL, NULL, NULL),
	(20, 'Subway #41', 1, 41, 4, '2017-05-29 17:00:50', 1, NULL, NULL, NULL),
	(21, 'Subway #42', 1, 42, 4, '2017-05-29 17:00:50', 1, NULL, NULL, NULL),
	(22, 'Subway #43', 1, 43, 4, '2017-05-29 17:00:50', 1, NULL, NULL, NULL),
	(23, 'Subway #44', 1, 44, 4, '2017-05-29 17:00:50', 1, NULL, NULL, NULL),
	(24, 'Subway #45', 1, 45, 4, '2017-05-29 17:00:50', 1, NULL, NULL, NULL),
	(25, 'Subway #46', 1, 46, 4, '2017-05-29 17:00:50', 1, NULL, NULL, NULL),
	(26, 'Subway #47', 1, 47, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(27, 'Subway #48', 1, 48, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(28, 'Subway #49', 1, 49, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(29, 'Subway #50', 1, 50, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(30, 'Subway #51', 1, 51, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(31, 'Subway #52', 1, 52, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(32, 'Subway #53', 1, 53, 4, '2017-05-29 17:00:51', 1, NULL, NULL, NULL),
	(33, 'Subway #54', 1, 54, 4, '2017-05-29 17:00:52', 1, NULL, NULL, NULL),
	(34, 'Subway #55', 1, 55, 4, '2017-05-29 17:00:52', 1, NULL, NULL, NULL),
	(35, 'Subway #56', 1, 56, 4, '2017-05-29 17:00:52', 1, NULL, NULL, NULL),
	(36, 'Chipotle #58', 2, 58, 4, '2017-05-29 17:00:52', 1, NULL, NULL, NULL),
	(37, 'Chipotle #59', 2, 59, 4, '2017-05-29 17:00:53', 1, NULL, NULL, NULL),
	(38, 'Chipotle #60', 2, 60, 4, '2017-05-29 17:00:53', 1, NULL, NULL, NULL),
	(39, 'Chipotle #61', 2, 61, 4, '2017-05-29 17:00:53', 1, NULL, NULL, NULL),
	(40, 'Chipotle #62', 2, 62, 4, '2017-05-29 17:00:53', 1, NULL, NULL, NULL),
	(41, 'Chipotle #63', 2, 63, 4, '2017-05-29 17:00:53', 1, NULL, NULL, NULL),
	(42, 'Chipotle #64', 2, 64, 4, '2017-05-29 17:00:54', 1, NULL, NULL, NULL),
	(43, 'Chipotle #65', 2, 65, 4, '2017-05-29 17:00:54', 1, NULL, NULL, NULL),
	(44, 'Chipotle #66', 2, 66, 4, '2017-05-29 17:00:54', 1, NULL, NULL, NULL),
	(45, 'Chipotle #67', 2, 67, 4, '2017-05-29 17:00:54', 1, NULL, NULL, NULL),
	(46, 'Chipotle #68', 2, 68, 4, '2017-05-29 17:00:54', 1, NULL, NULL, NULL),
	(47, 'Chipotle #69', 2, 69, 4, '2017-05-29 17:00:54', 1, NULL, NULL, NULL),
	(48, 'Taco Bell #71', 3, 71, 4, '2017-05-29 17:00:55', 1, NULL, NULL, NULL),
	(49, 'Taco Bell #72', 3, 72, 4, '2017-05-29 17:00:55', 1, NULL, NULL, NULL),
	(50, 'Taco Bell #73', 3, 73, 4, '2017-05-29 17:00:55', 1, NULL, NULL, NULL),
	(51, 'Taco Bell #74', 3, 74, 4, '2017-05-29 17:00:55', 1, NULL, NULL, NULL),
	(52, 'Taco Bell #75', 3, 75, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(53, 'Taco Bell #76', 3, 76, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(54, 'Taco Bell #77', 3, 77, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(55, 'Taco Bell #78', 3, 78, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(56, 'Taco Bell #79', 3, 79, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(57, 'Taco Bell #80', 3, 80, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(58, 'Taco Bell #81', 3, 81, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(59, 'Taco Bell #82', 3, 82, 4, '2017-05-29 17:00:56', 1, NULL, NULL, NULL),
	(60, 'Taco Bell #83', 3, 83, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(61, 'Taco Bell #84', 3, 84, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(62, 'Taco Bell #85', 3, 85, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(63, 'Taco Bell #86', 3, 86, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(64, 'Taco Bell #87', 3, 87, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(65, 'Taco Bell #88', 3, 88, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(66, 'Taco Bell #89', 3, 89, 4, '2017-05-29 17:00:57', 1, NULL, NULL, NULL),
	(67, 'Taco Bell #90', 3, 90, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(68, 'Taco Bell #91', 3, 91, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(69, 'Taco Bell #92', 3, 92, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(70, 'Taco Bell #93', 3, 93, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(71, 'Taco Bell #94', 3, 94, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(72, 'Taco Bell #95', 3, 95, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(73, 'Taco Bell #96', 3, 96, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(74, 'Taco Bell #97', 3, 97, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(75, 'Taco Bell #98', 3, 98, 4, '2017-05-29 17:00:58', 1, NULL, NULL, NULL),
	(76, 'Taco Bell #99', 3, 99, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(77, 'Taco Bell #100', 3, 100, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(78, 'Taco Bell #101', 3, 101, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(79, 'Taco Bell #102', 3, 102, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(80, 'Taco Bell #103', 3, 103, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(81, 'Taco Bell #104', 3, 104, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(82, 'Taco Bell #105', 3, 105, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(83, 'Taco Bell #106', 3, 106, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(84, 'Taco Bell #107', 3, 107, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(85, 'Taco Bell #108', 3, 108, 4, '2017-05-29 17:00:59', 1, NULL, NULL, NULL),
	(86, 'Taco Bell #109', 3, 109, 4, '2017-05-29 17:01:00', 1, NULL, NULL, NULL),
	(87, 'Taco Bell #110', 3, 110, 4, '2017-05-29 17:01:00', 1, NULL, NULL, NULL),
	(88, 'Taco Bell #111', 3, 111, 4, '2017-05-29 17:01:00', 1, NULL, NULL, NULL),
	(89, 'Taco Bell #112', 3, 112, 4, '2017-05-29 17:01:00', 1, NULL, NULL, NULL),
	(90, 'Panera #114', 4, 114, 4, '2017-05-29 17:01:00', 1, NULL, NULL, NULL),
	(91, 'Panera #115', 4, 115, 4, '2017-05-29 17:01:00', 1, NULL, NULL, NULL),
	(92, 'Panera #116', 4, 116, 4, '2017-05-29 17:01:01', 1, NULL, NULL, NULL),
	(93, 'Panera #117', 4, 117, 4, '2017-05-29 17:01:01', 1, NULL, NULL, NULL),
	(94, 'Panera #118', 4, 118, 4, '2017-05-29 17:01:01', 1, NULL, NULL, NULL),
	(166, 'Subway #295', 1, 0, 1, '2017-07-12 14:54:19', 1, NULL, NULL, NULL);
/*!40000 ALTER TABLE `provider_locations` ENABLE KEYS */;

-- Dumping data for table hni.provider_location_hours: ~165 rows (approximately)
DELETE FROM `provider_location_hours`;
/*!40000 ALTER TABLE `provider_location_hours` DISABLE KEYS */;
INSERT INTO `provider_location_hours` (`id`, `provider_location_id`, `dow`, `open_hour`, `close_hour`) VALUES
	(1, 1, 'Mon', 7, 22),
	(2, 2, 'Mon', 9, 22),
	(3, 3, 'Mon', 7, 20),
	(4, 4, 'Mon', 7, 21),
	(5, 5, 'Mon', 7, 22),
	(6, 6, 'Mon', 7, 16),
	(7, 7, 'Mon', 7, 21),
	(8, 8, 'Mon', 7, 20),
	(9, 9, 'Mon', 11, 19),
	(10, 10, 'Mon', 7, 22),
	(11, 11, 'Mon', 7, 21),
	(12, 12, 'Mon', 7, 22),
	(13, 13, 'Mon', 7, 21),
	(14, 14, 'Mon', 7, 22),
	(15, 15, 'Mon', 7, 22),
	(16, 16, 'Mon', 7, 21),
	(17, 17, 'Mon', 7, 21),
	(18, 18, 'Mon', 7, 21),
	(19, 19, 'Mon', 7, 22),
	(20, 20, 'Mon', 7, 22),
	(21, 21, 'Mon', 8, 21),
	(22, 22, 'Mon', 7, 22),
	(23, 23, 'Mon', 8, 21),
	(24, 24, 'Mon', 7, 21),
	(25, 25, 'Mon', 7, 0),
	(26, 26, 'Mon', 0, 23),
	(27, 27, 'Mon', 8, 21),
	(28, 28, 'Mon', 7, 21),
	(29, 29, 'Mon', 10, 21),
	(30, 30, 'Mon', 7, 23),
	(31, 31, 'Mon', 9, 21),
	(32, 32, 'Mon', 7, 21),
	(33, 33, 'Mon', 7, 21),
	(34, 34, 'Mon', 8, 21),
	(35, 35, 'Mon', 7, 21),
	(36, 36, 'Mon', 11, 22),
	(37, 37, 'Mon', 11, 21),
	(38, 38, 'Mon', 11, 22),
	(39, 39, 'Mon', 11, 21),
	(40, 40, 'Mon', 11, 21),
	(41, 41, 'Mon', 11, 22),
	(42, 42, 'Mon', 11, 21),
	(43, 43, 'Mon', 11, 22),
	(44, 44, 'Mon', 11, 22),
	(45, 45, 'Mon', 11, 22),
	(46, 46, 'Mon', 11, 21),
	(47, 47, 'Mon', 11, 21),
	(48, 48, 'Mon', 7, 13),
	(49, 49, 'Mon', 7, 15),
	(50, 50, 'Mon', 7, 14),
	(51, 51, 'Mon', 7, 15),
	(52, 52, 'Mon', 7, 12),
	(53, 53, 'Mon', 7, 14),
	(54, 54, 'Mon', 7, 14),
	(55, 55, 'Mon', 7, 14),
	(56, 56, 'Mon', 7, 15),
	(57, 57, 'Mon', 7, 14),
	(58, 58, 'Mon', 7, 13),
	(59, 59, 'Mon', 7, 13),
	(60, 60, 'Mon', 7, 13),
	(61, 61, 'Mon', 7, 12),
	(62, 62, 'Mon', 7, 14),
	(63, 63, 'Mon', 7, 13),
	(64, 64, 'Mon', 7, 13),
	(65, 65, 'Mon', 7, 12),
	(66, 66, 'Mon', 7, 13),
	(67, 67, 'Mon', 7, 14),
	(68, 68, 'Mon', 6, 13),
	(69, 69, 'Mon', 7, 14),
	(70, 70, 'Mon', 9, 13),
	(71, 71, 'Mon', 6, 14),
	(72, 72, 'Mon', 7, 12),
	(73, 73, 'Mon', 7, 13),
	(74, 74, 'Mon', 7, 14),
	(75, 75, 'Mon', 7, 13),
	(76, 76, 'Mon', 7, 14),
	(77, 77, 'Mon', 10, 22),
	(78, 78, 'Mon', 7, 14),
	(79, 79, 'Mon', 7, 13),
	(80, 80, 'Mon', 7, 13),
	(81, 81, 'Mon', 7, 14),
	(82, 82, 'Mon', 7, 14),
	(83, 83, 'Mon', 7, 23),
	(84, 84, 'Mon', 7, 14),
	(85, 85, 'Mon', 7, 14),
	(86, 86, 'Mon', 7, 14),
	(87, 87, 'Mon', 7, 23),
	(88, 88, 'Mon', 7, 0),
	(89, 89, 'Mon', 7, 1),
	(90, 90, 'Mon', 5, 20),
	(91, 91, 'Mon', 6, 22),
	(92, 92, 'Mon', 6, 20),
	(93, 93, 'Mon', 7, 21),
	(94, 94, 'Mon', 6, 22),
	(95, 95, 'Mon', 6, 22),
	(96, 96, 'Mon', 6, 22),
	(97, 97, 'Mon', 6, 21),
	(98, 98, 'Mon', 6, 22),
	(99, 99, 'Mon', 6, 22),
	(100, 100, 'Mon', 6, 22),
	(101, 101, 'Mon', 6, 21),
	(102, 102, 'Mon', 6, 22),
	(103, 103, 'Mon', 6, 22),
	(104, 104, 'Mon', 6, 22),
	(105, 105, 'Mon', 6, 21),
	(106, 106, 'Mon', 6, 22),
	(107, 107, 'Mon', 7, 16),
	(108, 108, 'Mon', 6, 22),
	(109, 109, 'Mon', 6, 22),
	(110, 110, 'Mon', 6, 22),
	(111, 111, 'Mon', 6, 22),
	(112, 112, 'Mon', 6, 22),
	(113, 113, 'Mon', 6, 22),
	(114, 114, 'Mon', 6, 22),
	(115, 115, 'Mon', 6, 22),
	(116, 116, 'Mon', 6, 22),
	(117, 117, 'Mon', 7, 15),
	(118, 118, 'Mon', 7, 15),
	(119, 119, 'Mon', 7, 15),
	(120, 120, 'Mon', 7, 15),
	(121, 121, 'Mon', 7, 15),
	(122, 122, 'Mon', 7, 15),
	(123, 123, 'Mon', 7, 15),
	(124, 124, 'Mon', 7, 15),
	(125, 125, 'Mon', 7, 15),
	(126, 126, 'Mon', 7, 15),
	(127, 127, 'Mon', 7, 15),
	(128, 128, 'Mon', 7, 15),
	(129, 129, 'Mon', 7, 15),
	(130, 130, 'Mon', 7, 15),
	(131, 131, 'Mon', 7, 15),
	(132, 132, 'Mon', 7, 15),
	(133, 133, 'Mon', 7, 15),
	(134, 134, 'Mon', 7, 15),
	(135, 135, 'Mon', 7, 15),
	(136, 136, 'Mon', 7, 15),
	(137, 137, 'Mon', 7, 15),
	(138, 138, 'Mon', 7, 15),
	(139, 139, 'Mon', 7, 15),
	(140, 140, 'Mon', 7, 15),
	(141, 141, 'Mon', 7, 15),
	(142, 142, 'Mon', 7, 15),
	(143, 143, 'Mon', 7, 15),
	(144, 144, 'Mon', 7, 15),
	(145, 145, 'Mon', 7, 15),
	(146, 146, 'Mon', 7, 15),
	(147, 147, 'Mon', 7, 15),
	(148, 148, 'Mon', 7, 15),
	(149, 149, 'Mon', 7, 15),
	(150, 150, 'Mon', 7, 15),
	(151, 151, 'Mon', 7, 15),
	(152, 152, 'Mon', 7, 15),
	(153, 153, 'Mon', 7, 15),
	(154, 154, 'Mon', 7, 15),
	(155, 155, 'Mon', 7, 15),
	(156, 156, 'Mon', 7, 15),
	(157, 157, 'Mon', 7, 15),
	(158, 158, 'Mon', 7, 15),
	(159, 159, 'Mon', 7, 15),
	(160, 160, 'Mon', 7, 15),
	(161, 161, 'Mon', 7, 15),
	(162, 162, 'Mon', 7, 15),
	(163, 163, 'Mon', 7, 15),
	(164, 164, 'Mon', 7, 15),
	(165, 166, 'Mon', 13, 1);
/*!40000 ALTER TABLE `provider_location_hours` ENABLE KEYS */;

-- Dumping data for table hni.race: ~0 rows (approximately)
DELETE FROM `race`;
/*!40000 ALTER TABLE `race` DISABLE KEYS */;
/*!40000 ALTER TABLE `race` ENABLE KEYS */;

-- Dumping data for table hni.registration_state: ~0 rows (approximately)
DELETE FROM `registration_state`;
/*!40000 ALTER TABLE `registration_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `registration_state` ENABLE KEYS */;

-- Dumping data for table hni.reports: ~18 rows (approximately)
DELETE FROM `reports`;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` (`id`, `report_path`, `label`, `role`) VALUES
	(1, 'ngo/all', 'NGO list', 1),
	(2, 'volunteers/all', 'Volunteer list', 1),
	(3, 'customers/all', 'All Participant list', 1),
	(5, 'customers/ngo', 'Customers ', 6),
	(6, 'customers/ngo', 'Customers', 7),
	(7, 'volunteers/all', 'Volunteer list', 6),
	(8, 'volunteers/all', 'Volunteer list', 5),
	(9, 'volunteers/all', 'Volunteer list', 7),
	(10, 'ngo/all', 'NGO List', 6),
	(11, 'orders/all', 'Orders', 4),
	(12, 'orders/all', 'Orders completed', 3),
	(13, 'orders/all', 'Orders ', 2),
	(14, 'orders/all', 'Orders', 6),
	(15, 'orders/all', 'Orders placed', 1),
	(16, 'customers/organization', 'Organization', 6),
	(17, 'provider/all', 'Providers', 1),
	(18, 'orders/all', 'Orders', 7),
	(19, 'customers/organization', 'Organization', 7);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;

-- Dumping data for table hni.security_permissions: ~27 rows (approximately)
DELETE FROM `security_permissions`;
/*!40000 ALTER TABLE `security_permissions` DISABLE KEYS */;
INSERT INTO `security_permissions` (`id`, `domain`, `value`) VALUES
	(1, '*', '*'),
	(11, 'organizations', 'create'),
	(12, 'organizations', 'read'),
	(13, 'organizations', 'update'),
	(14, 'organizations', 'delete'),
	(15, 'organizations', '*'),
	(21, 'users', 'create'),
	(22, 'users', 'read'),
	(23, 'users', 'update'),
	(24, 'users', 'delete'),
	(25, 'users', '*'),
	(41, 'providers', 'create'),
	(42, 'providers', 'read'),
	(43, 'providers', 'update'),
	(44, 'providers', 'delete'),
	(45, 'providers', '*'),
	(61, 'orders', 'create'),
	(62, 'orders', 'read'),
	(63, 'orders', 'update'),
	(64, 'orders', 'delete'),
	(65, 'orders', '*'),
	(67, 'orders', 'provision'),
	(81, 'activation-codes', 'create'),
	(82, 'activation-codes', 'read'),
	(83, 'activation-codes', 'update'),
	(84, 'activation-codes', 'delete'),
	(85, 'activation-codes', '*');
/*!40000 ALTER TABLE `security_permissions` ENABLE KEYS */;

-- Dumping data for table hni.security_roles: ~7 rows (approximately)
DELETE FROM `security_roles`;
/*!40000 ALTER TABLE `security_roles` DISABLE KEYS */;
INSERT INTO `security_roles` (`id`, `name`) VALUES
	(1, 'Super User'),
	(2, 'Administrator'),
	(3, 'Volunteer'),
	(4, 'Client'),
	(5, 'User'),
	(6, 'NGOAdmin'),
	(7, 'NGO');
/*!40000 ALTER TABLE `security_roles` ENABLE KEYS */;

-- Dumping data for table hni.security_role_permissions: ~14 rows (approximately)
DELETE FROM `security_role_permissions`;
/*!40000 ALTER TABLE `security_role_permissions` DISABLE KEYS */;
INSERT INTO `security_role_permissions` (`role_id`, `permission_id`, `all_instances`) VALUES
	(1, 1, 1),
	(2, 15, 0),
	(2, 25, 1),
	(2, 45, 1),
	(2, 65, 1),
	(2, 85, 1),
	(3, 1, 1),
	(3, 12, 0),
	(3, 67, 1),
	(4, 1, 1),
	(4, 12, 0),
	(5, 12, 0),
	(6, 1, 1),
	(7, 1, 1);
/*!40000 ALTER TABLE `security_role_permissions` ENABLE KEYS */;

-- Dumping data for table hni.sms_provider: ~2 rows (approximately)
DELETE FROM `sms_provider`;
/*!40000 ALTER TABLE `sms_provider` DISABLE KEYS */;
INSERT INTO `sms_provider` (`id`, `provider`, `long_code`, `short_code`, `state_code`, `description`, `created`) VALUES
	(1, 'TWILIO', '3143000305', NULL, 'MO', 'St Louis, MO US', '2017-05-23'),
	(2, 'TWILIO', '4582053006', NULL, 'OR', 'Pilot, Klamath Falls', '2017-06-29');
/*!40000 ALTER TABLE `sms_provider` ENABLE KEYS */;

-- Dumping data for table hni.users: ~4 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `first_name`, `last_name`, `gender_code`, `mobile_phone`, `email`, `deleted`, `hashed_secret`, `salt`, `created`, `active`, `created_by`, `updated_by`) VALUES
	(1, 'Super', 'User', 'M', 'mphone', 'superuser@hni.com', 0, '16GDSclWR0Q6Yth823pvsM8opu2apSNURdoRmuu5+4E=', '+zdIVX2RzTIQ/9hhcZpy8w==', '2017-05-07 12:58:13', 1, NULL, NULL),
	(9, 'Rahul', 'Krishna', 'M', '1232322334', 'U45914@ust-global.com', 0, '+pVg1lxXX1ey/y1SC1BtGSSSXa+WEy20mrhkjtROP2o=', 'pCUYWL9HlrbBKC/65i2UYA==', '2017-05-07 13:19:59', 1, 1, NULL),
	(10, 'Rahul', 'Krishna', 'M', '2153020127', 'Rahul.Nair@ust-global.com', 0, 'CDGr4OUr3Qt+RpNDxGdQUSRezWzb0rQsn3v2S1kZCwc=', 'zDRa9VO4FsAtCWgRiJOu9g==', '2017-05-23 17:34:51', 1, NULL, 1),
	(12, 'ENROLL', 'Rahul', NULL, '1232322334', 'rahulkrishna222@yahoo.co.in', 1, 'O5193mDDov2uZxKlmN6xbr8MwOD+Cb5824yf8kHq7us=', 'tToZVCKaInJfvUAe9kNNbw==', '2017-06-15 18:22:37', 0, NULL, 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping data for table hni.user_address: ~3 rows (approximately)
DELETE FROM `user_address`;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` (`user_id`, `address_id`) VALUES
	(1, 17),
	(9, 16),
	(10, 17);
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;

-- Dumping data for table hni.user_organization_role: ~17 rows (approximately)
DELETE FROM `user_organization_role`;
/*!40000 ALTER TABLE `user_organization_role` DISABLE KEYS */;
INSERT INTO `user_organization_role` (`user_id`, `organization_id`, `role_id`) VALUES
	(1, 2, 1),
	(2, 2, 7),
	(3, 2, 7),
	(4, 2, 7),
	(5, 2, 2),
	(5, 2, 5),
	(5, 4, 2),
	(5, 4, 5),
	(6, 2, 2),
	(6, 2, 5),
	(6, 4, 2),
	(6, 4, 5),
	(7, 3, 3),
	(8, 3, 3),
	(9, 8, 6),
	(10, 2, 4),
	(12, 1, 4);
/*!40000 ALTER TABLE `user_organization_role` ENABLE KEYS */;

-- Dumping data for table hni.user_provider_role: ~0 rows (approximately)
DELETE FROM `user_provider_role`;
/*!40000 ALTER TABLE `user_provider_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_provider_role` ENABLE KEYS */;

-- Dumping data for table hni.user_status: ~0 rows (approximately)
DELETE FROM `user_status`;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;

-- Dumping data for table hni.volunteer: ~1 rows (approximately)
DELETE FROM `volunteer`;
/*!40000 ALTER TABLE `volunteer` DISABLE KEYS */;
INSERT INTO `volunteer` (`id`, `user_id`, `created`, `created_by`, `birthday`, `sex`, `race`, `education`, `marital_status`, `income`, `kids`, `employer`, `non_profit`, `available_for_place_order`) VALUES
	(1, 10, '2017-05-23', 1, '1988-11-17', 'M', 5, 4, 1, 4, 1, 'UST', 'Y', 0);
/*!40000 ALTER TABLE `volunteer` ENABLE KEYS */;

-- Dumping data for table hni.volunteer_availability: ~1 rows (approximately)
DELETE FROM `volunteer_availability`;
/*!40000 ALTER TABLE `volunteer_availability` DISABLE KEYS */;
INSERT INTO `volunteer_availability` (`id`, `created`, `created_by`, `volunteer_id`, `timeline`, `weekday`) VALUES
	(1, '2017-06-15', 1, 1, 1, 'Monday');
/*!40000 ALTER TABLE `volunteer_availability` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

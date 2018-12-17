 SET NAMES utf8mb4 ;

--
-- Current Database: `fitness`
--

CREATE DATABASE IF NOT EXISTS `fitness_db`;

USE `fitness_db`;


DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL
);

--
-- Table structure for table `weight`
--

DROP TABLE IF EXISTS `user_day`;
CREATE TABLE `user_day` (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    `user_id` INTEGER NOT NULL,
    `weight` float NOT NULL,
    `date` date NOT NULL,
    
    FOREIGN KEY (`user_id`) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `weight`
--

/*
LOCK TABLES `user_day` WRITE;
INSERT INTO `user_day`(user_id,weight,`date`) VALUES 
(1, 97,'2018-11-22'),
(1, 96.9,'2018-11-23'),
(1, 96.3,'2018-11-24'),
(1, 96.5,'2018-11-25'),
(1, 96.9,'2018-11-26'),
(1, 97.2,'2018-11-27'),
(1, 96.3,'2018-11-28'),
(1, 96.5,'2018-11-29'),
(1, 96.5,'2018-12-03'),
(1, 96.3,'2018-12-04'),
(1, 95.7,'2018-12-05'),
(1, 96.2,'2018-12-06');
UNLOCK TABLES;
*/



--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    `name` varchar(200) NOT NULL,
    `calories` int(11) DEFAULT NULL,
    `protein` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (user_id) REFERENCES `user`(id)

) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `food`
--

/*
LOCK TABLES `food` WRITE;
INSERT INTO `food`(id,user_id,name,calories,protein) VALUES 
(1, 1,'Geishakaka 100g',554,8),
(1, 2,'Carlssons fyrkant macka',273,8),
(1, 3,'Proteinshake',146,27),
(1, 4,'Pasta Kottfarssas',735,57),
(1, 5,'Tortillas',790,55),
(1, 6,'Kvarg m fron och walnot',NULL,NULL),
(1, 7,'Kladdkaka (halv)',NULL,NULL),
(1, 8,'Yoghurt m.musli fr.butik',110,4),
(1, 9,'Julmust 1 glas',NULL,NULL),
(1, 10,'Quornwok',750,32),
(1, 11,'Random 200kcal',200,NULL),
(1, 12,'Indisk Tikka Masala',1000,20),
(1, 13,'Kyckling rodcurry',886,45),    
(1, 14,'Gravad lax m potatis',600,30),
(1, 15,'Rosmarinlax',600,29),  
(1, 16,'Idas bar',220,13),
(1, 17,'Star Nutrition bar',196,20),   
(1, 18,'Icha Kyckling Rodcurry',519,28),
(1, 19,'Kycklingwok',700,44),
(1, 20,'Kexchoklad',302,4),
(1, 21,'Kyckling med bulgur, quinoa, gronkal',653,75),
(1, 22,'Lax med risotto',715,32);
UNLOCK TABLES;
*/
--
-- Table structure for table `meal`
--

DROP TABLE IF EXISTS `meal`;
CREATE TABLE `meal` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `food` int(11) NOT NULL,
    `user_day_id` INTEGER NOT NULL,
    PRIMARY KEY (`id`),
    KEY `food` (`food`),
    FOREIGN KEY (user_day_id) REFERENCES user_day(id),
    CONSTRAINT `meal_ibfk_2` FOREIGN KEY (`food`) REFERENCES `food` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `meal`
--

/*
LOCK TABLES `meal` WRITE;
INSERT INTO `meal`(id,food,user_day_id) VALUES 
(1,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(2,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(3,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(4,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(5,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(6,4,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(7,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(8,6,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-22')),
(9,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(10,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(11,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(12,8,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(13,4,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(14,7,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(15,9,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(16,10,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-23')),
(17,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(18,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(19,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(20,4,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(21,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(22,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(23,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(24,9,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-24')),
(25,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(26,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(27,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(28,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(29,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(30,4,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(31,11,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(32,12,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-25')),
(33,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(34,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(35,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(36,6,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(37,14,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(38,8,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(39,13,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(40,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(41,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-26')),
(42,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(43,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(44,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(45,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(46,16,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(47,17,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(48,15,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(49,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(50,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-27')),
(51,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(52,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(53,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(54,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(55,17,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(56,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(57,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(58,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-28')),
(59,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(60,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(61,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(62,10,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(63,20,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(64,20,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(65,20,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(66,19,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(67,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(68,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-11-29')),
(69,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(70,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(71,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(72,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(73,6,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(74,17,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(75,21,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(76,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(77,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-03')),
(78,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(79,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(80,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(81,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(82,17,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(83,4,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(84,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(85,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-04')),
(86,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(87,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(88,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(89,4,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(90,6,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(91,17,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(92,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(93,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(94,22,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-05')),
(95,2,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(96,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(97,3,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(98,6,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(99,5,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(100,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(101,1,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06')),
(102,14,(SELECT id FROM user_day WHERE user_id = 1 AND `date` = '2018-12-06'));
UNLOCK TABLES;
*/

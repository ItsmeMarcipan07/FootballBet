-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for betting_app
CREATE DATABASE IF NOT EXISTS `betting_app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `betting_app`;

-- Dumping structure for table betting_app.bets
CREATE TABLE IF NOT EXISTS `bets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `match_id` int(11) NOT NULL,
  `bet_amount` double NOT NULL,
  `bet_type` enum('Team1','Draw','Team2') NOT NULL,
  `is_won` tinyint(1) DEFAULT 0,
  `odds` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `match_id` (`match_id`),
  CONSTRAINT `bets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `bets_ibfk_2` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table betting_app.bets: ~24 rows (approximately)
DELETE FROM `bets`;
INSERT INTO `bets` (`id`, `user_id`, `match_id`, `bet_amount`, `bet_type`, `is_won`, `odds`) VALUES
	(1, 1, 1, 10, 'Team1', 0, NULL),
	(3, 1, 1, 10, 'Team1', 0, NULL),
	(5, 1, 1, 10, 'Team1', 0, 2.5),
	(6, 1, 1, 10, 'Team2', 0, 2.8),
	(7, 1, 4, 100, 'Team1', 1, 1.1),
	(8, 1, 4, 100, 'Team1', 1, 1.1),
	(9, 1, 4, 15, 'Draw', 1, 1.1),
	(10, 1, 4, 10, 'Team1', 1, 1.1),
	(11, 1, 4, 10, 'Team1', 1, 1.1),
	(12, 1, 4, 10, 'Team1', 1, 1.1),
	(13, 1, 4, 100, 'Team1', 1, 1.1),
	(14, 1, 4, 100, 'Team1', 1, 1.1),
	(15, 1, 4, 10, 'Team2', 1, 1.1),
	(16, 1, 4, 100, 'Team2', 1, 1.1),
	(17, 1, 4, 100, 'Draw', 1, 1.1),
	(18, 1, 4, 100, 'Team1', 1, 1.1),
	(19, 1, 4, 100, 'Team1', 1, 1.1),
	(20, 1, 4, 100, 'Team1', 1, 1.1),
	(21, 1, 4, 100, 'Team1', 1, 1.1),
	(22, 1, 4, 1, 'Team1', 1, 1.1),
	(23, 1, 4, 100, 'Team1', 1, 1.1),
	(24, 1, 4, 100, 'Team1', 1, 1.1),
	(25, 1, 4, 1, 'Team1', 1, 1.1),
	(26, 1, 2, 100, 'Team1', 1, 1.8);

-- Dumping structure for table betting_app.matches
CREATE TABLE IF NOT EXISTS `matches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team1` varchar(50) NOT NULL,
  `team2` varchar(50) NOT NULL,
  `coef_team1` double NOT NULL,
  `coef_draw` double NOT NULL,
  `coef_team2` double NOT NULL,
  `result` enum('Team1','Draw','Team2') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table betting_app.matches: ~3 rows (approximately)
DELETE FROM `matches`;
INSERT INTO `matches` (`id`, `team1`, `team2`, `coef_team1`, `coef_draw`, `coef_team2`, `result`) VALUES
	(1, 'Team A', 'Team B', 2.5, 3, 2.8, NULL),
	(2, 'Team C', 'Team D', 1.8, 3.5, 4, 'Team2'),
	(3, 'Team E', 'Team F', 2.2, 3.2, 3, NULL),
	(4, 'Levski', 'CSKA', 1.1, 1.1, 1.1, 'Team1');

-- Dumping structure for table betting_app.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `balance` double NOT NULL DEFAULT 100,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table betting_app.users: ~6 rows (approximately)
DELETE FROM `users`;
INSERT INTO `users` (`id`, `username`, `password`, `balance`, `email`) VALUES
	(1, 'admin', 'admin', 97343, NULL),
	(2, 'test', 'test', 100, 'test'),
	(3, 'test1', 'test1', 100, 'test1'),
	(4, 'test2', 'test2', 100, 'test2'),
	(6, 'test5', '', 100, ''),
	(7, 'test7', 'test7', 100, 'test7'),
	(8, 'sadfadsfd', 'asdfasdsa', 0, 'sdfasd');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

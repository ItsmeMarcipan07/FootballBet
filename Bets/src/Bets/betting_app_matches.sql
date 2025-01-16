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

-- Dumping structure for table betting_app.matches
CREATE TABLE IF NOT EXISTS `matches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team1` varchar(100) NOT NULL,
  `team2` varchar(100) NOT NULL,
  `odds_team1` double NOT NULL,
  `odds_draw` double NOT NULL,
  `odds_team2` double NOT NULL,
  `result` enum('Team1','Draw','Team2') DEFAULT NULL,
  `status` enum('Active','Concluded') NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table betting_app.matches: ~4 rows (approximately)
INSERT INTO `matches` (`id`, `team1`, `team2`, `odds_team1`, `odds_draw`, `odds_team2`, `result`, `status`) VALUES
	(1, 'Levski', 'CSKA', 2.17, 3.05, 2.05, 'Team2', 'Active'),
	(2, 'TeamA', 'TeamB', 1.1, 1.1, 1.1, NULL, 'Active'),
	(3, 'TeamC', 'TeamD', 1.1, 1.1, 1.1, NULL, 'Active'),
	(4, 'TeamE', 'TeamF', 1.1, 1.1, 1.1, NULL, 'Active');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

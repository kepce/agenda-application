-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: tasks
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `tasks`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `tasks` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `tasks`;

--
-- Table structure for table `task_table`
--

DROP TABLE IF EXISTS `task_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `task_table` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_creation_date` varchar(15) NOT NULL,
  `task_name` varchar(35) NOT NULL,
  `task_description` varchar(300) NOT NULL,
  `task_status` varchar(5) NOT NULL,
  `task_due_date` varchar(50) NOT NULL,
  `task_repetition` int(11) NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_table`
--

LOCK TABLES `task_table` WRITE;
/*!40000 ALTER TABLE `task_table` DISABLE KEYS */;
INSERT INTO `task_table` VALUES (1,'29 / 04 / 2019','Management Meeting','Will start at 11am. Important subjects to discuss.','false','30 / 04 / 2019',0),(2,'30 / 04 / 2019','Weekly Maintenance','Periodic maintenance of technical equipments.','false','01 / 01 / 2020',2),(3,'30 / 04 / 2019','Weekly Maintenance','Periodic maintenance of technical equipments.','false','01 / 01 / 2020',0),(4,'30 / 04 / 2019','Prepare Daily Plan','To-do list for the day.','false','01 / 01 / 2020',1),(5,'30 / 04 / 2019','Prepare Daily Plan','To-do list for the day.','false','01 / 01 / 2020',0),(6,'30 / 04 / 2019','Management Meeting','Important.','false','01 / 05 / 2019',0),(7,'30 / 04 / 2019','Prepare Feedback Form','Feedback form to be prepared.','false','04 / 05 / 2019',0),(8,'01 / 05 / 2019','Prepare Daily Plan','To-do list for the day.','false','01 / 01 / 2020',0),(9,'02 / 05 / 2019','Prepare Daily Plan','To-do list for the day.','false','01 / 01 / 2020',0);
/*!40000 ALTER TABLE `task_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-29 12:24:18

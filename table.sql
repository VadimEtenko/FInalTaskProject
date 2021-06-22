/*
SQLyog Ultimate v12.2.6 (64 bit)
MySQL - 8.0.25 : Database - hotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hotel` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `hotel`;

/*Table structure for table `booked_rooms` */

DROP TABLE IF EXISTS `booked_rooms`;

CREATE TABLE `booked_rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `status_id` int NOT NULL,
  `time_in` date NOT NULL,
  `time_out` date NOT NULL,
  `time_creating` date NOT NULL,
  `is_paid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `room_id` (`room_id`),
  KEY `user_id` (`user_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `booked_rooms_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `hotel_rooms` (`id`),
  CONSTRAINT `booked_rooms_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `booked_rooms_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `status_of_room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `booked_rooms` */

/*Table structure for table `class_of_room` */

DROP TABLE IF EXISTS `class_of_room`;

CREATE TABLE `class_of_room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `class` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `class_of_room` */

insert  into `class_of_room`(`id`,`class`) values 
(1,'Junior Suite'),
(2,'Suite'),
(3,'De Luxe'),
(4,'Duplex');

/*Table structure for table `hotel_rooms` */

DROP TABLE IF EXISTS `hotel_rooms`;

CREATE TABLE `hotel_rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `class_id` int NOT NULL,
  `number_of_beds` int NOT NULL,
  `cost` double(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `hotel_rooms_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class_of_room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hotel_rooms` */

insert  into `hotel_rooms`(`id`,`number`,`class_id`,`number_of_beds`,`cost`) values 
(1,1,1,1,500.00),
(2,2,1,2,750.00),
(3,3,1,3,1000.00),
(4,4,1,4,1250.00),
(5,5,2,1,1500.00),
(6,6,2,2,1750.00),
(7,7,2,3,2000.00),
(8,8,2,4,2250.00),
(9,9,3,1,2500.00),
(10,10,3,2,2750.00),
(11,11,3,3,3000.00),
(12,12,3,4,3250.00),
(13,13,4,1,3500.00),
(14,14,4,2,3750.00),
(15,15,4,3,4000.00),
(16,16,4,4,4250.00);

/*Table structure for table `offers` */

DROP TABLE IF EXISTS `offers`;

CREATE TABLE `offers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `room_id` int NOT NULL,
  `time_in` date NOT NULL,
  `time_out` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `room_id` (`room_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `hotel_rooms` (`id`),
  CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `offers` */

/*Table structure for table `request_wish` */

DROP TABLE IF EXISTS `request_wish`;

CREATE TABLE `request_wish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `class_id` int NOT NULL,
  `number_of_beds` int NOT NULL,
  `time_in` date NOT NULL,
  `time_out` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `class_id` (`class_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `request_wish_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class_of_room` (`id`),
  CONSTRAINT `request_wish_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `request_wish` */

/*Table structure for table `requested_rooms` */

DROP TABLE IF EXISTS `requested_rooms`;

CREATE TABLE `requested_rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `room_id` int NOT NULL,
  `time_in` date NOT NULL,
  `time_out` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `requested_rooms_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `requested_rooms_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `hotel_rooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `requested_rooms` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `roles` */

insert  into `roles`(`id`,`role_name`) values 
(0,'user'),
(1,'manager');

/*Table structure for table `status_of_room` */

DROP TABLE IF EXISTS `status_of_room`;

CREATE TABLE `status_of_room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `status_of_room` */

insert  into `status_of_room`(`id`,`status`) values 
(0,'Вільний'),
(1,'Заброньований'),
(2,'Зайнятий'),
(3,'Недоступний');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `surname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` int NOT NULL,
  `local` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`name`,`surname`,`login`,`password`,`email`,`role_id`,`local`) values 
(0,'Ivan','Ivanov','Ivan228','password','email@mail.com',1,'ru'),
(2,'Fidele','Hooff','F_H','1234','em@mail.com',0,'ru'),
(3,'Jim','Johns','JJ','4321','@gmail.com',0,'ru'),
(54,'Никиточка','ЛиХоЛат','Zikkurat2015','fgkvtsi','putana@gmail.com',0,'ru'),
(60,'Даниил','Чесноков','Chesk','qwert','tret@mfai.com',0,'ru');

/*Table structure for table `users_notifications` */

DROP TABLE IF EXISTS `users_notifications`;

CREATE TABLE `users_notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `booked_id` int DEFAULT NULL,
  `text` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `booked_id` (`booked_id`),
  CONSTRAINT `users_notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `users_notifications_ibfk_2` FOREIGN KEY (`booked_id`) REFERENCES `booked_rooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users_notifications` */

/* Trigger structure for table `booked_rooms` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `isFreeBeforeInsert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `isFreeBeforeInsert` BEFORE INSERT ON `booked_rooms` FOR EACH ROW BEGIN
	  IF (NEW.status_id = 0) THEN
		SET NEW.status_id = NULL;
	  END IF;	  
    END */$$


DELIMITER ;

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `delete_unpaied` */

/*!50106 DROP EVENT IF EXISTS `delete_unpaied`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `delete_unpaied` ON SCHEDULE EVERY 1 DAY STARTS '2021-06-22 20:48:55' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	DELETE FROM users_notifications
	WHERE booked_id IN
		(SELECT id FROM booked_rooms WHERE booked_rooms.is_paid = FALSE
		AND
		DATEDIFF(CURRENT_DATE, booked_rooms.time_creating) >= 2);
		
	DELETE FROM booked_rooms WHERE booked_rooms.is_paid = FALSE
	AND
	DATEDIFF(CURRENT_DATE, booked_rooms.time_creating) >= 2;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

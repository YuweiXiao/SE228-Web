/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-03-27 10:24:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `bookID` int(255) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(255) NOT NULL,
  `title` varchar(20) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `press` varchar(20) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `inventory` int(255) DEFAULT NULL,
  `sales` int(255) DEFAULT NULL,
  PRIMARY KEY (`bookID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES ('1', '1234567', 'ICS3', '5050', 'Edu.', 'lisi', '100', '0');
INSERT INTO `books` VALUES ('2', '882343', 'DB2', '9999', 'Edu.', 'zhangsan', '291', '9');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `orderTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `isDeal` char(1) NOT NULL,
  PRIMARY KEY (`orderID`),
  KEY `user` (`userID`),
  CONSTRAINT `user` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '1', '2016-03-27 10:24:25', '0');

-- ----------------------------
-- Table structure for ordersbook
-- ----------------------------
DROP TABLE IF EXISTS `ordersbook`;
CREATE TABLE `ordersbook` (
  `ordersBookID` int(255) NOT NULL AUTO_INCREMENT,
  `orderID` int(11) NOT NULL,
  `bookID` int(255) DEFAULT NULL,
  `amount` int(255) DEFAULT '1',
  PRIMARY KEY (`ordersBookID`),
  KEY `books` (`bookID`),
  KEY `order` (`orderID`),
  CONSTRAINT `books` FOREIGN KEY (`bookID`) REFERENCES `books` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ordersbook
-- ----------------------------
INSERT INTO `ordersbook` VALUES ('1', '1', '1', '2');
INSERT INTO `ordersbook` VALUES ('2', '1', '2', '4');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', 'admin');
INSERT INTO `role` VALUES ('1', 'user');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `roleID` int(255) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `e_mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `role` (`roleID`),
  CONSTRAINT `role` FOREIGN KEY (`roleID`) REFERENCES `role` (`roleID`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'zhangsan', '123456', '0', '张三341', '1', 'zhangsan@126.com');
INSERT INTO `users` VALUES ('7', 'admin', '123456', '0', 'Admin', '0', 'admin@126.com');

/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : ui_image

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 15/06/2018 15:25:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` text,
  `sourceCategory` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `targetCategory` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sourceCategoryAndSourceUnique` (`sourceCategory`,`source`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of action
-- ----------------------------
BEGIN;
INSERT INTO `action` VALUES (22, 'delete', 'FillupCreditCard', 'pop_bind_credit_card', NULL, NULL);
INSERT INTO `action` VALUES (23, 'delete', 'FillupCreditCard', 'bind_credit_card_top_tips', NULL, NULL);
INSERT INTO `action` VALUES (24, 'move', 'FillupCreditCard', 'bind_credit_card_close', 'CusServiceImages', NULL);
INSERT INTO `action` VALUES (25, 'move', 'HotLine', 'special_car', 'Step4', NULL);
INSERT INTO `action` VALUES (26, 'delete', 'HotLine', 'long_Service', NULL, NULL);
INSERT INTO `action` VALUES (27, 'delete', 'Step4', 'driving_license_example', NULL, NULL);
INSERT INTO `action` VALUES (28, 'delete', 'Step4', 'icon_process_gray', NULL, NULL);
INSERT INTO `action` VALUES (29, 'delete', '2.0首页', '首页_抢单_灰_icon', NULL, NULL);
INSERT INTO `action` VALUES (30, 'delete', '2.0首页', '通知', NULL, NULL);
INSERT INTO `action` VALUES (31, 'delete', '2.0首页', '首页_费用管理_icon', NULL, NULL);
INSERT INTO `action` VALUES (32, 'delete', '2.0首页', '首页-banner', NULL, NULL);
INSERT INTO `action` VALUES (33, 'delete', '2.0首页', '首页_公司信息_icon', NULL, NULL);
INSERT INTO `action` VALUES (34, 'delete', '2.0首页', '首页_车辆管理_icon', NULL, NULL);
INSERT INTO `action` VALUES (35, 'delete', '2.0首页', '首页_我的订单_icon', NULL, NULL);
INSERT INTO `action` VALUES (36, 'delete', '2.0首页', '首页_长包业务_icon', NULL, NULL);
INSERT INTO `action` VALUES (37, 'delete', '2.0首页', '首页_个人信息_icon', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `remoteAddress` text,
  `branch` text,
  `gitName` text,
  `gitPass` text,
  `resPath` text,
  `projectType` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of project
-- ----------------------------
BEGIN;
INSERT INTO `project` VALUES (5, 'EHaiUI', 'http://192.168.1.15:8080/tfs/Mobile.Dev/Android/_git/EHaiUI', 'master', 'rjh3xrlsmpbjvbklzowpizarts5qijwhihrysyieotfcoq4j6bpq', 'rjh3xrlsmpbjvbklzowpizarts5qijwhihrysyieotfcoq4j6bpq', 'ehaiUI/src/main/res', 'Android');
INSERT INTO `project` VALUES (6, '司机PAD Android', 'http://192.168.1.15:8080/tfs/Mobile.Dev/Android/_git/DriverAndroid', 'develop', 'i3htgbyqirfz7qeoxmxx4hcjwrkqq6lkopaphvx5l75u5swsb23a', 'i3htgbyqirfz7qeoxmxx4hcjwrkqq6lkopaphvx5l75u5swsb23a', 'EhaiPadClient/src/main/res', 'Android');
INSERT INTO `project` VALUES (7, '一嗨租车 Android', 'http://192.168.1.15:8080/tfs/Mobile.Dev/Android/_git/EhaiCarService', 'dev', 'rjh3xrlsmpbjvbklzowpizarts5qijwhihrysyieotfcoq4j6bpq', 'rjh3xrlsmpbjvbklzowpizarts5qijwhihrysyieotfcoq4j6bpq', 'app/src/main/res', 'Android');
INSERT INTO `project` VALUES (8, '一嗨租车 IOS', 'http://192.168.1.15:8080/tfs/Mobile.Dev/IOS/_git/SelfDrivingIOS', 'master', 'igh3ykjqhdkoemyaifewcz3njwfnllmzeqghfewqawxazg6qu4pa', 'igh3ykjqhdkoemyaifewcz3njwfnllmzeqghfewqawxazg6qu4pa', '1haiiPhone/Images.xcassets', 'IOS');
INSERT INTO `project` VALUES (9, '门店PAD Android', 'http://192.168.1.15:8080/tfs/Mobile.Dev/Android/_git/EhaiStorePad', 'dev', '6qjkkkql2yzs3ysveym4gva2n253eo7ehj6qdlw3uxluy4alhgla', '6qjkkkql2yzs3ysveym4gva2n253eo7ehj6qdlw3uxluy4alhgla', 'EhaiStoreSystemPad/src/main/res', 'Android');
INSERT INTO `project` VALUES (10, '司机PAD IOS', 'http://192.168.1.15:8080/tfs/Mobile.Dev/IOS/_git/DriverPadNew', 'Develop', 'ud65d6oyvqytzoobhbdatdospqzbgm5nlqa2p434tn2uz2l3pbza', 'ud65d6oyvqytzoobhbdatdospqzbgm5nlqa2p434tn2uz2l3pbza', 'driver/Images.xcassets;driver/Launcher.xcassets', 'IOS');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `pass` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'xiaojinzi', 'xiaojinzi');
INSERT INTO `user` VALUES (2, '小金子', 'xiaojinzi');
INSERT INTO `user` VALUES (3, 'xiaojinzi2', 'xiaojinzi');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

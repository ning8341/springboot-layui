/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50561
Source Host           : localhost:3306
Source Database       : bookkeeping

Target Server Type    : MYSQL
Target Server Version : 50561
File Encoding         : 65001

Date: 2020-07-22 17:50:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` varchar(64) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `pid` varchar(64) DEFAULT NULL COMMENT '父菜单id',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常  1删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '系统设置', '0', '系统管理', '', '1', '0', null, null, '2020-07-22 10:33:31', '1');
INSERT INTO `t_permission` VALUES ('2', '账号管理', '1', '账号管理', '/user/userList', '1', '0', null, null, '2020-07-22 10:34:50', '1');
INSERT INTO `t_permission` VALUES ('3', '角色管理', '1', '角色管理', '/role/roleList', '2', '0', null, null, '2020-07-22 10:34:57', '1');
INSERT INTO `t_permission` VALUES ('7', '菜单管理', '1', '权限管理', '/permission/permissionList', '3', '0', null, null, '2020-07-22 10:34:43', '1');
INSERT INTO `t_permission` VALUES ('8736f52c1a084afd93a4f67c4ecb353f', '商品录入', 'd8c9be87143d4ef2989dea2b37d62eea', '录入商品', '/goods/goodsList', '1', '1', '2020-07-22 10:36:26', '1', '2020-07-22 11:12:18', '1');
INSERT INTO `t_permission` VALUES ('9', '账目管理', '0', '账目的管理', '', '3', '0', null, null, '2020-07-22 10:34:16', '1');
INSERT INTO `t_permission` VALUES ('d69e44d74bde4b5d9b9c0c67ee534f98', '台账', '9', '以月为单位的台账', '/standing/standingList', null, '0', '2020-07-21 09:37:49', '1', '2020-07-22 09:04:31', '1');
INSERT INTO `t_permission` VALUES ('d8c9be87143d4ef2989dea2b37d62eea', '商品管理', '0', '商品的管理模块', '', '2', '1', null, '1', '2020-07-22 11:12:18', '1');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(64) NOT NULL COMMENT '权限角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `permissions` varchar(255) DEFAULT NULL COMMENT '权限',
  `role_status` int(1) NOT NULL DEFAULT '1' COMMENT '1：有效 \r\n            0：无效',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常  1删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '系统管理员', '系统管理员', 'd8c9be87143d4ef2989dea2b37d62eea,1,9', '1', '0', '2020-07-16 15:32:43', null, '2020-07-22 09:52:30', '1');
INSERT INTO `t_role` VALUES ('1a3cc81b93ff41a492428aead161ef59', 'dsdf', 'asdfdsafs', '9', '1', '1', '2020-07-07 16:11:40', '1', '2020-07-17 08:54:06', '1');
INSERT INTO `t_role` VALUES ('2', '普通管理员', '普通管理员', '9', '1', '0', '2020-07-07 15:32:43', null, '2020-07-17 10:13:58', '1');
INSERT INTO `t_role` VALUES ('f4669812f5e54e5e8e9f2e75d853eebb', 'ss', 'ss', '1,9', '0', '1', '2020-07-07 15:49:00', '1', '2020-07-17 08:54:06', '1');

-- ----------------------------
-- Table structure for t_standing
-- ----------------------------
DROP TABLE IF EXISTS `t_standing`;
CREATE TABLE `t_standing` (
  `id` varchar(64) NOT NULL,
  `real_in` double(255,0) DEFAULT NULL,
  `real_out` double(255,0) DEFAULT NULL,
  `total` double(50,0) DEFAULT NULL COMMENT '总计（含外卖）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `bill_time` datetime DEFAULT NULL COMMENT '记账日期',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常  1删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='台账表';

-- ----------------------------
-- Records of t_standing
-- ----------------------------
INSERT INTO `t_standing` VALUES ('035912e9-a25e-44ac-87e7-b791ba7abe5b', '5', null, '5', '', '2020-05-01 00:00:00', '0', '2020-07-22 17:19:40', '1', null, null);
INSERT INTO `t_standing` VALUES ('2d279889-2636-4029-a397-bb06517d2639', '100', null, '100', '', '2020-07-01 00:00:00', '0', '2020-07-22 16:58:25', '1', null, null);
INSERT INTO `t_standing` VALUES ('755bd1ab-222e-4f40-8edb-4479da472716', '1', null, '1', '', '2020-06-01 00:00:00', '0', '2020-07-22 17:02:15', '1', null, null);
INSERT INTO `t_standing` VALUES ('7c803ec1-1c9f-4068-9950-5e17ad3286f4', '2', null, '2', '', '2020-07-02 00:00:00', '0', '2020-07-22 16:58:42', '1', null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '系统用户名称',
  `pwd` varchar(250) DEFAULT NULL COMMENT '系统用户密码',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0：无效；1：有效）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常  1删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理员帐号';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', '13411182215', '1', '密码admin', '0', '2020-06-28 17:36:29', null, '2020-07-17 10:54:53', '1');
INSERT INTO `t_user` VALUES ('17dfc77e1b2644f5b78f9451aa52ec5a', 'dongwn', 'e10adc3949ba59abbe56e057f20f883e', '2', '15076500954', '1', '密码123456', '0', '2020-07-06 15:58:02', '1', '2020-07-06 17:47:39', '1');
INSERT INTO `t_user` VALUES ('a7f918a933534ad3a8706cce945976e8', 'xxxxx', 'e10adc3949ba59abbe56e057f20f883e', '1', '1111111', '0', null, '0', '2020-07-07 11:27:18', '1', '2020-07-07 11:39:01', '1');
INSERT INTO `t_user` VALUES ('aef95d889e6b44e9a2019644a727e4c3', 'ss', 'c485a54780a4c22a5335672b7d655287', '1', '12344444444', '0', null, '0', '2020-07-07 11:36:28', '1', '2020-07-07 11:39:01', '1');

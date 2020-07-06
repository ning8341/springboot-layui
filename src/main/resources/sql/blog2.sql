/*
Navicat MySQL Data Transfer

Source Server         : dongwn---127.0.0.1
Source Server Version : 50561
Source Host           : localhost:3306
Source Database       : blog2

Target Server Type    : MYSQL
Target Server Version : 50561
File Encoding         : 65001

Date: 2020-06-28 18:10:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` varchar(64) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `status` varchar(255) DEFAULT NULL COMMENT '发送状态  0 未发送  1发送',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常  1删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES ('1', '我是文章1', '我是文章content', null, '1', '2020-06-24 15:49:57', null, null, null);

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
INSERT INTO `t_permission` VALUES ('1', '系统管理', '0', '系统管理', '', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('18', '文章管理', '9', '文章管理', '/article/articleManage', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('2', '账号管理', '1', '账号管理', '/user/userList', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('3', '角色管理', '1', '角色管理', '/role/roleManage', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('7', '权限管理', '1', '权限管理', '/permission/permissionManage', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('9', '博客管理', '0', '博客的管理', '', '0', null, null, null, null);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(64) NOT NULL COMMENT '权限角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `permissions` varchar(20) DEFAULT NULL COMMENT '权限',
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
INSERT INTO `t_role` VALUES ('1', '系统管理员', '系统管理员', '1,9', '1', '0', null, null, null, null);
INSERT INTO `t_role` VALUES ('2', '普通管理员', '普通管理员', '9', '1', '0', null, null, null, null);

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
INSERT INTO `t_user` VALUES ('1', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', '1', '13411182215', '1', '密码admin', '0', '2020-06-28 17:36:29', null, null, null);
INSERT INTO `t_user` VALUES ('ebf3c881f2f6425583b67a94dc9d826a', 'xxx', '', '2', '1', '0', null, '0', '2020-06-28 17:36:33', null, '2020-06-28 18:03:55', '1');

/*
Navicat MySQL Data Transfer

Source Server         : dongwn---127.0.0.1
Source Server Version : 50561
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50561
File Encoding         : 65001

Date: 2020-06-25 17:55:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_permission`;
CREATE TABLE `base_admin_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单id',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `create_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `update_time` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) DEFAULT NULL COMMENT '删除标志（0:删除 1：存在）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of base_admin_permission
-- ----------------------------
INSERT INTO `base_admin_permission` VALUES ('1', '系统管理', '0', '系统管理', '', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');
INSERT INTO `base_admin_permission` VALUES ('2', '账号管理', '1', '账号管理', '/user/userManage', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');
INSERT INTO `base_admin_permission` VALUES ('3', '角色管理', '1', '角色管理', '/role/roleManage', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');
INSERT INTO `base_admin_permission` VALUES ('7', '权限管理', '1', '权限管理', '/permission/permissionManage', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');
INSERT INTO `base_admin_permission` VALUES ('9', '博客管理', '0', '博客的管理', '', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');
INSERT INTO `base_admin_permission` VALUES ('18', '文章管理', '9', '文章管理', '/article/articleManage', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');

-- ----------------------------
-- Table structure for base_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_role`;
CREATE TABLE `base_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `permissions` varchar(20) DEFAULT NULL COMMENT '权限',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `role_status` int(1) NOT NULL DEFAULT '1' COMMENT '1：有效 \r\n            0：无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户角色表';

-- ----------------------------
-- Records of base_admin_role
-- ----------------------------
INSERT INTO `base_admin_role` VALUES ('1', '系统管理员', '系统管理员', '1,9', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');
INSERT INTO `base_admin_role` VALUES ('2', '普通管理员', '普通管理员', '9', '2020-06-24 15:49:57', '2020-06-24 15:49:57', '1');

-- ----------------------------
-- Table structure for base_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_user`;
CREATE TABLE `base_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sys_user_name` varchar(50) NOT NULL COMMENT '系统用户名称',
  `sys_user_pwd` varchar(250) DEFAULT NULL COMMENT '系统用户密码',
  `role_id` int(255) DEFAULT NULL COMMENT '角色',
  `user_phone` varchar(11) NOT NULL COMMENT '手机号',
  `reg_time` varchar(255) DEFAULT NULL COMMENT '登记时间',
  `user_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0：无效；1：有效）',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统管理员帐号';

-- ----------------------------
-- Records of base_admin_user
-- ----------------------------
INSERT INTO `base_admin_user` VALUES ('1', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', '1', '13411182215', '2020-06-24 15:49:57', '1', '密码admin');

-- ----------------------------
-- Table structure for base_article
-- ----------------------------
DROP TABLE IF EXISTS `base_article`;
CREATE TABLE `base_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `delFlag` int(1) DEFAULT NULL COMMENT '删除标志（0:删除 1：存在）',
  `createTime` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_article
-- ----------------------------
INSERT INTO `base_article` VALUES ('1', '我是文章1', '我是文章content', '1', '2020-06-24 15:49:57');

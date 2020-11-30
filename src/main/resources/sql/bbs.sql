/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50561
Source Host           : localhost:3306
Source Database       : bbs

Target Server Type    : MYSQL
Target Server Version : 50561
File Encoding         : 65001

Date: 2020-11-30 16:37:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单标题',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `menu_type` int(11) DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
  `perms` varchar(255) DEFAULT NULL COMMENT '菜单权限编码',
  `sort_no` int(10) DEFAULT NULL COMMENT '菜单排序',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除状态 0正常 1已删除',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_prem_pid` (`parent_id`) USING BTREE,
  KEY `index_prem_sort_no` (`sort_no`) USING BTREE,
  KEY `index_prem_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '0', '新增用户', '/user/add', '2', 'user:add', '1', '0', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('2', '0', '删除用户', '/user/delete', '2', 'user:delete', '2', '0', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('3', '0', '修改用户', '/user/update', '2', 'user:update', '3', '0', '0', null, null, null, null);
INSERT INTO `t_permission` VALUES ('4', '0', '查询用户', '/user/list', '2', 'user:list', '4', '0', '0', null, null, null, null);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '管理角色', '0', null, null, null, null);
INSERT INTO `t_role` VALUES ('2', 'test', '测试角色', '0', null, null, null, null);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_group_role_per_id` (`role_id`,`permission_id`) USING BTREE,
  KEY `index_group_role_id` (`role_id`) USING BTREE,
  KEY `index_group_per_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '1', '0', null, null, null, null);
INSERT INTO `t_role_permission` VALUES ('2', '1', '2', '0', null, null, null, null);
INSERT INTO `t_role_permission` VALUES ('3', '1', '3', '0', null, null, null, null);
INSERT INTO `t_role_permission` VALUES ('4', '1', '4', '0', null, null, null, null);
INSERT INTO `t_role_permission` VALUES ('5', '2', '4', '0', null, null, null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '登录账号',
  `real_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(255) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL COMMENT '0注册会员  1 中级会员  2高级会员  3钻石会员 4 超级会员',
  `status` tinyint(1) DEFAULT NULL COMMENT '0正常 1冻结',
  `sex` tinyint(1) DEFAULT NULL COMMENT '0女 1男',
  `pass_word` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT 'md5密码盐',
  `login_at` datetime DEFAULT NULL COMMENT '最新的登录时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_user_name` (`user_name`) USING BTREE,
  KEY `index_user_del_flag` (`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '超级管理员', 'test@126.com', '4', '0', '1', 'cb362cfeefbf3d8d', 'RCGTeGiH', '2020-11-30 15:16:26', '0', '1', '2020-11-30 14:22:28', null, null);
INSERT INTO `t_user` VALUES ('2', 'test', '测试用户', 'testxx@126.com', '1', '0', '1', '471439d316637955', '5FMD48RM', '2020-11-30 15:16:30', '0', '1', '2020-11-30 14:22:32', null, null);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态(0-正常,1-已删除)',
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index2_groupuu_user_id` (`user_id`) USING BTREE,
  KEY `index2_groupuu_ole_id` (`role_id`) USING BTREE,
  KEY `index2_groupuu_useridandroleid` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1', '0', null, null, null, null);
INSERT INTO `t_user_role` VALUES ('2', '2', '2', '0', null, null, null, null);

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50152
Source Host           : localhost:3306
Source Database       : anynote

Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001

Date: 2011-06-17 11:16:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `an_account`
-- ----------------------------
DROP TABLE IF EXISTS `an_account`;
CREATE TABLE `an_account` (
  `ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账目ID',
  `ACCOUNT_BOOK_ID` int(11) NOT NULL COMMENT '账本ID',
  `CATEGORY_ID` int(11) NOT NULL DEFAULT '0' COMMENT '账目分类ID',
  `ACCOUNT_DATE` date NOT NULL DEFAULT '0000-00-00' COMMENT '收支日期',
  `ACCOUNT_TYPE` char(1) NOT NULL DEFAULT '' COMMENT '收支类型',
  `MONEY` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '收支金额',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态 1：公开 2：私人',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`ACCOUNT_ID`),
  KEY `FK_ACCOUNT2BOOK` (`ACCOUNT_BOOK_ID`),
  KEY `FK_ACCOUNT2CATEGORY` (`CATEGORY_ID`),
  CONSTRAINT `FK_ACCOUNT2BOOK` FOREIGN KEY (`ACCOUNT_BOOK_ID`) REFERENCES `an_account_book` (`ACCOUNT_BOOK_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ACCOUNT2CATEGORY` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `an_account_category` (`CATEGORY_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账目';

-- ----------------------------
-- Records of an_account
-- ----------------------------

-- ----------------------------
-- Table structure for `an_account_book`
-- ----------------------------
DROP TABLE IF EXISTS `an_account_book`;
CREATE TABLE `an_account_book` (
  `ACCOUNT_BOOK_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账本ID',
  `ACCOUNT_BOOK_NAME` varchar(20) NOT NULL DEFAULT '0' COMMENT '账本名称',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态 1：公开 2：私人',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`ACCOUNT_BOOK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账本';

-- ----------------------------
-- Records of an_account_book
-- ----------------------------

-- ----------------------------
-- Table structure for `an_account_category`
-- ----------------------------
DROP TABLE IF EXISTS `an_account_category`;
CREATE TABLE `an_account_category` (
  `CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '目账分类ID',
  `CATEGORY_NAME` varchar(20) NOT NULL DEFAULT '' COMMENT '目账分类名称',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收支项目';

-- ----------------------------
-- Records of an_account_category
-- ----------------------------

-- ----------------------------
-- Table structure for `an_album`
-- ----------------------------
DROP TABLE IF EXISTS `an_album`;
CREATE TABLE `an_album` (
  `ALBUM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '相册ID',
  `ALBUM_NAME` varchar(20) NOT NULL COMMENT '相册名称',
  `PARENT_ID` int(11) NOT NULL COMMENT '父节点ID',
  `ISLEAF` char(1) NOT NULL COMMENT '是否为叶子节点 0:否,1:是',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ALBUM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='相册';

-- ----------------------------
-- Records of an_album
-- ----------------------------

-- ----------------------------
-- Table structure for `an_document`
-- ----------------------------
DROP TABLE IF EXISTS `an_document`;
CREATE TABLE `an_document` (
  `DOCUMENT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文档ID',
  `DOCUMENT_NAME` varchar(255) NOT NULL COMMENT '文档名称',
  `LINK` varchar(200) DEFAULT NULL COMMENT '文档链接',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '文档类型',
  `SIZE` int(11) DEFAULT NULL COMMENT '文档大小',
  `TAGS` varchar(255) DEFAULT NULL COMMENT '文档标签',
  `PARENT_ID` int(11) NOT NULL COMMENT '父节点ID',
  `ISLEAF` char(1) NOT NULL COMMENT '是否为叶子节点 0:否,1:是',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`DOCUMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档管理';

-- ----------------------------
-- Records of an_document
-- ----------------------------

-- ----------------------------
-- Table structure for `an_feed`
-- ----------------------------
DROP TABLE IF EXISTS `an_feed`;
CREATE TABLE `an_feed` (
  `FEED_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '订阅源ID',
  `FEED_NAME` varchar(255) NOT NULL COMMENT '订阅源名称',
  `FEED_URL` varchar(255) DEFAULT NULL COMMENT '订阅源URL',
  `FEED_COUNT` int(11) NOT NULL DEFAULT '0',
  `PARENT_ID` int(11) NOT NULL COMMENT '父节点ID',
  `ISLEAF` char(1) NOT NULL COMMENT '是否为叶子节点 0:否,1:是',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`FEED_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订阅源';

-- ----------------------------
-- Records of an_feed
-- ----------------------------

-- ----------------------------
-- Table structure for `an_feed_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `an_feed_favorite`;
CREATE TABLE `an_feed_favorite` (
  `FEED_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '订阅ID',
  `TITLE` varchar(200) DEFAULT '' COMMENT '标题',
  `LINK` varchar(200) DEFAULT NULL COMMENT '链接',
  `DESCRIPTION` longtext COMMENT '摘要',
  `UPDATED` datetime DEFAULT NULL COMMENT '订阅时间',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态 1：公开 2：私人',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Fag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`FEED_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订阅收藏';

-- ----------------------------
-- Records of an_feed_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for `an_note`
-- ----------------------------
DROP TABLE IF EXISTS `an_note`;
CREATE TABLE `an_note` (
  `NOTE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '笔记ID',
  `CATEGORY_ID` int(11) NOT NULL DEFAULT '0' COMMENT '分类ID',
  `TITLE` varchar(100) NOT NULL DEFAULT '' COMMENT '标题',
  `CONTENT` longtext NOT NULL COMMENT '内容',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态 1：公开 2：私人',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Fag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`NOTE_ID`),
  KEY `FK_NOTE2CATEGORY` (`CATEGORY_ID`),
  CONSTRAINT `FK_NOTE2CATEGORY` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `an_note_category` (`CATEGORY_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='笔记';

-- ----------------------------
-- Records of an_note
-- ----------------------------

-- ----------------------------
-- Table structure for `an_note_category`
-- ----------------------------
DROP TABLE IF EXISTS `an_note_category`;
CREATE TABLE `an_note_category` (
  `CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `CATEGORY_NAME` varchar(20) NOT NULL DEFAULT '' COMMENT '分类名称',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='笔记分类';

-- ----------------------------
-- Records of an_note_category
-- ----------------------------

-- ----------------------------
-- Table structure for `an_picture`
-- ----------------------------
DROP TABLE IF EXISTS `an_picture`;
CREATE TABLE `an_picture` (
  `PICTURE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `ALBUM_ID` int(11) NOT NULL COMMENT '相册ID',
  `PICTURE_NAME` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `TYPE` varchar(10) DEFAULT NULL COMMENT '图片类型',
  `LDATA` longblob COMMENT '原图数据',
  `LPATH` varchar(100) DEFAULT NULL COMMENT '原图路径',
  `SPATH` varchar(100) DEFAULT NULL COMMENT '缩略图路径',
  `SDATA` longblob COMMENT '缩略图数据',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`PICTURE_ID`),
  KEY `FK_Picture2AlbumReference` (`ALBUM_ID`),
  CONSTRAINT `FK_PICTURE2ALBUM` FOREIGN KEY (`ALBUM_ID`) REFERENCES `an_album` (`ALBUM_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片';

-- ----------------------------
-- Records of an_picture
-- ----------------------------

-- ----------------------------
-- Table structure for `an_todo`
-- ----------------------------
DROP TABLE IF EXISTS `an_todo`;
CREATE TABLE `an_todo` (
  `TODO_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `CATEGORY_ID` int(11) DEFAULT NULL COMMENT '任务分类ID',
  `TODO_CONTENT` varchar(500) NOT NULL DEFAULT '' COMMENT '任务内容',
  `DEAL_DATE` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '处理时间',
  `LEVEL` char(1) NOT NULL DEFAULT '2' COMMENT '任务优先级',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`TODO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务';

-- ----------------------------
-- Records of an_todo
-- ----------------------------

-- ----------------------------
-- Table structure for `an_todo_category`
-- ----------------------------
DROP TABLE IF EXISTS `an_todo_category`;
CREATE TABLE `an_todo_category` (
  `CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `CATEGORY_NAME` varchar(20) NOT NULL DEFAULT '' COMMENT '分类名称',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务分类';

-- ----------------------------
-- Records of an_todo_category
-- ----------------------------

-- ----------------------------
-- Table structure for `an_user`
-- ----------------------------
DROP TABLE IF EXISTS `an_user`;
CREATE TABLE `an_user` (
  `USER_ID` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `USER_NAME` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `PASSWORD` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `ROLE` char(1) NOT NULL DEFAULT '1' COMMENT '权限',
  `SEX` char(1) NOT NULL DEFAULT '1' COMMENT '性别 1:男 2:女',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `PHONE` varchar(20) DEFAULT NULL COMMENT '电话',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '1：启用 2：停用',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of an_user
-- ----------------------------
INSERT INTO `an_user` VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', '1', null, 'Anynote@163.com', '13770347150', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');

-- ----------------------------
-- Table structure for `an_user_meta`
-- ----------------------------
DROP TABLE IF EXISTS `an_user_meta`;
CREATE TABLE `an_user_meta` (
  `USER_META_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户元数据ID',
  `USER_ID` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `META_KEY` varchar(255) NOT NULL DEFAULT '' COMMENT '元数据KEY',
  `META_VALUE` varchar(255) DEFAULT '' COMMENT '元数据VALUE',
  `STATUS` char(1) NOT NULL DEFAULT '1',
  `DELFLAG` char(1) NOT NULL DEFAULT '1' COMMENT '删除Flag 1：未删除 2：已删除',
  `CREATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '登录者',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `UPDATE_USER` varchar(20) NOT NULL DEFAULT '' COMMENT '更新者',
  `UPDATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`USER_META_ID`),
  KEY `FK_USERMETA2USER` (`USER_ID`),
  CONSTRAINT `FK_USERMETA2USER` FOREIGN KEY (`USER_ID`) REFERENCES `an_user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户元数据';

-- ----------------------------
-- Records of an_user_meta
-- ----------------------------
INSERT INTO `an_user_meta` VALUES ('1', 'admin', 'theme', 'ext-all.css', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('2', 'admin', 'homePage', '/websrc/page/todo/todoList.jsp', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('3', 'admin', 'showNote', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('4', 'admin', 'showTodo', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('5', 'admin', 'showFeed', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('6', 'admin', 'showAccount', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('7', 'admin', 'showPicture', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('8', 'admin', 'showSystem', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO `an_user_meta` VALUES ('9', 'admin', 'showDocument', 'on', '1', '1', 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');

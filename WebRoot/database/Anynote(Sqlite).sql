/*
Navicat SQLite Data Transfer

Source Server         : SQLite
Source Server Version : 30623
Source Host           : localhost:0

Target Server Type    : SQLite
Target Server Version : 30623
File Encoding         : 65001

Date: 2011-06-17 11:14:29
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for "main"."AN_ACCOUNT"
-- ----------------------------
DROP TABLE "main"."AN_ACCOUNT";
CREATE TABLE "AN_ACCOUNT"(
	"ACCOUNT_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"ACCOUNT_BOOK_ID" INTEGER NOT NULL,
	"CATEGORY_ID" INTEGER NOT NULL,
	"ACCOUNT_DATE" DATETIME NOT NULL,
	"ACCOUNT_TYPE" CHAR NOT NULL,
	"MONEY" DOUBLE(10, 2)NOT NULL,
	"REMARK" VARCHAR(100),
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_ACCOUNT
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_ACCOUNT_BOOK"
-- ----------------------------
DROP TABLE "main"."AN_ACCOUNT_BOOK";
CREATE TABLE "AN_ACCOUNT_BOOK"(
	"ACCOUNT_BOOK_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"ACCOUNT_BOOK_NAME" VARCHAR(20)NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_ACCOUNT_BOOK
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_ACCOUNT_CATEGORY"
-- ----------------------------
DROP TABLE "main"."AN_ACCOUNT_CATEGORY";
CREATE TABLE "AN_ACCOUNT_CATEGORY"(
	"CATEGORY_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"CATEGORY_NAME" VARCHAR(20)NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_ACCOUNT_CATEGORY
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_ALBUM"
-- ----------------------------
DROP TABLE "main"."AN_ALBUM";
CREATE TABLE "AN_ALBUM"(
	"ALBUM_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"ALBUM_NAME" VARCHAR(20)NOT NULL,
	"PARENT_ID" INTEGER NOT NULL,
	"ISLEAF" CHAR NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_ALBUM
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_DOCUMENT"
-- ----------------------------
DROP TABLE "main"."AN_DOCUMENT";
CREATE TABLE "AN_DOCUMENT"(
	"DOCUMENT_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"DOCUMENT_NAME" VARCHAR(255)NOT NULL,
	"LINK" VARCHAR(200),
	"TYPE" VARCHAR(50),
	"SIZE" INTEGER,
	"TAGS" VARCHAR(255),
	"PARENT_ID" INTEGER NOT NULL,
	"ISLEAF" CHAR NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_DOCUMENT
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_FEED"
-- ----------------------------
DROP TABLE "main"."AN_FEED";
CREATE TABLE "AN_FEED"(
	"FEED_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"FEED_NAME" VARCHAR(255)NOT NULL,
	"FEED_URL" VARCHAR(255),
	"FEED_COUNT" INTEGER NOT NULL,
	"PARENT_ID" INTEGER NOT NULL,
	"ISLEAF" CHAR NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_FEED
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_FEED_FAVORITE"
-- ----------------------------
DROP TABLE "main"."AN_FEED_FAVORITE";
CREATE TABLE "AN_FEED_FAVORITE"(
	"FEED_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"TITLE" VARCHAR(200),
	"LINK" VARCHAR(255),
	"DESCRIPTION" TEXT,
	"UPDATED" DATETIME,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_FEED_FAVORITE
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_NOTE"
-- ----------------------------
DROP TABLE "main"."AN_NOTE";
CREATE TABLE "AN_NOTE"(
	"NOTE_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"CATEGORY_ID" INTEGER NOT NULL,
	"TITLE" VARCHAR(100)NOT NULL,
	"CONTENT" TEXT NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_NOTE
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_NOTE_CATEGORY"
-- ----------------------------
DROP TABLE "main"."AN_NOTE_CATEGORY";
CREATE TABLE "AN_NOTE_CATEGORY"(
	"CATEGORY_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"CATEGORY_NAME" VARCHAR(20)NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_NOTE_CATEGORY
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_PICTURE"
-- ----------------------------
DROP TABLE "main"."AN_PICTURE";
CREATE TABLE "AN_PICTURE"(
	"PICTURE_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"ALBUM_ID" INTEGER NOT NULL,
	"PICTURE_NAME" VARCHAR(255),
	"TYPE" VARCHAR(10),
	"LDATA" BLOB,
	"LPATH" VARCHAR(100),
	"SDATA" BLOB,
	"SPATH" VARCHAR(100),
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_PICTURE
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_TODO"
-- ----------------------------
DROP TABLE "main"."AN_TODO";
CREATE TABLE "AN_TODO"(
	"TODO_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"CATEGORY_ID" INTEGER,
	"TODO_CONTENT" VARCHAR(500)NOT NULL,
	"DEAL_DATE" DATETIME,
	"LEVEL" CHAR NOT NULL DEFAULT 2,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_TODO
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_TODO_CATEGORY"
-- ----------------------------
DROP TABLE "main"."AN_TODO_CATEGORY";
CREATE TABLE "AN_TODO_CATEGORY"(
	"CATEGORY_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"CATEGORY_NAME" VARCHAR(20)NOT NULL,
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_TODO_CATEGORY
-- ----------------------------

-- ----------------------------
-- Table structure for "main"."AN_USER"
-- ----------------------------
DROP TABLE "main"."AN_USER";
CREATE TABLE "AN_USER"(
	"USER_ID" VARCHAR(20)PRIMARY KEY NOT NULL,
	"USER_NAME" VARCHAR(20)NOT NULL,
	"PASSWORD" VARCHAR(32)NOT NULL,
	"ROLE" CHAR NOT NULL,
	"SEX" CHAR NOT NULL,
	"BIRTHDAY" DATETIME,
	"EMAIL" VARCHAR(50)NOT NULL,
	"PHONE" VARCHAR(20),
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_USER
-- ----------------------------
INSERT INTO "AN_USER" VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 1, 1, null, 'Anynote@163.com', 13770347150, 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');

-- ----------------------------
-- Table structure for "main"."AN_USER_META"
-- ----------------------------
DROP TABLE "main"."AN_USER_META";
CREATE TABLE "AN_USER_META"(
	"USER_META_ID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	"USER_ID" VARCHAR(20)NOT NULL,
	"META_KEY" VARCHAR(255)NOT NULL,
	"META_VALUE" VARCHAR(255),
	"STATUS" CHAR NOT NULL DEFAULT 1,
	"DELFLAG" CHAR NOT NULL DEFAULT 1,
	"CREATE_USER" VARCHAR(20)NOT NULL,
	"CREATE_TIME" DATETIME NOT NULL,
	"UPDATE_USER" VARCHAR(20)NOT NULL,
	"UPDATE_TIME" DATETIME NOT NULL
);

-- ----------------------------
-- Records of AN_USER_META
-- ----------------------------
INSERT INTO "AN_USER_META" VALUES (1, 'admin', 'theme', 'ext-all.css', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (2, 'admin', 'homePage', '/websrc/page/todo/todoList.jsp', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (3, 'admin', 'showNote', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (4, 'admin', 'showTodo', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (5, 'admin', 'showFeed', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (6, 'admin', 'showAccount', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (7, 'admin', 'showPicture', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (8, 'admin', 'showSystem', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');
INSERT INTO "AN_USER_META" VALUES (9, 'admin', 'showDocument', 'on', 1, 1, 'admin', '2010-07-01 00:00:00', 'admin', '2010-07-01 00:00:00');

-- ----------------------------
-- Triggers structure for table "main"."AN_ACCOUNT_BOOK"
-- ----------------------------
DROP TRIGGER IF EXISTS "main"."FRG_ACCOUNT_BOOKID";
DELIMITER ;;
CREATE TRIGGER FRG_ACCOUNT_BOOKID AFTER DELETE ON "AN_ACCOUNT_BOOK" FOR EACH ROW
BEGIN
	DELETE
FROM
	"AN_ACCOUNT"
WHERE
	ACCOUNT_BOOK_ID = OLD.ACCOUNT_BOOK_ID;
END
;;
DELIMITER ;

-- ----------------------------
-- Triggers structure for table "main"."AN_ACCOUNT_CATEGORY"
-- ----------------------------
DROP TRIGGER IF EXISTS "main"."FRG_ACCOUNT_CATEGORYID";
DELIMITER ;;
CREATE TRIGGER FRG_ACCOUNT_CATEGORYID AFTER DELETE ON "AN_ACCOUNT_CATEGORY" FOR EACH ROW
BEGIN
	DELETE
FROM
	"AN_ACCOUNT"
WHERE
	CATEGORY_ID = OLD.CATEGORY_ID;
END
;;
DELIMITER ;

-- ----------------------------
-- Triggers structure for table "main"."AN_ALBUM"
-- ----------------------------
DROP TRIGGER IF EXISTS "main"."FRG_PICTURE_ALBUMID";
DELIMITER ;;
CREATE TRIGGER FRG_PICTURE_ALBUMID AFTER DELETE ON "AN_ALBUM" FOR EACH ROW
BEGIN
	DELETE
FROM
	"AN_PICTURE"
WHERE
	ALBUM_ID = OLD.ALBUM_ID;
END
;;
DELIMITER ;

-- ----------------------------
-- Triggers structure for table "main"."AN_NOTE_CATEGORY"
-- ----------------------------
DROP TRIGGER IF EXISTS "main"."FRG_NOTE_CATEGORYID";
DELIMITER ;;
CREATE TRIGGER FRG_NOTE_CATEGORYID AFTER DELETE ON "AN_NOTE_CATEGORY" FOR EACH ROW
BEGIN
	DELETE
FROM
	"AN_NOTE"
WHERE
	CATEGORY_ID = OLD.CATEGORY_ID;
END
;;
DELIMITER ;

-- ----------------------------
-- Triggers structure for table "main"."AN_USER"
-- ----------------------------
DROP TRIGGER IF EXISTS "main"."FRG_USERMETA_USERID";
DELIMITER ;;
CREATE TRIGGER FRG_USERMETA_USERID AFTER DELETE ON "AN_USER" FOR EACH ROW
BEGIN
	DELETE
FROM
	"AN_USER_META"
WHERE
	USER_ID = OLD.USER_ID;
END
;;
DELIMITER ;

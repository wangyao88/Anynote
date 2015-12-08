/*
Navicat SQL Server Data Transfer

Source Server         : SQLExpress
Source Server Version : 90000
Source Host           : 127.0.0.1:1433
Source Database       : anynote
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 90000
File Encoding         : 65001

Date: 2011-06-17 09:08:15
*/


-- ----------------------------
-- Table structure for [dbo].[account]
-- ----------------------------
DROP TABLE [dbo].[an_account]
GO
CREATE TABLE [dbo].[an_account] (
[ACCOUNT_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[ACCOUNT_BOOK_ID] int NOT NULL ,
[CATEGORY_ID] int NOT NULL DEFAULT ('0') ,
[ACCOUNT_DATE] datetime NOT NULL DEFAULT ('0000-00-00') ,
[ACCOUNT_TYPE] char(1) NOT NULL DEFAULT '' ,
[MONEY] money NOT NULL DEFAULT ('0.00') ,
[REMARK] varchar(100) NULL DEFAULT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_account
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_account_book]
-- ----------------------------
DROP TABLE [dbo].[account_book]
GO
CREATE TABLE [dbo].[an_account_book] (
[ACCOUNT_BOOK_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[ACCOUNT_BOOK_NAME] varchar(20) NOT NULL DEFAULT ('0') ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_account_book
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_account_category]
-- ----------------------------
DROP TABLE [dbo].[an_account_category]
GO
CREATE TABLE [dbo].[an_account_category] (
[CATEGORY_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[CATEGORY_NAME] varchar(20) NOT NULL DEFAULT '' ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_account_category
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_album]
-- ----------------------------
DROP TABLE [dbo].[an_album]
GO
CREATE TABLE [dbo].[an_album] (
[ALBUM_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[ALBUM_NAME] varchar(20) NOT NULL ,
[PARENT_ID] int NOT NULL ,
[ISLEAF] char(1) NOT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_album
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_document]
-- ----------------------------
DROP TABLE [dbo].[an_document]
GO
CREATE TABLE [dbo].[an_document] (
[DOCUMENT_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[DOCUMENT_NAME] varchar(255) NOT NULL ,
[LINK] varchar(200) NULL DEFAULT NULL ,
[TYPE] varchar(50) NULL DEFAULT NULL ,
[SIZE] int NULL DEFAULT NULL ,
[TAGS] varchar(255) NULL DEFAULT NULL ,
[PARENT_ID] int NOT NULL ,
[ISLEAF] char(1) NOT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_document
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_feed]
-- ----------------------------
DROP TABLE [dbo].[an_feed]
GO
CREATE TABLE [dbo].[an_feed] (
[FEED_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[FEED_NAME] varchar(255) NOT NULL ,
[FEED_URL] varchar(255) NULL DEFAULT NULL ,
[FEED_COUNT] int NOT NULL DEFAULT ('0') ,
[PARENT_ID] int NOT NULL ,
[ISLEAF] char(1) NOT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_feed
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_feed_favorite]
-- ----------------------------
DROP TABLE [dbo].[an_feed_favorite]
GO
CREATE TABLE [dbo].[an_feed_favorite] (
[FEED_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[TITLE] varchar(200) NULL DEFAULT '' ,
[LINK] varchar(200) NULL DEFAULT NULL ,
[DESCRIPTION] text NULL ,
[UPDATED] datetime NULL DEFAULT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_feed_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_note]
-- ----------------------------
DROP TABLE [dbo].[an_note]
GO
CREATE TABLE [dbo].[an_note] (
[NOTE_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[CATEGORY_ID] int NOT NULL DEFAULT ('0') ,
[TITLE] varchar(100) NOT NULL DEFAULT '' ,
[CONTENT] text NOT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_note
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_note_category]
-- ----------------------------
DROP TABLE [dbo].[an_note_category]
GO
CREATE TABLE [dbo].[an_note_category] (
[CATEGORY_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[CATEGORY_NAME] varchar(20) NOT NULL DEFAULT '' ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_note_category
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_picture]
-- ----------------------------
DROP TABLE [dbo].[an_picture]
GO
CREATE TABLE [dbo].[an_picture] (
[PICTURE_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[ALBUM_ID] int NOT NULL ,
[PICTURE_NAME] varchar(255) NULL DEFAULT NULL ,
[TYPE] varchar(10) NULL DEFAULT NULL ,
[LDATA] image NULL ,
[LPATH] varchar(100) NULL DEFAULT NULL ,
[SPATH] varchar(100) NULL DEFAULT NULL ,
[SDATA] image NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_picture
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_todo]
-- ----------------------------
DROP TABLE [dbo].[an_todo]
GO
CREATE TABLE [dbo].[an_todo] (
[TODO_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[CATEGORY_ID] int NULL DEFAULT NULL ,
[TODO_CONTENT] varchar(500) NOT NULL DEFAULT '' ,
[DEAL_DATE] datetime NULL DEFAULT ('0000-00-00 00:00:00') ,
[LEVEL] char(1) NOT NULL DEFAULT ('2') ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_todo
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_todo_category]
-- ----------------------------
DROP TABLE [dbo].[an_todo_category]
GO
CREATE TABLE [dbo].[an_todo_category] (
[CATEGORY_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[CATEGORY_NAME] varchar(20) NOT NULL DEFAULT '' ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_todo_category
-- ----------------------------

-- ----------------------------
-- Table structure for [dbo].[an_user]
-- ----------------------------
DROP TABLE [dbo].[an_user]
GO
CREATE TABLE [dbo].[an_user] (
[USER_ID] varchar(20) NOT NULL DEFAULT '' PRIMARY KEY ,
[USER_NAME] varchar(20) NOT NULL DEFAULT '' ,
[PASSWORD] varchar(32) NOT NULL DEFAULT '' ,
[ROLE] char(1) NOT NULL DEFAULT ('1') ,
[SEX] char(1) NOT NULL DEFAULT ('1') ,
[BIRTHDAY] datetime NULL DEFAULT NULL ,
[EMAIL] varchar(50) NOT NULL DEFAULT NULL ,
[PHONE] varchar(20) NULL DEFAULT NULL ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_user
-- ----------------------------
INSERT INTO [dbo].[an_user] ([USER_ID], [USER_NAME], [PASSWORD], [ROLE], [SEX], [BIRTHDAY], [EMAIL], [PHONE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'admin', N'21232f297a57a5a743894a0e4a801fc3', N'1', N'1', null, N'Anynote@163.com', N'13770347150', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO

-- ----------------------------
-- Table structure for [dbo].[an_user_meta]
-- ----------------------------
DROP TABLE [dbo].[an_user_meta]
GO
CREATE TABLE [dbo].[an_user_meta] (
[USER_META_ID] int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
[USER_ID] varchar(20) NOT NULL DEFAULT '' ,
[META_KEY] varchar(255) NOT NULL DEFAULT '' ,
[META_VALUE] varchar(255) NULL DEFAULT '' ,
[STATUS] char(1) NOT NULL DEFAULT ('1') ,
[DELFLAG] char(1) NOT NULL DEFAULT ('1') ,
[CREATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[CREATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00') ,
[UPDATE_USER] varchar(20) NOT NULL DEFAULT '' ,
[UPDATE_TIME] datetime NOT NULL DEFAULT ('0000-00-00 00:00:00')
)


GO

-- ----------------------------
-- Records of an_user_meta
-- ----------------------------
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'theme', N'ext-all.css', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'homePage', N'/websrc/page/todo/todoList.jsp', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showNote', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showTodo', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showFeed', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showAccount', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showPicture', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showSystem', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO
INSERT INTO [dbo].[an_user_meta] ([USER_ID], [META_KEY], [META_VALUE], [STATUS], [DELFLAG], [CREATE_USER], [CREATE_TIME], [UPDATE_USER], [UPDATE_TIME]) VALUES (N'admin', N'showDocument', N'on', N'1', N'1', N'admin', N'2010-07-01 00:00:00.000', N'admin', N'2010-07-01 00:00:00.000');
GO


-- ----------------------------
-- Index structure for `an_account`
-- ----------------------------
ALTER TABLE [dbo].[an_account] ADD CONSTRAINT [FK_ACCOUNT2BOOK] FOREIGN KEY ([ACCOUNT_BOOK_ID]) REFERENCES [dbo].[an_account_book] ([ACCOUNT_BOOK_ID]) ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[an_account] ADD CONSTRAINT [FK_ACCOUNT2CATEGORY] FOREIGN KEY ([CATEGORY_ID]) REFERENCES [dbo].[an_account_category] ([CATEGORY_ID]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Index structure for `an_note`
-- ----------------------------
ALTER TABLE [dbo].[an_note] ADD CONSTRAINT [FK_NOTE2CATEGORY] FOREIGN KEY ([CATEGORY_ID]) REFERENCES [dbo].[an_note_category] ([CATEGORY_ID]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Index structure for `an_picture`
-- ----------------------------
ALTER TABLE [dbo].[an_picture] ADD CONSTRAINT [FK_PICTURE2ALBUM] FOREIGN KEY ([ALBUM_ID]) REFERENCES [dbo].[an_album] ([ALBUM_ID]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Index structure for `an_user_meta`
-- ----------------------------
ALTER TABLE [dbo].[an_user_meta] ADD CONSTRAINT [FK_USERMETA2USER] FOREIGN KEY ([USER_ID]) REFERENCES [dbo].[an_user] ([USER_ID]) ON DELETE CASCADE ON UPDATE CASCADE
GO
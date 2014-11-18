/*
Navicat MySQL Data Transfer

Source Server         : local-mysql
Source Server Version : 50148
Source Host           : localhost:3306
Source Database       : seobi

Target Server Type    : MYSQL
Target Server Version : 50148
File Encoding         : 65001

Date: 2014-11-18 19:08:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_talk
-- ----------------------------
DROP TABLE IF EXISTS `tb_talk`;
CREATE TABLE `tb_talk` (
  `qq` varchar(11) DEFAULT NULL,
  `qqName` varchar(50) DEFAULT NULL,
  `talkContent` varchar(200) DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  `talkTxid` varchar(30) NOT NULL,
  `browseCount` int(11) DEFAULT NULL,
  `praiseCount` int(11) DEFAULT NULL,
  `fromDevice` varchar(10) DEFAULT NULL,
  `gps` varchar(30) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `imgPath` varchar(100) DEFAULT NULL,
  `qx` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`talkTxid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `AID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `QQ` varchar(10) DEFAULT NULL COMMENT 'QQ号码',
  `QQ_NAME` varchar(128) DEFAULT NULL COMMENT 'QQ昵称',
  PRIMARY KEY (`AID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='账号表';

-- ----------------------------
-- Table structure for t_proxy
-- ----------------------------
DROP TABLE IF EXISTS `t_proxy`;
CREATE TABLE `t_proxy` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `proxy_ip` varchar(45) NOT NULL COMMENT '代理主机ip',
  `proxy_port` int(10) unsigned NOT NULL COMMENT '代理主机端口',
  `last_test_time` datetime DEFAULT NULL COMMENT '最后检测时间',
  `telnet_available` char(1) DEFAULT 'N' COMMENT 'telnet是否通',
  `http_get_available` char(1) DEFAULT 'N' COMMENT '是否允许HTTP get请求',
  `http_post_available` char(1) DEFAULT 'N' COMMENT '是否允许http post 请求',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=2295 DEFAULT CHARSET=utf8 COMMENT='代理信息表';

-- ----------------------------
-- Table structure for t_related_search_key
-- ----------------------------
DROP TABLE IF EXISTS `t_related_search_key`;
CREATE TABLE `t_related_search_key` (
  `rsk_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tm_id` int(11) NOT NULL COMMENT '说说ID',
  `related_search_key` varchar(128) NOT NULL COMMENT '相关搜索的关键字',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`rsk_id`),
  KEY `FK_t_related_search_key_1` (`tm_id`),
  CONSTRAINT `FK_t_related_search_key_1` FOREIGN KEY (`tm_id`) REFERENCES `t_talk_msg` (`TM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='相关搜索关键字';

-- ----------------------------
-- Table structure for t_talk_img
-- ----------------------------
DROP TABLE IF EXISTS `t_talk_img`;
CREATE TABLE `t_talk_img` (
  `TI_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '说说图片表主键',
  `TM_ID` int(11) NOT NULL COMMENT '说说信息主键',
  `IMG_PATH` varchar(256) NOT NULL COMMENT '图片地址',
  PRIMARY KEY (`TI_ID`),
  KEY `FK_Reference_3` (`TM_ID`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`TM_ID`) REFERENCES `t_talk_msg` (`TM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='说说图片表';

-- ----------------------------
-- Table structure for t_talk_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_talk_msg`;
CREATE TABLE `t_talk_msg` (
  `TM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '说说信息主键',
  `AID` int(11) DEFAULT NULL COMMENT '账号ID',
  `TALK_CONTENT` varchar(256) DEFAULT NULL COMMENT '说说内容',
  `PUBLISH_TIME` datetime DEFAULT NULL COMMENT '发表时间',
  `TM_TX_ID` varchar(64) DEFAULT NULL,
  `BROWSE_COUNT` int(11) DEFAULT NULL COMMENT '浏览次数',
  `PRAISE_COUNT` int(11) DEFAULT NULL COMMENT '赞数',
  `FROM_DEVICE` varchar(64) DEFAULT NULL COMMENT '来自设备',
  `GPS` varchar(128) DEFAULT NULL COMMENT '地理位置信息',
  `analyzed` char(1) DEFAULT 'N',
  `analyze_time` datetime DEFAULT NULL,
  `originality_percent` double DEFAULT NULL,
  `REMARK` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`TM_ID`),
  KEY `FK_Reference_1` (`AID`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`AID`) REFERENCES `t_account` (`AID`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8 COMMENT='说说信息表，如果有表情符号，需要把把表情符号加进去';

-- ----------------------------
-- Table structure for t_talk_msg_related_search_key
-- ----------------------------
DROP TABLE IF EXISTS `t_talk_msg_related_search_key`;
CREATE TABLE `t_talk_msg_related_search_key` (
  `tm_rsk_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tm_id` int(10) unsigned NOT NULL,
  `rsk_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tm_rsk_id`),
  KEY `FK_t_talk_msg_related_search_key_1` (`rsk_id`),
  CONSTRAINT `FK_t_talk_msg_related_search_key_1` FOREIGN KEY (`rsk_id`) REFERENCES `t_related_search_key` (`rsk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='qq说说相关的搜索关键字';

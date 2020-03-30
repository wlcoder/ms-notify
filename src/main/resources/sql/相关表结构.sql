CREATE TABLE `notify_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nid` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '通知id',
  `nname` varchar(32) NOT NULL COMMENT '通知名称',
  `type` varchar(20) DEFAULT NULL COMMENT '通知方式: email sms',
  `subject` varchar(32) DEFAULT NULL COMMENT '主题',
  `content` varchar(500) DEFAULT NULL COMMENT '通知内容',
  `status` int(1) DEFAULT NULL COMMENT '状态 0:禁用 1：启用',
  `cron` varchar(32) DEFAULT NULL COMMENT 'cron表达式',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `notify_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nid` varchar(20) DEFAULT NULL COMMENT '通知id',
  `nname` varchar(32) DEFAULT NULL COMMENT '通知名称',
  `type` varchar(20) DEFAULT NULL COMMENT '通知方式 :email sms',
  `status` int(1) DEFAULT NULL COMMENT '发送状态 0：失败 1：成功',
  `subject` varchar(32) DEFAULT NULL COMMENT '主题',
  `content` varchar(500) DEFAULT NULL COMMENT '发送内容',
  `sender` varchar(20) DEFAULT NULL COMMENT '发送人',
  `receiver` varchar(20) DEFAULT NULL COMMENT '接收人',
  `senddate` date DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
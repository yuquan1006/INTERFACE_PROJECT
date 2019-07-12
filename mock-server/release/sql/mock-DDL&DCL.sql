create database if not exists mock default charset utf8 collate utf8_general_ci;
create user 'mock'@'%' identified by 'mock';
grant all privileges on mock.* to 'mock'@'%';
create user 'mock'@'localhost' identified by 'mock';
grant all privileges on mock.* to 'mock'@'localhost';;
flush privileges

use mock;

CREATE TABLE `response_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_point` varchar(64) NOT NULL COMMENT '接入点',
  `group` varchar(64) NOT NULL COMMENT '组',
  `response_template_name` varchar(64) NOT NULL COMMENT '响应模板名称',
  `response_template_desc` varchar(512) NOT NULL COMMENT '响应模板描述',
  `response_template_content` mediumtext NOT NULL COMMENT '响应模板内容',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `sleep_time` int(11) NULL DEFAULT NULL COMMENT '响应睡眠时间',
  PRIMARY KEY (`id`)
) COMMENT='响应模板表';
-- CREATE UNIQUE INDEX unique_response_template ON response_template (response_template_name);
CREATE INDEX index_response_template ON response_template (access_point,response_template_name, status);

CREATE TABLE `response_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_point` varchar(64) NOT NULL COMMENT '接入点',
  `response_template_name` varchar(64) NOT NULL COMMENT '响应模板名称',
  `unique_request_id` varchar(64) NOT NULL COMMENT '唯一请求id',
  `request_content` mediumtext NOT NULL COMMENT '请求内容',
  `response_content` mediumtext NOT NULL COMMENT '响应内容',
  `request_date` TIMESTAMP NULL DEFAULT NULL COMMENT '请求时间点',
  `response_date` TIMESTAMP NULL DEFAULT NULL COMMENT '响应时间点',
  PRIMARY KEY (`id`)
) COMMENT='响应记录表';
ALTER TABLE response_record ADD INDEX  index_response_record(access_point,response_template_name, unique_request_id);

CREATE TABLE `notify_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_point` varchar(64) NOT NULL COMMENT '接入点',
  `group` varchar(64) NOT NULL COMMENT '组',
	`notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `notify_template_name` varchar(64) NOT NULL COMMENT '通知模板名称',
  `notify_template_desc` varchar(512) NOT NULL COMMENT '通知模板描述',
  `notify_template_content` mediumtext NOT NULL COMMENT '通知模板内容',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `sleep_time` int(11) NULL DEFAULT NULL COMMENT '通知睡眠时间',
  PRIMARY KEY (`id`)
) COMMENT='通知模板表';
-- CREATE UNIQUE INDEX unique_notify_template ON notify_template (notify_template_name);
CREATE INDEX index_notify_template ON notify_template (access_point,notify_template_name, status);

CREATE TABLE `notify_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_point` varchar(64) NOT NULL COMMENT '接入点',
 `notify_template_name` varchar(64) NOT NULL COMMENT '通知模板名称',
  `unique_request_id` varchar(64) NOT NULL COMMENT '唯一请求id',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `notify_request_content` mediumtext NOT NULL COMMENT '通知请求内容',
  `notify_response_content` mediumtext NOT NULL COMMENT '通知响应内容',
  `notify_request_date` TIMESTAMP NULL DEFAULT NULL COMMENT '通知请求时间点',
  `notify_response_date` TIMESTAMP NULL DEFAULT NULL COMMENT '通知响应时间点',
  PRIMARY KEY (`id`)
) COMMENT='通知记录表';
ALTER TABLE notify_record ADD INDEX  index_notify_record(access_point,notify_template_name, unique_request_id);

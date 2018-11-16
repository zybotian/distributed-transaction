CREATE DATABASE `db_account` DEFAULT CHARACTER SET utf8;

USE `db_account`;

CREATE TABLE IF NOT EXISTS `db_account`.`account`(
  `id`              VARCHAR(64)     NOT NULL DEFAULT '' COMMENT '主键ID',
  `balance`         BIGINT          NOT NULL DEFAULT 0  COMMENT '当前余额',
  `create_time`     BIGINT          NOT NULL DEFAULT 0  COMMENT '创建时间',
  `update_time`     BIGINT          NOT NULL DEFAULT 0  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `db_account`.`event`(
  `id`              BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '自增主键',
  `type`            INT             NOT NULL DEFAULT 0       COMMENT '事件类型',
  `progress`        INT             NOT NULL DEFAULT 0       COMMENT '事件进度',
  `content`         VARCHAR(1024)   NOT NULL DEFAULT ''      COMMENT '事件内容',
  `create_time`     BIGINT          NOT NULL DEFAULT 0       COMMENT '创建时间',
  `update_time`     BIGINT          NOT NULL DEFAULT 0       COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	test_id BIGINT(20) NOT NULL COMMENT '主键ID',
	tenant_id BIGINT(20) NOT NULL DEFAULT  0 COMMENT '租户ID',
	name VARCHAR(30) NOT NULL DEFAULT '' COMMENT '名称',
	age INT(11) NOT NULL DEFAULT 0  COMMENT '年龄',
	test_type INT(11) NOT NULL DEFAULT 1  COMMENT '测试下划线字段命名类型',
	test_date DATETIME NOT NULL DEFAULT now()  COMMENT '日期',
	role BIGINT(20) NOT NULL DEFAULT 1  COMMENT '测试',
	phone VARCHAR(11) NOT NULL DEFAULT ''  COMMENT '手机号码',
	PRIMARY KEY (test_id)
);

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `app_prod`
-- ----------------------------
DROP TABLE IF EXISTS app_prod;
CREATE TABLE app_prod (
  pro_id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品id',
  pro_name varchar(50) NOT NULL COMMENT '商品名称',
  pro_type char(2)  DEFAULT NULL COMMENT '商品类型',
  pro_oriprice decimal(15,2) DEFAULT NULL COMMENT '商品原价格',
  pro_price decimal(15,2) DEFAULT NULL COMMENT '商品现价格',
  pro_brief varchar(500)  DEFAULT NULL COMMENT '商品简介',
  pro_content text COMMENT '商品详情',
  pro_pic varchar(300) DEFAULT NULL COMMENT '商品缩略图',
  pro_sold_num int(11) DEFAULT NULL COMMENT '销售量',
  pro_total_stocks int(11) DEFAULT NULL COMMENT '库存量',
  pro_status char(2) DEFAULT NULL COMMENT '状态',
  pro_putaway_time datetime DEFAULT NULL COMMENT '发布时间',
  category_id bigint(20) DEFAULT NULL COMMENT '分类id',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  create_user varchar(20) DEFAULT NULL COMMENT '创建用户',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  update_user varchar(20) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (pro_id),
  KEY pro_name (pro_name)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
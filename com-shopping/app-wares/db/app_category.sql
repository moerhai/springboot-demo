SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `app_category`
-- ----------------------------
DROP TABLE IF EXISTS app_category;
CREATE TABLE app_category (
  cat_id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品分类id',
  parent_id bigint(20) NOT NULL COMMENT '父级id',
  cat_name varchar(30) NOT NULL COMMENT '商品分类名称',
  cat_status char(2) DEFAULT NULL COMMENT '商品分类状态',
  cat_sort int(4) DEFAULT NULL COMMENT '商品分类排序',
  cat_icon varchar(100) DEFAULT NULL COMMENT '分类图标',
  cat_pic varchar(300) DEFAULT NULL COMMENT '商品分类缩略图',
  cat_level int(2) DEFAULT NULL COMMENT '商品分类级别',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  create_user varchar(20) DEFAULT NULL COMMENT '创建用户',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  update_user varchar(20) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (cat_id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
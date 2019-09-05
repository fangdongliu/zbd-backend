/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : index_point

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 05/09/2019 19:50:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for map_course_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `map_course_evaluation`;
CREATE TABLE `map_course_evaluation`  (
                                          `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                          `student_grade` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生年级',
                                          `school_year` datetime(0) NULL DEFAULT NULL COMMENT '学年',
                                          `index_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标点number(e.g.4.2, 4.3)',
                                          `index_proportion_value` double NULL DEFAULT NULL COMMENT '达成目标值(指标点系数)',
                                          `evaluation_value` double NULL DEFAULT NULL COMMENT '评价值(某课程针对其所需达成目标的实际评价值)',
                                          `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                          `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                          `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                          `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
                                          `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程-指标点-评价值关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_course_index
-- ----------------------------
DROP TABLE IF EXISTS `map_course_index`;
CREATE TABLE `map_course_index`  (
                                     `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                     `course_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程id，from sys_course 表',
                                     `index_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标要求id ，from sys_index 表',
                                     `proportion_value` double NULL DEFAULT NULL COMMENT '达成目标值，指标系数之和=1',
                                     `school_grade` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生年级(e.g. 2016级)',
                                     `statistic_year` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统计年份(示例：2014代表2014-2015学年)',
                                     `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                     `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                     `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                     `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                     `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程-毕业要求(指标点)-关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_course_weeklen
-- ----------------------------
DROP TABLE IF EXISTS `map_course_weeklen`;
CREATE TABLE `map_course_weeklen`  (
                                       `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                       `course_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程id',
                                       `week_number` int(11) NULL DEFAULT NULL COMMENT '周次',
                                       `week_average_len` int(11) NULL DEFAULT NULL COMMENT '课程平均学时',
                                       `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项备注',
                                       `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                       `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                       `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                       `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                       `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程-平均周时关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_role_power
-- ----------------------------
DROP TABLE IF EXISTS `map_role_power`;
CREATE TABLE `map_role_power`  (
                                   `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                   `role_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id from sys_role表',
                                   `power_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限id from sys_power 表',
                                   `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                   `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                   `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                   `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                   `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_student_course
-- ----------------------------
DROP TABLE IF EXISTS `map_student_course`;
CREATE TABLE `map_student_course`  (
                                       `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                       `user_work_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生work_id from sys_user 表',
                                       `course_select_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程选课课号 - from map_teacher_course 表\r\n(学期-课程编号-教师工号-该师该学期第几门课)',
                                       `total_grade` double NULL DEFAULT NULL COMMENT '总成绩',
                                       `input_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成绩录入人名称',
                                       `grade_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成绩标志',
                                       `exam_nature` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试性质',
                                       `supplement_repeat_semester` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补重学期',
                                       `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                       `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                       `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                       `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                       `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生-课程关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_student_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `map_student_evaluation`;
CREATE TABLE `map_student_evaluation`  (
                                           `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                           `work_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生/教师工号',
                                           `course_select_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选课课号(学期-课程编号-教师工号-该师该学期第几门课)',
                                           `index_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标要求id ，from sys_index 表',
                                           `comment_value` int(11) NULL DEFAULT NULL COMMENT '课程指标点评价值(0->4)',
                                           `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                           `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                           `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                           `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                           `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生指标点评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `map_teacher_course`;
CREATE TABLE `map_teacher_course`  (
                                       `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                       `user_work_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师work_id - from sys_user 表',
                                       `course_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程id - from sys_course 表',
                                       `course_select_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选课课号(学期-课程编号-教师工号-该师该学期第几门课)',
                                       `course_campus` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开课校区',
                                       `course_area` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能区(上课地点)',
                                       `course_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲课班级名称',
                                       `course_elect_number` int(11) NULL DEFAULT NULL COMMENT '选课人数',
                                       `course_arrange_number` int(11) NULL DEFAULT NULL COMMENT '排课人数',
                                       `course_teache_week` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲课周次',
                                       `week_len` int(11) NULL DEFAULT NULL COMMENT '课程周学时',
                                       `plan_len` int(11) NULL DEFAULT NULL COMMENT '安排学时',
                                       `teach_len` int(11) NULL DEFAULT NULL COMMENT '课程讲课学时',
                                       `practice_len` int(11) NULL DEFAULT NULL COMMENT '课程实践学时',
                                       `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                       `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                       `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                       `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                       `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师-课程对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for map_user_role
-- ----------------------------
DROP TABLE IF EXISTS `map_user_role`;
CREATE TABLE `map_user_role`  (
                                  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id from sys_user 表',
                                  `role_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id，from sys_role 表',
                                  `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                  `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                  `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                  `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of map_user_role
-- ----------------------------
INSERT INTO `map_user_role` VALUES ('0001', '0001', '0001', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `map_user_role` VALUES ('0002', '0001', '0002', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `map_user_role` VALUES ('0003', '0002', '0002', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_course
-- ----------------------------
DROP TABLE IF EXISTS `sys_course`;
CREATE TABLE `sys_course`  (
                               `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                               `course_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
                               `course_credit` double NULL DEFAULT NULL COMMENT '课程学分',
                               `course_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程代码/编码',
                               `course_semester` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开课学年学期(示例：2018-2019-1)',
                               `course_department` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程开课单位/院系/部门',
                               `course_route` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程所在路线',
                               `course_character` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程性质/属性',
                               `course_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程体系/类别',
                               `course_kind` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程种类(文学与艺术)',
                               `course_attribution` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程归属(文化素质通识课)',
                               `assess_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程考核方式',
                               `type_Identification` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养环节类别标识',
                               `module_Identification` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块与层次标识',
                               `is_substitute` int(11) NULL DEFAULT NULL COMMENT '是否可用高层次课程代替代课程(-1不可，0可)',
                               `total_len` int(11) NULL DEFAULT NULL COMMENT '课程总学时',
                               `semester_len` int(11) NULL DEFAULT NULL COMMENT '课程学期学时(一般和课程总学时相同)',
                               `experiment_len` int(11) NULL DEFAULT NULL COMMENT '课程上机/实验学时',
                               `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                               `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                               `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                               `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                               `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                               `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_credit
-- ----------------------------
DROP TABLE IF EXISTS `sys_credit`;
CREATE TABLE `sys_credit`  (
                               `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                               `credit_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '某一项得分名称',
                               `course_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应课程的id - from sys_course 表',
                               `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                               `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                               `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                               `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                               `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '某课程学分统计表(包含多少项)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
                             `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                             `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项名称',
                             `type_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型 from 字典类型表sys_dict_type',
                             `sort` int(11) NULL DEFAULT NULL COMMENT '字典项排序值',
                             `parent_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父字典项 from 字典表',
                             `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项备注',
                             `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                             `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                             `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
                                  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                                  `type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型名',
                                  `sort` int(11) NULL DEFAULT NULL COMMENT '字典类型排序值',
                                  `parent_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父字典类型 from 字典类型表',
                                  `remarks` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型说明',
                                  `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                  `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                                  `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                  `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典项类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_index
-- ----------------------------
DROP TABLE IF EXISTS `sys_index`;
CREATE TABLE `sys_index`  (
                              `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
                              `index_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标点要求序号 (4.1/4.2)',
                              `index_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标要求内容',
                              `parent_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标要求父类型 - from sys_index 表',
                              `sort` int(11) NULL DEFAULT NULL COMMENT '指标要求序号',
                              `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                              `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                              `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                              `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                              `status` int(11) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '状态值(-1失效，0默认值)',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '毕业要求指标点表(责任教授/教学干事上传)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
                            `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志编号',
                            `log_module` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志模块',
                            `request_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求IP',
                            `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id from sys_user',
                            `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
                            `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
                            `log_action` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作行为',
                            `log_result` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求结果',
                            `log_time` datetime(0) NULL DEFAULT NULL COMMENT '请求时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_action`;
CREATE TABLE `sys_log_action`  (
                                   `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
                                   `action_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
                                   `action_module` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作所属的模块',
                                   `action_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动作描述',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户日志记录的请求初始表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_action
-- ----------------------------
INSERT INTO `sys_log_action` VALUES ('shuidquec', '/abc/bcd.do', '用户中心', '新建用户');

-- ----------------------------
-- Table structure for sys_power
-- ----------------------------
DROP TABLE IF EXISTS `sys_power`;
CREATE TABLE `sys_power`  (
                              `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                              `power_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
                              `power_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权代码',
                              `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父权限id from sys_power 表',
                              `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                              `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                              `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                              `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                              `status` int(11) NULL DEFAULT 0 COMMENT '状态值(-1失效，0默认值)',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                             `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
                             `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色授权码',
                             `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                             `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                             `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             `status` int(11) UNSIGNED ZEROFILL NULL DEFAULT 00000000000 COMMENT '状态值(-1失效，0默认值)',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0001', '管理员', 'a0000', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('0002', '学生', 's0000', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('0003', '教师', 't0000', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                             `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
                             `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
                             `work_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号/工号',
                             `user_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户类型/账号类型',
                             `user_pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
                             `user_department` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门/学院',
                             `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级名称',
                             `start_year` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入学年份(即为学生年级)',
                             `education_system` int(11) NULL DEFAULT NULL COMMENT '学生学制',
                             `train_level` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培养层次',
                             `user_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师职称',
                             `lastPasswordResetDate` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改密码时间',
                             `create_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                             `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
                             `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             `status` int(11) UNSIGNED ZEROFILL NULL DEFAULT 00000000000 COMMENT '状态值(-1失效，0默认值)',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0001', 'zm', '周淼', '1120161969', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('0002', 'hl', '贺璐', '1120161981', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

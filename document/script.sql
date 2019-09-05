create table map_course_evaluation
(
    id                     varchar(100)  not null comment '主键id'
        primary key,
    student_grade          varchar(100)  null comment '学生年级',
    school_year            datetime      null comment '学年',
    index_number           varchar(100)  null comment '指标点number(e.g.4.2, 4.3)',
    index_proportion_value double        null comment '达成目标值(指标点系数)',
    evaluation_value       double        null comment '评价值(某课程针对其所需达成目标的实际评价值)',
    create_user_id         varchar(100)  null comment '创建人id',
    create_date            datetime      null comment '创建时间',
    modify_user_id         varchar(100)  null comment '修改人id',
    modify_date            datetime      null comment '修改日期',
    status                 int default 0 null comment '状态值(-1失效，0默认值)'
)
    comment '课程-指标点-评价值关联表';

create table map_course_index
(
    id               varchar(100)              not null comment '主键id'
        primary key,
    course_id        varchar(100)              null comment '课程id，from sys_course 表',
    index_id         varchar(100)              null comment '指标要求id ，from sys_index 表',
    proportion_value double                    null comment '达成目标值，指标系数之和=1',
    statistic_year   varchar(100)              null comment '统计年份(示例：2014-2015学年)',
    create_user_id   varchar(100)              null comment '创建人id',
    create_date      datetime                  null comment '创建时间',
    modify_user_id   varchar(100)              null comment '修改人id',
    modify_date      datetime                  null comment '修改时间',
    status           int(11) unsigned zerofill null comment '状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)'
)
    comment '课程-毕业要求(指标点)-关联表';

create table map_course_weeklen
(
    id               varchar(100)              not null comment '主键id'
        primary key,
    course_id        varchar(100)              null comment '课程id',
    week_number      int                       null comment '周次',
    week_average_len int                       null comment '课程平均学时',
    remarks          varchar(255)              null comment '字典项备注',
    create_user_id   varchar(100)              null comment '创建人id',
    create_date      datetime                  null comment '创建时间',
    modify_user_id   varchar(100)              null comment '修改人id',
    modify_date      datetime                  null comment '修改时间',
    status           int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '课程-平均周时关联表';

create table map_role_power
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    role_id        varchar(100)              null comment '角色id from sys_role表',
    power_id       varchar(100)              null comment '权限id from sys_power 表',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '角色-权限关联表';

create table map_student_course
(
    id                         varchar(100)              not null comment '主键id'
        primary key,
    user_work_id               varchar(100)              null comment '学生work_id from sys_user 表',
    course_select_number       varchar(100)              null comment '课程选课课号 - from map_teacher_course 表
(学期-课程编号-教师工号-该师该学期第几门课)',
    total_grade                double                    null comment '总成绩',
    input_user_name            varchar(100)              null comment '成绩录入人名称',
    grade_sign                 varchar(100)              null comment '成绩标志',
    exam_nature                varchar(100)              null comment '考试性质',
    supplement_repeat_semester varchar(100)              null comment '补重学期',
    create_user_id             varchar(100)              null comment '创建人id',
    create_date                datetime                  null comment '创建时间',
    modify_user_id             varchar(100)              null comment '修改人id',
    modify_date                datetime                  null comment '修改时间',
    status                     int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '学生-课程关联表';

create table map_student_credit
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    user_id        varchar(100)              null comment '学生的id - from sys_user 表',
    credit_id      varchar(100)              null comment '小分id - from sys_credit 表',
    credit_value   double                    null comment '某一项得分数值',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '学生-课程小分(弃用)';

create table map_student_evaluation
(
    id                   varchar(100)              not null comment '主键id'
        primary key,
    work_id              varchar(100)              null comment '学生/教师工号',
    course_select_number varchar(100)              null comment '选课课号(学期-课程编号-教师工号-该师该学期第几门课)',
    index_id             varchar(100)              null comment '指标要求id ，from sys_index 表',
    comment_value        int                       null comment '课程指标点评价值(0->4)',
    create_user_id       varchar(100)              null comment '创建人id',
    create_date          datetime                  null comment '创建时间',
    modify_user_id       varchar(100)              null comment '修改人id',
    modify_date          datetime                  null comment '修改时间',
    status               int(11) unsigned zerofill null comment '状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)'
)
    comment '学生指标点评价表';

create table map_teacher_course
(
    id                    varchar(100)              not null comment '主键id'
        primary key,
    user_work_id          varchar(100)              null comment '教师work_id - from sys_user 表',
    course_id             varchar(100)              null comment '课程id - from sys_course 表',
    course_select_number  varchar(100)              null comment '选课课号(学期-课程编号-教师工号-该师该学期第几门课)',
    course_campus         varchar(100)              null comment '开课校区',
    course_area           varchar(100)              null comment '功能区(上课地点)',
    course_class          varchar(100)              null comment '讲课班级名称',
    course_elect_number   int                       null comment '选课人数',
    course_arrange_number int                       null comment '排课人数',
    course_teache_week    varchar(100)              null comment '讲课周次',
    week_len              int                       null comment '课程周学时',
    plan_len              int                       null comment '安排学时',
    teach_len             int                       null comment '课程讲课学时',
    practice_len          int                       null comment '课程实践学时',
    create_user_id        varchar(100)              null comment '创建人id',
    create_date           datetime                  null comment '创建时间',
    modify_user_id        varchar(100)              null comment '修改人id',
    modify_date           datetime                  null comment '修改时间',
    status                int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '教师-课程对应表';

create table map_user_role
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    user_id        varchar(100)              null comment '用户id from sys_user 表',
    role_id        varchar(100)              null comment '角色id，from sys_role 表',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '用户-角色关联表';

create table sys_course
(
    id                    varchar(100)              not null comment '主键id'
        primary key,
    course_name           varchar(200)              null comment '课程名称',
    course_credit         double                    null comment '课程学分',
    course_number         varchar(100)              null comment '课程代码/编码',
    course_semester       varchar(100)              null comment '开课学年学期(示例：2018-2019-1)',
    course_department     varchar(100)              null comment '课程开课单位/院系/部门',
    course_route          varchar(100)              null comment '课程所在路线',
    course_character      varchar(100)              null comment '课程性质/属性',
    course_type           varchar(100)              null comment '课程体系/类别',
    course_kind           varchar(100)              null comment '课程种类(文学与艺术)',
    course_attribution    varchar(100)              null comment '课程归属(文化素质通识课)',
    assess_method         varchar(100)              null comment '课程考核方式',
    type_Identification   varchar(100)              null comment '培养环节类别标识',
    module_Identification varchar(100)              null comment '模块与层次标识',
    is_substitute         int                       null comment '是否可用高层次课程代替代课程(-1不可，0可)',
    total_len             int                       null comment '课程总学时',
    semester_len          int                       null comment '课程学期学时(一般和课程总学时相同)',
    experiment_len        int                       null comment '课程上机/实验学时',
    remarks               varchar(255)              null comment '备注',
    create_user_id        varchar(100)              null comment '创建人id',
    create_date           datetime                  null comment '创建时间',
    modify_user_id        varchar(100)              null comment '修改人id',
    modify_date           datetime                  null comment '修改时间',
    status                int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '课程表';

create table sys_credit
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    credit_name    varchar(100)              null comment '某一项得分名称',
    course_id      varchar(100)              null comment '对应课程的id - from sys_course 表',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '某课程学分统计表(包含多少项)';

create table sys_dict
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    dict_name      varchar(100)              null comment '字典项名称',
    type_id        varchar(100)              null comment '字典类型 from 字典类型表sys_dict_type',
    sort           int                       null comment '字典项排序值',
    parent_id      varchar(100)              null comment '父字典项 from 字典表',
    remarks        varchar(255)              null comment '字典项备注',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '字典表';

create table sys_dict_type
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    type_name      varchar(100)              null comment '字典类型名',
    sort           int                       null comment '字典类型排序值',
    parent_id      varchar(100)              null comment '父字典类型 from 字典类型表',
    remarks        varchar(200)              null comment '字典类型说明',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '字典项类型表';

create table sys_index
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    index_content  varchar(255)              null comment '指标要求内容',
    parent_id      varchar(100)              null comment '指标要求父类型 - from sys_index 表',
    sort           int                       null comment '指标要求序号',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '毕业要求指标点表(责任教授/教学干事上传)';

create table sys_log
(
    id         varchar(100) not null comment '日志编号'
        primary key,
    log_module varchar(50)  null comment '日志模块',
    request_ip varchar(20)  null comment '请求IP',
    user_id    varchar(100) null comment '用户id from sys_user',
    role_name  varchar(100) null comment '角色名称',
    user_name  varchar(100) null comment '用户名称',
    log_action varchar(100) null comment '操作行为',
    log_result varchar(100) null comment '请求结果',
    log_time   datetime     null comment '请求时间'
)
    comment '用户操作日志表';

create table sys_log_action
(
    id            varchar(100) not null comment '权限id'
        primary key,
    action_url    varchar(100) null comment 'URL',
    action_module varchar(100) null comment '操作所属的模块',
    action_desc   varchar(100) null comment '动作描述'
)
    comment '用户日志记录的请求初始表';

create table sys_power
(
    id             varchar(100)              not null comment '主键'
        primary key,
    power_name     varchar(100)              null comment '权限名称',
    power_code     varchar(100)              null comment '授权代码',
    parent_id      varchar(255)              null comment '父权限id from sys_power 表',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '权限表';

create table sys_role
(
    id             varchar(100)              not null comment '主键'
        primary key,
    role_name      varchar(100)              null comment '角色名称',
    role_code      varchar(100)              null comment '角色授权码',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '角色表';

create table sys_user
(
    id                    varchar(100)              not null comment '主键'
        primary key,
    user_name             varchar(100)              null comment '用户名',
    real_name             varchar(100)              null comment '真实姓名',
    work_id               varchar(100)              null comment '学号/工号',
    user_type             varchar(100)              null comment '用户类型/账号类型',
    user_pwd              varchar(100)              null comment '用户密码',
    user_department       varchar(100)              null comment '所属部门/学院',
    class_name            varchar(100)              null comment '班级名称',
    start_year            varchar(100)              null comment '入学年份',
    education_system      int                       null comment '学生学制',
    train_level           varchar(100)              null comment '培养层次',
    user_title            varchar(100)              null comment '教师职称',
    lastPasswordResetDate datetime                  null comment '最后一次修改密码时间',
    create_user_id        varchar(100)              null comment '创建人id',
    create_date           datetime                  null comment '创建时间',
    modify_user_id        varchar(100)              null comment '修改人id',
    modify_date           datetime                  null comment '修改时间',
    status                int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '系统用户表';


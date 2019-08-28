create table map_course_index
(
    id               varchar(100)              not null comment '主键id'
        primary key,
    course_id        varchar(100)              null comment '课程id，from sys_course 表',
    index_id         varchar(100)              null comment '指标要求id ，from sys_index 表',
    proportion_value double                    null comment '该课程占该指标要求的 系数比重，指标系数之和=1',
    statistic_year   varchar(100)              null comment '统计年份(示例：2014-2015学年)',
    create_user_id   varchar(100)              null comment '创建人id',
    create_date      datetime                  null comment '创建时间',
    modify_user_id   varchar(100)              null comment '修改人id',
    modify_date      datetime                  null comment '修改时间',
    status           int(11) unsigned zerofill null comment '状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)'
)
    comment '课程-毕业要求(指标点)-关联表';

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

create table map_user_course
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    user_id        varchar(100)              null comment '用户id from sys_user 表',
    course         varchar(100)              null comment '课程id，from sys_course 表',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '教师-课程对应表';

create table map_user_credit
(
    id             varchar(100)              not null comment '主键id'
        primary key,
    user_id        varchar(100)              null comment '对应学生的id from sys_user 表',
    credit_value   double                    null comment '某一项得分数值',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '用户-课程小分关联表';

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
    course_type           varchar(100)              null comment '课程类别',
    course_character      varchar(100)              null comment '课程性质',
    course_number         varchar(100)              null comment '课程代码',
    course_name           varchar(200)              null comment '课程名称',
    course_credit         double                    null comment '课程学分',
    course_len            int                       null comment '课程学时',
    teach_len             int                       null comment '讲课学时',
    experiment_len        int                       null comment '实验学时',
    practice_len          int                       null comment '上机学时',
    type_Identification   varchar(100)              null comment '培养环节类别标识',
    module_Identification varchar(100)              null comment '模块与层次标识',
    is_substitute         int                       null comment '是否可用高层次课程代替代课程(-1不可，0可)',
    d1_len                int                       null comment '第1周平均学时',
    d2_len                int                       null comment '第2周平均学时',
    d3_len                int                       null comment '第3周平均学时',
    d4_len                int                       null comment '第4周平均学时',
    d5_len                int                       null comment '第5周平均学时',
    d6_len                int                       null comment '第6周平均学时',
    d7_len                int                       null comment '第7周平均学时',
    d8_len                int                       null comment '第8周平均学时',
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
    course_id      varchar(100)              null comment '对应课程的id from sys_course 表',
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
    parent_id      varchar(100)              null comment '指标要求父类型 from 指标要求表',
    sort           int                       null comment '指标要求序号',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '毕业要求指标点表(责任教授/教学干事 上传)';

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
    id             varchar(100)              not null comment '主键'
        primary key,
    user_name      varchar(100)              null comment '用户名',
    user_type      varchar(100)              null comment '用户类型(字典项)',
    user_pwd       varchar(100)              null comment '用户密码',
    work_id        varchar(100)              null comment '学号/工号',
    create_user_id varchar(100)              null comment '创建人id',
    create_date    datetime                  null comment '创建时间',
    modify_user_id varchar(100)              null comment '修改人id',
    modify_date    datetime                  null comment '修改时间',
    status         int(11) unsigned zerofill null comment '状态值(-1失效，0默认值)'
)
    comment '系统用户表';



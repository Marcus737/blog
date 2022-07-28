create table classify
(
    classify_id int auto_increment
        primary key,
    name        char(60)                           not null comment '标签名字',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '标签创建时间'
)
    comment '分类';


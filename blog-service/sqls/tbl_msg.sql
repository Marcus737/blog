create table msg
(
    id         int auto_increment
        primary key,
    content    varchar(255)                        not null,
    created_at timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    username   varchar(20)                         not null,
    avatar     varchar(255)                        not null,
    constraint msg_username_uindex
        unique (username)
);


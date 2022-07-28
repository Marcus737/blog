create table user
(
    user_id    int auto_increment
        primary key,
    username   char(60)                            not null comment '用户名',
    account    varchar(60)                         null,
    password   char(70)                            not null comment '密码',
    about_me   varchar(255)                        null comment '个人简介',
    birthday   timestamp                           null comment '生日',
    avatar     char(255)                           null comment '头像',
    motto      varchar(255)                        null comment '座右铭',
    created_at timestamp default CURRENT_TIMESTAMP not null comment '注册时间'
);

INSERT INTO tbl.user (user_id, username, account, password, about_me, birthday, avatar, motto, created_at) VALUES (1, 'whenyouregone', null, '$2a$10$vXwNshaH7X1AwEGrN4vWJuBWzY9KMnTTuLuWqExPfAHSIQEZZMa3K', '一个努力成为不器的人', null, 'https://img2.woyaogexing.com/2022/07/04/02e6149641b7d38a!400x400.jpg', '读史书', '2022-07-23 15:46:25');
INSERT INTO tbl.user (user_id, username, account, password, about_me, birthday, avatar, motto, created_at) VALUES (2, 'anonymousUser', null, '123', null, null, null, null, '2022-07-23 16:02:53');
INSERT INTO tbl.user (user_id, username, account, password, about_me, birthday, avatar, motto, created_at) VALUES (3, 'live', null, '$2a$10$elnHdqfkAm6VphCYL1DphumeDhqnUScZ8es1R/tKcRkLTIDsJ2kym', null, null, null, null, '2022-07-23 19:19:46');
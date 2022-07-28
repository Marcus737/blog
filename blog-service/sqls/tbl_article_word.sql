create table article_word
(
    id         int auto_increment
        primary key,
    floor_id   int       default 0                 null comment '楼层id',
    content    varchar(255)                        not null comment '评论详情',
    created_at timestamp default CURRENT_TIMESTAMP not null comment '评论时间',
    article_id int                                 not null comment '文章id',
    user_id    int                                 not null comment '用户id',
    constraint article_word_article_article_id_fk
        foreign key (article_id) references article (id)
            on update cascade on delete cascade,
    constraint article_word_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on update cascade on delete cascade
)
    comment '用户评论表';


create table user_article_like
(
    user_id    int not null,
    article_id int null,
    constraint user_article_like_user_id_article_id_uindex
        unique (user_id, article_id),
    constraint user_article_like_article_id_fk
        foreign key (article_id) references article (id)
            on update cascade on delete cascade,
    constraint user_article_like_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on update cascade on delete cascade
);

INSERT INTO tbl.user_article_like (user_id, article_id) VALUES (3, 1);
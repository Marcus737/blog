create table user_role
(
    user_id int not null,
    role_id int not null,
    constraint user_role_role_role_id_fk
        foreign key (role_id) references role (role_id)
            on update cascade on delete cascade,
    constraint user_role_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on update cascade on delete cascade
);

INSERT INTO tbl.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tbl.user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tbl.user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO tbl.user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO tbl.user_role (user_id, role_id) VALUES (3, 2);
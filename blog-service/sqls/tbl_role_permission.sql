create table role_permission
(
    role_id       int null,
    permission_id int null,
    constraint role_permission_permission_permission_id_fk
        foreign key (permission_id) references permission (permission_id)
            on update cascade on delete cascade,
    constraint role_permission_role_role_id_fk
        foreign key (role_id) references role (role_id)
            on update cascade on delete cascade
);

INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (2, 4);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (2, 8);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (2, 12);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (2, 18);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 7);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 10);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 11);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 16);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 19);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (1, 20);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 2);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 3);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 9);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 13);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 14);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 15);
INSERT INTO tbl.role_permission (role_id, permission_id) VALUES (3, 17);
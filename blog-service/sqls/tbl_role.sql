create table role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(255) not null
);

INSERT INTO tbl.role (role_id, role_name) VALUES (1, 'admin');
INSERT INTO tbl.role (role_id, role_name) VALUES (2, 'account');
INSERT INTO tbl.role (role_id, role_name) VALUES (3, 'unknown');
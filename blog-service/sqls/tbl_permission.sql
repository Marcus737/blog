create table permission
(
    permission_id   int auto_increment
        primary key,
    permission_name varchar(255) not null
);

INSERT INTO tbl.permission (permission_id, permission_name) VALUES (1, '/**');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (2, '/article/getArticleList');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (3, '/article/getArticleDetail');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (4, '/article/likeArticle');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (5, '/article/addArticle');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (6, '/article/updateArticle');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (7, '/article/delArticle');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (8, '/articleWord/addArticleWord');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (9, '/articleWord/getArticleWordList');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (10, '/articleWord/getArtWordList');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (11, '/articleWord/delArticleWord');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (12, '/user/logout');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (13, '/user/login');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (14, '/msg/getMsgList');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (15, '/msg/addMsg');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (16, '/msg/delMsg');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (17, '/user/createUser');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (18, '/user/whoami');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (19, '/user/getHomeInfo');
INSERT INTO tbl.permission (permission_id, permission_name) VALUES (20, '/user/getUserByUsername/**');
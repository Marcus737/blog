create table controllerlog
(
    id          int auto_increment
        primary key,
    user_id     int                                 null comment '用户id',
    description text                                null,
    method      varchar(255)                        null comment '方法名',
    params      varchar(255)                        null comment '方法参数
',
    request_ip  char(25)                            null comment '请求的ip',
    username    varchar(255)                        null comment '用户名',
    browser     varchar(255)                        null comment '浏览器',
    create_date timestamp default CURRENT_TIMESTAMP null comment '日志创建时间',
    time        bigint                              null comment '执行时间',
    uri         varchar(255)                        null comment '请求路径',
    type        varchar(255)                        null
);

INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (1, null, null, 'POST', null, '127.0.0.1', 'anonymousUser', 'MSEdge', '2022-07-23 18:55:58', 6, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (2, null, null, 'POST', null, '127.0.0.1', 'anonymousUser', 'MSEdge', '2022-07-23 18:56:34', 2, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (3, null, null, 'POST', null, '127.0.0.1', 'anonymousUser', 'MSEdge', '2022-07-23 18:58:51', 91686, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (4, null, null, 'POST', null, '127.0.0.1', 'anonymousUser', 'MSEdge', '2022-07-23 19:00:41', 85487, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (5, null, null, 'POST', null, '127.0.0.1', 'whenyouregone', 'MSEdge', '2022-07-23 19:01:20', 40018, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (6, null, null, 'POST', null, '127.0.0.1', 'whenyouregone', 'MSEdge', '2022-07-23 19:01:50', 201, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (7, null, null, 'POST', null, '127.0.0.1', 'whenyouregone', 'MSEdge', '2022-07-23 19:08:52', 182, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (8, null, null, 'POST', null, '127.0.0.1', 'whenyouregone', 'MSEdge', '2022-07-23 19:18:40', 82, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (9, null, null, 'POST', null, '127.0.0.1', 'live', 'MSEdge', '2022-07-23 19:19:53', 97, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (10, null, null, 'POST', null, '127.0.0.1', 'whenyouregone', 'MSEdge', '2022-07-23 19:25:04', 172, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (11, null, null, 'POST', null, '127.0.0.1', 'live', 'MSEdge', '2022-07-23 19:29:39', 95, '/user/login', 'INFO');
INSERT INTO tbl.controllerlog (id, user_id, description, method, params, request_ip, username, browser, create_date, time, uri, type) VALUES (12, null, null, 'POST', null, '127.0.0.1', 'whenyouregone', 'MSEdge', '2022-07-23 20:06:53', 81, '/user/login', 'INFO');
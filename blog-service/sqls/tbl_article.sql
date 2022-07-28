create table article
(
    id          int auto_increment comment '文章id'
        primary key,
    user_id     int                                 not null comment '作者用户id',
    img_url     char(255)                           not null comment '文章图片',
    music_name  varchar(100)                        null comment '文章配的音乐',
    title       char(60)                            not null comment '文章标题',
    description varchar(255)                        not null comment '文章描述',
    visits_num  int       default 0                 not null comment '访问次数',
    like_num    int       default 0                 not null comment '点赞次数',
    created_at  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at  timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    content     text                                not null comment '文章内容',
    classify_id int                                 null,
    music_url   varchar(255)                        null,
    constraint article_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on update cascade on delete cascade
);

create index article_classify_classify_id_fk
    on article (classify_id);

INSERT INTO tbl.article (id, user_id, img_url, music_name, title, description, visits_num, like_num, created_at, updated_at, content, classify_id, music_url) VALUES (1, 1, 'http://127.0.0.1:2689/download?code=MTI3LjAuMC4xXGNiOTk3MDg0LWUxZjQtNGMyYi1iMjgyLTJkMmE5MDFhOGY0My0tMDY1MmY5MjQ5MGEwZGJjMTVlNmI5ZjE5YWUwZTlkYzAuanBlZw==', '莫失莫忘', 'Centos安装Java', 'Centos安装Java', 6, 1, '2022-07-23 19:18:47', '2022-07-23 19:18:47', '## 1.检查是否安装java
`rpm -qa  | grep java`
## 2.卸载自带的openjdk
`yum -y remove java-1.*`

## 3.上传压缩包 
> 下载好jdk，用xftp上传到opt

## 4.解压
`tar -zxvf xxxx.tar.gz`

## 5.配置环境变量

```xml
vi /etc/profile.d/hadoop-eco.sh
 
输入以下代码：
JAVA_HOME=/opt/jdk
PATH=$JAVA_HOME/bin:$PATH

使配置生效
source /etc/profile.d/hadoop-eco.sh
```


Linux中修改环境变量及生效方法（永久、临时）环境变量查看
https://blog.csdn.net/u011630575/article/details/49839893



', null, 'http://127.0.0.1:2689/download?code=MTI3LjAuMC4xXDkwZmY3YjU3LTZmZjMtNDVhMC1hZWY0LWQ1MTc1MTBmM2ZhNS0t5paH5q2m6LSdIC0g6I6r5aSx6I6r5b+YLm1wMw==');
CREATE DATABASE client;
create table client_user_info
(
  id           bigint auto_increment
    primary key,
  name         varchar(50)   default ''                not null,
  access_token varchar(1000) default ''                not null,
  expires      datetime      default CURRENT_TIMESTAMP not null,
  detail       text                                    null,
  password varchar(100) default '' not null,
  refresh_token varchar(100) default  '' not null
);


drop table if exists production;
  create table production(
  house_id int(10) not null primary key,
station varchar(128),
plan varchar(128) ,
actual varchar(128),
roa varchar(1024) ,
opr varchar(128) ,
create_time datetime not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
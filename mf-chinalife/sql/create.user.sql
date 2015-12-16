drop table if exists `user`;
create table user(
user_id int(10) not null primary key auto_increment,
user_nickname varchar(64) not null,
user_pwd varchar(64) not null,
user_email varchar(64) not null,
user_category varchar(64) not null,
create_time datetime not null,
update_time timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



{"sn", "distinguishedName", "sAMAccountName", "mail","title",
            "telephoneNumber", "whenCreated", "whenChanged", "proxyAddress", "physicalDeliveryOfficeName",
            "name", "mailNickname", "givenName", "cn", "company", "userAccountControl", "msExchUserCulture",
            "department", "displayName", "description", "userPrincipalName"};
create table DomainUser(
  user_id int(10) not null primary key auto_increment,
  sn VARCHAR(128),
  distinguishedName VARCHAR(128),
  sAMAccountName VARCHAR(128),
  mail VARCHAR(128),
  title VARCHAR(128),
  telephoneNumber VARCHAR(128),
  whenCreated VARCHAR(128),
  whenChanged VARCHAR(128),
  proxyAddress VARCHAR(128),
  physicalDeliveryOfficeName VARCHAR(128),
  name VARCHAR(128),
  mailNickname VARCHAR(128),
  givenName VARCHAR(128),
  cn VARCHAR(128),
  company(128),
  userAccountControl VARCHAR(128),
  msExchUserCulture VARCHAR(128),
  department VARCHAR(128),
  displayName VARCHAR(128),
  description VARCHAR(128),
  userPrincipalName VARCHAR(128),
  cn VARCHAR(128),
  userRole varchar(128),
  userGroup varchar(128)
  temp1 VARCHAR(128),
  temp2 VARCHAR(128),
  temp3 VARCHAR(128),
  temp4 VARCHAR(128),
  temp5 VARCHAR(128)
)
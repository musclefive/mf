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


/*MS SQL */
CREATE TABLE [dbo].[breakdown](
	[No] [int]  IDENTITY(1,1) NOT NULL primary key,
	[break_no] [int] NOT NULL,
	[break_level] [nchar](10) NULL,
	[break_reason] [varchar](100) NULL,
	[break_type] [nchar](10) NULL,
	[break_name] [varchar](10) NULL,
	[start_time] [datetime] NULL,
	[end_time] [datetime] NULL,
	[duration] [varchar](100) NULL,
	[temp1] [varchar](10) NULL,
	[temp2] [varchar](10) NULL
) ON [PRIMARY]

GO

alter table [dbo].[teampc11] add flag varchar(4) default (0)

update [dbo].[teampc11] set flag = '0'
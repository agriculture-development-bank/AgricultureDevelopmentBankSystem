create table ACT_GE_PROPERTY (
    NAME_ varchar(64),
    VALUE_ varchar(300),
    REV_ int,
    primary key (NAME_)
);

create table ACT_GE_BYTEARRAY (
    ID_ varchar(64),
    REV_ int,
    NAME_ varchar(255),
    DEPLOYMENT_ID_ varchar(64),
    BYTES_ BLOB,
    GENERATED_ TINYINT,
    primary key (ID_)
);

insert into ACT_GE_PROPERTY( "NAME_", "VALUE_", "REV_" )
values ('common.schema.version', '6.4.0.0', 1);

insert into ACT_GE_PROPERTY( "NAME_", "VALUE_", "REV_" )
values ('next.dbid', '1', 1);

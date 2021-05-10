create sequence hibernate_sequence start 1 increment 1;

create table messages
(
    id       int8 not null,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(2048) not null,
    user_id  int8 not null,
    primary key (id)
);

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table usrs
(
    id              int8 not null,
    activation_code varchar(255),
    email           varchar(255),
    is_active       boolean,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

alter table messages
    add constraint message_user_fk foreign key (user_id) references usrs;

alter table user_role
    add constraint user_role_user_fk foreign key (user_id) references usrs;

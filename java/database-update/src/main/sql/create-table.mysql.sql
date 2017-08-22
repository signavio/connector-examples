create table if not exists case_event (
    id integer primary key auto_increment,
    case_id varchar(24) not null,
    created timestamp not null,
    type varchar(16));

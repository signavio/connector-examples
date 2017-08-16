create table if not exists case_event (
    id serial primary key ,
    case_id varchar(24) not null,
    created timestamp not null,
    type varchar(16));

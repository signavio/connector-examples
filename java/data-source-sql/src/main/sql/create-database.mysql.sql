CREATE TABLE if not exists products (
    id integer PRIMARY KEY NOT NULL,
    name varchar(24) NOT NULL,
    productRange varchar(16),
    added date NOT NULL);

INSERT INTO products (id, name, productRange, added) values (1, 'Widget Basic', 'bronze', CURRENT_DATE);
INSERT INTO products (id, name, productRange, added) values (2, 'Widget', 'silver', CURRENT_DATE);
INSERT INTO products (id, name, productRange, added) values (3, 'Widget Pro', 'gold', CURRENT_DATE);

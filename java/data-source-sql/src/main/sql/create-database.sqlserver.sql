CREATE TABLE products (
    id int PRIMARY KEY NOT NULL,
    name varchar(24) NOT NULL,
    range varchar(16),
    added date NOT NULL)

GO

INSERT INTO products (id, name, range, added) values (1, 'Widget Basic', 'bronze', CURRENT_DATE);
INSERT INTO products (id, name, range, added) values (2, 'Widget', 'silver', CURRENT_DATE);
INSERT INTO products (id, name, range, added) values (3, 'Widget Pro', 'gold', CURRENT_DATE);

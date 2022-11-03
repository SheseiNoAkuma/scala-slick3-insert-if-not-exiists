CREATE TABLE IF NOT EXISTS example_table
(
    id        uuid         not null,
    name      VARCHAR(255) not null,
    date_time timestamp    not null,
    note      text,
    primary key (id)
);

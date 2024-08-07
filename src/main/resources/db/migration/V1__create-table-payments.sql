CREATE TABLE payments(
    id bigint(20) not null auto_increment,
    amount decimal(19,2) not null,
    name varchar(100) not null,
    number varchar(19) not null,
    expiration varchar(7) not null,
    code varchar(3) not null,
    status varchar(255) not null,
    order_id bigint(20) not null,
    payment_method varchar(20) not null,

    primary key (id)
);

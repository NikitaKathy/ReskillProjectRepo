drop database if exists prod_db;
create database prod_db;
use prod_db;

create table product (
prod_id varchar(30) not null,
product_name varchar(100),
price float,
stock int,
description varchar(500),
image varchar(50),
seller_id varchar(30),
category varchar(30),
sub_category varchar(30),
product_rating float,

constraint product_pk primary key (prod_id)
);

create table subscribed_product (
buyer_id varchar(30),
prod_id varchar(30),
quantity int,

constraint subs_prod_pk primary key (buyer_id,prod_id)
);
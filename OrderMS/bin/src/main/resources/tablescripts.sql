drop database if exists order_db;
create database order_db;
use order_db;
create table order_table(
order_id varchar(30) primary key,
buyer_id varchar(30),
amount float,
date Date,
address varchar(50),
status varchar(20))
;
create table products_ordered(
buyer_id varchar(30),
product_id varchar(30),
seller_id varchar(30),
quantity integer,
constraint products_ordered_pk primary key (product_id,buyer_id)
 );
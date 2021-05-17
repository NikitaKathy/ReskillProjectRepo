drop database if exists user_db;
create database user_db;
use user_db;

create table buyer (
buyer_id varchar(30) not null,
name varchar(30),
email varchar(30),
phone_number varchar(10),
password varchar(60),
is_privileged varchar(5),
reward_points varchar(20),
is_active varchar(5),

constraint buyer_pk primary key (buyer_id)

);


create table seller (
seller_id varchar(30) not null,
name varchar(30),
email varchar(30),
phone_number varchar(10),
password varchar(60),
is_active varchar(5),

constraint seller_pk primary key (seller_id)

);

create table wishlist (
buyer_id varchar(30),
prod_id varchar(30),


constraint wishlist_pk primary key (prod_id,buyer_id)

);

create table cart (
buyer_id varchar(30),
prod_id varchar(30),
quantity int,


constraint wishlist_pk primary key (prod_id,buyer_id)

);

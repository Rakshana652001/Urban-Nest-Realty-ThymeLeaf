create schema UrbanNestRealty;
use UrbanNestRealty;

create table user
(
	id varchar(20) primary key,
	name varchar(20),
    phone_number varchar(20) , 
    designation varchar(20),
    email_id varchar(30),
    password varchar(20),
    address varchar(50),
    district varchar(20),
    state varchar(20),
    deleted_User boolean default false    
);


create table property_registration
(
	seq_no int primary key auto_increment,
	seller_id varchar(20),
    property_name varchar(20),
    property_id varchar(20),
    approval varchar(20) not null,
    property_images  blob ,
    property_document  blob ,
    property_price varchar(20),
    property_address varchar(200),
    property_district varchar(20),
    property_state varchar(20),
    registered_date date,
    purchased_date date,
    customer_id varchar(20),
    register_status varchar(20) not null,
	payment_status varchar(20),
    deleted_User boolean default false,
    foreign key (seller_id)references user(id)
);


create table sales_record
(
	s_no int primary key auto_increment,
	customer_id varchar(20),
    seller_id varchar(20),
    government_id blob not null,
    approval varchar(20),
    property_address varchar(200) unique,
    payment_method varchar(20),
    total_amount varchar(30),
    payabel_amount varchar(30),
    customer_account varchar(20),
    seller_account varchar(20),
    payed_status varchar(20),
    purchased_date date,
    deleted_User boolean default false,
    foreign key(customer_id)references user(id),
    foreign key(seller_id)references property_registration(seller_id)
);


select * from user;
select * from property_registration;
select * from sales_record;
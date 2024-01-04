create database project_05;
use project_05;
# tao bang Users
create table Users(
                      user_id varchar(100) primary key ,
                      username varchar(100) unique ,
                      email varchar(255),
                      full_name varchar(100) not null ,
                      status bit,
                      password varchar(255),
                      avatar varchar(255),
                      phone varchar(15) unique,
                      address varchar(255) not null ,
                      create_at DATE ,
                      update_at DATE
);
# Tao bang Role
create table Role(
                     role_id bigint auto_increment primary key ,
                     role_name enum ('ADMIN','USER')
);
# Tao bang user_role
create table user_role(
    user_id varchar(100),
    role_id bigint,
    constraint fk_3 primary key (user_id,role_id),
    constraint fk_10 foreign key (user_id) references users(user_id),
    constraint fk_11 foreign key (role_id) references role(role_id)
);
# Tao bang Category
create table Category(
                         category_id bigint auto_increment primary key,
                         category_name varchar(100) not null ,
                         description text,
                         status bit
);
# Tao bang Products
create table products(
                         product_id bigint auto_increment primary key,
                         sku varchar(100) unique ,
                         product_name varchar(100) unique not null ,
                         description text,
                         unit_price decimal(10,2),
                         stock_quantity int check ( stock_quantity >= 0 ),
                         image varchar(255),
                         category_id bigint not null,
                         create_at date ,
                         update_at date,
                         constraint fk_12 foreign key (category_id) references category(category_id)
);
# tao bang orders
create table orders(
                       order_id bigint auto_increment primary key,
                       serial_number varchar(100) unique,
                       user_id varchar(100) not null,
                       constraint fk_1 foreign key (user_id) references Users(user_id),
                       total_price decimal(10,2),
                       status enum('WAITING','CONFIRM','DELIVERY','SUCCESS','CANCEL','DENIED'),
                       note varchar(100),
                       receive_name varchar(100),
                       receive_address varchar(255),
                       receive_phone varchar(15),
                       create_at date ,
                       received_at date
);

# bang orderDetail
create table order_detail(
                             order_id bigint,
                             product_id bigint,
                             constraint pk_orderDetail primary key (order_id,product_id),
                             constraint fk_2 foreign key (order_id) references orders(order_id),
                             constraint fk_3 foreign key (product_id) references products(product_id),
                             name varchar(100),
                             unit_price decimal(10,2),
                             order_quantity int check ( order_quantity > 0 )
);

# bang shopping cart
create table shopping_cart(
                              shopping_cart_id int auto_increment primary key,
                              product_id bigint not null,
                              user_id varchar(100) not null,
                              order_quantity int check ( order_quantity > 0 ),
                              constraint fk_4 foreign key (product_id) references products(product_id),
                              constraint fk_5 foreign key (user_id) references Users(user_id)
);

# bang address
create table Address(
                        address_id bigint auto_increment primary key,
                        user_id varchar(100),
                        full_address varchar(255),
                        phone varchar(15),
                        receive_name varchar(50),
                        constraint fk_6 foreign key (user_id) references Users(user_id)
);

# bang wish_list
create table wish_list(
                          wish_list_id bigint auto_increment primary key,
                          user_id varchar(100),
                          product_id bigint,
                          constraint fk_7 foreign key (user_id) references Users(user_id),
                          constraint fk_8 foreign key (product_id) references products(product_id)
);
# alter table products add foreign key (category_id) references category(category_id);
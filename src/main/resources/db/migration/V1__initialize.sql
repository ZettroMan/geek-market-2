create table users
(
    id       bigserial primary key,
    username varchar(30) not null,
    password varchar(80) not null,
    email    varchar(50) unique
);

create table roles
(
    id   serial primary key,
    name varchar(50) not null
);

create table users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

create table profiles
(
    id         bigserial primary key,
    user_id    bigint references users (id),
    firstname  varchar(255),
    secondname varchar(255),
    phone      varchar(255),
    birthyear  int check (birthyear > 1900),
    sex        char(1) check (sex in ('M', 'F')),
    city       varchar(255),
    address    varchar(255),
    hobbies    varchar(255)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('SOMETHING');

insert into users (username, password, email)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
       ('admin', '$2y$12$L/JBAATUVttRpVnsq4YzHeU9ojI/1BsLdAfkZvdF0wdqMkrwmGL32', 'admin@gmail.com');

insert into profiles (user_id, firstname, secondname, phone, birthyear, sex, city, address, hobbies)
values (1, 'Юзер', 'Обыкновенный', '+7976546513', '1980', 'F', 'Тверь', 'ул. Ленина, д. 42, кв. 31', 'Programming'),
       (2, 'Админ', 'Всемогущий', '+7999999999', '1999', 'M', 'Москва', 'Кутузовский проспект, д. 123, кв. 45', 'Longboarding');


insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2);

create table categories
(
    id    bigserial primary key,
    title varchar(255)
);

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       int,
    category_id bigint references categories (id)
);

create table orders
(
    id      bigserial primary key,
    user_id bigint references users (id),
    price   int,
    address varchar(1000)
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint references products (id),
    order_id          bigint references orders (id),
    price             int,
    price_per_product int,
    quantity          int
);

insert into categories (title)
values ('Food'),
       ('Notebook'),
       ('Smartphone');

insert into products (title, price, category_id)
values ('Bread', 1, 1),
       ('Samsung V100', 2, 3),
       ('Acer X1000', 3, 2);

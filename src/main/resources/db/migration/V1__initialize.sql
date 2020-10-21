create table users (
  id                    bigserial,
  username              varchar(30) not null,
  password              varchar(80) not null,
  email                 varchar(50) unique,
  primary key (id)
);

create table roles (
  id                    serial,
  name                  varchar(50) not null,
  primary key (id)
);

CREATE TABLE users_roles (
  user_id               bigint not null,
  role_id               int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN'), ('SOMETHING');

insert into users (username, password, email)
values
('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com');

insert into users_roles (user_id, role_id) values (1, 1), (1, 2);

create table categories (
    id                      bigserial primary key,
    title                   varchar(255)
);

create table products (
    id                      bigserial primary key,
    title                   varchar(255),
    price                   int,
    category_id             bigint references categories(id)
);

create table orders (
    id                      bigserial primary key,
    user_id                 bigint references users(id),
    price                   int,
    address                 varchar(1000)
);

create table order_items (
    id                      bigserial primary key,
    product_id              bigint references products(id),
    order_id                bigint references orders(id),
    price                   int,
    price_per_product       int,
    quantity                int
);

insert into categories (title)
values
'Food',
'Fashion',
'Auto',
'Culture events',
'Electronics',
'Home appliances',
'Sports',
'Healthcare',
'Beauty',
'Jewelry',
'Toys';

insert into products (title, price, category_id)
values
('Bread1', 50, 1),
('Bread2', 40, 1),
('Bread3', 60, 1),
('Men`s costume', 11000, 2),
('Hat', 3500, 2),
('Coat', 17000, 2),
('Fur coat', 250000, 2),
('Breaking pads', 6000, 3),
('Oil filter', 1300, 3),
('Air freshener', 250, 3),
('Compressor', 2670, 3),
('Theater spectacle', 3100, 4),
('Rock concert', 4500, 4),
('Visit to museum', 1200, 4),
('Computer', 40000, 5),
('Notebook', 65000, 5),
('Tablet computer', 18500, 5),
('Smartphone', 14000, 5),
('Musical center', 12500, 5),
('Audio player', 6400, 5),
('Wash machine', 21500, 6),
('Toster', 2350, 6),
('Mixer', 3450, 6),
('Blender', 6400, 6),
('Sandwich maker', 4300, 6),
('Refrigerator', 23650, 6),
('Gas panel', 8700, 6),
('Microwave oven', 6900, 6),
('Dumbbell', 6300, 7),
('Skates', 7200, 7),
('Skies', 14500, 7),
('Tennis rocket', 6800, 7),
('Stationary bike', 36800, 7),
('Massager', 800, 8),
('Blood pressure monitor', 5800, 8),
('Contact lenses', 560, 8),
('Glasses', 3560, 8),
('Slimming cream', 4600, 8),
('Perfume', 9700, 9),
('Toilet water', 4850, 9),
('Cologne', 2390, 9),
('Golden ring', 8790, 10),
('Golden earrings', 12350, 10),
('Golden necklace with emeralds', 350000, 10),
('Spinner', 570, 11),
('Tetris', 680, 11),
('Teddy bear', 2440, 11),
('Monopoly', 1500, 11),
('Water gun', 380, 11);

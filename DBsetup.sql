# noinspection SqlNoDataSourceInspectionForFile

Create table Components
(
    id   serial primary key,
    name varchar(64)
);

CREATE TABLE Parts
(
    id    serial primary key,
    name  varchar(64),
    count int not null
);

CREATE table Recipes
(
    id   serial primary key,
    name varchar(64),
    part int references Parts (id)
);
CREATE TABLE Recipes_components
(
    recipe     int references Recipes (id),
    components int references Components (id)
);

CREATE TABLE Batches
(
    id            serial primary key,
    employee_id   varchar(64),
    has_completed bool
);

CREATE TABLE Parts_Batches
(
    part_id  int references Parts (id),
    batch_id int references Batches (id),
    count    int default 1
);

CREATE TABLE BatchEvents(
  batch_id serial primary key,
  name varchar(64),
  description varchar(250),
  faulty bool,
  progression double,
  timestamp timestamp default now()
);

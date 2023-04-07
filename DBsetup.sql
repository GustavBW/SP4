create table batch
(
    id            bigint       not null
        primary key,
    employee_id   varchar(255) null,
    has_completed bit          not null
)
    collate = utf8mb4_0900_ai_ci;

create table batch_event
(
    id          bigint       not null
        primary key,
    description varchar(255) null,
    faulty      bit          not null,
    name        varchar(255) null,
    progression float        not null,
    timestamp   bigint       not null,
    batch_id    bigint       null,
    constraint FKyjxx8p88ot8hf8lee97i1mf1
        foreign key (batch_id) references batch (id)
)
    collate = utf8mb4_0900_ai_ci;

create table batch_event_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;

create table batch_part_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;

create table batch_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;

create table component
(
    id   bigint       not null
        primary key,
    name varchar(255) null
)
    collate = utf8mb4_0900_ai_ci;

create table component_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;

create table part
(
    id          bigint       not null
        primary key,
    count       int          null,
    description varchar(255) null,
    name        varchar(255) null,
    recipe_id   bigint       null
)
    collate = utf8mb4_0900_ai_ci;

create table batch_part
(
    id       bigint not null
        primary key,
    count    int    null,
    batch_id bigint null,
    part_id  bigint null,
    constraint FK9w5rpov9wrwioyooqaeujt0lc
        foreign key (part_id) references part (id),
    constraint FKf39c3w5mb84ys1jcs5mlmyoa
        foreign key (batch_id) references batch (id)
)
    collate = utf8mb4_0900_ai_ci;

create table part_batch_parts
(
    part_id        bigint not null,
    batch_parts_id bigint not null,
    primary key (part_id, batch_parts_id),
    constraint FKced3qwdev45qwpcekwsiffnfp
        foreign key (batch_parts_id) references batch_part (id),
    constraint FKjp9a4y3i3cvf77hio7nobv32q
        foreign key (part_id) references part (id)
)
    collate = utf8mb4_0900_ai_ci;

create table part_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;

create table recipe
(
    id           bigint not null
        primary key,
    part_made_id bigint null,
    constraint FKg3yk3n6mmxu6mbwfmv9lxo4qc
        foreign key (part_made_id) references part (id)
)
    collate = utf8mb4_0900_ai_ci;

create table component_recipes
(
    component_id bigint not null,
    recipes_id   bigint not null,
    primary key (component_id, recipes_id),
    constraint FK17yqfjpr78fct1lt575weq93r
        foreign key (component_id) references component (id),
    constraint FK4i4w6nr8gnnb4eekoo6gnniy8
        foreign key (recipes_id) references recipe (id)
)
    collate = utf8mb4_0900_ai_ci;

alter table part
    add constraint FKlrj8d21pgfpeu3sc0mu5p7y0h
        foreign key (recipe_id) references recipe (id);

create table recipe_component
(
    id           bigint not null
        primary key,
    count        int    not null,
    component_id bigint null,
    recipe_id    bigint null,
    constraint FK7se0bxglecj5iuc3qlhyfaetu
        foreign key (component_id) references component (id),
    constraint FK8tcesnqw1a5s0drf6hqms5b8v
        foreign key (recipe_id) references recipe (id)
)
    collate = utf8mb4_0900_ai_ci;

create table recipe_component_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;

create table recipe_components_required
(
    recipe_id              bigint not null,
    components_required_id bigint not null,
    primary key (recipe_id, components_required_id),
    constraint UK_d4u0skbvv2adpvqcjlxe3vijm
        unique (components_required_id),
    constraint FK6e21uh6sy3nd61lddd0lv623h
        foreign key (components_required_id) references recipe_component (id),
    constraint FKhiiuago2e6vjb32m291k2x7k0
        foreign key (recipe_id) references recipe (id)
)
    collate = utf8mb4_0900_ai_ci;

create table recipe_seq
(
    next_val bigint null
)
    collate = utf8mb4_0900_ai_ci;
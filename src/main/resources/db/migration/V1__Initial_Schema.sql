-- Создание таблицы app_user
create table if not exists app_user (
                                        lvl integer not null,
                                        xp integer not null,
                                        id uuid not null,
                                        team_id uuid,
                                        email varchar(255) not null unique,
                                        password varchar(255) not null,
                                        username varchar(255) not null unique,
                                        primary key (id)
);

-- Создание таблицы project
create table if not exists project (
                                       id uuid not null,
                                       name varchar(255) not null,
                                       primary key (id)
);

-- Создание таблицы role
create table if not exists role (
                                    id uuid not null,
                                    name varchar(255) not null unique,
                                    primary key (id)
);

-- Создание таблицы task
create table if not exists task (
                                    completed boolean not null,
                                    assigned_user_id uuid,
                                    id uuid not null,
                                    project_id uuid,
                                    description varchar(255),
                                    title varchar(255) not null,
                                    primary key (id)
);

-- Создание таблицы team
create table if not exists team (
                                    id uuid not null,
                                    name varchar(255),
                                    primary key (id)
);

-- Создание таблицы team_achievement
create table if not exists team_achievement (
                                                points integer not null,
                                                id uuid not null,
                                                team_id uuid,
                                                description varchar(255),
                                                name varchar(255) not null,
                                                primary key (id)
);

-- Создание таблицы user_project
create table if not exists user_project (
                                            project_id uuid not null,
                                            user_id uuid not null
);

-- Создание таблицы user_role
create table if not exists user_role (
                                         role_id uuid not null,
                                         user_id uuid not null
);

-- Создание таблицы user_achievement
create table if not exists user_achievement (
                                                points integer not null,
                                                id uuid not null,
                                                user_id uuid,
                                                description varchar(255),
                                                name varchar(255) not null,
                                                primary key (id)
);

-- Создание таблицы user_profile
create table if not exists user_profile (
                                            id uuid not null,
                                            user_id uuid unique,
                                            avatar_url varchar(255),
                                            background_color varchar(255),
                                            theme varchar(255),
                                            primary key (id)
);

-- Внешние ключи
alter table app_user add constraint fk_team foreign key (team_id) references team (id);
alter table task add constraint fk_task_project foreign key (project_id) references project (id);
alter table task add constraint fk_task_assigned_user foreign key (assigned_user_id) references app_user (id);
alter table team_achievement add constraint fk_team_achievement_team foreign key (team_id) references team (id);
alter table user_achievement add constraint fk_user_achievement_user foreign key (user_id) references app_user (id);
alter table user_project add constraint fk_user_project_user foreign key (user_id) references app_user (id);
alter table user_project add constraint fk_user_project_project foreign key (project_id) references project (id);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references app_user (id);
alter table user_role add constraint fk_user_role_role foreign key (role_id) references role (id);
alter table user_profile add constraint fk_user_profile_user foreign key (user_id) references app_user (id);

-- Индексы
create unique index idx_app_user_email on app_user(email);
create unique index idx_app_user_username on app_user(username);
create index idx_user_project_user_id on user_project(user_id);
create index idx_user_project_project_id on user_project(project_id);
create index idx_user_role_user_id on user_role(user_id);
create index idx_user_role_role_id on user_role(role_id);

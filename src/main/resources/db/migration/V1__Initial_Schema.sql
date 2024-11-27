-- V1__Initial_Schema.sql

-- Создание таблицы app_user, если она не существует
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

-- Создание таблицы project, если она не существует
create table if not exists project (
                                       id uuid not null,
                                       name varchar(255) not null,
                                       primary key (id)
);

-- Создание таблицы role, если она не существует
create table if not exists role (
                                    id uuid not null,
                                    name varchar(255) not null unique,
                                    primary key (id)
);

-- Создание таблицы task, если она не существует
create table if not exists task (
                                    completed boolean not null,
                                    assigned_user_id uuid,
                                    id uuid not null,
                                    project_id uuid,
                                    description varchar(255),
                                    title varchar(255) not null,
                                    primary key (id)
);

-- Создание таблицы team, если она не существует
create table if not exists team (
                                    id uuid not null,
                                    name varchar(255),
                                    primary key (id)
);

-- Создание таблицы team_achievement, если она не существует
create table if not exists team_achievement (
                                                points integer not null,
                                                id uuid not null,
                                                team_id uuid,
                                                description varchar(255),
                                                name varchar(255),
                                                primary key (id)
);

-- Создание таблицы user_project, если она не существует
create table if not exists user_project (
                                            project_id uuid not null,
                                            user_id uuid not null
);

-- Создание таблицы user_role, если она не существует
create table if not exists user_role (
                                         role_id uuid not null,
                                         user_id uuid not null
);

-- Создание таблицы user_achievement, если она не существует
create table if not exists user_achievement (
                                                points integer not null,
                                                id uuid not null,
                                                user_id uuid,
                                                description varchar(255),
                                                name varchar(255),
                                                primary key (id)
);

-- Создание таблицы user_profile, если она не существует
create table if not exists user_profile (
                                            id uuid not null,
                                            user_id uuid unique,
                                            avatar_url varchar(255),
                                            background_color varchar(255),
                                            theme varchar(255),
                                            primary key (id)
);

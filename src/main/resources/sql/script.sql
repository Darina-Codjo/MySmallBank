    create table account (
       uid varchar(255) not null,
        type varchar(255),
        balance real,
        holder_id varchar(255) not null,
        primary key (uid)
    )
 
    
    create table holder (
       id varchar(255) not null,
        birth_date date,
        city varchar(255),
        country varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        street varchar(255),
        zip_code varchar(255),
        primary key (id)
    )
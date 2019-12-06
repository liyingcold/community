CREATE TABLE public."user" (
    id serial NOT NULL,
    account_id varchar(100) NULL,
    "name" varchar(50) NULL,
    "token" varchar(36) NULL,
     gmt_create int8 NULL,
     gmt_modified int8 NULL,
     CONSTRAINT user_pk PRIMARY KEY (id)
);


CREATE TABLE public.weather (
     city varchar(80) NULL,
     temp_lo int4 NULL,
     prcp float4 NULL,
     "date" date NULL
);

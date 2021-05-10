create extension if not exists pgcrypto;

update usrs set password = crypt(password, gen_salt('bf', 8));
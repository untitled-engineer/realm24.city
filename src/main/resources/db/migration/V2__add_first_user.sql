insert into usrs (id, username, password, is_active, email)
    values (1, 'als', 'als', true, 'alex.slobodianiuk.84@gmail.com');

insert into user_role (user_id, roles)
    values (1, 'ROLE_USER'), (1, 'ROLE_ADMIN');
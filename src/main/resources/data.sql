INSERT INTO role (id, description, name) VALUES (NULL, 'Administrador', 'Administrador');
INSERT INTO role (id, description, name) VALUES (NULL, 'Usuario', 'Usuario');


INSERT INTO user (id, email, first_name, identity, last_name, password, username) VALUES (NULL, 'magg.4@hotmail.com', 'Miguel', '1090465924', 'Gonzalez', '$2a$04$n6WIRDQlIByVFi.5rtQwEOTAzpzLPzIIG/O6quaxRKY2LlIHG8uty', 'miguel');


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
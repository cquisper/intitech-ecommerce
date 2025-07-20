ALTER TABLE users_roles
ALTER
COLUMN role_id
SET ON DELETE
CASCADE;

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (first_name, last_name, email, password, mobile, enabled, photo, address, refresh_token)
VALUES ('Cristhian', 'Quispe', 'cquisper4@autonoma.edu.pe', '$2a$10$f6P6ZZ6vgmsztgdO0QZ74.eZjSi0giEltt98S3yy9isBrtAte8NN2',
        '123456789', true, null, 'Lima, Peru', null),
       ('Juan', 'Perez', 'juan@email.com', '$2a$10$oWVcz0lqpCSVpRK8AJWXhukTst2UyZZzPdPUITLkb1ckrF4vnM9mW', '987654321',
        true, null, 'Lima, Peru', null);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);
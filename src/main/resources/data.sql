INSERT INTO users (id, email, password)
VALUES ('9c2b5bdc-63ff-45c5-808e-460bb51fa695', 'admin@mail.com', '$2a$10$yJVZ4abDTTW9uB06MvW.Ku1OKGhrYQKYIUX7waPyZgKQ236D5BgOO');

INSERT INTO privileges (id, name)
VALUES ('1a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a', 'READ');
INSERT INTO privileges (id, name)
VALUES ('2a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a', 'WRITE');
INSERT INTO privileges (id, name)
VALUES ('3a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a', 'DELETE');

INSERT INTO roles (id, name)
VALUES ('83fd9323-0410-49fa-bd9d-ee4120a7a636', 'USER');
INSERT INTO roles (id, name)
VALUES ('8cf9f82d-a5df-4f3a-bba7-aa3fd5750379', 'ADMIN');

INSERT INTO roles_privileges (role_id, privileges_id)
VALUES ('83fd9323-0410-49fa-bd9d-ee4120a7a636', '1a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a');
INSERT INTO roles_privileges (role_id, privileges_id)
VALUES ('8cf9f82d-a5df-4f3a-bba7-aa3fd5750379', '1a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a');
INSERT INTO roles_privileges (role_id, privileges_id)
VALUES ('8cf9f82d-a5df-4f3a-bba7-aa3fd5750379', '2a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a');
INSERT INTO roles_privileges (role_id, privileges_id)
VALUES ('8cf9f82d-a5df-4f3a-bba7-aa3fd5750379', '3a0c2c9a-9c8a-4f8a-8a9a-3b1f8d2b3a1a');

INSERT INTO users_roles (user_id, role_id)
VALUES ('9c2b5bdc-63ff-45c5-808e-460bb51fa695', '8cf9f82d-a5df-4f3a-bba7-aa3fd5750379');
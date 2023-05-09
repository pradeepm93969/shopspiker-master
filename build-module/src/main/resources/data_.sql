INSERT INTO user_service.oauth_client_details
VALUES (1, now(), 'system', now(), 'system', 3600, 1,
'otp,login,registration', 'PASSWORD,REFRESH_TOKEN,REGISTRATION_TOKEN', 'CUSTOMER_WEB_CLIENT',
'CUSTOMER_WEB_CLIENT', 1, 86400, '', 'DEFAULT');

INSERT INTO user_service.role
VALUES ('ROLE_SUPER_ADMIN', now(), '1', now(), '1', 1, 'Super admin user'),
('ROLE_B2C_USER', now(), '1', now(), '1', 1, 'Customer');

INSERT INTO user_service.user
VALUES (1, now(), 'system', now(), 'system', 1, NULL,
'1989-05-20 04:00:00', 'admin@goli.pk', 0, 'admin', 'M', NULL, 0, 'admin', '2022-03-18 10:48:07',
'543306145', 971, 1, '{bcrypt}$2a$10$5GFDpMbXNKWs8j2XVpDJA.71VZJ2OWQ9chlI4k.Y0BrgDyS8S/gh6',
'DESKTOP-UNKNOWN', NULL, '971543306145', NULL, NULL, NULL);

INSERT INTO user_service.user_role
VALUES (1, 'ROLE_SUPER_ADMIN');
INSERT INTO user_service.user_role
VALUES (2, 'ROLE_B2C_USER');

INSERT INTO user_service.permission
VALUES (1, now(), 'system', now(), 'system', 1, 'security', 'MANAGE_SECURITY');
INSERT INTO user_service.permission
VALUES (2, now(), 'system', now(), 'system', 1, 'security', 'READ_SECURITY');
INSERT INTO user_service.permission
VALUES (3, now(), 'system', now(), 'system', 1, 'user', 'MANAGE_USER');
INSERT INTO user_service.permission
VALUES (4, now(), 'system', now(), 'system', 1, 'user', 'READ_USER');
INSERT INTO user_service.permission
VALUES (5, now(), 'system', now(), 'system', 1, 'vendor', 'MANAGE_VENDOR');
INSERT INTO user_service.permission
VALUES (6, now(), 'system', now(), 'system', 1, 'vendor', 'READ_VENDOR');


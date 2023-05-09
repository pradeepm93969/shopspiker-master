INSERT INTO shop_with_love.oauth_client_details  VALUES
(1, '3600', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system',
'OTP,LOGIN,REGISTRATION', 'PASSWORD,REFRESH_TOKEN,REGISTRATION_TOKEN', 'CUSTOMER_WEB_CLIENT',
'CUSTOMER_WEB_CLIENT', 1, '86400', '','DEFAULT');

INSERT INTO shop_with_love.role
VALUES ('ROLE_SUPER_ADMIN', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 'Super admin user'),
('ROLE_B2C_USER', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 'Customer');

INSERT INTO shop_with_love.user
VALUES (1, 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 1,
'admin@shopwithlove.com', 1, 'admin', NULL, 0, 'admin', NULL,
'{bcrypt}$2a$10$5GFDpMbXNKWs8j2XVpDJA.71VZJ2OWQ9chlI4k.Y0BrgDyS8S/gh6',
'', 1, 'DESKTOP'),
(2, 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 1,
'user@shopwithlove.com', 1, 'user', NULL, 0, 'user', NULL,
'{bcrypt}$2a$10$5GFDpMbXNKWs8j2XVpDJA.71VZJ2OWQ9chlI4k.Y0BrgDyS8S/gh6',
'', 1, 'DESKTOP');

INSERT INTO shop_with_love.user_role
VALUES (1, 'ROLE_SUPER_ADMIN'),
(2, 'ROLE_B2C_USER');

INSERT INTO shop_with_love.permission
VALUES ('MANAGE_SECURITY', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 'security', 'MANAGE_SECURITY'),
('READ_SECURITY', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 'security', 'READ_SECURITY'),
('MANAGE_CUSTOMER', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 'customer', 'MANAGE_CUSTOMER'),
('READ_CUSTOMER', 1, '2023-01-01 00:00:00', 'system', '2023-01-01 00:00:00', 'system', 'customer', 'READ_CUSTOMER');


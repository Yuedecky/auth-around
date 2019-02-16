INSERT INTO user (username,email, password, activated) VALUES ('admin', 'admin@mail.me', '$2a$10$4Wxp.KkisQe6O2nUaROiNukXEZ0BQ5I9GMY12D2CpoAEHsafchRTu', true);
INSERT INTO user (username,email, password, activated) VALUES ('user', 'user@mail.me', '$2a$10$fNwWQga..asdzVZtIAmSlOj7t7HYehh43n8w17eNPjGzreT7SxQ.m', true);
INSERT INTO user (username,email, password, activated) VALUES ('broad', 'broad@abc.com', '$2a$10$iLdx7MBXvpmfn9iYADb6yuI9IdG9bOVREUKwmuGzUUiahi.3c2sHq', true);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (username,authority) VALUES ('broad', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('user', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('admin', 'ROLE_ADMIN');
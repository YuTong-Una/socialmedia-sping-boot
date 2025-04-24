INSERT INTO users (user_name, phone_number, email, password, cover_image, biography)
VALUES 
('David', '0911000001', 'david@example.com', '$2a$10$7n9jX1l0a3gG5XaMYvXpW.h7X9rfjcT7wG3ZqLE6JdbXN1MZLo3eG', NULL, '熱愛程式與咖啡'),
('Emily', '0911000002', 'emily@example.com', '$2a$10$gYqz.f8bDZ3bGz2LKQUrM.lPzEMz/B6m9OcMRpB5FGpRH4d8ey3Ge', NULL, '旅行讓我放鬆'),
('Frank', '0911000003', 'frank@example.com', '$2a$10$OBCkWaEhdcV2W3MrcPGk1eRTrErY8yNxkZli9wK2VvMC04mr0tZ9K', NULL, '生活需要一點幽默'),
('Grace', '0911000004', 'grace@example.com', '$2a$10$6TVGHJKZRXvyeN0XXWIDFOk1Y9TTgAgYz1z2fqxhhIApIkHTMxWde', NULL, '設計是我的語言'),
('Henry', '0911000005', 'henry@example.com', '$2a$10$S6hhnL5Y9U0PAfXrlVTXROrFev4DxHtG/YqOz5b4tPjN8HpBzn7ku', NULL, '喜歡健身與閱讀');

INSERT INTO posts (user_id, content, image)
VALUES 
(1, '今天學了 Spring Boot 超有趣！', NULL),
(2, '在京都旅遊拍了好多照片', NULL),
(3, '你們都用哪個 text editor？', NULL),
(4, '剛設計完一組 UI 好有成就感！', NULL),
(5, '健身房今天滿滿人啊...', NULL);

INSERT INTO comment (user_id, post_id, content)
VALUES 
(5, 3, '我也剛開始學 Spring Boot！一起加油！'),
(4, 2, '期待看到你的旅遊攝影作品'),
(2, 1, '我用 VSCode 覺得還不錯用'),
(1, 4, 'UI 有圖嗎？我好奇！'),
(3, 5, '哈哈我也常這樣，尤其週五晚上');

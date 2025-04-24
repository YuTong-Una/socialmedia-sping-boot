INSERT INTO users (user_name, phone_number, email, password, cover_image, biography)
VALUES 
('David', '0911000001', 'david@example.com', '$2a$10$7n9jX1l0a3gG5XaMYvXpW.h7X9rfjcT7wG3ZqLE6JdbXN1MZLo3eG', NULL, '���R�{���P�@��'),
('Emily', '0911000002', 'emily@example.com', '$2a$10$gYqz.f8bDZ3bGz2LKQUrM.lPzEMz/B6m9OcMRpB5FGpRH4d8ey3Ge', NULL, '�Ȧ����ک��P'),
('Frank', '0911000003', 'frank@example.com', '$2a$10$OBCkWaEhdcV2W3MrcPGk1eRTrErY8yNxkZli9wK2VvMC04mr0tZ9K', NULL, '�ͬ��ݭn�@�I���q'),
('Grace', '0911000004', 'grace@example.com', '$2a$10$6TVGHJKZRXvyeN0XXWIDFOk1Y9TTgAgYz1z2fqxhhIApIkHTMxWde', NULL, '�]�p�O�ڪ��y��'),
('Henry', '0911000005', 'henry@example.com', '$2a$10$S6hhnL5Y9U0PAfXrlVTXROrFev4DxHtG/YqOz5b4tPjN8HpBzn7ku', NULL, '���w�����P�\Ū');

INSERT INTO posts (user_id, content, image)
VALUES 
(1, '���ѾǤF Spring Boot �W����I', NULL),
(2, '�b�ʳ��ȹC��F�n�h�Ӥ�', NULL),
(3, '�A�̳��έ��� text editor�H', NULL),
(4, '��]�p���@�� UI �n�����N�P�I', NULL),
(5, '�����Ф��Ѻ����H��...', NULL);

INSERT INTO comment (user_id, post_id, content)
VALUES 
(5, 3, '�ڤ]��}�l�� Spring Boot�I�@�_�[�o�I'),
(4, 2, '���ݬݨ�A���ȹC��v�@�~'),
(2, 1, '�ڥ� VSCode ı�o�٤�����'),
(1, 4, 'UI ���϶ܡH�ڦn�_�I'),
(3, 5, '�����ڤ]�`�o�ˡA�ר�g���ߤW');

SET FOREIGN_KEY_CHECKS = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO blog_post(id, title, content, date_created)
VALUES(41, 'Title post 1', 'Post content 1', '2021-06-03T12:31:18'),
(42, 'Title post 2', 'Post content 2', '2021-06-03T12:31:18' ),
(43, 'Title post 3', 'Post content 3', '2021-06-03T12:31:18'),
(44, 'Title post 4', 'Post content 4', '2021-06-03T12:31:18'),
(45, 'Title post 5', 'Post content 5', '2021-06-03T12:31:18');

SET FOREIGN_KEY_CHECKS = 1;

INSERT IGNORE INTO classes (class_id, subject, name) VALUES
(1, '国語', 'N/A'),
(2, '国語', '特選'),
(3, '国語', 'α'),
(4, '国語', 'β1'),
(5, '国語', 'β2'),
(6, '国語', 'サッカー1'),
(7, '国語', 'サッカー2'),
(8, '数学', 'N/A'),
(9, '数学', '特選'),
(10, '数学', 'α'),
(11, '数学', 'β1'),
(12, '数学', 'β2'),
(13, '数学', 'サッカー1'),
(14, '数学', 'サッカー2'),
(15, '英語', 'N/A'),
(16, '英語', '特選'),
(17, '英語', 'α'),
(18, '英語', 'β1'),
(19, '英語', 'β2'),
(20, '英語', 'サッカー1'),
(21, '英語', 'サッカー2'),
(22, '理科', 'N/A'),
(23, '理科', '特選'),
(24, '理科', 'α'),
(25, '理科', 'β1'),
(26, '理科', 'β2'),
(27, '理科', 'サッカー1'),
(28, '理科', 'サッカー2'),
(29, '社会', 'N/A'),
(30, '社会', '特選'),
(31, '社会', 'α'),
(32, '社会', 'β1'),
(33, '社会', 'β2'),
(34, '社会', 'サッカー1'),
(35, '社会', 'サッカー2');


insert ignore into statuses(status_id,name) values
(1,'講習'),
(2,'個別'),
(3,'体験'),
(4,'本科');

insert ignore into students(student_id,el1,code,name )values
(1,2000,1,'ave');


insert ignore into roles(role_id,name) values
(1,"ROLE_ADMIN"),
(2,"ROLE_GENERAL");

insert ignore into cram_schools(name) values
("八王子みなみ野教室"),
("めじろ台教室"),
("西八王子教室"),
("金井教室"),
("玉川学園教室"),
("多摩境教室"),
("鶴川真光寺教室"),
("あきるの教室"),
("秋川教室");

insert ignore into users(name,password,role_id,cram_school_id) values
("admin1",'$2a$10$SWGqOvoZAJ/EbhJTvM1gSu3PcLsmtw5ZWZrbViU.gd9LK5q05d6gW',1,1),
('general1','$2a$10$XT3Q7oBiUoMtKrovlHLeOeNsXN0A6cd8EITfDKv/7Iou0BRrhKLgO',2,1);

INSERT IGNORE INTO `schools` (`school_id`, `name`, `cram_school_id`) VALUES
(15, 'いずみの森中学校', 1),
(19, 'みなみ野君田小学校', 1),
(21, 'みなみ野小学校', 1),
(3, 'めじろ台1', 2),
(4, 'めじろ台2', 2),
(5, 'フェリシア高校', 6),
(12, '七国中学校', 1),
(20, '七国小学校', 1),
(17, '南多摩中学校', 1),
(22, '君田小学校', 1),
(13, '堺中学校', 1),
(8, '堺中学校', 6),
(11, '小山中央小学校', 6),
(10, '小山中学校', 6),
(14, '武蔵岡中学校', 1),
(16, '町田市立堺中学校', 1),
(6, '町田高校', 6),
(18, '第六中学校', 1),
(7, '翔陽高校', 6),
(9, '鑓水中学校', 6),
(23,'みなみ野中学校',1);

INSERT IGNORE INTO `funnels` (`funnel_id`,`name`) VALUES
    (1,'口コミ・紹介'),
    (2,'口コミ・紹介（親）'),
    (3,'口コミ・紹介（子）'),
    (4,'兄弟'),
    (5,'広告'),
    (6,'直来'),
    (7,'比較サイト'),
    (8,'blog'),
    (9,'SNS'),
    (10,'チラシ'),
    (11,'HP'),
    (12,'Ameba塾探し'),
    (13,'塾ナビ'),
    (14,'塾選'),
    (15,'塾シル'),
    (16,'塾ログ');

INSERT IGNORE INTO `actions` (`action_name`) VALUES
    ('初回面談'),
    ('体験授業'),
    ('入塾面談'),
    ('入塾');



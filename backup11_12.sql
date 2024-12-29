-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- ホスト: 127.0.0.1
-- 生成日時: 2024-11-12 07:11:58
-- サーバのバージョン： 10.4.32-MariaDB
-- PHP のバージョン: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- データベース: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(10) UNSIGNED NOT NULL,
  `dbase` varchar(255) NOT NULL DEFAULT '',
  `user` varchar(255) NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `query` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) NOT NULL,
  `col_name` varchar(64) NOT NULL,
  `col_type` varchar(64) NOT NULL,
  `col_length` text DEFAULT NULL,
  `col_collation` varchar(64) NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) DEFAULT '',
  `col_default` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `column_name` varchar(64) NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `transformation` varchar(255) NOT NULL DEFAULT '',
  `transformation_options` varchar(255) NOT NULL DEFAULT '',
  `input_transformation` varchar(255) NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) NOT NULL,
  `settings_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL,
  `export_type` varchar(10) NOT NULL,
  `template_name` varchar(64) NOT NULL,
  `template_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db` varchar(64) NOT NULL DEFAULT '',
  `table` varchar(64) NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp(),
  `sqlquery` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) NOT NULL,
  `item_name` varchar(64) NOT NULL,
  `item_type` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- テーブルのデータのダンプ `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('root', '[{\"db\":\"shoei\",\"table\":\"other_test_results\"},{\"db\":\"shoei\",\"table\":\"other_tests\"},{\"db\":\"shoei\",\"table\":\"mock_test_results\"},{\"db\":\"shoei\",\"table\":\"roles\"},{\"db\":\"shoei\",\"table\":\"regular_test_sets\"},{\"db\":\"shoei\",\"table\":\"regular_test_results\"},{\"db\":\"shoei\",\"table\":\"classes\"},{\"db\":\"shoei\",\"table\":\"class_students\"},{\"db\":\"shoei\",\"table\":\"students\"},{\"db\":\"shoei\",\"table\":\"regular_tests\"}]');

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) NOT NULL DEFAULT '',
  `master_table` varchar(64) NOT NULL DEFAULT '',
  `master_field` varchar(64) NOT NULL DEFAULT '',
  `foreign_db` varchar(64) NOT NULL DEFAULT '',
  `foreign_table` varchar(64) NOT NULL DEFAULT '',
  `foreign_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `search_name` varchar(64) NOT NULL DEFAULT '',
  `search_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT 0,
  `x` float UNSIGNED NOT NULL DEFAULT 0,
  `y` float UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `display_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `prefs` text NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

--
-- テーブルのデータのダンプ `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('root', 'shoei', 'users', '{\"CREATE_TIME\":\"2024-10-28 19:09:14\"}', '2024-11-02 07:29:10');

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text NOT NULL,
  `schema_sql` text DEFAULT NULL,
  `data_sql` longtext DEFAULT NULL,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `config_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- テーブルのデータのダンプ `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('root', '2024-11-11 11:50:02', '{\"Console\\/Mode\":\"collapse\",\"lang\":\"ja\",\"NavigationWidth\":230}');

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) NOT NULL,
  `tab` varchar(64) NOT NULL,
  `allowed` enum('Y','N') NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- テーブルの構造 `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) NOT NULL,
  `usergroup` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- ダンプしたテーブルのインデックス
--

--
-- テーブルのインデックス `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- テーブルのインデックス `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- テーブルのインデックス `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- テーブルのインデックス `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- テーブルのインデックス `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- テーブルのインデックス `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- テーブルのインデックス `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- テーブルのインデックス `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- テーブルのインデックス `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- テーブルのインデックス `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- テーブルのインデックス `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- テーブルのインデックス `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- テーブルのインデックス `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- テーブルのインデックス `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- テーブルのインデックス `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- テーブルのインデックス `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- テーブルのインデックス `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- テーブルのインデックス `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- テーブルのインデックス `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- ダンプしたテーブルの AUTO_INCREMENT
--

--
-- テーブルの AUTO_INCREMENT `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- テーブルの AUTO_INCREMENT `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- テーブルの AUTO_INCREMENT `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- テーブルの AUTO_INCREMENT `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- テーブルの AUTO_INCREMENT `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- テーブルの AUTO_INCREMENT `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- データベース: `shoei`
--
CREATE DATABASE IF NOT EXISTS `shoei` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `shoei`;

-- --------------------------------------------------------

--
-- テーブルの構造 `classes`
--

CREATE TABLE `classes` (
  `class_id` int(11) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `classes`
--

INSERT INTO `classes` (`class_id`, `subject`, `name`, `cram_school_id`) VALUES
(1, '国語', 'N/A', 1),
(3, '数学', 'N/A', 1),
(5, '理科', 'N/A', 1),
(4, '社会', 'N/A', 1),
(2, '英語', 'N/A', 1);

-- --------------------------------------------------------

--
-- テーブルの構造 `class_students`
--

CREATE TABLE `class_students` (
  `class_student_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `created_at` date NOT NULL,
  `changed_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- テーブルの構造 `cram_schools`
--

CREATE TABLE `cram_schools` (
  `cram_school_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `cram_schools`
--

INSERT INTO `cram_schools` (`cram_school_id`, `name`) VALUES
(8, 'あきるの教室'),
(2, 'めじろ台教室'),
(1, '八王子みなみ野教室'),
(6, '多摩境教室'),
(5, '玉川学園教室'),
(9, '秋川教室'),
(3, '西八王子教室'),
(4, '金井教室'),
(7, '鶴川真光寺教室');

-- --------------------------------------------------------

--
-- テーブルの構造 `mock_tests`
--

CREATE TABLE `mock_tests` (
  `mock_test_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `mock_tests`
--

INSERT INTO `mock_tests` (`mock_test_id`, `name`, `date`, `cram_school_id`) VALUES
(3, '2024_進研テスト_中３_1_131122008_utf8_csv.csv', '2024-10-10', 1),
(4, '2024年度　中３ハイレベル第４回成績データ（中学生）ダウンロード (5)UTF8.csv', '2024-10-10', 1);

-- --------------------------------------------------------

--
-- テーブルの構造 `mock_test_results`
--

CREATE TABLE `mock_test_results` (
  `mock_test_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `japanese` int(11) DEFAULT NULL,
  `japanese_ss` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `math_ss` int(11) DEFAULT NULL,
  `english` int(11) DEFAULT NULL,
  `english_ss` int(11) DEFAULT NULL,
  `science` int(11) DEFAULT NULL,
  `science_ss` int(11) DEFAULT NULL,
  `social` int(11) DEFAULT NULL,
  `social_ss` int(11) DEFAULT NULL,
  `jme_ss` int(11) DEFAULT NULL,
  `jmess_ss` int(11) DEFAULT NULL,
  `dream_school1` varchar(255) DEFAULT NULL,
  `dream_school1_probability` int(11) DEFAULT NULL,
  `dream_school2` varchar(255) DEFAULT NULL,
  `dream_school2_probability` int(11) DEFAULT NULL,
  `dream_school3` varchar(255) DEFAULT NULL,
  `dream_school3_probability` int(11) DEFAULT NULL,
  `dream_school4` varchar(255) DEFAULT NULL,
  `dream_school4_probability` int(11) DEFAULT NULL,
  `dream_school5` varchar(255) DEFAULT NULL,
  `dream_school5_probability` int(11) DEFAULT NULL,
  `dream_school6` varchar(255) DEFAULT NULL,
  `dream_school6_probability` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `mock_test_results`
--

INSERT INTO `mock_test_results` (`mock_test_id`, `student_id`, `japanese`, `japanese_ss`, `math`, `math_ss`, `english`, `english_ss`, `science`, `science_ss`, `social`, `social_ss`, `jme_ss`, `jmess_ss`, `dream_school1`, `dream_school1_probability`, `dream_school2`, `dream_school2_probability`, `dream_school3`, `dream_school3_probability`, `dream_school4`, `dream_school4_probability`, `dream_school5`, `dream_school5_probability`, `dream_school6`, `dream_school6_probability`) VALUES
(3, 171, 80, 60, 55, 51, 53, 51, 40, 47, 68, 59, 55, 54, '法政大学', 0, '南平－普通', 20, '帝京大学', 20, '八王子－進学', 40, '', 0, '', 0),
(3, 180, 40, 38, 48, 47, 62, 56, 42, 48, 39, 45, 47, 46, '昭和－普通', 0, '八王子実践－特進', 20, '成瀬－普通', 20, '日大明誠－普通', 40, '', 0, '', 0),
(3, 181, 80, 60, 85, 68, 88, 68, 66, 61, 62, 56, 68, 64, '国立－普通', 0, '早稲田実業', 40, '明大付属明治', 40, '立川－普通', 20, '', 0, '', 0),
(3, 182, 76, 58, 78, 64, 65, 57, 74, 66, 43, 47, 61, 59, '八王子東－普通', 20, '日野台－普通', 40, '中央大学附属', 20, '八王子－進学', 90, '', 0, '', 0),
(3, 183, 84, 63, 68, 59, 71, 60, 61, 58, 64, 57, 62, 60, '八王子東－普通', 20, '明大付属明治', 20, '明大付属八王子', 20, '八王子－特選', 40, '', 0, '', 0),
(3, 184, 64, 51, 85, 68, 66, 57, 36, 44, 85, 68, 60, 59, '国分寺－単位制', 20, '町田－普通', 40, '中央大学附属', 20, '帝京大学', 40, '', 0, '', 0),
(3, 185, 80, 60, 50, 48, 38, 44, 31, 42, 31, 41, 51, 46, '南平－普通', 0, '翔陽－単位制', 20, '八王子－進学', 20, '日本大学第三－普通', 20, '', 0, '', 0),
(3, 186, 62, 50, 85, 68, 56, 53, 53, 54, 62, 56, 58, 57, '国学院久我山', 20, '実践学園－文理', 90, '細田学園－進学α', 90, '拓殖大学第一－進学', 40, '', 0, '', 0),
(3, 187, 56, 47, 55, 51, 47, 49, 46, 50, 71, 61, 49, 52, '八王子東－普通', 0, '八王子－進学', 20, '成瀬－普通', 60, '片倉－普通', 90, '', 0, '', 0),
(3, 188, 82, 61, 68, 59, 72, 60, 76, 67, 76, 63, 61, 64, '立川－普通', 20, '早稲田実業', 0, '中央大学附属', 20, '南平－普通', 90, '', 0, '', 0),
(3, 189, 84, 63, 90, 71, 97, 73, 77, 67, 77, 64, 71, 70, '早稲田実業', 60, '国立－普通', 40, '八王子東－普通', 80, '中央大学附属', 90, '', 0, '', 0),
(3, 190, 46, 41, 53, 50, 60, 55, 44, 49, 41, 46, 49, 48, '八王子北－普通', 90, '小川－普通', 80, '片倉－普通', 90, '松が谷－普通', 40, '', 0, '', 0),
(3, 191, 68, 53, 65, 57, 35, 43, 42, 48, 50, 50, 51, 50, '片倉－普通', 90, '町田－普通', 0, '翔陽－単位制', 40, '日野－普通', 40, '', 0, '', 0),
(3, 192, 84, 63, 90, 71, 88, 68, 69, 63, 70, 60, 69, 67, '国立－普通', 20, '立川－創造理数', 20, '慶応義塾', 40, '明大付属八王子', 80, '', 0, '', 0),
(3, 193, 82, 61, 68, 59, 59, 54, 41, 47, 65, 58, 59, 57, '八王子東－普通', 0, '帝京大学', 20, '南平－普通', 40, '拓殖大学第一－進学', 60, '', 0, '', 0),
(3, 194, 64, 51, 43, 44, 56, 53, 39, 46, 53, 52, 50, 49, '明大付属八王子', 0, '南平－普通', 20, '八王子－進学', 20, '八王子実践－特進', 40, '', 0, '', 0),
(3, 195, 40, 38, 38, 42, 36, 44, 32, 42, 51, 51, 40, 42, '富士森－普通', 20, '日大明誠－普通', 20, '成瀬－普通', 0, '八王子実践－総進', 20, '', 0, '', 0),
(3, 196, 86, 64, 63, 56, 88, 68, 81, 69, 91, 71, 64, 68, '立川－普通', 60, '慶応義塾', 20, '明大付属八王子', 40, '八王子東－普通', 60, '', 0, '', 0),
(3, 197, 76, 58, 75, 63, 73, 61, 66, 61, 77, 64, 62, 63, '西－普通', 20, '広尾学園－医進・ＳＣ', 20, '立川－普通', 40, '帝京大学', 40, '', 0, '', 0),
(3, 198, 76, 58, 48, 47, 48, 49, 44, 49, 29, 40, 51, 48, '日野台－普通', 0, '翔陽－単位制', 40, '桜美林－進学', 20, '日大明誠－特進', 40, '', 0, '', 0),
(3, 199, 78, 59, 75, 63, 49, 49, 48, 51, 56, 53, 57, 55, '国立－普通', 0, '日野台－普通', 20, '中央大学附属', 0, '創価', 60, '', 0, '', 0),
(3, 200, 70, 55, 60, 54, 65, 57, 42, 48, 50, 50, 56, 53, '南平－普通', 40, '富士森－普通', 90, '片倉－普通', 90, '成瀬－普通', 80, '', 0, '', 0),
(3, 201, 82, 61, 70, 60, 66, 57, 63, 59, 50, 50, 61, 58, '桐朋', 40, '帝京大学', 40, '町田－普通', 20, '日野台－普通', 20, '', 0, '', 0),
(3, 203, 50, 43, 65, 57, 46, 48, 47, 51, 44, 47, 49, 49, '翔陽－単位制', 60, '富士森－普通', 60, '八王子実践－総進', 80, '南平－普通', 0, '', 0, '', 0),
(3, 204, 76, 58, 75, 63, 45, 48, 56, 56, 50, 50, 56, 55, '日野台－普通', 20, '日野台－普通', 20, '八王子東－普通', 0, '翔陽－単位制', 90, '', 0, '', 0),
(3, 205, 84, 63, 60, 54, 62, 56, 62, 59, 58, 54, 58, 58, '早稲田実業', 0, '明大付属明治', 0, '国立－普通', 0, '八王子東－普通', 0, '', 0, '', 0),
(3, 206, 52, 44, 60, 54, 40, 45, 61, 58, 76, 63, 47, 53, '日野台－普通', 0, '南平－普通', 20, '中央大学附属', 0, '八王子実践－特進', 20, '', 0, '', 0),
(3, 207, 64, 51, 55, 51, 29, 40, 42, 48, 50, 50, 47, 47, '日大明誠－普通', 40, '東海大相模', 20, '富士森－普通', 40, '片倉－普通', 90, '', 0, '', 0),
(3, 208, 74, 57, 70, 60, 80, 64, 56, 56, 65, 58, 62, 60, '町田－普通', 20, '日本大学第三－普通', 80, '日野台－普通', 40, '成瀬－普通', 90, '', 0, '', 0),
(3, 209, 88, 65, 80, 65, 89, 68, 87, 73, 80, 65, 68, 70, '国立－普通', 60, '八王子東－普通', 90, '早稲田実業', 40, '帝京大学', 80, '', 0, '', 0),
(3, 210, 64, 51, 48, 47, 50, 50, 61, 58, 59, 55, 49, 53, '八王子桑志－システム情報', 90, '八王子－進学', 20, '松が谷－普通', 80, '片倉－普通', 90, '', 0, '', 0),
(3, 211, 62, 50, 65, 57, 62, 56, 46, 50, 53, 52, 55, 53, '明大付属八王子', 0, '町田－普通', 0, '南平－普通', 40, '日野台－普通', 20, '', 0, '', 0),
(3, 213, 42, 39, 45, 45, 32, 42, 42, 48, 50, 50, 41, 44, '府中－普通', 0, '日野－普通', 20, '昭和－普通', 0, '日大明誠－普通', 20, '', 0, '', 0),
(3, 226, 66, 52, 70, 60, 76, 62, 69, 63, 65, 58, 59, 60, '町田－普通', 40, '八王子東－普通', 20, '桜美林－進学', 60, '八王子－特進', 40, '', 0, '', 0),
(3, 230, 84, 63, 70, 60, 76, 62, 68, 62, 86, 68, 63, 65, '国立－普通', 20, '立川－普通', 40, '明大付属明治', 20, '帝京大学', 40, '', 0, '', 0),
(3, 231, 96, 71, 73, 62, 89, 68, 73, 65, 82, 66, 69, 68, '明大付属明治', 40, '国立－普通', 40, '中央大学附属', 80, '八王子東－普通', 60, '', 0, '', 0),
(3, 232, 70, 55, 60, 54, 83, 65, 74, 66, 83, 67, 60, 63, '立川－普通', 20, '明大付属明治', 0, '国分寺－単位制', 40, '明大付属八王子', 20, '', 0, '', 0),
(3, 233, 88, 65, 73, 62, 91, 69, 92, 76, 88, 69, 67, 71, '日比谷－普通', 60, '国立－普通', 80, '中央大学附属', 60, '慶応義塾女子', 20, '', 0, '', 0),
(3, 234, 72, 56, 65, 57, 67, 58, 58, 57, 57, 54, 58, 57, '町田－普通', 20, '中央大学附属', 0, '昭和－普通', 40, '南平－普通', 60, '', 0, '', 0),
(3, 235, 70, 55, 58, 53, 78, 63, 50, 52, 49, 49, 58, 55, '拓殖大学第一－進学', 40, '南平－普通', 40, '関東国際－英語', 90, '中央大学附属', 0, '', 0, '', 0),
(3, 236, 68, 53, 70, 60, 54, 52, 35, 44, 62, 56, 55, 53, '八王子東－普通', 0, '国分寺－単位制', 0, '帝京大学', 20, '八王子－進学', 40, '', 0, '', 0),
(3, 237, 78, 59, 60, 54, 80, 64, 60, 58, 53, 52, 61, 58, '町田－普通', 40, '昭和－普通', 40, '八王子実践－特進', 90, '共立女子第二－総進', 90, '', 0, '', 0),
(3, 238, 54, 45, 70, 60, 41, 46, 50, 52, 31, 41, 50, 48, '南平－普通', 0, '八王子実践－特進', 40, '拓殖大学第一－進学', 20, '桜美林－進学', 20, '', 0, '', 0),
(3, 239, 84, 63, 50, 48, 59, 54, 45, 49, 42, 46, 56, 52, '南平－普通', 20, '日大明誠－特進', 60, '松が谷－普通', 90, '八王子実践－選抜', 90, '', 0, '', 0),
(3, 240, 72, 56, 70, 60, 83, 65, 56, 56, 62, 56, 62, 60, '日野台－普通', 40, '昭和－普通', 40, '八王子－特進', 60, '翔陽－単位制', 90, '', 0, '', 0),
(3, 241, 92, 67, 80, 65, 74, 61, 78, 68, 76, 63, 66, 67, '八王子東－普通', 40, '立川－創造理数', 20, '八王子－特選', 60, '国分寺－単位制', 60, '', 0, '', 0),
(3, 242, 74, 57, 70, 60, 73, 61, 59, 57, 65, 58, 60, 60, '八王子東－普通', 40, '町田－普通', 60, '八王子－進学', 80, '八王子－特選', 40, '', 0, '', 0),
(3, 243, 42, 39, 35, 40, 34, 43, 23, 37, 29, 40, 39, 38, '八王子北－普通', 40, '永山－普通', 80, '八王子実践－総進', 20, '光明相模原－総合', 40, '', 0, '', 0),
(3, 244, 70, 55, 63, 56, 76, 62, 56, 56, 68, 59, 59, 59, '中央大学附属', 20, '明大付属八王子', 20, '昭和－普通', 40, '日野台－普通', 40, '', 0, '', 0),
(3, 245, 70, 55, 55, 51, 35, 43, 34, 43, 39, 45, 49, 47, '松が谷－普通', 40, '富士森－普通', 40, '片倉－普通', 90, '八王子実践－総進', 80, '', 0, '', 0),
(3, 246, 90, 66, 45, 45, 55, 52, 37, 45, 47, 49, 55, 52, '八王子桑志－デザイン', 90, '町田－普通', 0, '片倉－造形美術', 90, '立川－普通', 0, '', 0, '', 0),
(3, 247, 56, 47, 38, 42, 41, 46, 35, 44, 39, 45, 44, 44, '中央大学附属', 0, '法政大学', 0, '八王子－進学', 0, '日大明誠－特進', 0, '', 0, '', 0),
(3, 248, 86, 64, 45, 45, 50, 50, 41, 47, 41, 46, 53, 50, '成瀬－普通', 40, '松が谷－普通', 80, '日野台－普通', 0, '相模女子大学－進学', 80, '', 0, '', 0),
(3, 249, 46, 41, 45, 45, 45, 48, 38, 46, 45, 48, 44, 45, '松が谷－普通', 20, '東海大学菅生－進学', 40, '翔陽－単位制', 20, '青梅総合', 40, '', 0, '', 0),
(3, 250, 80, 60, 60, 54, 47, 49, 69, 63, 58, 54, 54, 56, '松が谷－普通', 90, '日野－普通', 90, '翔陽－単位制', 90, '八王子実践－総進', 90, '', 0, '', 0),
(3, 251, 62, 50, 60, 54, 75, 62, 64, 60, 54, 52, 56, 57, '町田－普通', 40, '日野台－普通', 40, '八王子－進学', 60, '成瀬－普通', 90, '', 0, '', 0),
(3, 252, 94, 69, 65, 57, 87, 67, 66, 61, 57, 54, 66, 63, '八王子東－普通', 40, '国分寺－単位制', 60, '日野台－普通', 80, '明大付属八王子', 60, '', 0, '', 0),
(4, 181, 72, 57, 52, 51, 71, 56, 0, NULL, 0, NULL, 56, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 182, 37, 31, 47, 48, 34, 34, 0, NULL, 0, NULL, 34, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 183, 76, 61, 34, 40, 37, 35, 0, NULL, 0, NULL, 42, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 184, 56, 45, 37, 42, 34, 34, 0, NULL, 0, NULL, 37, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 188, 62, 50, 37, 42, 40, 37, 0, NULL, 0, NULL, 40, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 189, 90, 71, 57, 54, 65, 52, 0, NULL, 0, NULL, 61, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 192, 70, 56, 85, 70, 70, 55, 0, NULL, 0, NULL, 64, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 193, 42, 34, 34, 40, 35, 34, 0, NULL, 0, NULL, 32, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 196, 61, 49, 40, 44, 47, 41, 0, NULL, 0, NULL, 42, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 197, 60, 48, 42, 45, 50, 43, 0, NULL, 0, NULL, 44, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 199, 64, 51, 22, 33, 18, 24, 0, NULL, 0, NULL, 30, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 201, 72, 57, 61, 56, 40, 37, 0, NULL, 0, NULL, 50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 205, 40, 33, 29, 37, 51, 44, 0, NULL, 0, NULL, 35, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 208, 50, 41, 19, 31, 43, 39, 0, NULL, 0, NULL, 32, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 209, 66, 53, 51, 50, 50, 43, 0, NULL, 0, NULL, 48, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 214, 72, 57, 47, 48, 78, 60, 0, NULL, 0, NULL, 56, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 215, 72, 57, 16, 30, 42, 38, 0, NULL, 0, NULL, 37, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 216, 50, 41, 45, 47, 29, 31, 0, NULL, 0, NULL, 36, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 230, 64, 51, 60, 56, 48, 42, 0, NULL, 0, NULL, 49, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 231, 66, 53, 18, 31, 63, 51, 0, NULL, 0, NULL, 42, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 233, 90, 71, 62, 57, 62, 50, 0, NULL, 0, NULL, 61, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 236, 68, 54, 37, 42, 32, 32, 0, NULL, 0, NULL, 39, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 241, 72, 57, 29, 37, 40, 37, 0, NULL, 0, NULL, 41, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 252, 54, 44, 19, 31, 50, 43, 0, NULL, 0, NULL, 35, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- テーブルの構造 `other_tests`
--

CREATE TABLE `other_tests` (
  `other_test_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `school_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `semester` tinyint(4) NOT NULL,
  `grade` tinyint(4) NOT NULL,
  `japanese` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `english` int(11) DEFAULT NULL,
  `science` int(11) DEFAULT NULL,
  `social` int(11) DEFAULT NULL,
  `music` int(11) DEFAULT NULL,
  `art` int(11) DEFAULT NULL,
  `tech` int(11) DEFAULT NULL,
  `pe` int(11) DEFAULT NULL,
  `school_school_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `other_tests`
--

INSERT INTO `other_tests` (`other_test_id`, `name`, `school_id`, `date`, `semester`, `grade`, `japanese`, `math`, `english`, `science`, `social`, `music`, `art`, `tech`, `pe`, `school_school_id`) VALUES
(5, 'その他のテスト1', 44, '2024-11-11', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, 'その他のテスト２', 49, '2024-10-25', 2, 3, 100, 80, 88, 44, NULL, NULL, NULL, 44, NULL, NULL),
(7, 'その他のテスト2', 43, '2024-11-07', 3, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(8, 'その他のテスト3', 43, '2024-10-10', 3, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- テーブルの構造 `other_test_results`
--

CREATE TABLE `other_test_results` (
  `other_test_result_id` int(11) NOT NULL,
  `other_test_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `japanese` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `english` int(11) DEFAULT NULL,
  `science` int(11) DEFAULT NULL,
  `social` int(11) DEFAULT NULL,
  `music` int(11) DEFAULT NULL,
  `art` int(11) DEFAULT NULL,
  `tech` int(11) DEFAULT NULL,
  `pe` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `other_test_results`
--

INSERT INTO `other_test_results` (`other_test_result_id`, `other_test_id`, `student_id`, `japanese`, `math`, `english`, `science`, `social`, `music`, `art`, `tech`, `pe`) VALUES
(1, 5, 328, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 6, 417, NULL, 111, NULL, 33, 22, 44, NULL, NULL, NULL),
(3, 7, 173, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(4, 7, 174, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(5, 7, 175, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(6, 7, 176, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(7, 7, 178, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(8, 7, 181, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(9, 7, 182, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(10, 7, 183, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(11, 7, 185, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(12, 7, 186, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(13, 7, 187, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(14, 7, 194, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(15, 7, 195, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(16, 7, 197, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(17, 7, 198, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(18, 7, 199, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(19, 7, 200, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(20, 7, 201, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(21, 7, 202, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(22, 7, 204, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(23, 7, 206, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(24, 7, 211, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(25, 7, 214, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(26, 7, 216, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(27, 7, 223, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(28, 7, 225, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(29, 7, 228, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(30, 7, 230, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(31, 7, 233, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(32, 7, 234, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(33, 7, 235, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(34, 7, 236, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(35, 7, 237, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(36, 7, 238, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(37, 7, 239, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(38, 7, 241, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(39, 7, 244, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(40, 7, 417, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(41, 8, 305, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(42, 8, 308, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(43, 8, 309, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(44, 8, 311, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(45, 8, 312, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(46, 8, 313, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(47, 8, 314, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(48, 8, 316, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(49, 8, 317, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(50, 8, 318, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(51, 8, 319, 77, 55, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(52, 8, 325, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(53, 8, 326, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(54, 8, 329, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(55, 8, 330, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(56, 8, 332, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(57, 8, 333, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(58, 8, 335, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(59, 8, 336, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(60, 8, 338, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(61, 8, 339, 100, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(62, 8, 417, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- テーブルの構造 `regular_tests`
--

CREATE TABLE `regular_tests` (
  `regular_test_id` int(11) NOT NULL,
  `regular_test_set_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `japanese` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `english` int(11) DEFAULT NULL,
  `science` int(11) DEFAULT NULL,
  `social` int(11) DEFAULT NULL,
  `music` int(11) DEFAULT NULL,
  `art` int(11) DEFAULT NULL,
  `tech` int(11) DEFAULT NULL,
  `pe` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `regular_tests`
--

INSERT INTO `regular_tests` (`regular_test_id`, `regular_test_set_id`, `school_id`, `date`, `japanese`, `math`, `english`, `science`, `social`, `music`, `art`, `tech`, `pe`) VALUES
(1, 1, 58, '2024-11-02', 88, 55, 75, 55, 53, 78, 88, 90, 122),
(2, 1, 59, '2024-09-11', 33, 233, NULL, 33, 44, 55, 56, 77, 77),
(3, 1, 60, '2024-11-02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 1, 61, '2024-11-02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 1, 62, '2024-11-02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, 2, 42, '2024-11-06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, 2, 43, '2024-11-06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(8, 2, 44, '2024-11-06', 191, NULL, NULL, NULL, 44, NULL, NULL, NULL, NULL),
(9, 2, 47, '2024-11-06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(10, 2, 49, '2024-11-06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- テーブルの構造 `regular_test_results`
--

CREATE TABLE `regular_test_results` (
  `regular_test_result_id` int(11) NOT NULL,
  `regular_test_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `japanese` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `english` int(11) DEFAULT NULL,
  `science` int(11) DEFAULT NULL,
  `social` int(11) DEFAULT NULL,
  `music` int(11) DEFAULT NULL,
  `art` int(11) DEFAULT NULL,
  `tech` int(11) DEFAULT NULL,
  `pe` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `regular_test_results`
--

INSERT INTO `regular_test_results` (`regular_test_result_id`, `regular_test_id`, `student_id`, `japanese`, `math`, `english`, `science`, `social`, `music`, `art`, `tech`, `pe`) VALUES
(1, 1, 374, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 2, 375, 55, 44, 0, 0, 0, 0, 0, 0, 0),
(3, 2, 376, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(4, 2, 382, 100, 0, 0, 0, 0, 0, 0, 0, 0),
(5, 2, 383, 0, 0, 0, 77, 0, 0, 0, 0, 0),
(6, 2, 384, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(7, 2, 393, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(8, 3, 377, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(9, 4, 378, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(10, 4, 380, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(11, 4, 385, 0, 33, 0, 0, 0, 0, 0, 0, 0),
(12, 4, 396, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(13, 5, 379, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(15, 2, 415, 22, 55, 44, 99, 101, 33, 44, 35, 88),
(16, 6, 306, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(17, 6, 307, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(18, 6, 310, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(19, 6, 315, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(20, 6, 320, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(21, 6, 321, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(22, 6, 322, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(23, 6, 323, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(24, 6, 324, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(25, 6, 331, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(26, 6, 334, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(27, 6, 337, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(28, 6, 340, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(29, 7, 305, 79, 0, 0, 0, 0, 0, 0, 0, 0),
(30, 7, 308, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(31, 7, 309, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(32, 7, 311, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(33, 7, 312, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(34, 7, 313, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(35, 7, 314, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(36, 7, 316, 100, 0, 0, 0, 0, 0, 0, 0, 0),
(37, 7, 317, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(38, 7, 318, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(39, 7, 319, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(40, 7, 325, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(41, 7, 326, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(42, 7, 329, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(43, 7, 330, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(44, 7, 332, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(45, 7, 333, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(46, 7, 335, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(47, 7, 336, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(48, 7, 338, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(49, 7, 339, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(50, 8, 328, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(51, 8, 417, 55, 44, NULL, NULL, NULL, NULL, NULL, 55, 33);

-- --------------------------------------------------------

--
-- テーブルの構造 `regular_test_sets`
--

CREATE TABLE `regular_test_sets` (
  `regular_test_set_id` int(11) NOT NULL,
  `term` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `semester` int(11) NOT NULL,
  `is_mid` int(11) NOT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `regular_test_sets`
--

INSERT INTO `regular_test_sets` (`regular_test_set_id`, `term`, `grade`, `semester`, `is_mid`, `cram_school_id`) VALUES
(1, 2024, 3, 2, 0, 9),
(2, 2024, 1, 3, 0, 1);

-- --------------------------------------------------------

--
-- テーブルの構造 `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `roles`
--

INSERT INTO `roles` (`role_id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_GENERAL');

-- --------------------------------------------------------

--
-- テーブルの構造 `schools`
--

CREATE TABLE `schools` (
  `school_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `schools`
--

INSERT INTO `schools` (`school_id`, `name`, `cram_school_id`) VALUES
(65, 'あきる野東中学校', 9),
(64, 'あきる野西中学校', 9),
(46, 'いずみの森中学校', 1),
(42, 'みなみ野中学校', 1),
(51, 'みなみ野君田小学校', 1),
(53, 'みなみ野小学校', 1),
(3, 'めじろ台1', 2),
(4, 'めじろ台2', 2),
(5, 'フェリシア高校', 6),
(43, '七国中学校', 1),
(52, '七国小学校', 1),
(56, '不明', 1),
(69, '不明', 9),
(58, '八王子第七中学校', 9),
(68, '八王子第二小学校', 9),
(49, '南多摩中学校', 1),
(54, '君田小学校', 1),
(44, '堺中学校', 1),
(8, '堺中学校', 6),
(63, '大久野中学校', 9),
(11, '小山中央小学校', 6),
(10, '小山中学校', 6),
(60, '帝京八王子中学校', 9),
(59, '平井中学校', 9),
(62, '御堂中学校', 9),
(57, '拓殖第一高等学校', 9),
(45, '武蔵岡中学校', 1),
(47, '町田市立堺中学校', 1),
(6, '町田高校', 6),
(61, '秋多中学校', 9),
(50, '第六中学校', 1),
(7, '翔陽高校', 6),
(66, '草花小学校', 9),
(67, '西秋留小学校', 9),
(9, '鑓水中学校', 6);

-- --------------------------------------------------------

--
-- テーブルの構造 `school_records`
--

CREATE TABLE `school_records` (
  `school_record_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `school_record_set_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `school_records`
--

INSERT INTO `school_records` (`school_record_id`, `school_id`, `school_record_set_id`) VALUES
(1, 42, 1),
(9, 42, 2),
(2, 43, 1),
(10, 43, 2),
(3, 44, 1),
(11, 44, 2),
(4, 45, 1),
(12, 45, 2),
(5, 46, 1),
(13, 46, 2),
(6, 47, 1),
(14, 47, 2),
(7, 49, 1),
(15, 49, 2),
(8, 50, 1),
(16, 50, 2),
(17, 58, 4),
(18, 59, 4),
(19, 60, 4),
(20, 61, 4),
(21, 62, 4),
(22, 63, 4),
(23, 64, 4),
(24, 65, 4);

-- --------------------------------------------------------

--
-- テーブルの構造 `school_record_results`
--

CREATE TABLE `school_record_results` (
  `school_record_result_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `school_record_id` int(11) NOT NULL,
  `japanese` int(11) DEFAULT NULL,
  `math` int(11) DEFAULT NULL,
  `english` int(11) DEFAULT NULL,
  `science` int(11) DEFAULT NULL,
  `social` int(11) DEFAULT NULL,
  `music` int(11) DEFAULT NULL,
  `art` int(11) DEFAULT NULL,
  `technology` int(11) DEFAULT NULL,
  `pe` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `school_record_results`
--

INSERT INTO `school_record_results` (`school_record_result_id`, `student_id`, `school_record_id`, `japanese`, `math`, `english`, `science`, `social`, `music`, `art`, `technology`, `pe`) VALUES
(1, 171, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0),
(2, 172, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(3, 177, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(4, 179, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(5, 180, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(6, 184, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(7, 188, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(8, 189, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(9, 190, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(10, 192, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(11, 196, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(12, 203, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(13, 207, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(14, 208, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(15, 209, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(16, 210, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(17, 215, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(18, 217, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(19, 220, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(20, 221, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(21, 222, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(22, 224, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(23, 227, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(24, 229, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(25, 231, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(26, 232, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(27, 242, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(28, 243, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(29, 245, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(30, 246, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(31, 247, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(32, 248, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(33, 249, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(34, 250, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(35, 252, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(36, 253, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(37, 254, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(38, 173, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(39, 174, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(40, 175, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(41, 176, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(42, 178, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(43, 181, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(44, 182, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(45, 183, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(46, 185, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(47, 186, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(48, 187, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(49, 194, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(50, 195, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(51, 197, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(52, 198, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(53, 199, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(54, 200, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(55, 201, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(56, 202, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(57, 204, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(58, 206, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(59, 211, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(60, 214, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(61, 216, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(62, 223, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(63, 225, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(64, 228, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(65, 230, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(66, 233, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(67, 234, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(68, 235, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(69, 236, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(70, 237, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(71, 238, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(72, 239, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(73, 241, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(74, 244, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(75, 191, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(76, 212, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(77, 218, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(78, 226, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(79, 240, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(80, 251, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(81, 193, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(82, 219, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(83, 205, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(84, 213, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(85, 256, 9, 3, 4, 4, 0, 0, 0, 0, 0, 0),
(86, 258, 9, 0, 6, 6, 0, 0, 0, 0, 0, 0),
(87, 259, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(88, 260, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(89, 261, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(90, 268, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(91, 269, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(92, 270, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(93, 276, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(94, 283, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(95, 299, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(96, 302, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(97, 303, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(98, 304, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(99, 255, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(100, 257, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(101, 262, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(102, 263, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(103, 264, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(104, 265, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(105, 266, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(106, 267, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(107, 271, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(108, 272, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(109, 273, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(110, 274, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(111, 275, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(112, 277, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(113, 278, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(114, 279, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(115, 280, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(116, 281, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(117, 282, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(118, 285, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(119, 286, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(120, 287, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(121, 288, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(122, 289, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(123, 290, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(124, 291, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(125, 292, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(126, 293, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(127, 294, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(128, 295, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(129, 296, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(130, 297, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(131, 298, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(132, 300, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(133, 301, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(134, 284, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(135, 374, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(136, 375, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(137, 376, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(138, 382, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(139, 383, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(140, 384, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(141, 393, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(142, 377, 19, 2, 0, 0, 0, 0, 0, 0, 0, 0),
(143, 378, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(144, 380, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(145, 385, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(146, 396, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(147, 379, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(148, 381, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(149, 386, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(150, 388, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(151, 387, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(152, 389, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(153, 391, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(154, 394, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(155, 395, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(156, 390, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(157, 392, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(158, 397, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- テーブルの構造 `school_record_sets`
--

CREATE TABLE `school_record_sets` (
  `school_record_set_id` int(11) NOT NULL,
  `term` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `semester` int(11) NOT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `school_record_sets`
--

INSERT INTO `school_record_sets` (`school_record_set_id`, `term`, `grade`, `semester`, `cram_school_id`) VALUES
(1, 2023, 2, 2, 1),
(2, 2024, 2, 2, 1),
(3, 2024, 3, 2, 9),
(4, 2024, 3, 1, 9),
(5, 2022, 1, 1, 8);

-- --------------------------------------------------------

--
-- テーブルの構造 `school_students`
--

CREATE TABLE `school_students` (
  `school_student_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `created_at` date NOT NULL,
  `changed_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `school_students`
--

INSERT INTO `school_students` (`school_student_id`, `school_id`, `student_id`, `created_at`, `changed_at`) VALUES
(154, 42, 154, '1970-01-01', '9999-12-31'),
(155, 43, 155, '1970-01-01', '9999-12-31'),
(156, 43, 156, '1970-01-01', '9999-12-31'),
(157, 43, 157, '1970-01-01', '9999-12-31'),
(158, 43, 158, '1970-01-01', '9999-12-31'),
(159, 43, 159, '1970-01-01', '9999-12-31'),
(160, 42, 160, '1970-01-01', '9999-12-31'),
(161, 42, 161, '1970-01-01', '9999-12-31'),
(162, 42, 162, '1970-01-01', '9999-12-31'),
(163, 42, 163, '1970-01-01', '9999-12-31'),
(164, 43, 164, '1970-01-01', '9999-12-31'),
(165, 43, 165, '1970-01-01', '9999-12-31'),
(166, 43, 166, '1970-01-01', '9999-12-31'),
(167, 42, 167, '1970-01-01', '9999-12-31'),
(168, 42, 168, '1970-01-01', '9999-12-31'),
(169, 43, 169, '1970-01-01', '9999-12-31'),
(170, 43, 170, '1970-01-01', '9999-12-31'),
(171, 42, 171, '1970-01-01', '9999-12-31'),
(172, 42, 172, '1970-01-01', '9999-12-31'),
(173, 43, 173, '1970-01-01', '9999-12-31'),
(174, 43, 174, '1970-01-01', '9999-12-31'),
(175, 43, 175, '1970-01-01', '9999-12-31'),
(176, 43, 176, '1970-01-01', '9999-12-31'),
(177, 42, 177, '1970-01-01', '9999-12-31'),
(178, 43, 178, '1970-01-01', '9999-12-31'),
(179, 42, 179, '1970-01-01', '9999-12-31'),
(180, 42, 180, '1970-01-01', '9999-12-31'),
(181, 43, 181, '1970-01-01', '9999-12-31'),
(182, 43, 182, '1970-01-01', '9999-12-31'),
(183, 43, 183, '1970-01-01', '9999-12-31'),
(184, 42, 184, '1970-01-01', '9999-12-31'),
(185, 43, 185, '1970-01-01', '9999-12-31'),
(186, 43, 186, '1970-01-01', '9999-12-31'),
(187, 43, 187, '1970-01-01', '9999-12-31'),
(188, 42, 188, '1970-01-01', '9999-12-31'),
(189, 42, 189, '1970-01-01', '9999-12-31'),
(190, 42, 190, '1970-01-01', '9999-12-31'),
(191, 44, 191, '1970-01-01', '9999-12-31'),
(192, 42, 192, '1970-01-01', '9999-12-31'),
(193, 45, 193, '1970-01-01', '9999-12-31'),
(194, 43, 194, '1970-01-01', '9999-12-31'),
(195, 43, 195, '1970-01-01', '9999-12-31'),
(196, 42, 196, '1970-01-01', '9999-12-31'),
(197, 43, 197, '1970-01-01', '9999-12-31'),
(198, 43, 198, '1970-01-01', '9999-12-31'),
(199, 43, 199, '1970-01-01', '9999-12-31'),
(200, 43, 200, '1970-01-01', '9999-12-31'),
(201, 43, 201, '1970-01-01', '9999-12-31'),
(202, 43, 202, '1970-01-01', '9999-12-31'),
(203, 42, 203, '1970-01-01', '9999-12-31'),
(204, 43, 204, '1970-01-01', '9999-12-31'),
(205, 46, 205, '1970-01-01', '9999-12-31'),
(206, 43, 206, '1970-01-01', '9999-12-31'),
(207, 42, 207, '1970-01-01', '9999-12-31'),
(208, 42, 208, '1970-01-01', '9999-12-31'),
(209, 42, 209, '1970-01-01', '9999-12-31'),
(210, 42, 210, '1970-01-01', '9999-12-31'),
(211, 43, 211, '1970-01-01', '9999-12-31'),
(212, 44, 212, '1970-01-01', '9999-12-31'),
(213, 47, 213, '1970-01-01', '9999-12-31'),
(214, 43, 214, '1970-01-01', '9999-12-31'),
(215, 42, 215, '1970-01-01', '9999-12-31'),
(216, 43, 216, '1970-01-01', '9999-12-31'),
(217, 42, 217, '1970-01-01', '9999-12-31'),
(218, 44, 218, '1970-01-01', '9999-12-31'),
(219, 45, 219, '1970-01-01', '9999-12-31'),
(220, 42, 220, '1970-01-01', '9999-12-31'),
(221, 42, 221, '1970-01-01', '9999-12-31'),
(222, 42, 222, '1970-01-01', '9999-12-31'),
(223, 43, 223, '1970-01-01', '9999-12-31'),
(224, 42, 224, '1970-01-01', '9999-12-31'),
(225, 43, 225, '1970-01-01', '9999-12-31'),
(226, 44, 226, '1970-01-01', '9999-12-31'),
(227, 42, 227, '1970-01-01', '9999-12-31'),
(228, 43, 228, '1970-01-01', '9999-12-31'),
(229, 42, 229, '1970-01-01', '9999-12-31'),
(230, 43, 230, '1970-01-01', '9999-12-31'),
(231, 42, 231, '1970-01-01', '9999-12-31'),
(232, 42, 232, '1970-01-01', '9999-12-31'),
(233, 43, 233, '1970-01-01', '9999-12-31'),
(234, 43, 234, '1970-01-01', '9999-12-31'),
(235, 43, 235, '1970-01-01', '9999-12-31'),
(236, 43, 236, '1970-01-01', '9999-12-31'),
(237, 43, 237, '1970-01-01', '9999-12-31'),
(238, 43, 238, '1970-01-01', '9999-12-31'),
(239, 43, 239, '1970-01-01', '9999-12-31'),
(240, 44, 240, '1970-01-01', '9999-12-31'),
(241, 43, 241, '1970-01-01', '9999-12-31'),
(242, 42, 242, '1970-01-01', '9999-12-31'),
(243, 42, 243, '1970-01-01', '9999-12-31'),
(244, 43, 244, '1970-01-01', '9999-12-31'),
(245, 42, 245, '1970-01-01', '9999-12-31'),
(246, 42, 246, '1970-01-01', '9999-12-31'),
(247, 42, 247, '1970-01-01', '9999-12-31'),
(248, 42, 248, '1970-01-01', '9999-12-31'),
(249, 42, 249, '1970-01-01', '9999-12-31'),
(250, 42, 250, '1970-01-01', '9999-12-31'),
(251, 44, 251, '1970-01-01', '9999-12-31'),
(252, 42, 252, '1970-01-01', '9999-12-31'),
(253, 42, 253, '1970-01-01', '9999-12-31'),
(254, 42, 254, '1970-01-01', '9999-12-31'),
(255, 43, 255, '1970-01-01', '9999-12-31'),
(256, 42, 256, '1970-01-01', '9999-12-31'),
(257, 43, 257, '1970-01-01', '9999-12-31'),
(258, 42, 258, '1970-01-01', '9999-12-31'),
(259, 42, 259, '1970-01-01', '9999-12-31'),
(260, 42, 260, '1970-01-01', '9999-12-31'),
(261, 42, 261, '1970-01-01', '9999-12-31'),
(262, 43, 262, '1970-01-01', '9999-12-31'),
(263, 43, 263, '1970-01-01', '9999-12-31'),
(264, 43, 264, '1970-01-01', '9999-12-31'),
(265, 43, 265, '1970-01-01', '9999-12-31'),
(266, 43, 266, '1970-01-01', '9999-12-31'),
(267, 43, 267, '1970-01-01', '9999-12-31'),
(268, 42, 268, '1970-01-01', '9999-12-31'),
(269, 42, 269, '1970-01-01', '9999-12-31'),
(270, 42, 270, '1970-01-01', '9999-12-31'),
(271, 43, 271, '1970-01-01', '9999-12-31'),
(272, 43, 272, '1970-01-01', '9999-12-31'),
(273, 43, 273, '1970-01-01', '9999-12-31'),
(274, 43, 274, '1970-01-01', '9999-12-31'),
(275, 43, 275, '1970-01-01', '9999-12-31'),
(276, 42, 276, '1970-01-01', '9999-12-31'),
(277, 43, 277, '1970-01-01', '9999-12-31'),
(278, 43, 278, '1970-01-01', '9999-12-31'),
(279, 43, 279, '1970-01-01', '9999-12-31'),
(280, 43, 280, '1970-01-01', '9999-12-31'),
(281, 43, 281, '1970-01-01', '9999-12-31'),
(282, 43, 282, '1970-01-01', '9999-12-31'),
(283, 42, 283, '1970-01-01', '9999-12-31'),
(284, 49, 284, '1970-01-01', '9999-12-31'),
(285, 43, 285, '1970-01-01', '9999-12-31'),
(286, 43, 286, '1970-01-01', '9999-12-31'),
(287, 43, 287, '1970-01-01', '9999-12-31'),
(288, 43, 288, '1970-01-01', '9999-12-31'),
(289, 43, 289, '1970-01-01', '9999-12-31'),
(290, 43, 290, '1970-01-01', '9999-12-31'),
(291, 43, 291, '1970-01-01', '9999-12-31'),
(292, 43, 292, '1970-01-01', '9999-12-31'),
(293, 43, 293, '1970-01-01', '9999-12-31'),
(294, 43, 294, '1970-01-01', '9999-12-31'),
(295, 43, 295, '1970-01-01', '9999-12-31'),
(296, 43, 296, '1970-01-01', '9999-12-31'),
(297, 43, 297, '1970-01-01', '9999-12-31'),
(298, 43, 298, '1970-01-01', '9999-12-31'),
(299, 42, 299, '1970-01-01', '9999-12-31'),
(300, 43, 300, '1970-01-01', '9999-12-31'),
(301, 43, 301, '1970-01-01', '9999-12-31'),
(302, 42, 302, '1970-01-01', '9999-12-31'),
(303, 42, 303, '1970-01-01', '9999-12-31'),
(304, 42, 304, '1970-01-01', '9999-12-31'),
(305, 43, 305, '1970-01-01', '9999-12-31'),
(306, 42, 306, '1970-01-01', '9999-12-31'),
(307, 42, 307, '1970-01-01', '9999-12-31'),
(308, 43, 308, '1970-01-01', '9999-12-31'),
(309, 43, 309, '1970-01-01', '9999-12-31'),
(310, 42, 310, '1970-01-01', '9999-12-31'),
(311, 43, 311, '1970-01-01', '9999-12-31'),
(312, 43, 312, '1970-01-01', '9999-12-31'),
(313, 43, 313, '1970-01-01', '9999-12-31'),
(314, 43, 314, '1970-01-01', '9999-12-31'),
(315, 42, 315, '1970-01-01', '9999-12-31'),
(316, 43, 316, '1970-01-01', '9999-12-31'),
(317, 43, 317, '1970-01-01', '9999-12-31'),
(318, 43, 318, '1970-01-01', '9999-12-31'),
(319, 43, 319, '1970-01-01', '9999-12-31'),
(320, 42, 320, '1970-01-01', '9999-12-31'),
(321, 42, 321, '1970-01-01', '9999-12-31'),
(322, 42, 322, '1970-01-01', '9999-12-31'),
(323, 42, 323, '1970-01-01', '9999-12-31'),
(324, 42, 324, '1970-01-01', '9999-12-31'),
(325, 43, 325, '1970-01-01', '9999-12-31'),
(326, 43, 326, '1970-01-01', '9999-12-31'),
(327, 50, 327, '1970-01-01', '9999-12-31'),
(328, 44, 328, '1970-01-01', '9999-12-31'),
(329, 43, 329, '1970-01-01', '9999-12-31'),
(330, 43, 330, '1970-01-01', '9999-12-31'),
(331, 42, 331, '1970-01-01', '9999-12-31'),
(332, 43, 332, '1970-01-01', '9999-12-31'),
(333, 43, 333, '1970-01-01', '9999-12-31'),
(334, 42, 334, '1970-01-01', '9999-12-31'),
(335, 43, 335, '1970-01-01', '9999-12-31'),
(336, 43, 336, '1970-01-01', '9999-12-31'),
(337, 42, 337, '1970-01-01', '9999-12-31'),
(338, 43, 338, '1970-01-01', '9999-12-31'),
(339, 43, 339, '1970-01-01', '9999-12-31'),
(340, 42, 340, '1970-01-01', '9999-12-31'),
(341, 51, 341, '1970-01-01', '9999-12-31'),
(342, 52, 342, '1970-01-01', '9999-12-31'),
(343, 52, 343, '1970-01-01', '9999-12-31'),
(344, 53, 344, '1970-01-01', '9999-12-31'),
(345, 52, 345, '1970-01-01', '9999-12-31'),
(346, 52, 346, '1970-01-01', '9999-12-31'),
(347, 52, 347, '1970-01-01', '9999-12-31'),
(348, 52, 348, '1970-01-01', '9999-12-31'),
(349, 51, 349, '1970-01-01', '9999-12-31'),
(350, 56, 350, '1970-01-01', '9999-12-31'),
(351, 54, 351, '1970-01-01', '9999-12-31'),
(352, 56, 352, '1970-01-01', '9999-12-31'),
(353, 52, 353, '1970-01-01', '9999-12-31'),
(354, 52, 354, '1970-01-01', '9999-12-31'),
(355, 53, 355, '1970-01-01', '9999-12-31'),
(356, 51, 356, '1970-01-01', '9999-12-31'),
(357, 52, 357, '1970-01-01', '9999-12-31'),
(358, 53, 358, '1970-01-01', '9999-12-31'),
(359, 51, 359, '1970-01-01', '9999-12-31'),
(360, 51, 360, '1970-01-01', '9999-12-31'),
(361, 53, 361, '1970-01-01', '9999-12-31'),
(362, 52, 362, '1970-01-01', '9999-12-31'),
(363, 52, 363, '1970-01-01', '9999-12-31'),
(364, 52, 364, '1970-01-01', '9999-12-31'),
(365, 52, 365, '1970-01-01', '9999-12-31'),
(366, 52, 366, '1970-01-01', '9999-12-31'),
(367, 52, 367, '1970-01-01', '9999-12-31'),
(368, 52, 368, '1970-01-01', '9999-12-31'),
(369, 52, 369, '1970-01-01', '9999-12-31'),
(370, 52, 370, '1970-01-01', '9999-12-31'),
(371, 51, 371, '1970-01-01', '9999-12-31'),
(372, 51, 372, '1970-01-01', '9999-12-31'),
(373, 57, 373, '1970-01-01', '9999-12-31'),
(374, 58, 374, '1970-01-01', '9999-12-31'),
(375, 59, 375, '1970-01-01', '9999-12-31'),
(376, 59, 376, '1970-01-01', '9999-12-31'),
(377, 60, 377, '1970-01-01', '9999-12-31'),
(378, 61, 378, '1970-01-01', '9999-12-31'),
(379, 62, 379, '1970-01-01', '9999-12-31'),
(380, 61, 380, '1970-01-01', '9999-12-31'),
(381, 63, 381, '1970-01-01', '9999-12-31'),
(382, 59, 382, '1970-01-01', '9999-12-31'),
(383, 59, 383, '1970-01-01', '9999-12-31'),
(384, 59, 384, '1970-01-01', '9999-12-31'),
(385, 61, 385, '1970-01-01', '9999-12-31'),
(386, 63, 386, '1970-01-01', '9999-12-31'),
(387, 64, 387, '1970-01-01', '9999-12-31'),
(388, 63, 388, '1970-01-01', '9999-12-31'),
(389, 64, 389, '1970-01-01', '9999-12-31'),
(390, 65, 390, '1970-01-01', '9999-12-31'),
(391, 64, 391, '1970-01-01', '9999-12-31'),
(392, 65, 392, '1970-01-01', '9999-12-31'),
(393, 59, 393, '1970-01-01', '9999-12-31'),
(394, 64, 394, '1970-01-01', '9999-12-31'),
(395, 64, 395, '1970-01-01', '9999-12-31'),
(396, 61, 396, '1970-01-01', '9999-12-31'),
(397, 65, 397, '1970-01-01', '9999-12-31'),
(398, 64, 398, '1970-01-01', '9999-12-31'),
(399, 64, 399, '1970-01-01', '9999-12-31'),
(400, 64, 400, '1970-01-01', '9999-12-31'),
(401, 64, 401, '1970-01-01', '9999-12-31'),
(402, 61, 402, '1970-01-01', '9999-12-31'),
(403, 61, 403, '1970-01-01', '9999-12-31'),
(404, 64, 404, '1970-01-01', '9999-12-31'),
(405, 65, 405, '1970-01-01', '9999-12-31'),
(406, 65, 406, '1970-01-01', '9999-12-31'),
(407, 65, 407, '1970-01-01', '9999-12-31'),
(408, 61, 408, '1970-01-01', '9999-12-31'),
(409, 61, 409, '1970-01-01', '9999-12-31'),
(410, 65, 410, '1970-01-01', '9999-12-31'),
(411, 61, 411, '1970-01-01', '9999-12-31'),
(412, 66, 412, '1970-01-01', '9999-12-31'),
(413, 67, 413, '1970-01-01', '9999-12-31'),
(414, 68, 414, '1970-01-01', '9999-12-31'),
(415, 56, 416, '1970-01-01', '9999-12-31'),
(416, 56, 418, '1970-01-01', '9999-12-31');

-- --------------------------------------------------------

--
-- テーブルの構造 `statuses`
--

CREATE TABLE `statuses` (
  `status_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `statuses`
--

INSERT INTO `statuses` (`status_id`, `name`) VALUES
(3, '体験'),
(2, '個別'),
(4, '本科'),
(1, '講習');

-- --------------------------------------------------------

--
-- テーブルの構造 `status_students`
--

CREATE TABLE `status_students` (
  `status_student_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `created_at` date NOT NULL,
  `changed_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `status_students`
--

INSERT INTO `status_students` (`status_student_id`, `status_id`, `student_id`, `created_at`, `changed_at`) VALUES
(1, 4, 1, '1970-01-01', '9999-12-31'),
(2, 4, 2, '1970-01-01', '9999-12-31'),
(3, 4, 3, '1970-01-01', '9999-12-31'),
(4, 4, 4, '1970-01-01', '9999-12-31'),
(5, 4, 5, '1970-01-01', '9999-12-31'),
(6, 4, 6, '1970-01-01', '9999-12-31'),
(7, 4, 7, '1970-01-01', '9999-12-31'),
(8, 4, 8, '1970-01-01', '9999-12-31'),
(9, 4, 9, '1970-01-01', '9999-12-31'),
(10, 4, 10, '1970-01-01', '9999-12-31'),
(11, 4, 11, '1970-01-01', '9999-12-31'),
(12, 4, 12, '1970-01-01', '9999-12-31'),
(13, 4, 13, '1970-01-01', '9999-12-31'),
(14, 4, 14, '1970-01-01', '9999-12-31'),
(15, 4, 15, '1970-01-01', '9999-12-31'),
(16, 4, 16, '1970-01-01', '9999-12-31'),
(17, 4, 17, '1970-01-01', '9999-12-31'),
(18, 4, 18, '1970-01-01', '9999-12-31'),
(19, 4, 19, '1970-01-01', '9999-12-31'),
(20, 4, 20, '1970-01-01', '9999-12-31'),
(21, 4, 21, '1970-01-01', '9999-12-31'),
(22, 4, 22, '1970-01-01', '9999-12-31'),
(23, 4, 23, '1970-01-01', '9999-12-31'),
(24, 4, 24, '1970-01-01', '9999-12-31'),
(25, 4, 25, '1970-01-01', '9999-12-31'),
(26, 4, 26, '1970-01-01', '9999-12-31'),
(27, 4, 27, '1970-01-01', '9999-12-31'),
(28, 4, 28, '1970-01-01', '9999-12-31'),
(29, 4, 29, '1970-01-01', '9999-12-31'),
(30, 4, 30, '1970-01-01', '9999-12-31'),
(31, 4, 31, '1970-01-01', '9999-12-31'),
(32, 4, 32, '1970-01-01', '9999-12-31'),
(33, 4, 33, '1970-01-01', '9999-12-31'),
(34, 4, 34, '1970-01-01', '9999-12-31'),
(35, 4, 35, '1970-01-01', '9999-12-31'),
(36, 4, 36, '1970-01-01', '9999-12-31'),
(37, 4, 37, '1970-01-01', '9999-12-31'),
(38, 4, 38, '1970-01-01', '9999-12-31'),
(39, 4, 39, '1970-01-01', '9999-12-31'),
(40, 4, 40, '1970-01-01', '9999-12-31'),
(41, 4, 41, '1970-01-01', '9999-12-31'),
(42, 4, 42, '1970-01-01', '9999-12-31'),
(43, 4, 43, '1970-01-01', '9999-12-31'),
(44, 4, 44, '1970-01-01', '9999-12-31'),
(45, 4, 45, '1970-01-01', '9999-12-31'),
(46, 4, 46, '1970-01-01', '9999-12-31'),
(47, 4, 47, '1970-01-01', '9999-12-31'),
(48, 4, 48, '1970-01-01', '9999-12-31'),
(49, 4, 49, '1970-01-01', '9999-12-31'),
(50, 4, 50, '1970-01-01', '9999-12-31'),
(51, 4, 51, '1970-01-01', '9999-12-31'),
(52, 4, 52, '1970-01-01', '9999-12-31'),
(53, 4, 53, '1970-01-01', '9999-12-31'),
(54, 4, 54, '1970-01-01', '9999-12-31'),
(55, 4, 55, '1970-01-01', '9999-12-31'),
(56, 4, 56, '1970-01-01', '9999-12-31'),
(57, 4, 57, '1970-01-01', '9999-12-31'),
(58, 4, 58, '1970-01-01', '9999-12-31'),
(59, 4, 59, '1970-01-01', '9999-12-31'),
(60, 4, 60, '1970-01-01', '9999-12-31'),
(61, 4, 61, '1970-01-01', '9999-12-31'),
(62, 4, 62, '1970-01-01', '9999-12-31'),
(63, 4, 63, '1970-01-01', '9999-12-31'),
(64, 4, 64, '1970-01-01', '9999-12-31'),
(65, 4, 65, '1970-01-01', '9999-12-31'),
(66, 4, 66, '1970-01-01', '9999-12-31'),
(67, 4, 67, '1970-01-01', '9999-12-31'),
(68, 4, 68, '1970-01-01', '9999-12-31'),
(69, 4, 69, '1970-01-01', '9999-12-31'),
(70, 4, 70, '1970-01-01', '9999-12-31'),
(71, 4, 71, '1970-01-01', '9999-12-31'),
(72, 4, 72, '1970-01-01', '9999-12-31'),
(73, 4, 73, '1970-01-01', '9999-12-31'),
(74, 4, 74, '1970-01-01', '9999-12-31'),
(75, 4, 75, '1970-01-01', '9999-12-31'),
(76, 4, 76, '1970-01-01', '9999-12-31'),
(77, 4, 77, '1970-01-01', '9999-12-31'),
(78, 4, 78, '1970-01-01', '9999-12-31'),
(79, 4, 79, '1970-01-01', '9999-12-31'),
(80, 4, 80, '1970-01-01', '9999-12-31'),
(81, 4, 81, '1970-01-01', '9999-12-31'),
(82, 4, 82, '1970-01-01', '9999-12-31'),
(83, 4, 83, '1970-01-01', '9999-12-31'),
(84, 4, 84, '1970-01-01', '9999-12-31'),
(85, 4, 85, '1970-01-01', '9999-12-31'),
(86, 4, 86, '1970-01-01', '9999-12-31'),
(87, 4, 87, '1970-01-01', '9999-12-31'),
(88, 4, 88, '1970-01-01', '9999-12-31'),
(89, 4, 89, '1970-01-01', '9999-12-31'),
(90, 4, 90, '1970-01-01', '9999-12-31'),
(91, 4, 91, '1970-01-01', '9999-12-31'),
(92, 4, 92, '1970-01-01', '9999-12-31'),
(93, 4, 93, '1970-01-01', '9999-12-31'),
(94, 4, 94, '1970-01-01', '9999-12-31'),
(95, 4, 95, '1970-01-01', '9999-12-31'),
(96, 4, 96, '1970-01-01', '9999-12-31'),
(97, 4, 97, '1970-01-01', '9999-12-31'),
(98, 4, 98, '1970-01-01', '9999-12-31'),
(99, 4, 99, '1970-01-01', '9999-12-31'),
(100, 4, 100, '1970-01-01', '9999-12-31'),
(101, 4, 101, '1970-01-01', '9999-12-31'),
(102, 4, 102, '1970-01-01', '9999-12-31'),
(103, 4, 103, '1970-01-01', '9999-12-31'),
(104, 4, 104, '1970-01-01', '9999-12-31'),
(105, 4, 105, '1970-01-01', '9999-12-31'),
(106, 4, 106, '1970-01-01', '9999-12-31'),
(107, 4, 107, '1970-01-01', '9999-12-31'),
(108, 4, 108, '1970-01-01', '9999-12-31'),
(109, 4, 109, '1970-01-01', '9999-12-31'),
(110, 4, 110, '1970-01-01', '9999-12-31'),
(111, 4, 111, '1970-01-01', '9999-12-31'),
(112, 2, 112, '1970-01-01', '9999-12-31'),
(113, 4, 113, '1970-01-01', '9999-12-31'),
(114, 4, 114, '1970-01-01', '9999-12-31'),
(115, 4, 115, '1970-01-01', '9999-12-31'),
(116, 2, 116, '1970-01-01', '9999-12-31'),
(117, 4, 117, '1970-01-01', '9999-12-31'),
(118, 4, 118, '1970-01-01', '9999-12-31'),
(119, 4, 119, '1970-01-01', '9999-12-31'),
(120, 4, 120, '1970-01-01', '9999-12-31'),
(121, 2, 121, '1970-01-01', '9999-12-31'),
(122, 4, 122, '1970-01-01', '9999-12-31'),
(123, 2, 123, '1970-01-01', '9999-12-31'),
(124, 4, 124, '1970-01-01', '9999-12-31'),
(125, 4, 125, '1970-01-01', '9999-12-31'),
(126, 4, 126, '1970-01-01', '9999-12-31'),
(127, 4, 127, '1970-01-01', '9999-12-31'),
(128, 4, 128, '1970-01-01', '9999-12-31'),
(129, 4, 129, '1970-01-01', '9999-12-31'),
(130, 4, 130, '1970-01-01', '9999-12-31'),
(131, 4, 131, '1970-01-01', '9999-12-31'),
(132, 4, 132, '1970-01-01', '9999-12-31'),
(133, 4, 133, '1970-01-01', '9999-12-31'),
(134, 4, 134, '1970-01-01', '9999-12-31'),
(135, 4, 135, '1970-01-01', '9999-12-31'),
(136, 4, 136, '1970-01-01', '9999-12-31'),
(137, 4, 137, '1970-01-01', '9999-12-31'),
(138, 2, 138, '1970-01-01', '9999-12-31'),
(139, 4, 139, '1970-01-01', '9999-12-31'),
(140, 4, 140, '1970-01-01', '9999-12-31'),
(141, 4, 141, '1970-01-01', '9999-12-31'),
(142, 4, 142, '1970-01-01', '9999-12-31'),
(143, 2, 143, '1970-01-01', '9999-12-31'),
(144, 4, 144, '1970-01-01', '9999-12-31'),
(145, 2, 145, '1970-01-01', '9999-12-31'),
(146, 4, 146, '1970-01-01', '9999-12-31'),
(147, 4, 147, '1970-01-01', '9999-12-31'),
(148, 4, 148, '1970-01-01', '9999-12-31'),
(149, 4, 149, '1970-01-01', '9999-12-31'),
(150, 4, 150, '1970-01-01', '9999-12-31'),
(151, 4, 151, '1970-01-01', '9999-12-31'),
(152, 4, 152, '1970-01-01', '9999-12-31'),
(153, 4, 153, '1970-01-01', '9999-12-31'),
(154, 4, 154, '1970-01-01', '9999-12-31'),
(155, 4, 155, '1970-01-01', '9999-12-31'),
(156, 4, 156, '1970-01-01', '9999-12-31'),
(157, 4, 157, '1970-01-01', '9999-12-31'),
(158, 4, 158, '1970-01-01', '9999-12-31'),
(159, 4, 159, '1970-01-01', '9999-12-31'),
(160, 4, 160, '1970-01-01', '9999-12-31'),
(161, 4, 161, '1970-01-01', '9999-12-31'),
(162, 4, 162, '1970-01-01', '9999-12-31'),
(163, 4, 163, '1970-01-01', '9999-12-31'),
(164, 4, 164, '1970-01-01', '9999-12-31'),
(165, 4, 165, '1970-01-01', '9999-12-31'),
(166, 4, 166, '1970-01-01', '9999-12-31'),
(167, 4, 167, '1970-01-01', '9999-12-31'),
(168, 4, 168, '1970-01-01', '9999-12-31'),
(169, 4, 169, '1970-01-01', '9999-12-31'),
(170, 4, 170, '1970-01-01', '9999-12-31'),
(171, 4, 171, '1970-01-01', '9999-12-31'),
(172, 4, 172, '1970-01-01', '9999-12-31'),
(173, 4, 173, '1970-01-01', '9999-12-31'),
(174, 4, 174, '1970-01-01', '9999-12-31'),
(175, 4, 175, '1970-01-01', '9999-12-31'),
(176, 4, 176, '1970-01-01', '9999-12-31'),
(177, 4, 177, '1970-01-01', '9999-12-31'),
(178, 4, 178, '1970-01-01', '9999-12-31'),
(179, 4, 179, '1970-01-01', '9999-12-31'),
(180, 4, 180, '1970-01-01', '9999-12-31'),
(181, 4, 181, '1970-01-01', '9999-12-31'),
(182, 4, 182, '1970-01-01', '9999-12-31'),
(183, 4, 183, '1970-01-01', '9999-12-31'),
(184, 4, 184, '1970-01-01', '9999-12-31'),
(185, 4, 185, '1970-01-01', '9999-12-31'),
(186, 4, 186, '1970-01-01', '9999-12-31'),
(187, 4, 187, '1970-01-01', '9999-12-31'),
(188, 4, 188, '1970-01-01', '9999-12-31'),
(189, 4, 189, '1970-01-01', '9999-12-31'),
(190, 4, 190, '1970-01-01', '9999-12-31'),
(191, 4, 191, '1970-01-01', '9999-12-31'),
(192, 4, 192, '1970-01-01', '9999-12-31'),
(193, 4, 193, '1970-01-01', '9999-12-31'),
(194, 4, 194, '1970-01-01', '9999-12-31'),
(195, 4, 195, '1970-01-01', '9999-12-31'),
(196, 4, 196, '1970-01-01', '9999-12-31'),
(197, 4, 197, '1970-01-01', '9999-12-31'),
(198, 4, 198, '1970-01-01', '9999-12-31'),
(199, 4, 199, '1970-01-01', '9999-12-31'),
(200, 4, 200, '1970-01-01', '9999-12-31'),
(201, 4, 201, '1970-01-01', '9999-12-31'),
(202, 4, 202, '1970-01-01', '9999-12-31'),
(203, 4, 203, '1970-01-01', '9999-12-31'),
(204, 4, 204, '1970-01-01', '9999-12-31'),
(205, 4, 205, '1970-01-01', '9999-12-31'),
(206, 4, 206, '1970-01-01', '9999-12-31'),
(207, 4, 207, '1970-01-01', '9999-12-31'),
(208, 4, 208, '1970-01-01', '9999-12-31'),
(209, 4, 209, '1970-01-01', '9999-12-31'),
(210, 4, 210, '1970-01-01', '9999-12-31'),
(211, 4, 211, '1970-01-01', '9999-12-31'),
(212, 4, 212, '1970-01-01', '9999-12-31'),
(213, 4, 213, '1970-01-01', '9999-12-31'),
(214, 4, 214, '1970-01-01', '9999-12-31'),
(215, 4, 215, '1970-01-01', '9999-12-31'),
(216, 4, 216, '1970-01-01', '9999-12-31'),
(217, 4, 217, '1970-01-01', '9999-12-31'),
(218, 4, 218, '1970-01-01', '9999-12-31'),
(219, 4, 219, '1970-01-01', '9999-12-31'),
(220, 4, 220, '1970-01-01', '9999-12-31'),
(221, 4, 221, '1970-01-01', '9999-12-31'),
(222, 4, 222, '1970-01-01', '9999-12-31'),
(223, 4, 223, '1970-01-01', '9999-12-31'),
(224, 4, 224, '1970-01-01', '9999-12-31'),
(225, 4, 225, '1970-01-01', '9999-12-31'),
(226, 4, 226, '1970-01-01', '9999-12-31'),
(227, 4, 227, '1970-01-01', '9999-12-31'),
(228, 4, 228, '1970-01-01', '9999-12-31'),
(229, 4, 229, '1970-01-01', '9999-12-31'),
(230, 4, 230, '1970-01-01', '9999-12-31'),
(231, 4, 231, '1970-01-01', '9999-12-31'),
(232, 4, 232, '1970-01-01', '9999-12-31'),
(233, 4, 233, '1970-01-01', '9999-12-31'),
(234, 4, 234, '1970-01-01', '9999-12-31'),
(235, 4, 235, '1970-01-01', '9999-12-31'),
(236, 4, 236, '1970-01-01', '9999-12-31'),
(237, 4, 237, '1970-01-01', '9999-12-31'),
(238, 4, 238, '1970-01-01', '9999-12-31'),
(239, 4, 239, '1970-01-01', '9999-12-31'),
(240, 4, 240, '1970-01-01', '9999-12-31'),
(241, 4, 241, '1970-01-01', '9999-12-31'),
(242, 4, 242, '1970-01-01', '9999-12-31'),
(243, 4, 243, '1970-01-01', '9999-12-31'),
(244, 4, 244, '1970-01-01', '9999-12-31'),
(245, 4, 245, '1970-01-01', '9999-12-31'),
(246, 4, 246, '1970-01-01', '9999-12-31'),
(247, 4, 247, '1970-01-01', '9999-12-31'),
(248, 4, 248, '1970-01-01', '9999-12-31'),
(249, 4, 249, '1970-01-01', '9999-12-31'),
(250, 4, 250, '1970-01-01', '9999-12-31'),
(251, 4, 251, '1970-01-01', '9999-12-31'),
(252, 4, 252, '1970-01-01', '9999-12-31'),
(253, 4, 253, '1970-01-01', '9999-12-31'),
(254, 4, 254, '1970-01-01', '9999-12-31'),
(255, 4, 255, '1970-01-01', '9999-12-31'),
(256, 4, 256, '1970-01-01', '9999-12-31'),
(257, 4, 257, '1970-01-01', '9999-12-31'),
(258, 4, 258, '1970-01-01', '9999-12-31'),
(259, 4, 259, '1970-01-01', '9999-12-31'),
(260, 4, 260, '1970-01-01', '9999-12-31'),
(261, 4, 261, '1970-01-01', '9999-12-31'),
(262, 4, 262, '1970-01-01', '9999-12-31'),
(263, 4, 263, '1970-01-01', '9999-12-31'),
(264, 4, 264, '1970-01-01', '9999-12-31'),
(265, 4, 265, '1970-01-01', '9999-12-31'),
(266, 4, 266, '1970-01-01', '9999-12-31'),
(267, 4, 267, '1970-01-01', '9999-12-31'),
(268, 4, 268, '1970-01-01', '9999-12-31'),
(269, 4, 269, '1970-01-01', '9999-12-31'),
(270, 4, 270, '1970-01-01', '9999-12-31'),
(271, 4, 271, '1970-01-01', '9999-12-31'),
(272, 4, 272, '1970-01-01', '9999-12-31'),
(273, 4, 273, '1970-01-01', '9999-12-31'),
(274, 4, 274, '1970-01-01', '9999-12-31'),
(275, 4, 275, '1970-01-01', '9999-12-31'),
(276, 4, 276, '1970-01-01', '9999-12-31'),
(277, 4, 277, '1970-01-01', '9999-12-31'),
(278, 4, 278, '1970-01-01', '9999-12-31'),
(279, 4, 279, '1970-01-01', '9999-12-31'),
(280, 4, 280, '1970-01-01', '9999-12-31'),
(281, 4, 281, '1970-01-01', '9999-12-31'),
(282, 4, 282, '1970-01-01', '9999-12-31'),
(283, 4, 283, '1970-01-01', '9999-12-31'),
(284, 4, 284, '1970-01-01', '9999-12-31'),
(285, 4, 285, '1970-01-01', '9999-12-31'),
(286, 4, 286, '1970-01-01', '9999-12-31'),
(287, 4, 287, '1970-01-01', '9999-12-31'),
(288, 4, 288, '1970-01-01', '9999-12-31'),
(289, 4, 289, '1970-01-01', '9999-12-31'),
(290, 4, 290, '1970-01-01', '9999-12-31'),
(291, 4, 291, '1970-01-01', '9999-12-31'),
(292, 4, 292, '1970-01-01', '9999-12-31'),
(293, 4, 293, '1970-01-01', '9999-12-31'),
(294, 4, 294, '1970-01-01', '9999-12-31'),
(295, 4, 295, '1970-01-01', '9999-12-31'),
(296, 4, 296, '1970-01-01', '9999-12-31'),
(297, 4, 297, '1970-01-01', '9999-12-31'),
(298, 4, 298, '1970-01-01', '9999-12-31'),
(299, 4, 299, '1970-01-01', '9999-12-31'),
(300, 4, 300, '1970-01-01', '9999-12-31'),
(301, 1, 301, '1970-01-01', '9999-12-31'),
(302, 4, 302, '1970-01-01', '9999-12-31'),
(303, 1, 303, '1970-01-01', '9999-12-31'),
(304, 4, 304, '1970-01-01', '9999-12-31'),
(305, 4, 305, '1970-01-01', '9999-12-31'),
(306, 4, 306, '1970-01-01', '9999-12-31'),
(307, 4, 307, '1970-01-01', '9999-12-31'),
(308, 1, 308, '1970-01-01', '9999-12-31'),
(309, 4, 309, '1970-01-01', '9999-12-31'),
(310, 4, 310, '1970-01-01', '9999-12-31'),
(311, 4, 311, '1970-01-01', '9999-12-31'),
(312, 4, 312, '1970-01-01', '9999-12-31'),
(313, 4, 313, '1970-01-01', '9999-12-31'),
(314, 4, 314, '1970-01-01', '9999-12-31'),
(315, 4, 315, '1970-01-01', '9999-12-31'),
(316, 4, 316, '1970-01-01', '9999-12-31'),
(317, 4, 317, '1970-01-01', '9999-12-31'),
(318, 4, 318, '1970-01-01', '9999-12-31'),
(319, 4, 319, '1970-01-01', '9999-12-31'),
(320, 1, 320, '1970-01-01', '9999-12-31'),
(321, 4, 321, '1970-01-01', '9999-12-31'),
(322, 4, 322, '1970-01-01', '9999-12-31'),
(323, 4, 323, '1970-01-01', '9999-12-31'),
(324, 4, 324, '1970-01-01', '9999-12-31'),
(325, 4, 325, '1970-01-01', '9999-12-31'),
(326, 4, 326, '1970-01-01', '9999-12-31'),
(327, 4, 327, '1970-01-01', '9999-12-31'),
(328, 4, 328, '1970-01-01', '9999-12-31'),
(329, 4, 329, '1970-01-01', '9999-12-31'),
(330, 4, 330, '1970-01-01', '9999-12-31'),
(331, 4, 331, '1970-01-01', '9999-12-31'),
(332, 4, 332, '1970-01-01', '9999-12-31'),
(333, 4, 333, '1970-01-01', '9999-12-31'),
(334, 4, 334, '1970-01-01', '9999-12-31'),
(335, 4, 335, '1970-01-01', '9999-12-31'),
(336, 4, 336, '1970-01-01', '9999-12-31'),
(337, 4, 337, '1970-01-01', '9999-12-31'),
(338, 4, 338, '1970-01-01', '9999-12-31'),
(339, 4, 339, '1970-01-01', '9999-12-31'),
(340, 4, 340, '1970-01-01', '9999-12-31'),
(341, 4, 341, '1970-01-01', '9999-12-31'),
(342, 4, 342, '1970-01-01', '9999-12-31'),
(343, 4, 343, '1970-01-01', '9999-12-31'),
(344, 4, 344, '1970-01-01', '9999-12-31'),
(345, 4, 345, '1970-01-01', '9999-12-31'),
(346, 4, 346, '1970-01-01', '9999-12-31'),
(347, 4, 347, '1970-01-01', '9999-12-31'),
(348, 4, 348, '1970-01-01', '9999-12-31'),
(349, 4, 349, '1970-01-01', '9999-12-31'),
(350, 4, 350, '1970-01-01', '9999-12-31'),
(351, 4, 351, '1970-01-01', '9999-12-31'),
(352, 4, 352, '1970-01-01', '9999-12-31'),
(353, 4, 353, '1970-01-01', '9999-12-31'),
(354, 1, 354, '1970-01-01', '9999-12-31'),
(355, 4, 355, '1970-01-01', '9999-12-31'),
(356, 4, 356, '1970-01-01', '9999-12-31'),
(357, 4, 357, '1970-01-01', '9999-12-31'),
(358, 4, 358, '1970-01-01', '9999-12-31'),
(359, 4, 359, '1970-01-01', '9999-12-31'),
(360, 4, 360, '1970-01-01', '9999-12-31'),
(361, 4, 361, '1970-01-01', '9999-12-31'),
(362, 4, 362, '1970-01-01', '9999-12-31'),
(363, 4, 363, '1970-01-01', '9999-12-31'),
(364, 4, 364, '1970-01-01', '9999-12-31'),
(365, 1, 365, '1970-01-01', '9999-12-31'),
(366, 1, 366, '1970-01-01', '9999-12-31'),
(367, 4, 367, '1970-01-01', '9999-12-31'),
(368, 4, 368, '1970-01-01', '9999-12-31'),
(369, 1, 369, '1970-01-01', '9999-12-31'),
(370, 1, 370, '1970-01-01', '9999-12-31'),
(371, 4, 371, '1970-01-01', '9999-12-31'),
(372, 4, 372, '1970-01-01', '9999-12-31'),
(373, 2, 373, '1970-01-01', '9999-12-31'),
(374, 4, 374, '1970-01-01', '9999-12-31'),
(375, 4, 375, '1970-01-01', '9999-12-31'),
(376, 4, 376, '1970-01-01', '9999-12-31'),
(377, 2, 377, '1970-01-01', '9999-12-31'),
(378, 4, 378, '1970-01-01', '9999-12-31'),
(379, 4, 379, '1970-01-01', '9999-12-31'),
(380, 4, 380, '1970-01-01', '9999-12-31'),
(381, 4, 381, '1970-01-01', '9999-12-31'),
(382, 2, 382, '1970-01-01', '9999-12-31'),
(383, 4, 383, '1970-01-01', '9999-12-31'),
(384, 2, 384, '1970-01-01', '9999-12-31'),
(385, 4, 385, '1970-01-01', '9999-12-31'),
(386, 4, 386, '1970-01-01', '9999-12-31'),
(387, 4, 387, '1970-01-01', '9999-12-31'),
(388, 4, 388, '1970-01-01', '9999-12-31'),
(389, 4, 389, '1970-01-01', '9999-12-31'),
(390, 4, 390, '1970-01-01', '9999-12-31'),
(391, 4, 391, '1970-01-01', '9999-12-31'),
(392, 4, 392, '1970-01-01', '9999-12-31'),
(393, 4, 393, '1970-01-01', '9999-12-31'),
(394, 4, 394, '1970-01-01', '9999-12-31'),
(395, 4, 395, '1970-01-01', '9999-12-31'),
(396, 4, 396, '1970-01-01', '9999-12-31'),
(397, 4, 397, '1970-01-01', '9999-12-31'),
(398, 4, 398, '1970-01-01', '9999-12-31'),
(399, 2, 399, '1970-01-01', '9999-12-31'),
(400, 4, 400, '1970-01-01', '9999-12-31'),
(401, 4, 401, '1970-01-01', '9999-12-31'),
(402, 4, 402, '1970-01-01', '9999-12-31'),
(403, 4, 403, '1970-01-01', '9999-12-31'),
(404, 2, 404, '1970-01-01', '9999-12-31'),
(405, 4, 405, '1970-01-01', '9999-12-31'),
(406, 2, 406, '1970-01-01', '9999-12-31'),
(407, 4, 407, '1970-01-01', '9999-12-31'),
(408, 4, 408, '1970-01-01', '9999-12-31'),
(409, 4, 409, '1970-01-01', '9999-12-31'),
(410, 4, 410, '1970-01-01', '9999-12-31'),
(411, 4, 411, '1970-01-01', '9999-12-31'),
(412, 4, 412, '1970-01-01', '9999-12-31'),
(413, 4, 413, '1970-01-01', '9999-12-31'),
(414, 4, 414, '1970-01-01', '9999-12-31'),
(415, 1, 416, '1970-01-01', '9999-12-31'),
(416, 3, 418, '1970-01-01', '9999-12-31');

-- --------------------------------------------------------

--
-- テーブルの構造 `students`
--

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `el1` int(11) NOT NULL,
  `code` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `students`
--

INSERT INTO `students` (`student_id`, `el1`, `code`, `name`, `cram_school_id`) VALUES
(154, 2015, 1230039, '大谷 茂壱', 1),
(155, 2015, 1240015, '中西 巧', 1),
(156, 2015, 1240023, '大浪 弘貴', 1),
(157, 2015, 1240030, '中田 宗亮', 1),
(158, 2015, 1240033, '村田 直弥', 1),
(159, 2015, 1240041, '竹井 理', 1),
(160, 2015, 1240042, '能津 悠斗', 1),
(161, 2015, 1240043, '今野 遥人', 1),
(162, 2015, 1240049, '川端 康太', 1),
(163, 2015, 1240053, '小西 智己', 1),
(164, 2015, 1249013, '田中 奏良', 1),
(165, 2015, 1249020, '藤田 咲和', 1),
(166, 2015, 1249025, '村田 和香奈', 1),
(167, 2015, 1249027, '柴田 明愛', 1),
(168, 2015, 1249038, '岩崎 百音', 1),
(169, 2015, 1249039, '和井 しおり', 1),
(170, 2015, 1249042, '荒井 陽', 1),
(171, 2016, 1250003, '和智 光祐', 1),
(172, 2016, 1250004, '大倉 聡介', 1),
(173, 2016, 1250005, '皆川 悠輔', 1),
(174, 2016, 1250006, '瀧澤 大翔', 1),
(175, 2016, 1250007, '松本 駿哉', 1),
(176, 2016, 1250009, '清水 俊信', 1),
(177, 2016, 1250010, '小牧 祐介', 1),
(178, 2016, 1250012, '後藤 琉生', 1),
(179, 2016, 1250013, '金子 倖大', 1),
(180, 2016, 1250014, '中部 太智', 1),
(181, 2016, 1250016, '山本 鼓太郎', 1),
(182, 2016, 1250017, '島元 航太郎', 1),
(183, 2016, 1250018, '小俣 淳也', 1),
(184, 2016, 1250019, '上木 星南', 1),
(185, 2016, 1250020, '杉山 海斗', 1),
(186, 2016, 1250021, '伊藤 優太', 1),
(187, 2016, 1250022, '松尾 勇之介', 1),
(188, 2016, 1250024, '小西 巧真', 1),
(189, 2016, 1250025, '桑名 巧', 1),
(190, 2016, 1250026, '中原 大貴', 1),
(191, 2016, 1250027, '嶌田 悠生', 1),
(192, 2016, 1250028, '齋藤 健勝', 1),
(193, 2016, 1250029, '山内 綾人', 1),
(194, 2016, 1250030, '上高原 慶人', 1),
(195, 2016, 1250032, '石川 駿斗', 1),
(196, 2016, 1250033, '大谷 逸馬', 1),
(197, 2016, 1250034, '大橋 颯佑', 1),
(198, 2016, 1250035, '岩﨑 雄飛', 1),
(199, 2016, 1250036, '三宅 利明', 1),
(200, 2016, 1250037, '齋藤 大亮', 1),
(201, 2016, 1250039, '渡邊 琉生', 1),
(202, 2016, 1250041, '宮島 拓未', 1),
(203, 2016, 1250042, '佐藤 諒真', 1),
(204, 2016, 1250043, '田中 悠瑚', 1),
(205, 2016, 1250044, '長谷川 蒼太', 1),
(206, 2016, 1250045, '山口 蓮斗', 1),
(207, 2016, 1250046, '佐藤 侑來', 1),
(208, 2016, 1250047, '塚本 翔', 1),
(209, 2016, 1250048, '武田 和也', 1),
(210, 2016, 1250049, '勝野 純成', 1),
(211, 2016, 1250050, '成冨 聡太', 1),
(212, 2016, 1250051, '野月 嘉人', 1),
(213, 2016, 1250052, '小林 温季', 1),
(214, 2016, 1250053, '片野 眞琴', 1),
(215, 2016, 1250054, '吉田 凰一郎', 1),
(216, 2016, 1250055, '川越 優芽', 1),
(217, 2016, 1250056, '海谷 悠人', 1),
(218, 2016, 1250057, '斗成 海吏', 1),
(219, 2016, 1259001, '矢野 みなみ', 1),
(220, 2016, 1259002, '安藤 汐織', 1),
(221, 2016, 1259004, '仲沢 美咲', 1),
(222, 2016, 1259005, '森野 瑠夏', 1),
(223, 2016, 1259006, '小藪 彩花', 1),
(224, 2016, 1259007, '伊藤 菜月', 1),
(225, 2016, 1259008, '佐内 結香', 1),
(226, 2016, 1259009, '鴨川 晏佳', 1),
(227, 2016, 1259010, '飯田 彩心', 1),
(228, 2016, 1259013, '丸中 実莉', 1),
(229, 2016, 1259014, '藤原 侑香', 1),
(230, 2016, 1259016, '勝山 真帆', 1),
(231, 2016, 1259018, '塚本 桜妃', 1),
(232, 2016, 1259019, '西田 伊織', 1),
(233, 2016, 1259020, '高橋 美玲', 1),
(234, 2016, 1259023, '福島 心結', 1),
(235, 2016, 1259024, '風間 琴葉', 1),
(236, 2016, 1259026, '下山 歩乃佳', 1),
(237, 2016, 1259028, '芳原 彩花', 1),
(238, 2016, 1259029, '松田 芽凜', 1),
(239, 2016, 1259030, '宍戸 和', 1),
(240, 2016, 1259031, '山口 千尋', 1),
(241, 2016, 1259032, '髙田 紗良', 1),
(242, 2016, 1259033, '大竹 沙季', 1),
(243, 2016, 1259034, '石上 莉奈', 1),
(244, 2016, 1259035, '伊藤 梛々香', 1),
(245, 2016, 1259036, '早坂 優芽', 1),
(246, 2016, 1259037, '難波 京花', 1),
(247, 2016, 1259038, '菱山 環子', 1),
(248, 2016, 1259039, '佐藤 結菜', 1),
(249, 2016, 1259040, '石垣 京子', 1),
(250, 2016, 1259041, '田中 沙樹', 1),
(251, 2016, 1259042, '石井 来望', 1),
(252, 2016, 1259043, '武田 優里', 1),
(253, 2016, 1259044, '茅野 七菜', 1),
(254, 2016, 1259045, '上田 桃香', 1),
(255, 2017, 1260001, '佐藤 一秀', 1),
(256, 2017, 1260006, '水野 瑛太', 1),
(257, 2017, 1260007, '長橋 琥珀', 1),
(258, 2017, 1260009, '梅垣 琥太朗', 1),
(259, 2017, 1260010, '長野 光利', 1),
(260, 2017, 1260011, '村上 洸', 1),
(261, 2017, 1260012, '磯部 琉斗', 1),
(262, 2017, 1260013, '小笠原 拓海', 1),
(263, 2017, 1260015, '三浦 諒', 1),
(264, 2017, 1260016, '井上 稜太', 1),
(265, 2017, 1260017, '田中 詩大', 1),
(266, 2017, 1260018, '清水 涼太郎', 1),
(267, 2017, 1260019, '竹井 希', 1),
(268, 2017, 1260020, '藤井 登士', 1),
(269, 2017, 1260021, '伊藤 漣之介', 1),
(270, 2017, 1260022, '勝沼 悠', 1),
(271, 2017, 1260023, '中村 聡佑', 1),
(272, 2017, 1260024, '新開 涼介', 1),
(273, 2017, 1260025, '中村 唯人', 1),
(274, 2017, 1260027, '熊田 遥斗', 1),
(275, 2017, 1260028, '小野 公聖', 1),
(276, 2017, 1260029, '榎本 優都', 1),
(277, 2017, 1260030, '柳田 勘佑', 1),
(278, 2017, 1260031, '横田 智也', 1),
(279, 2017, 1260032, '西島 惇貴', 1),
(280, 2017, 1260033, '串田 嶺央', 1),
(281, 2017, 1260034, '則武 大翔', 1),
(282, 2017, 1260035, '沓澤 郁依', 1),
(283, 2017, 1260036, '松本 直弥', 1),
(284, 2017, 1260037, '入田 琉碧', 1),
(285, 2017, 1260038, '芳賀 陽向', 1),
(286, 2017, 1260039, '佐藤 蒼真', 1),
(287, 2017, 1269007, '新田 望乃', 1),
(288, 2017, 1269008, '西園 桃花', 1),
(289, 2017, 1269013, '髙山 愛結', 1),
(290, 2017, 1269014, '内田 千穂', 1),
(291, 2017, 1269016, '梅田 柚奈', 1),
(292, 2017, 1269017, '木村 伊吹', 1),
(293, 2017, 1269018, '松本 芽采', 1),
(294, 2017, 1269019, '山本 真那', 1),
(295, 2017, 1269020, '小部 栞奈', 1),
(296, 2017, 1269022, '森田 奏', 1),
(297, 2017, 1269023, '石田 晋碧里', 1),
(298, 2017, 1269024, '三浦 ちひろ', 1),
(299, 2017, 1269025, '谷内 美咲', 1),
(300, 2017, 1269026, '武川 和佳奈', 1),
(301, 2017, 1269027, '鹿山 巴奈', 1),
(302, 2017, 1269028, '岩本 実織', 1),
(303, 2017, 1269029, '川口 小雪', 1),
(304, 2017, 1269030, '木住野 結月', 1),
(305, 2018, 1270001, '富田 悠月', 1),
(306, 2018, 1270002, '渡辺 良祐', 1),
(307, 2018, 1270003, '岩本 泰志', 1),
(308, 2018, 1270005, '後藤 響樹', 1),
(309, 2018, 1270006, '浦邉 藍磁', 1),
(310, 2018, 1270007, '村木 柚太', 1),
(311, 2018, 1270008, '中島 康太', 1),
(312, 2018, 1270009, '平石 瑛士', 1),
(313, 2018, 1270010, '中村 郁杜', 1),
(314, 2018, 1270012, '遠田 永翔', 1),
(315, 2018, 1270013, '木村 洸太', 1),
(316, 2018, 1270014, '入部 航一', 1),
(317, 2018, 1270015, '岡崎 幸祐', 1),
(318, 2018, 1270016, '山本 啓馬', 1),
(319, 2018, 1270017, '木下 泰成', 1),
(320, 2018, 1270018, '伊藤 志優', 1),
(321, 2018, 1270019, '髙橋 幸惺', 1),
(322, 2018, 1270020, '児嶋 泰壱', 1),
(323, 2018, 1270021, '津賀 凪翔', 1),
(324, 2018, 1279006, '藤田 璃胡', 1),
(325, 2018, 1279007, '藤田 結咲', 1),
(326, 2018, 1279008, '朝日 優衣', 1),
(327, 2018, 1279011, '反町 咲紀', 1),
(328, 2018, 1279012, '戸叶 新雫', 1),
(329, 2018, 1279013, '久田 結貴', 1),
(330, 2018, 1279014, '上髙原 璃音', 1),
(331, 2018, 1279016, '関谷 美羽', 1),
(332, 2018, 1279018, '佐藤 愛耶', 1),
(333, 2018, 1279019, '黒木 咲來', 1),
(334, 2018, 1279020, '花井 彩恵', 1),
(335, 2018, 1279021, '岩岡 咲妃', 1),
(336, 2018, 1279022, '岡部 紗英', 1),
(337, 2018, 1279023, '森川 芽生', 1),
(338, 2018, 1279024, '小川 絢子', 1),
(339, 2018, 1279025, '八木 芽衣', 1),
(340, 2018, 1279026, '増島 佳音', 1),
(341, 2019, 1280001, '長橋 鋼', 1),
(342, 2019, 1280002, '松本 翔哉', 1),
(343, 2019, 1280004, '藤澤 蒼生', 1),
(344, 2019, 1280005, '水野 晶太', 1),
(345, 2019, 1280007, '佐内 柊太', 1),
(346, 2019, 1280008, '中根 旺輔', 1),
(347, 2019, 1280010, '鈴木 恵多', 1),
(348, 2019, 1280011, '原田 洸', 1),
(349, 2019, 1280012, '櫛田 駿太', 1),
(350, 2019, 1280013, '勝沼 司', 1),
(351, 2019, 1289002, '鈴木 めい', 1),
(352, 2019, 1289004, '大谷 紬', 1),
(353, 2019, 1289005, '伊地知 南希', 1),
(354, 2019, 1289006, '石垣 翔子', 1),
(355, 2019, 1289007, '西田 薫', 1),
(356, 2019, 1289008, '松下 由芽', 1),
(357, 2020, 1290002, '藤本 仁之介', 1),
(358, 2020, 1290003, '原田 恵太', 1),
(359, 2020, 1290004, '榎本 羽流', 1),
(360, 2020, 1290005, '上木 洸', 1),
(361, 2020, 1290006, '藤原 脩和', 1),
(362, 2020, 1290007, '伊藤 椋一', 1),
(363, 2020, 1290008, '浅羽 結基', 1),
(364, 2020, 1290009, '八木 皓介', 1),
(365, 2020, 1290010, '松本 海', 1),
(366, 2020, 1290011, '松本 空', 1),
(367, 2020, 1299001, '酒井 菜々美', 1),
(368, 2020, 1299002, '大岸 真菜美', 1),
(369, 2021, 1300001, '藤澤 瑠生', 1),
(370, 2021, 1300002, '古澤 時人', 1),
(371, 2021, 1300003, '櫛田 将太', 1),
(372, 2021, 1309001, '落合 萌香', 1),
(373, 2014, 22239001, '宮田 めい', 9),
(374, 2016, 22250002, '栗田 乾暉', 9),
(375, 2016, 22250004, '村木 颯', 9),
(376, 2016, 22250005, '濵名 賛斗', 9),
(377, 2016, 22250006, '内山 瑛太', 9),
(378, 2016, 22250007, '杉山 耕輔', 9),
(379, 2016, 22250008, '山野 悠真', 9),
(380, 2016, 22250009, '髙田 大成', 9),
(381, 2016, 22250010, '細川 大晴', 9),
(382, 2016, 22250011, '作家 叶真', 9),
(383, 2016, 22250012, '樽井 謙汰郎', 9),
(384, 2016, 22250013, '伊藤 礼夢', 9),
(385, 2016, 22259001, '降矢 莉愛夏', 9),
(386, 2016, 22259002, '小林 咲心', 9),
(387, 2016, 22259003, '松本 心花', 9),
(388, 2016, 22259004, '宮岡 結來', 9),
(389, 2016, 22259005, '近藤 瑚子', 9),
(390, 2016, 22259006, '藤井 佑季', 9),
(391, 2016, 22259007, '高石 なづな', 9),
(392, 2016, 22259008, '松村 眞帆', 9),
(393, 2016, 22259010, '細美 莉央', 9),
(394, 2016, 22259011, '髙橋 凛', 9),
(395, 2016, 22259012, '清水 心結', 9),
(396, 2016, 22259013, '和田 穂香', 9),
(397, 2016, 22259014, '伊藤 のぞみ', 9),
(398, 2017, 22260003, '村田 誠', 9),
(399, 2017, 22260004, '辻 智文', 9),
(400, 2017, 22260005, '宮原 陸司', 9),
(401, 2017, 22260006, '菊池 才駆', 9),
(402, 2017, 22269002, '村木 愛奈', 9),
(403, 2017, 22269003, '柳谷 瑠璃子', 9),
(404, 2017, 22269004, '天野 凛星', 9),
(405, 2018, 22270001, '鈴木 壮佑', 9),
(406, 2018, 22270002, '横島 琉偉', 9),
(407, 2018, 22279001, 'ケリー ジャスミン茉利乃', 9),
(408, 2018, 22279002, '赤石 琴音', 9),
(409, 2018, 22279003, '脇山 咲羽', 9),
(410, 2018, 22279004, '木許 夏希', 9),
(411, 2018, 22279005, '荒井 日衣奈', 9),
(412, 2019, 22289001, '桑原 汐里', 9),
(413, 2019, 22289002, '宮原 汐里', 9),
(414, 2020, 22299001, '栗田 彩桜', 9),
(415, 2000, 9, 'ave', 9),
(416, 2017, 8888888, 'hogehogemoheji', 1),
(417, 2000, 1, 'ave', 1),
(418, 2017, 1111111, '体験１', 1);

-- --------------------------------------------------------

--
-- テーブルの構造 `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `cram_school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `users`
--

INSERT INTO `users` (`user_id`, `name`, `password`, `role_id`, `cram_school_id`) VALUES
(1, 'admin1', '$2a$10$SWGqOvoZAJ/EbhJTvM1gSu3PcLsmtw5ZWZrbViU.gd9LK5q05d6gW', 1, 1),
(2, 'general1', '$2a$10$XT3Q7oBiUoMtKrovlHLeOeNsXN0A6cd8EITfDKv/7Iou0BRrhKLgO', 2, 1),
(104, 'general2', '$2a$10$4n5y.sSHmvitb3NE3wRmqu8rEuw9YkRdvQzQZoQLTT225do4blDHm', 2, 8);

--
-- ダンプしたテーブルのインデックス
--

--
-- テーブルのインデックス `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`class_id`),
  ADD UNIQUE KEY `subject` (`subject`,`name`,`cram_school_id`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- テーブルのインデックス `class_students`
--
ALTER TABLE `class_students`
  ADD PRIMARY KEY (`class_student_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `class_id` (`class_id`);

--
-- テーブルのインデックス `cram_schools`
--
ALTER TABLE `cram_schools`
  ADD PRIMARY KEY (`cram_school_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- テーブルのインデックス `mock_tests`
--
ALTER TABLE `mock_tests`
  ADD PRIMARY KEY (`mock_test_id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- テーブルのインデックス `mock_test_results`
--
ALTER TABLE `mock_test_results`
  ADD PRIMARY KEY (`mock_test_id`,`student_id`),
  ADD KEY `student_id` (`student_id`);

--
-- テーブルのインデックス `other_tests`
--
ALTER TABLE `other_tests`
  ADD PRIMARY KEY (`other_test_id`),
  ADD UNIQUE KEY `school_id` (`school_id`,`semester`,`grade`),
  ADD UNIQUE KEY `UK9erhi4lqrgy8638ot081bmqml` (`school_id`,`semester`,`grade`),
  ADD KEY `FKpvfjcnmmj1398ver5nv15xmi3` (`school_school_id`);

--
-- テーブルのインデックス `other_test_results`
--
ALTER TABLE `other_test_results`
  ADD PRIMARY KEY (`other_test_result_id`),
  ADD UNIQUE KEY `other_test_id` (`other_test_id`,`student_id`),
  ADD UNIQUE KEY `UKne8l76d77c2d6490vn06r45wv` (`other_test_id`,`student_id`),
  ADD KEY `FKf57h3tovoojscwjf6ueew9b4b` (`student_id`);

--
-- テーブルのインデックス `regular_tests`
--
ALTER TABLE `regular_tests`
  ADD PRIMARY KEY (`regular_test_id`),
  ADD KEY `school_id` (`school_id`),
  ADD KEY `regular_test_set_id` (`regular_test_set_id`);

--
-- テーブルのインデックス `regular_test_results`
--
ALTER TABLE `regular_test_results`
  ADD PRIMARY KEY (`regular_test_result_id`),
  ADD KEY `regular_test_id` (`regular_test_id`),
  ADD KEY `student_id` (`student_id`);

--
-- テーブルのインデックス `regular_test_sets`
--
ALTER TABLE `regular_test_sets`
  ADD PRIMARY KEY (`regular_test_set_id`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- テーブルのインデックス `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- テーブルのインデックス `schools`
--
ALTER TABLE `schools`
  ADD PRIMARY KEY (`school_id`),
  ADD UNIQUE KEY `name` (`name`,`cram_school_id`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- テーブルのインデックス `school_records`
--
ALTER TABLE `school_records`
  ADD PRIMARY KEY (`school_record_id`),
  ADD UNIQUE KEY `school_id` (`school_id`,`school_record_set_id`),
  ADD KEY `school_record_set_id` (`school_record_set_id`);

--
-- テーブルのインデックス `school_record_results`
--
ALTER TABLE `school_record_results`
  ADD PRIMARY KEY (`school_record_result_id`),
  ADD UNIQUE KEY `student_id` (`student_id`,`school_record_id`),
  ADD KEY `school_record_id` (`school_record_id`);

--
-- テーブルのインデックス `school_record_sets`
--
ALTER TABLE `school_record_sets`
  ADD PRIMARY KEY (`school_record_set_id`),
  ADD UNIQUE KEY `term` (`term`,`grade`,`semester`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- テーブルのインデックス `school_students`
--
ALTER TABLE `school_students`
  ADD PRIMARY KEY (`school_student_id`),
  ADD KEY `school_id` (`school_id`),
  ADD KEY `student_id` (`student_id`);

--
-- テーブルのインデックス `statuses`
--
ALTER TABLE `statuses`
  ADD PRIMARY KEY (`status_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- テーブルのインデックス `status_students`
--
ALTER TABLE `status_students`
  ADD PRIMARY KEY (`status_student_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `student_id` (`student_id`);

--
-- テーブルのインデックス `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- テーブルのインデックス `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `cram_school_id` (`cram_school_id`);

--
-- ダンプしたテーブルの AUTO_INCREMENT
--

--
-- テーブルの AUTO_INCREMENT `classes`
--
ALTER TABLE `classes`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- テーブルの AUTO_INCREMENT `class_students`
--
ALTER TABLE `class_students`
  MODIFY `class_student_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- テーブルの AUTO_INCREMENT `cram_schools`
--
ALTER TABLE `cram_schools`
  MODIFY `cram_school_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1673;

--
-- テーブルの AUTO_INCREMENT `mock_tests`
--
ALTER TABLE `mock_tests`
  MODIFY `mock_test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- テーブルの AUTO_INCREMENT `other_tests`
--
ALTER TABLE `other_tests`
  MODIFY `other_test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- テーブルの AUTO_INCREMENT `other_test_results`
--
ALTER TABLE `other_test_results`
  MODIFY `other_test_result_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- テーブルの AUTO_INCREMENT `regular_tests`
--
ALTER TABLE `regular_tests`
  MODIFY `regular_test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- テーブルの AUTO_INCREMENT `regular_test_results`
--
ALTER TABLE `regular_test_results`
  MODIFY `regular_test_result_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- テーブルの AUTO_INCREMENT `regular_test_sets`
--
ALTER TABLE `regular_test_sets`
  MODIFY `regular_test_set_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- テーブルの AUTO_INCREMENT `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- テーブルの AUTO_INCREMENT `schools`
--
ALTER TABLE `schools`
  MODIFY `school_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- テーブルの AUTO_INCREMENT `school_records`
--
ALTER TABLE `school_records`
  MODIFY `school_record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- テーブルの AUTO_INCREMENT `school_record_results`
--
ALTER TABLE `school_record_results`
  MODIFY `school_record_result_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=159;

--
-- テーブルの AUTO_INCREMENT `school_record_sets`
--
ALTER TABLE `school_record_sets`
  MODIFY `school_record_set_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- テーブルの AUTO_INCREMENT `school_students`
--
ALTER TABLE `school_students`
  MODIFY `school_student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=417;

--
-- テーブルの AUTO_INCREMENT `statuses`
--
ALTER TABLE `statuses`
  MODIFY `status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- テーブルの AUTO_INCREMENT `status_students`
--
ALTER TABLE `status_students`
  MODIFY `status_student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=417;

--
-- テーブルの AUTO_INCREMENT `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=419;

--
-- テーブルの AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=378;

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);

--
-- テーブルの制約 `class_students`
--
ALTER TABLE `class_students`
  ADD CONSTRAINT `class_students_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `class_students_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`);

--
-- テーブルの制約 `mock_tests`
--
ALTER TABLE `mock_tests`
  ADD CONSTRAINT `mock_tests_ibfk_1` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);

--
-- テーブルの制約 `mock_test_results`
--
ALTER TABLE `mock_test_results`
  ADD CONSTRAINT `mock_test_results_ibfk_1` FOREIGN KEY (`mock_test_id`) REFERENCES `mock_tests` (`mock_test_id`),
  ADD CONSTRAINT `mock_test_results_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- テーブルの制約 `other_tests`
--
ALTER TABLE `other_tests`
  ADD CONSTRAINT `FKpvfjcnmmj1398ver5nv15xmi3` FOREIGN KEY (`school_school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `other_tests_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`);

--
-- テーブルの制約 `other_test_results`
--
ALTER TABLE `other_test_results`
  ADD CONSTRAINT `FKf57h3tovoojscwjf6ueew9b4b` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `other_test_results_ibfk_1` FOREIGN KEY (`other_test_id`) REFERENCES `other_tests` (`other_test_id`);

--
-- テーブルの制約 `regular_tests`
--
ALTER TABLE `regular_tests`
  ADD CONSTRAINT `regular_tests_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `regular_tests_ibfk_2` FOREIGN KEY (`regular_test_set_id`) REFERENCES `regular_test_sets` (`regular_test_set_id`);

--
-- テーブルの制約 `regular_test_results`
--
ALTER TABLE `regular_test_results`
  ADD CONSTRAINT `regular_test_results_ibfk_1` FOREIGN KEY (`regular_test_id`) REFERENCES `regular_tests` (`regular_test_id`),
  ADD CONSTRAINT `regular_test_results_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- テーブルの制約 `regular_test_sets`
--
ALTER TABLE `regular_test_sets`
  ADD CONSTRAINT `regular_test_sets_ibfk_1` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);

--
-- テーブルの制約 `schools`
--
ALTER TABLE `schools`
  ADD CONSTRAINT `schools_ibfk_1` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);

--
-- テーブルの制約 `school_records`
--
ALTER TABLE `school_records`
  ADD CONSTRAINT `school_records_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `school_records_ibfk_2` FOREIGN KEY (`school_record_set_id`) REFERENCES `school_record_sets` (`school_record_set_id`);

--
-- テーブルの制約 `school_record_results`
--
ALTER TABLE `school_record_results`
  ADD CONSTRAINT `school_record_results_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `school_record_results_ibfk_2` FOREIGN KEY (`school_record_id`) REFERENCES `school_records` (`school_record_id`);

--
-- テーブルの制約 `school_record_sets`
--
ALTER TABLE `school_record_sets`
  ADD CONSTRAINT `school_record_sets_ibfk_1` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);

--
-- テーブルの制約 `school_students`
--
ALTER TABLE `school_students`
  ADD CONSTRAINT `school_students_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `school_students_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- テーブルの制約 `status_students`
--
ALTER TABLE `status_students`
  ADD CONSTRAINT `status_students_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`status_id`),
  ADD CONSTRAINT `status_students_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- テーブルの制約 `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);

--
-- テーブルの制約 `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  ADD CONSTRAINT `users_ibfk_2` FOREIGN KEY (`cram_school_id`) REFERENCES `cram_schools` (`cram_school_id`);
--
-- データベース: `test`
--
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

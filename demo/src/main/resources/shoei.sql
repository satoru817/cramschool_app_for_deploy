-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 24, 2024 at 07:56 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shoei`
--

-- --------------------------------------------------------

--
-- Table structure for table `classes`
--

CREATE TABLE `classes` (
  `class_id` int NOT NULL,
  `subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`class_id`, `subject`, `name`) VALUES
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

-- --------------------------------------------------------

--
-- Table structure for table `class_students`
--

CREATE TABLE `class_students` (
  `class_student_id` int NOT NULL,
  `student_id` int NOT NULL,
  `class_id` int NOT NULL,
  `created_at` date NOT NULL,
  `changed_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `class_students`
--

INSERT INTO `class_students` (`class_student_id`, `student_id`, `class_id`, `created_at`, `changed_at`) VALUES
(1, 42, 5, '1970-01-01', '9999-12-31'),
(2, 42, 8, '1970-01-01', '9999-12-31'),
(3, 42, 15, '1970-01-01', '9999-12-31'),
(4, 42, 22, '1970-01-01', '9999-12-31'),
(5, 42, 29, '1970-01-01', '9999-12-31'),
(6, 43, 1, '1970-01-01', '9999-12-31'),
(7, 43, 8, '1970-01-01', '9999-12-31'),
(8, 43, 15, '1970-01-01', '9999-12-31'),
(9, 43, 22, '1970-01-01', '9999-12-31'),
(10, 43, 29, '1970-01-01', '9999-12-31'),
(11, 44, 1, '1970-01-01', '9999-12-31'),
(12, 44, 8, '1970-01-01', '9999-12-31'),
(13, 44, 15, '1970-01-01', '9999-12-31'),
(14, 44, 22, '1970-01-01', '9999-12-31'),
(15, 44, 29, '1970-01-01', '9999-12-31'),
(16, 45, 1, '1970-01-01', '9999-12-31'),
(17, 45, 8, '1970-01-01', '9999-12-31'),
(18, 45, 15, '1970-01-01', '9999-12-31'),
(19, 45, 22, '1970-01-01', '9999-12-31'),
(20, 45, 29, '1970-01-01', '9999-12-31'),
(21, 46, 1, '1970-01-01', '9999-12-31'),
(22, 46, 8, '1970-01-01', '9999-12-31'),
(23, 46, 15, '1970-01-01', '9999-12-31'),
(24, 46, 22, '1970-01-01', '9999-12-31'),
(25, 46, 29, '1970-01-01', '9999-12-31'),
(26, 47, 1, '1970-01-01', '9999-12-31'),
(27, 47, 8, '1970-01-01', '9999-12-31'),
(28, 47, 15, '1970-01-01', '9999-12-31'),
(29, 47, 22, '1970-01-01', '9999-12-31'),
(30, 47, 29, '1970-01-01', '9999-12-31'),
(31, 48, 1, '1970-01-01', '9999-12-31'),
(32, 48, 8, '1970-01-01', '9999-12-31'),
(33, 48, 15, '1970-01-01', '9999-12-31'),
(34, 48, 22, '1970-01-01', '9999-12-31'),
(35, 48, 29, '1970-01-01', '9999-12-31'),
(36, 49, 1, '1970-01-01', '9999-12-31'),
(37, 49, 8, '1970-01-01', '9999-12-31'),
(38, 49, 15, '1970-01-01', '9999-12-31'),
(39, 49, 22, '1970-01-01', '9999-12-31'),
(40, 49, 29, '1970-01-01', '9999-12-31'),
(41, 50, 1, '1970-01-01', '9999-12-31'),
(42, 50, 8, '1970-01-01', '9999-12-31'),
(43, 50, 15, '1970-01-01', '9999-12-31'),
(44, 50, 22, '1970-01-01', '9999-12-31'),
(45, 50, 29, '1970-01-01', '9999-12-31'),
(46, 51, 1, '1970-01-01', '9999-12-31'),
(47, 51, 8, '1970-01-01', '9999-12-31'),
(48, 51, 15, '1970-01-01', '9999-12-31'),
(49, 51, 22, '1970-01-01', '9999-12-31'),
(50, 51, 29, '1970-01-01', '9999-12-31'),
(51, 56, 4, '1970-01-01', '9999-12-31'),
(52, 56, 11, '1970-01-01', '9999-12-31'),
(53, 56, 16, '1970-01-01', '9999-12-31'),
(54, 56, 22, '1970-01-01', '9999-12-31'),
(55, 56, 29, '1970-01-01', '9999-12-31'),
(56, 57, 1, '1970-01-01', '9999-12-31'),
(57, 57, 8, '1970-01-01', '9999-12-31'),
(58, 57, 15, '1970-01-01', '9999-12-31'),
(59, 57, 22, '1970-01-01', '9999-12-31'),
(60, 57, 29, '1970-01-01', '9999-12-31'),
(61, 54, 5, '1970-01-01', '2024-10-24'),
(62, 54, 10, '1970-01-01', '2024-10-24'),
(63, 54, 19, '1970-01-01', '2024-10-24'),
(64, 54, 24, '1970-01-01', '2024-10-24'),
(65, 54, 30, '1970-01-01', '9999-12-31'),
(66, 55, 1, '1970-01-01', '9999-12-31'),
(67, 55, 8, '1970-01-01', '9999-12-31'),
(68, 55, 15, '1970-01-01', '9999-12-31'),
(69, 55, 22, '1970-01-01', '9999-12-31'),
(70, 55, 29, '1970-01-01', '9999-12-31'),
(71, 54, 2, '2024-10-24', '2024-10-24'),
(72, 54, 9, '2024-10-24', '9999-12-31'),
(73, 54, 16, '2024-10-24', '9999-12-31'),
(74, 54, 23, '2024-10-24', '9999-12-31'),
(75, 54, 4, '2024-10-24', '9999-12-31'),
(76, 39, 5, '1970-01-01', '9999-12-31'),
(77, 39, 8, '1970-01-01', '9999-12-31'),
(78, 39, 15, '1970-01-01', '9999-12-31'),
(79, 39, 22, '1970-01-01', '9999-12-31'),
(80, 39, 29, '1970-01-01', '9999-12-31'),
(81, 40, 1, '1970-01-01', '9999-12-31'),
(82, 40, 8, '1970-01-01', '9999-12-31'),
(83, 40, 15, '1970-01-01', '9999-12-31'),
(84, 40, 22, '1970-01-01', '9999-12-31'),
(85, 40, 29, '1970-01-01', '9999-12-31'),
(86, 41, 1, '1970-01-01', '9999-12-31'),
(87, 41, 8, '1970-01-01', '9999-12-31'),
(88, 41, 15, '1970-01-01', '9999-12-31'),
(89, 41, 22, '1970-01-01', '9999-12-31'),
(90, 41, 29, '1970-01-01', '9999-12-31'),
(91, 23, 6, '1970-01-01', '2024-10-24'),
(92, 23, 8, '1970-01-01', '2024-10-24'),
(93, 23, 15, '1970-01-01', '9999-12-31'),
(94, 23, 22, '1970-01-01', '9999-12-31'),
(95, 23, 29, '1970-01-01', '9999-12-31'),
(96, 24, 1, '1970-01-01', '2024-10-24'),
(97, 24, 8, '1970-01-01', '2024-10-24'),
(98, 24, 15, '1970-01-01', '2024-10-24'),
(99, 24, 22, '1970-01-01', '9999-12-31'),
(100, 24, 29, '1970-01-01', '9999-12-31'),
(101, 25, 1, '1970-01-01', '9999-12-31'),
(102, 25, 8, '1970-01-01', '9999-12-31'),
(103, 25, 15, '1970-01-01', '9999-12-31'),
(104, 25, 22, '1970-01-01', '9999-12-31'),
(105, 25, 29, '1970-01-01', '9999-12-31'),
(106, 26, 1, '1970-01-01', '9999-12-31'),
(107, 26, 8, '1970-01-01', '9999-12-31'),
(108, 26, 15, '1970-01-01', '9999-12-31'),
(109, 26, 22, '1970-01-01', '9999-12-31'),
(110, 26, 29, '1970-01-01', '9999-12-31'),
(111, 27, 1, '1970-01-01', '2024-10-24'),
(112, 27, 8, '1970-01-01', '2024-10-24'),
(113, 27, 15, '1970-01-01', '9999-12-31'),
(114, 27, 22, '1970-01-01', '9999-12-31'),
(115, 27, 29, '1970-01-01', '9999-12-31'),
(116, 28, 1, '1970-01-01', '9999-12-31'),
(117, 28, 8, '1970-01-01', '9999-12-31'),
(118, 28, 15, '1970-01-01', '9999-12-31'),
(119, 28, 22, '1970-01-01', '9999-12-31'),
(120, 28, 29, '1970-01-01', '9999-12-31'),
(121, 29, 1, '1970-01-01', '9999-12-31'),
(122, 29, 8, '1970-01-01', '9999-12-31'),
(123, 29, 15, '1970-01-01', '9999-12-31'),
(124, 29, 22, '1970-01-01', '9999-12-31'),
(125, 29, 29, '1970-01-01', '9999-12-31'),
(126, 30, 1, '1970-01-01', '9999-12-31'),
(127, 30, 8, '1970-01-01', '9999-12-31'),
(128, 30, 15, '1970-01-01', '9999-12-31'),
(129, 30, 22, '1970-01-01', '9999-12-31'),
(130, 30, 29, '1970-01-01', '9999-12-31'),
(131, 31, 1, '1970-01-01', '9999-12-31'),
(132, 31, 8, '1970-01-01', '9999-12-31'),
(133, 31, 15, '1970-01-01', '9999-12-31'),
(134, 31, 22, '1970-01-01', '9999-12-31'),
(135, 31, 29, '1970-01-01', '9999-12-31'),
(136, 32, 1, '1970-01-01', '9999-12-31'),
(137, 32, 8, '1970-01-01', '9999-12-31'),
(138, 32, 15, '1970-01-01', '9999-12-31'),
(139, 32, 22, '1970-01-01', '9999-12-31'),
(140, 32, 29, '1970-01-01', '9999-12-31'),
(141, 23, 7, '2024-10-24', '9999-12-31'),
(142, 23, 14, '2024-10-24', '9999-12-31'),
(143, 27, 6, '2024-10-24', '2024-10-24'),
(144, 7, 5, '1970-01-01', '9999-12-31'),
(145, 7, 8, '1970-01-01', '2024-10-24'),
(146, 7, 15, '1970-01-01', '2024-10-24'),
(147, 7, 22, '1970-01-01', '2024-10-24'),
(148, 7, 29, '1970-01-01', '9999-12-31'),
(149, 8, 1, '1970-01-01', '2024-10-24'),
(150, 8, 8, '1970-01-01', '2024-10-24'),
(151, 8, 15, '1970-01-01', '2024-10-24'),
(152, 8, 22, '1970-01-01', '2024-10-24'),
(153, 8, 29, '1970-01-01', '2024-10-24'),
(154, 9, 1, '1970-01-01', '2024-10-24'),
(155, 9, 8, '1970-01-01', '2024-10-24'),
(156, 9, 15, '1970-01-01', '2024-10-24'),
(157, 9, 22, '1970-01-01', '2024-10-24'),
(158, 9, 29, '1970-01-01', '2024-10-24'),
(159, 10, 1, '1970-01-01', '2024-10-24'),
(160, 10, 8, '1970-01-01', '2024-10-24'),
(161, 10, 15, '1970-01-01', '2024-10-24'),
(162, 10, 22, '1970-01-01', '2024-10-24'),
(163, 10, 29, '1970-01-01', '2024-10-24'),
(164, 11, 1, '1970-01-01', '2024-10-24'),
(165, 11, 8, '1970-01-01', '2024-10-24'),
(166, 11, 15, '1970-01-01', '2024-10-24'),
(167, 11, 22, '1970-01-01', '2024-10-24'),
(168, 11, 29, '1970-01-01', '2024-10-24'),
(169, 12, 1, '1970-01-01', '2024-10-24'),
(170, 12, 8, '1970-01-01', '2024-10-24'),
(171, 12, 15, '1970-01-01', '2024-10-24'),
(172, 12, 22, '1970-01-01', '2024-10-24'),
(173, 12, 29, '1970-01-01', '2024-10-24'),
(174, 13, 1, '1970-01-01', '2024-10-24'),
(175, 13, 8, '1970-01-01', '2024-10-24'),
(176, 13, 15, '1970-01-01', '2024-10-24'),
(177, 13, 22, '1970-01-01', '2024-10-24'),
(178, 13, 29, '1970-01-01', '2024-10-24'),
(179, 14, 1, '1970-01-01', '2024-10-24'),
(180, 14, 8, '1970-01-01', '2024-10-24'),
(181, 14, 15, '1970-01-01', '2024-10-24'),
(182, 14, 22, '1970-01-01', '9999-12-31'),
(183, 14, 29, '1970-01-01', '2024-10-24'),
(184, 15, 1, '1970-01-01', '2024-10-24'),
(185, 15, 8, '1970-01-01', '2024-10-24'),
(186, 15, 15, '1970-01-01', '2024-10-24'),
(187, 15, 22, '1970-01-01', '2024-10-24'),
(188, 15, 29, '1970-01-01', '2024-10-24'),
(189, 16, 1, '1970-01-01', '2024-10-24'),
(190, 16, 8, '1970-01-01', '2024-10-24'),
(191, 16, 15, '1970-01-01', '2024-10-24'),
(192, 16, 22, '1970-01-01', '2024-10-24'),
(193, 16, 29, '1970-01-01', '2024-10-24'),
(194, 24, 5, '2024-10-24', '9999-12-31'),
(195, 24, 11, '2024-10-24', '2024-10-24'),
(196, 24, 16, '2024-10-24', '9999-12-31'),
(197, 24, 8, '2024-10-24', '9999-12-31'),
(198, 27, 5, '2024-10-24', '9999-12-31'),
(199, 27, 12, '2024-10-24', '9999-12-31'),
(200, 2, 4, '1970-01-01', '9999-12-31'),
(201, 2, 13, '1970-01-01', '9999-12-31'),
(202, 2, 19, '1970-01-01', '9999-12-31'),
(203, 2, 22, '1970-01-01', '9999-12-31'),
(204, 2, 29, '1970-01-01', '9999-12-31'),
(205, 3, 1, '1970-01-01', '9999-12-31'),
(206, 3, 8, '1970-01-01', '9999-12-31'),
(207, 3, 15, '1970-01-01', '9999-12-31'),
(208, 3, 22, '1970-01-01', '9999-12-31'),
(209, 3, 29, '1970-01-01', '9999-12-31'),
(210, 4, 1, '1970-01-01', '9999-12-31'),
(211, 4, 8, '1970-01-01', '9999-12-31'),
(212, 4, 15, '1970-01-01', '9999-12-31'),
(213, 4, 22, '1970-01-01', '9999-12-31'),
(214, 4, 29, '1970-01-01', '9999-12-31'),
(215, 5, 1, '1970-01-01', '9999-12-31'),
(216, 5, 8, '1970-01-01', '9999-12-31'),
(217, 5, 15, '1970-01-01', '9999-12-31'),
(218, 5, 22, '1970-01-01', '9999-12-31'),
(219, 5, 29, '1970-01-01', '9999-12-31'),
(220, 6, 1, '1970-01-01', '9999-12-31'),
(221, 6, 8, '1970-01-01', '9999-12-31'),
(222, 6, 15, '1970-01-01', '9999-12-31'),
(223, 6, 22, '1970-01-01', '9999-12-31'),
(224, 6, 29, '1970-01-01', '9999-12-31'),
(225, 7, 19, '2024-10-24', '9999-12-31'),
(226, 7, 13, '2024-10-24', '9999-12-31'),
(227, 7, 27, '2024-10-24', '9999-12-31'),
(228, 8, 4, '2024-10-24', '9999-12-31'),
(229, 8, 13, '2024-10-24', '9999-12-31'),
(230, 8, 17, '2024-10-24', '9999-12-31'),
(231, 8, 25, '2024-10-24', '9999-12-31'),
(232, 8, 31, '2024-10-24', '9999-12-31'),
(233, 9, 3, '2024-10-24', '9999-12-31'),
(234, 9, 11, '2024-10-24', '9999-12-31'),
(235, 9, 18, '2024-10-24', '9999-12-31'),
(236, 9, 26, '2024-10-24', '9999-12-31'),
(237, 9, 33, '2024-10-24', '9999-12-31'),
(238, 10, 4, '2024-10-24', '9999-12-31'),
(239, 10, 13, '2024-10-24', '9999-12-31'),
(240, 10, 19, '2024-10-24', '9999-12-31'),
(241, 10, 26, '2024-10-24', '9999-12-31'),
(242, 10, 32, '2024-10-24', '9999-12-31'),
(243, 11, 4, '2024-10-24', '9999-12-31'),
(244, 11, 10, '2024-10-24', '9999-12-31'),
(245, 11, 18, '2024-10-24', '9999-12-31'),
(246, 11, 28, '2024-10-24', '9999-12-31'),
(247, 11, 33, '2024-10-24', '9999-12-31'),
(248, 12, 4, '2024-10-24', '9999-12-31'),
(249, 12, 11, '2024-10-24', '9999-12-31'),
(250, 12, 19, '2024-10-24', '9999-12-31'),
(251, 12, 26, '2024-10-24', '9999-12-31'),
(252, 12, 34, '2024-10-24', '9999-12-31'),
(253, 13, 5, '2024-10-24', '9999-12-31'),
(254, 13, 13, '2024-10-24', '9999-12-31'),
(255, 13, 19, '2024-10-24', '9999-12-31'),
(256, 13, 26, '2024-10-24', '9999-12-31'),
(257, 13, 35, '2024-10-24', '9999-12-31'),
(258, 14, 3, '2024-10-24', '9999-12-31'),
(259, 14, 12, '2024-10-24', '9999-12-31'),
(260, 14, 20, '2024-10-24', '9999-12-31'),
(261, 14, 33, '2024-10-24', '9999-12-31'),
(262, 15, 3, '2024-10-24', '9999-12-31'),
(263, 15, 11, '2024-10-24', '9999-12-31'),
(264, 15, 18, '2024-10-24', '9999-12-31'),
(265, 15, 26, '2024-10-24', '9999-12-31'),
(266, 15, 33, '2024-10-24', '9999-12-31'),
(267, 16, 6, '2024-10-24', '9999-12-31'),
(268, 16, 13, '2024-10-24', '9999-12-31'),
(269, 16, 18, '2024-10-24', '9999-12-31'),
(270, 16, 25, '2024-10-24', '9999-12-31'),
(271, 16, 32, '2024-10-24', '9999-12-31'),
(272, 17, 4, '1970-01-01', '9999-12-31'),
(273, 17, 11, '1970-01-01', '9999-12-31'),
(274, 17, 19, '1970-01-01', '9999-12-31'),
(275, 17, 26, '1970-01-01', '9999-12-31'),
(276, 17, 33, '1970-01-01', '9999-12-31'),
(277, 18, 5, '1970-01-01', '9999-12-31'),
(278, 18, 13, '1970-01-01', '9999-12-31'),
(279, 18, 21, '1970-01-01', '9999-12-31'),
(280, 18, 28, '1970-01-01', '9999-12-31'),
(281, 18, 33, '1970-01-01', '9999-12-31'),
(282, 19, 4, '1970-01-01', '9999-12-31'),
(283, 19, 12, '1970-01-01', '9999-12-31'),
(284, 19, 19, '1970-01-01', '9999-12-31'),
(285, 19, 27, '1970-01-01', '9999-12-31'),
(286, 19, 34, '1970-01-01', '9999-12-31'),
(287, 20, 5, '1970-01-01', '9999-12-31'),
(288, 20, 12, '1970-01-01', '9999-12-31'),
(289, 20, 21, '1970-01-01', '9999-12-31'),
(290, 20, 28, '1970-01-01', '9999-12-31'),
(291, 20, 33, '1970-01-01', '9999-12-31'),
(292, 21, 6, '1970-01-01', '9999-12-31'),
(293, 21, 11, '1970-01-01', '9999-12-31'),
(294, 21, 19, '1970-01-01', '9999-12-31'),
(295, 21, 26, '1970-01-01', '9999-12-31'),
(296, 21, 34, '1970-01-01', '9999-12-31'),
(297, 22, 3, '1970-01-01', '9999-12-31'),
(298, 22, 10, '1970-01-01', '9999-12-31'),
(299, 22, 17, '1970-01-01', '9999-12-31'),
(300, 22, 22, '1970-01-01', '9999-12-31'),
(301, 22, 32, '1970-01-01', '9999-12-31');

-- --------------------------------------------------------

--
-- Table structure for table `mock_tests`
--

CREATE TABLE `mock_tests` (
  `mock_test_id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mock_test_results`
--

CREATE TABLE `mock_test_results` (
  `mock_test_id` int NOT NULL,
  `student_id` int NOT NULL,
  `japanese` int DEFAULT NULL,
  `japanese_ss` int DEFAULT NULL,
  `math` int DEFAULT NULL,
  `math_ss` int DEFAULT NULL,
  `english` int DEFAULT NULL,
  `english_ss` int DEFAULT NULL,
  `science` int DEFAULT NULL,
  `science_ss` int DEFAULT NULL,
  `social` int DEFAULT NULL,
  `social_ss` int DEFAULT NULL,
  `jme_ss` int DEFAULT NULL,
  `jmess_ss` int DEFAULT NULL,
  `dream_school1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dream_school1_probability` int DEFAULT NULL,
  `dream_school2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dream_school2_probability` int DEFAULT NULL,
  `dream_school3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dream_school3_probability` int DEFAULT NULL,
  `dream_school4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dream_school4_probability` int DEFAULT NULL,
  `dream_school5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dream_school5_probability` int DEFAULT NULL,
  `dream_school6` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dream_school6_probability` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `regular_test`
--

CREATE TABLE `regular_test` (
  `regular_test_id` int NOT NULL,
  `regular_test_set_id` int NOT NULL,
  `school_id` int NOT NULL,
  `date` date DEFAULT NULL,
  `japanese` int DEFAULT NULL,
  `math` int DEFAULT NULL,
  `english` int DEFAULT NULL,
  `science` int DEFAULT NULL,
  `social` int DEFAULT NULL,
  `music` int DEFAULT NULL,
  `art` int DEFAULT NULL,
  `tech` int DEFAULT NULL,
  `pe` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `regular_test_result`
--

CREATE TABLE `regular_test_result` (
  `regular_test_result_id` int NOT NULL,
  `regular_test_id` int NOT NULL,
  `student_id` int NOT NULL,
  `japanese` int DEFAULT NULL,
  `math` int DEFAULT NULL,
  `english` int DEFAULT NULL,
  `science` int DEFAULT NULL,
  `social` int DEFAULT NULL,
  `music` int DEFAULT NULL,
  `art` int DEFAULT NULL,
  `tech` int DEFAULT NULL,
  `pe` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `regular_test_set`
--

CREATE TABLE `regular_test_set` (
  `regular_test_set_id` int NOT NULL,
  `term` int NOT NULL,
  `grade` int NOT NULL,
  `semester` int NOT NULL,
  `is_mid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `schools`
--

CREATE TABLE `schools` (
  `school_id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `schools`
--

INSERT INTO `schools` (`school_id`, `name`) VALUES
(2, 'school1'),
(3, 'school2'),
(9, 'みなみ野中学校'),
(10, '七国中学校'),
(14, '七国小学校'),
(1, '不明'),
(5, '五日市中学校'),
(13, '南多摩中学校'),
(12, '堺中学校'),
(4, '増戸中学校'),
(8, '増戸小学校'),
(7, '大久野中学校'),
(6, '平井中学校'),
(11, '武蔵岡中学校');

-- --------------------------------------------------------

--
-- Table structure for table `school_record`
--

CREATE TABLE `school_record` (
  `school_record_id` int NOT NULL,
  `school_id` int NOT NULL,
  `school_record_set_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `school_record`
--

INSERT INTO `school_record` (`school_record_id`, `school_id`, `school_record_set_id`) VALUES
(6, 4, 1),
(3, 5, 1),
(8, 6, 1),
(7, 7, 1),
(1, 9, 1),
(2, 10, 1),
(9, 11, 1),
(5, 12, 1),
(4, 13, 1);

-- --------------------------------------------------------

--
-- Table structure for table `school_record_result`
--

CREATE TABLE `school_record_result` (
  `school_record_result_id` int NOT NULL,
  `student_id` int NOT NULL,
  `school_record_id` int NOT NULL,
  `japanese` int DEFAULT NULL,
  `math` int DEFAULT NULL,
  `english` int DEFAULT NULL,
  `science` int DEFAULT NULL,
  `social` int DEFAULT NULL,
  `music` int DEFAULT NULL,
  `art` int DEFAULT NULL,
  `tech` int DEFAULT NULL,
  `pe` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `school_record_result`
--

INSERT INTO `school_record_result` (`school_record_result_id`, `student_id`, `school_record_id`, `japanese`, `math`, `english`, `science`, `social`, `music`, `art`, `tech`, `pe`) VALUES
(1, 10, 1, 3, 5, 4, 0, 4, 4, 3, 4, 3),
(2, 12, 1, 3, 3, 5, 4, 1, 3, 5, 3, 4),
(3, 13, 1, 5, 3, 4, 3, 2, 5, 3, 5, 3),
(4, 17, 1, 4, 3, 5, 2, 1, 5, 5, 4, 4),
(5, 18, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4),
(6, 19, 1, 3, 3, 2, 5, 1, 1, 3, 3, 3),
(7, 20, 1, 1, 2, 2, 2, 2, 4, 5, 3, 3),
(8, 22, 1, 5, 3, 3, 3, 2, 5, 1, 5, 4),
(9, 7, 2, 3, 3, 2, 1, 1, 3, 3, 3, 4),
(10, 8, 2, 3, 3, 5, 4, 4, 4, 4, 3, 4),
(11, 9, 2, 3, 5, 3, 2, 5, 3, 5, 4, 3),
(12, 11, 2, 5, 3, 5, 4, 3, 5, 3, 4, 3),
(13, 15, 2, 5, 3, 3, 2, 3, 5, 3, 5, 3),
(14, 16, 2, 5, 3, 5, 3, 5, 2, 5, 3, 4),
(15, 21, 5, 5, 5, 3, 5, 3, 5, 3, 5, 3),
(16, 14, 9, 5, 4, 4, 3, 5, 2, 5, 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `school_record_set`
--

CREATE TABLE `school_record_set` (
  `school_record_set_id` int NOT NULL,
  `term` int NOT NULL,
  `grade` int NOT NULL,
  `semester` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `school_record_set`
--

INSERT INTO `school_record_set` (`school_record_set_id`, `term`, `grade`, `semester`) VALUES
(1, 2024, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `school_student`
--

CREATE TABLE `school_student` (
  `school_student_id` int NOT NULL,
  `school_id` int NOT NULL,
  `student_id` int NOT NULL,
  `created_at` date NOT NULL,
  `changed_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `school_student`
--

INSERT INTO `school_student` (`school_student_id`, `school_id`, `student_id`, `created_at`, `changed_at`) VALUES
(1, 9, 2, '1970-01-01', '9999-12-31'),
(2, 10, 3, '1970-01-01', '9999-12-31'),
(3, 10, 4, '1970-01-01', '9999-12-31'),
(4, 10, 5, '1970-01-01', '9999-12-31'),
(5, 10, 6, '1970-01-01', '9999-12-31'),
(6, 10, 7, '1970-01-01', '9999-12-31'),
(7, 10, 8, '1970-01-01', '9999-12-31'),
(8, 10, 9, '1970-01-01', '9999-12-31'),
(9, 9, 10, '1970-01-01', '9999-12-31'),
(10, 10, 11, '1970-01-01', '9999-12-31'),
(11, 9, 12, '1970-01-01', '9999-12-31'),
(12, 9, 13, '1970-01-01', '9999-12-31'),
(13, 11, 14, '1970-01-01', '9999-12-31'),
(14, 10, 15, '1970-01-01', '9999-12-31'),
(15, 10, 16, '1970-01-01', '9999-12-31'),
(16, 9, 17, '1970-01-01', '9999-12-31'),
(17, 9, 18, '1970-01-01', '9999-12-31'),
(18, 9, 19, '1970-01-01', '9999-12-31'),
(19, 9, 20, '1970-01-01', '9999-12-31'),
(20, 12, 21, '1970-01-01', '9999-12-31'),
(21, 9, 22, '1970-01-01', '9999-12-31'),
(22, 10, 23, '1970-01-01', '9999-12-31'),
(23, 10, 24, '1970-01-01', '9999-12-31'),
(24, 10, 25, '1970-01-01', '9999-12-31'),
(25, 10, 26, '1970-01-01', '9999-12-31'),
(26, 10, 27, '1970-01-01', '9999-12-31'),
(27, 10, 28, '1970-01-01', '9999-12-31'),
(28, 10, 29, '1970-01-01', '9999-12-31'),
(29, 10, 30, '1970-01-01', '9999-12-31'),
(30, 9, 31, '1970-01-01', '9999-12-31'),
(31, 13, 32, '1970-01-01', '9999-12-31'),
(32, 10, 33, '1970-01-01', '9999-12-31'),
(33, 10, 34, '1970-01-01', '9999-12-31'),
(34, 10, 35, '1970-01-01', '9999-12-31'),
(35, 10, 36, '1970-01-01', '9999-12-31'),
(36, 10, 37, '1970-01-01', '9999-12-31'),
(37, 1, 38, '1970-01-01', '9999-12-31'),
(38, 10, 39, '1970-01-01', '9999-12-31'),
(39, 10, 40, '1970-01-01', '9999-12-31'),
(40, 10, 41, '1970-01-01', '9999-12-31'),
(41, 10, 42, '1970-01-01', '9999-12-31'),
(42, 10, 43, '1970-01-01', '9999-12-31'),
(43, 9, 44, '1970-01-01', '9999-12-31'),
(44, 1, 45, '1970-01-01', '9999-12-31'),
(45, 10, 46, '1970-01-01', '9999-12-31'),
(46, 10, 47, '1970-01-01', '9999-12-31'),
(47, 9, 48, '1970-01-01', '9999-12-31'),
(48, 10, 49, '1970-01-01', '9999-12-31'),
(49, 14, 50, '1970-01-01', '9999-12-31'),
(50, 14, 51, '1970-01-01', '9999-12-31'),
(51, 14, 52, '1970-01-01', '9999-12-31'),
(52, 14, 53, '1970-01-01', '9999-12-31'),
(53, 14, 54, '1970-01-01', '9999-12-31'),
(54, 14, 55, '1970-01-01', '9999-12-31'),
(55, 14, 56, '1970-01-01', '9999-12-31'),
(56, 14, 57, '1970-01-01', '9999-12-31');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `status_id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`status_id`, `name`) VALUES
(3, '体験'),
(2, '個別'),
(4, '本科'),
(1, '講習');

-- --------------------------------------------------------

--
-- Table structure for table `status_students`
--

CREATE TABLE `status_students` (
  `status_student_id` int NOT NULL,
  `status_id` int NOT NULL,
  `student_id` int NOT NULL,
  `created_at` date NOT NULL,
  `changed_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status_students`
--

INSERT INTO `status_students` (`status_student_id`, `status_id`, `student_id`, `created_at`, `changed_at`) VALUES
(1, 4, 2, '1970-01-01', '9999-12-31'),
(2, 4, 3, '1970-01-01', '9999-12-31'),
(3, 4, 4, '1970-01-01', '9999-12-31'),
(4, 4, 5, '1970-01-01', '9999-12-31'),
(5, 4, 6, '1970-01-01', '9999-12-31'),
(6, 4, 7, '1970-01-01', '9999-12-31'),
(7, 4, 8, '1970-01-01', '9999-12-31'),
(8, 4, 9, '1970-01-01', '9999-12-31'),
(9, 4, 10, '1970-01-01', '9999-12-31'),
(10, 4, 11, '1970-01-01', '9999-12-31'),
(11, 4, 12, '1970-01-01', '9999-12-31'),
(12, 4, 13, '1970-01-01', '9999-12-31'),
(13, 4, 14, '1970-01-01', '9999-12-31'),
(14, 4, 15, '1970-01-01', '9999-12-31'),
(15, 4, 16, '1970-01-01', '9999-12-31'),
(16, 4, 17, '1970-01-01', '9999-12-31'),
(17, 4, 18, '1970-01-01', '9999-12-31'),
(18, 4, 19, '1970-01-01', '9999-12-31'),
(19, 4, 20, '1970-01-01', '9999-12-31'),
(20, 4, 21, '1970-01-01', '9999-12-31'),
(21, 4, 22, '1970-01-01', '9999-12-31'),
(22, 4, 23, '1970-01-01', '9999-12-31'),
(23, 4, 24, '1970-01-01', '9999-12-31'),
(24, 4, 25, '1970-01-01', '9999-12-31'),
(25, 4, 26, '1970-01-01', '9999-12-31'),
(26, 4, 27, '1970-01-01', '9999-12-31'),
(27, 4, 28, '1970-01-01', '9999-12-31'),
(28, 4, 29, '1970-01-01', '9999-12-31'),
(29, 4, 30, '1970-01-01', '9999-12-31'),
(30, 4, 31, '1970-01-01', '9999-12-31'),
(31, 4, 32, '1970-01-01', '9999-12-31'),
(32, 4, 33, '1970-01-01', '9999-12-31'),
(33, 4, 34, '1970-01-01', '9999-12-31'),
(34, 4, 35, '1970-01-01', '9999-12-31'),
(35, 4, 36, '1970-01-01', '9999-12-31'),
(36, 1, 37, '1970-01-01', '9999-12-31'),
(37, 1, 38, '1970-01-01', '9999-12-31'),
(38, 1, 39, '1970-01-01', '9999-12-31'),
(39, 4, 40, '1970-01-01', '9999-12-31'),
(40, 4, 41, '1970-01-01', '9999-12-31'),
(41, 4, 42, '1970-01-01', '9999-12-31'),
(42, 4, 43, '1970-01-01', '9999-12-31'),
(43, 4, 44, '1970-01-01', '9999-12-31'),
(44, 1, 45, '1970-01-01', '9999-12-31'),
(45, 4, 46, '1970-01-01', '9999-12-31'),
(46, 4, 47, '1970-01-01', '9999-12-31'),
(47, 4, 48, '1970-01-01', '9999-12-31'),
(48, 4, 49, '1970-01-01', '9999-12-31'),
(49, 4, 50, '1970-01-01', '9999-12-31'),
(50, 4, 51, '1970-01-01', '9999-12-31'),
(51, 4, 52, '1970-01-01', '9999-12-31'),
(52, 1, 53, '1970-01-01', '9999-12-31'),
(53, 1, 54, '1970-01-01', '9999-12-31'),
(54, 1, 55, '1970-01-01', '9999-12-31'),
(55, 1, 56, '1970-01-01', '9999-12-31'),
(56, 1, 57, '1970-01-01', '9999-12-31');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` int NOT NULL,
  `el1` int NOT NULL,
  `code` bigint DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `el1`, `code`, `name`) VALUES
(1, 2000, 1, 'ave'),
(2, 2015, 1230039, '大谷 茂壱'),
(3, 2015, 1240015, '中西 巧'),
(4, 2015, 1240023, '大浪 弘貴'),
(5, 2015, 1240030, '中田 宗亮'),
(6, 2015, 1240033, '村田 直弥'),
(7, 2016, 1250006, '瀧澤 大翔'),
(8, 2016, 1250007, '松本 駿哉'),
(9, 2016, 1250009, '清水 俊信'),
(10, 2016, 1250010, '小牧 祐介'),
(11, 2016, 1250012, '後藤 琉生'),
(12, 2016, 1250013, '金子 倖大'),
(13, 2016, 1250028, '齋藤 健勝'),
(14, 2016, 1250029, '山内 綾人'),
(15, 2016, 1250030, '上高原 慶人'),
(16, 2016, 1250032, '石川 駿斗'),
(17, 2016, 1250033, '大谷 逸馬'),
(18, 2016, 1259039, '佐藤 結菜'),
(19, 2016, 1259040, '石垣 京子'),
(20, 2016, 1259041, '田中 沙樹'),
(21, 2016, 1259042, '石井 来望'),
(22, 2016, 1259043, '武田 優里'),
(23, 2017, 1260015, '三浦 諒'),
(24, 2017, 1260016, '井上 稜太'),
(25, 2017, 1260017, '田中 詩大'),
(26, 2017, 1260018, '清水 涼太郎'),
(27, 2017, 1260019, '竹井 希'),
(28, 2017, 1260033, '串田 嶺央'),
(29, 2017, 1260034, '則武 大翔'),
(30, 2017, 1260035, '沓澤 郁依'),
(31, 2017, 1260036, '松本 直弥'),
(32, 2017, 1260037, '入田 琉碧'),
(33, 2017, 1269019, '山本 真那'),
(34, 2017, 1269020, '小部 栞奈'),
(35, 2017, 1269022, '森田 奏'),
(36, 2017, 1269023, '石田 晋碧里'),
(37, 2017, 1269027, '鹿山 巴奈'),
(38, 2017, 1269029, '川口 小雪'),
(39, 2018, 1270005, '後藤 響樹'),
(40, 2018, 1270008, '中島 康太'),
(41, 2018, 1270009, '平石 瑛士'),
(42, 2018, 1270010, '中村 郁杜'),
(43, 2018, 1270012, '遠田 永翔'),
(44, 2018, 1270013, '木村 洸太'),
(45, 2018, 1270018, '伊藤 志優'),
(46, 2018, 1279018, '佐藤 愛耶'),
(47, 2018, 1279019, '黒木 咲來'),
(48, 2018, 1279020, '花井 彩恵'),
(49, 2018, 1279021, '岩岡 咲妃'),
(50, 2019, 1280007, '佐内 柊太'),
(51, 2019, 1280008, '中根 旺輔'),
(52, 2019, 1280010, '鈴木 恵多'),
(53, 2019, 1289006, '石垣 翔子'),
(54, 2020, 1290010, '松本 海'),
(55, 2020, 1290011, '松本 空'),
(56, 2021, 1300001, '藤澤 瑠生'),
(57, 2021, 1300002, '古澤 時人');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `class_students`
--
ALTER TABLE `class_students`
  ADD PRIMARY KEY (`class_student_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `class_id` (`class_id`);

--
-- Indexes for table `mock_tests`
--
ALTER TABLE `mock_tests`
  ADD PRIMARY KEY (`mock_test_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `mock_test_results`
--
ALTER TABLE `mock_test_results`
  ADD PRIMARY KEY (`mock_test_id`,`student_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `regular_test`
--
ALTER TABLE `regular_test`
  ADD PRIMARY KEY (`regular_test_id`),
  ADD KEY `school_id` (`school_id`),
  ADD KEY `regular_test_set_id` (`regular_test_set_id`);

--
-- Indexes for table `regular_test_result`
--
ALTER TABLE `regular_test_result`
  ADD PRIMARY KEY (`regular_test_result_id`),
  ADD KEY `regular_test_id` (`regular_test_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `regular_test_set`
--
ALTER TABLE `regular_test_set`
  ADD PRIMARY KEY (`regular_test_set_id`);

--
-- Indexes for table `schools`
--
ALTER TABLE `schools`
  ADD PRIMARY KEY (`school_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `school_record`
--
ALTER TABLE `school_record`
  ADD PRIMARY KEY (`school_record_id`),
  ADD UNIQUE KEY `school_id` (`school_id`,`school_record_set_id`),
  ADD KEY `school_record_set_id` (`school_record_set_id`);

--
-- Indexes for table `school_record_result`
--
ALTER TABLE `school_record_result`
  ADD PRIMARY KEY (`school_record_result_id`),
  ADD UNIQUE KEY `student_id` (`student_id`,`school_record_id`),
  ADD KEY `school_record_id` (`school_record_id`);

--
-- Indexes for table `school_record_set`
--
ALTER TABLE `school_record_set`
  ADD PRIMARY KEY (`school_record_set_id`),
  ADD UNIQUE KEY `term` (`term`,`grade`,`semester`);

--
-- Indexes for table `school_student`
--
ALTER TABLE `school_student`
  ADD PRIMARY KEY (`school_student_id`),
  ADD KEY `school_id` (`school_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`status_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `status_students`
--
ALTER TABLE `status_students`
  ADD PRIMARY KEY (`status_student_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `classes`
--
ALTER TABLE `classes`
  MODIFY `class_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `class_students`
--
ALTER TABLE `class_students`
  MODIFY `class_student_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=302;

--
-- AUTO_INCREMENT for table `mock_tests`
--
ALTER TABLE `mock_tests`
  MODIFY `mock_test_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `regular_test`
--
ALTER TABLE `regular_test`
  MODIFY `regular_test_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `regular_test_result`
--
ALTER TABLE `regular_test_result`
  MODIFY `regular_test_result_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `regular_test_set`
--
ALTER TABLE `regular_test_set`
  MODIFY `regular_test_set_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `schools`
--
ALTER TABLE `schools`
  MODIFY `school_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `school_record`
--
ALTER TABLE `school_record`
  MODIFY `school_record_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `school_record_result`
--
ALTER TABLE `school_record_result`
  MODIFY `school_record_result_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `school_record_set`
--
ALTER TABLE `school_record_set`
  MODIFY `school_record_set_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `school_student`
--
ALTER TABLE `school_student`
  MODIFY `school_student_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `status_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `status_students`
--
ALTER TABLE `status_students`
  MODIFY `status_student_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `class_students`
--
ALTER TABLE `class_students`
  ADD CONSTRAINT `class_students_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `class_students_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`);

--
-- Constraints for table `mock_test_results`
--
ALTER TABLE `mock_test_results`
  ADD CONSTRAINT `mock_test_results_ibfk_1` FOREIGN KEY (`mock_test_id`) REFERENCES `mock_tests` (`mock_test_id`),
  ADD CONSTRAINT `mock_test_results_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `regular_test`
--
ALTER TABLE `regular_test`
  ADD CONSTRAINT `regular_test_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `regular_test_ibfk_2` FOREIGN KEY (`regular_test_set_id`) REFERENCES `regular_test_set` (`regular_test_set_id`);

--
-- Constraints for table `regular_test_result`
--
ALTER TABLE `regular_test_result`
  ADD CONSTRAINT `regular_test_result_ibfk_1` FOREIGN KEY (`regular_test_id`) REFERENCES `regular_test` (`regular_test_id`),
  ADD CONSTRAINT `regular_test_result_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `school_record`
--
ALTER TABLE `school_record`
  ADD CONSTRAINT `school_record_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `school_record_ibfk_2` FOREIGN KEY (`school_record_set_id`) REFERENCES `school_record_set` (`school_record_set_id`);

--
-- Constraints for table `school_record_result`
--
ALTER TABLE `school_record_result`
  ADD CONSTRAINT `school_record_result_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `school_record_result_ibfk_2` FOREIGN KEY (`school_record_id`) REFERENCES `school_record` (`school_record_id`);

--
-- Constraints for table `school_student`
--
ALTER TABLE `school_student`
  ADD CONSTRAINT `school_student_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`),
  ADD CONSTRAINT `school_student_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `status_students`
--
ALTER TABLE `status_students`
  ADD CONSTRAINT `status_students_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`),
  ADD CONSTRAINT `status_students_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2025 at 08:56 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `becsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE `requests` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `date_request` datetime NOT NULL DEFAULT current_timestamp(),
  `status` varchar(254) NOT NULL DEFAULT 'pending',
  `reason` text NOT NULL,
  `date_approve` datetime DEFAULT NULL,
  `file` varchar(254) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`id`, `student_id`, `date_request`, `status`, `reason`, `date_approve`, `file`) VALUES
(30, 2, '2024-12-19 13:15:40', 'approved', 'For Purposes', '2024-12-19 13:26:31', 'GRADE'),
(31, 2, '2024-12-19 13:45:49', 'approved', 'Just So so', '2024-12-19 13:45:52', 'COR'),
(32, 1, '2024-12-19 13:47:59', 'approved', 'S', '2024-12-19 13:48:13', 'COR'),
(33, 1, '2024-12-19 15:46:11', 'approved', '', '2024-12-19 15:46:31', 'TOR, AD'),
(34, 2, '2025-01-02 12:51:28', 'approved', 'sad', '2025-01-02 12:51:38', 'GRADE'),
(35, 2, '2025-01-02 12:57:30', 'denied', '', NULL, 'TOR, AD, AUTHENTICATION'),
(36, 2, '2025-01-02 12:59:35', 'pending', 'Wsaa', NULL, 'COR'),
(37, 2, '2025-01-02 12:59:42', 'pending', 'sdwdS', NULL, 'GRADE'),
(39, 2, '2025-01-03 13:34:35', 'pending', '', NULL, 'Grade'),
(41, 2, '2025-01-03 13:35:26', 'pending', '', NULL, 'Grade'),
(43, 2, '2025-01-03 13:36:19', 'pending', '', NULL, 'Grade'),
(44, 2, '2025-01-03 13:36:35', 'pending', '', NULL, 'Grade, COR, TOR'),
(45, 2, '2025-01-03 13:37:26', 'pending', 'Yeas', NULL, 'Grade, TOR, AD'),
(46, 2, '2025-01-03 14:28:58', 'pending', 'Just Soso', NULL, 'COR, TOR');

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `name` varchar(254) NOT NULL,
  `status` varchar(254) NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id`, `name`, `status`) VALUES
(1, 'Grade', 'active'),
(2, 'COR', 'active'),
(3, 'TOR', 'active'),
(4, 'AD', 'active'),
(5, 'Good Moral', 'active'),
(6, 'Diploma', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `firstname` varchar(254) NOT NULL,
  `middlename` varchar(254) NOT NULL,
  `lastname` varchar(254) NOT NULL,
  `contact` varchar(254) NOT NULL,
  `school_id` varchar(254) NOT NULL,
  `year` int(11) NOT NULL,
  `section` varchar(254) NOT NULL,
  `date_created` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  `status` int(11) NOT NULL DEFAULT 0,
  `type` varchar(254) NOT NULL,
  `username` varchar(254) NOT NULL,
  `password` varchar(254) NOT NULL,
  `role` varchar(254) NOT NULL DEFAULT 'user',
  `program` varchar(254) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstname`, `middlename`, `lastname`, `contact`, `school_id`, `year`, `section`, `date_created`, `status`, `type`, `username`, `password`, `role`, `program`) VALUES
(1, 'Sheramie', 'A', 'Bigwas', '0912345678', '2022-5960', 2, 'B', '2024-11-18 09:56:34.655313', 0, 'INTERNAL', 'saming', 'saming123', 'user', 'BSIT'),
(2, 'Noir', 'H', 'Tempest', '0987643211', '2023-1234', 1, 'A', '2024-11-18 10:00:10.274753', 1, 'EXTERNAL', 'noir', 'noir123', 'user', ''),
(3, 'Carerra', 'D', 'Tempest', '09123456789', '2020-6666', 2, 'B', '2024-11-19 12:20:16.468942', 0, 'INTERNAL', 'carerra', 'carerra123', 'user', 'BSIT'),
(4, 'Sheramie', 'H.', 'Agoy', '0922463257', '2022-6214', 3, 'B', '2024-12-16 18:48:48.964414', 0, 'EXTERNAL', 'shera', 'okay', 'user', 'BSIT'),
(5, 'Blanc', 'K', 'Tempest', '09123456789', '2021-8989', 4, 'A', '2024-12-16 18:55:49.386861', 0, 'INTERNAL', 'blanc', 'blanc123', 'user', 'BSIT'),
(6, 'Vee', 'M', 'Wise', '09876543219', '2021-7893', 2, 'B', '2024-12-19 23:34:13.082441', 0, 'INTERNAL', 'veewise', 'veewise', 'user', 'BSIT');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `requests`
--
ALTER TABLE `requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

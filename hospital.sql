-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2024 at 07:08 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hospital`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `email`, `username`, `password`, `full_name`, `image`, `gender`, `date`) VALUES
(1, 'admin1@gamil.com', 'admin1', '1111', NULL, NULL, NULL, '2024-10-14'),
(2, 'admin2@gmail.com', 'admin2', '12345678', NULL, NULL, NULL, '2024-10-14'),
(3, 'admin3@gmail.com', 'admin3', 'admin1234', NULL, NULL, NULL, '2024-10-14');

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `time` time(6) DEFAULT NULL,
  `doctor` varchar(100) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `patient_name` varchar(100) DEFAULT NULL,
  `nic` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `date`, `time`, `doctor`, `reason`, `status`, `patient_name`, `nic`) VALUES
(1, '2024-11-04', '10:00:00.000000', 'Dr. Sarah Johnson', 'fever', 'Updated', 'Amal', '1121'),
(2, '2024-11-04', '10:00:00.000000', 'Dr. Sarah Johnson', 'Headache', 'Pending', 'Kamal', '1120'),
(3, '2024-11-06', '16:00:00.000000', 'Dr.Gunasingha', 'Fever', 'Pending', 'Kamal', '1120'),
(4, '2024-11-06', '10:00:00.000000', 'Dr. John Smith', 'Checkup', 'Scheduled', 'Amal', '1121'),
(5, '2024-03-23', '10:00:00.000000', 'Dr.Perera', 'Therapy', 'Pending', 'Nimal', '1123'),
(6, '2024-11-06', '15:00:00.000000', 'Dr. Emily Davis', 'Fever', 'Updated', 'Amal', '1121'),
(7, '2024-11-07', '15:00:00.000000', 'Dr. Sarah Johnson', 'Headache', 'Scheduled', 'Kamal', '1120'),
(8, '2024-11-08', '12:00:00.000000', 'Dr.Nimal', 'Checkup', 'Pending', 'Kamal', '1120'),
(9, '2024-11-06', '14:00:00.000000', 'Dr. Sarah Johnson', 'Theropy', 'Scheduled', 'Nimal', '1123');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `id` int(11) NOT NULL,
  `doctor_id` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `mobile_number` bigint(100) DEFAULT NULL,
  `specialized` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `doctor_id`, `password`, `full_name`, `email`, `gender`, `mobile_number`, `specialized`) VALUES
(1, 'doc_1', 'doc1@1234', 'doctor1', 'doc1@gmail.com', 'Male', 702525398, 'Cardiology'),
(2, 'doc_2', 'doc2@1234', 'doctor2', 'doc2@gmail.com', 'Female', 724845689, 'Pediatrics'),
(3, 'doc_3', 'doc3@123', 'Thusitha Perera', 'doc3@gmail.com', 'Male', 711692525, 'Neurology');

-- --------------------------------------------------------

--
-- Table structure for table `hospital_bills`
--

CREATE TABLE `hospital_bills` (
  `bill_id` int(11) NOT NULL,
  `patient_id` varchar(50) DEFAULT NULL,
  `patient_name` varchar(100) DEFAULT NULL,
  `doctor_name` varchar(100) DEFAULT NULL,
  `service_description` varchar(255) DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `bill_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `id` int(11) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `nic` varchar(20) DEFAULT NULL,
  `password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `full_name`, `gender`, `age`, `nic`, `password`) VALUES
(1, 'Kamal', 'Male', 22, '1120', '1111'),
(2, 'Amal', 'Male', 30, '1121', '2222'),
(3, 'Nimal', 'Male', 35, '1123', '3333'),
(4, 'Nimali Perera', 'Female', 30, '1030', '5555');

-- --------------------------------------------------------

--
-- Table structure for table `patient_notes`
--

CREATE TABLE `patient_notes` (
  `note_id` int(100) NOT NULL,
  `note_date` date NOT NULL,
  `patient_name` varchar(255) NOT NULL,
  `symptoms` varchar(255) DEFAULT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `doctor_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_notes`
--

INSERT INTO `patient_notes` (`note_id`, `note_date`, `patient_name`, `symptoms`, `diagnosis`, `treatment`, `doctor_name`) VALUES
(6, '2024-11-06', '', '', '', 'fgjht\'lj\'rl', NULL),
(7, '2024-11-07', 'nimal', 'jjnkn', 'dfhdhg', 'chgcfgf', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hospital_bills`
--
ALTER TABLE `hospital_bills`
  ADD PRIMARY KEY (`bill_id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patient_notes`
--
ALTER TABLE `patient_notes`
  ADD PRIMARY KEY (`note_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `hospital_bills`
--
ALTER TABLE `hospital_bills`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `patient_notes`
--
ALTER TABLE `patient_notes`
  MODIFY `note_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

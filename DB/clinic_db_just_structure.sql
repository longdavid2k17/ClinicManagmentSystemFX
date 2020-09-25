-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 25 Wrz 2020, 12:14
-- Wersja serwera: 10.4.11-MariaDB
-- Wersja PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `clinic`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `account_type`
--

CREATE TABLE `account_type` (
  `id_account` int(4) NOT NULL,
  `account_type` varchar(15) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `illnesses`
--

CREATE TABLE `illnesses` (
  `id` int(4) NOT NULL,
  `categorySymbol` varchar(2) COLLATE utf8mb4_polish_ci NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_polish_ci NOT NULL,
  `symptoms` varchar(2000) COLLATE utf8mb4_polish_ci NOT NULL,
  `treatment` varchar(1500) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `medicines`
--

CREATE TABLE `medicines` (
  `id` int(4) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_polish_ci NOT NULL,
  `price` double NOT NULL,
  `capacity` varchar(50) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages`
--

CREATE TABLE `messages` (
  `id_message` int(5) NOT NULL,
  `doctor_id` int(3) NOT NULL,
  `patient_id` int(3) NOT NULL,
  `message` varchar(100) COLLATE utf8mb4_polish_ci NOT NULL,
  `preffered_date` date NOT NULL,
  `readed` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `patients_dim`
--

CREATE TABLE `patients_dim` (
  `id_dim` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `growth` int(11) NOT NULL,
  `weight` double NOT NULL,
  `sex` varchar(10) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `private_data`
--

CREATE TABLE `private_data` (
  `data_id` int(4) NOT NULL,
  `user_id` int(4) NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_polish_ci NOT NULL,
  `surname` varchar(30) COLLATE utf8mb4_polish_ci NOT NULL,
  `pesel_number` varchar(12) COLLATE utf8mb4_polish_ci NOT NULL,
  `address_street` varchar(40) COLLATE utf8mb4_polish_ci NOT NULL,
  `address_zipcode` varchar(7) COLLATE utf8mb4_polish_ci NOT NULL,
  `address_city` varchar(40) COLLATE utf8mb4_polish_ci NOT NULL,
  `phone_number` varchar(12) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `recipts`
--

CREATE TABLE `recipts` (
  `id_recipt` int(6) NOT NULL,
  `user_id` int(4) NOT NULL,
  `doctor_id` int(4) NOT NULL,
  `medicine_id` int(4) NOT NULL,
  `description` text COLLATE utf8mb4_polish_ci NOT NULL,
  `creation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id_user` int(4) NOT NULL,
  `login` varchar(25) COLLATE utf8mb4_polish_ci NOT NULL,
  `password` varchar(25) COLLATE utf8mb4_polish_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `visits`
--

CREATE TABLE `visits` (
  `id_visit` int(10) NOT NULL,
  `patient_id` int(3) NOT NULL,
  `doctor_id` int(3) NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_polish_ci NOT NULL,
  `visit_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `account_type`
--
ALTER TABLE `account_type`
  ADD PRIMARY KEY (`id_account`);

--
-- Indeksy dla tabeli `illnesses`
--
ALTER TABLE `illnesses`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `medicines`
--
ALTER TABLE `medicines`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id_message`);

--
-- Indeksy dla tabeli `patients_dim`
--
ALTER TABLE `patients_dim`
  ADD PRIMARY KEY (`id_dim`),
  ADD UNIQUE KEY `user_id` (`id_dim`,`user_id`),
  ADD KEY `user_id_2` (`user_id`);

--
-- Indeksy dla tabeli `private_data`
--
ALTER TABLE `private_data`
  ADD PRIMARY KEY (`data_id`),
  ADD UNIQUE KEY `pesel_number` (`pesel_number`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indeksy dla tabeli `recipts`
--
ALTER TABLE `recipts`
  ADD PRIMARY KEY (`id_recipt`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Indeksy dla tabeli `visits`
--
ALTER TABLE `visits`
  ADD PRIMARY KEY (`id_visit`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `illnesses`
--
ALTER TABLE `illnesses`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `medicines`
--
ALTER TABLE `medicines`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `messages`
--
ALTER TABLE `messages`
  MODIFY `id_message` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `patients_dim`
--
ALTER TABLE `patients_dim`
  MODIFY `id_dim` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `private_data`
--
ALTER TABLE `private_data`
  MODIFY `data_id` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `recipts`
--
ALTER TABLE `recipts`
  MODIFY `id_recipt` int(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `visits`
--
ALTER TABLE `visits`
  MODIFY `id_visit` int(10) NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `account_type`
--
ALTER TABLE `account_type`
  ADD CONSTRAINT `account_type_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `patients_dim`
--
ALTER TABLE `patients_dim`
  ADD CONSTRAINT `patients_dim_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `private_data`
--
ALTER TABLE `private_data`
  ADD CONSTRAINT `private_data_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

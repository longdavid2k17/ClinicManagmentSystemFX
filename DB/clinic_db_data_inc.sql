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

--
-- Zrzut danych tabeli `account_type`
--

INSERT INTO `account_type` (`id_account`, `account_type`) VALUES
(0, 'patient'),
(1, 'patient'),
(2, 'patient'),
(15, 'doctor'),
(16, 'admin'),
(19, 'patient'),
(20, 'doctor'),
(21, 'doctor'),
(22, 'patient');

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

--
-- Zrzut danych tabeli `illnesses`
--

INSERT INTO `illnesses` (`id`, `categorySymbol`, `name`, `symptoms`, `treatment`) VALUES
(1, 'A', 'Cholera', 'biegunka, wymioty, wodnisty stolec', 'tetracyklina, leki bakteriobójcze'),
(2, 'B', 'Odra', 'złe samopoczucie dziecka,ogólne osłabienie, gorączka, nieżyt nosa, zapalenie spojówek,obrzęk powiek,światłowstręt, zapalenie gardła,zapalenie krtani, suchy kaszel,trójkąt Fiłatowa, plamki Koplika, wysypka, wysoka gorączka, duszność, sinica, przyspieszenie tętna, senność, apatia\r\n', 'Zalecana, ale nie wymagana hospitalizacja. Przyjmowanie dużej ilości płynów. Ibuprofen lub paracetamol (bez aspiryny!).Częste wietrzenie pomieszczenia, w którym przebywa na co dzień dziecko. Dieta dziecka chorego na odrę powinna zawierać produkty łatwe do przełknięcia. W trakcie leczenia dziecko powinno również przyjmować witaminę A. Rodzice powinni pamiętać, że odra w pewnych przypadkach prowadzi do powikłań, dlatego ważna jest kontrola lekarska pacjenta.');

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

--
-- Zrzut danych tabeli `medicines`
--

INSERT INTO `medicines` (`id`, `name`, `price`, `capacity`) VALUES
(1, 'Rutinoscorbin', 11.99, '20 tabletek'),
(2, 'Stoperan', 7.99, '6 tabletek'),
(3, 'Holinex', 7.99, '6 tabletek'),
(4, 'Hepaslimin', 19.99, '10 tabletek'),
(5, 'Nurofen Extra Grip', 25.99, '10 tabletek'),
(6, 'Essentiale Forte', 29.99, '30 sztuk'),
(7, 'Apap', 5.39, '12 sztuk'),
(8, 'Gold Omega-3', 23.59, '60 sztuk'),
(9, 'Gripex', 12.99, '60 ml');

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

--
-- Zrzut danych tabeli `patients_dim`
--

INSERT INTO `patients_dim` (`id_dim`, `user_id`, `growth`, `weight`, `sex`) VALUES
(1, 0, 195, 75.7, 'male'),
(2, 1, 167, 80.2, 'male'),
(3, 2, 171, 84.5, 'male'),
(4, 19, 188, 89.1, 'male'),
(5, 22, 155, 45.2, 'female');

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

--
-- Zrzut danych tabeli `private_data`
--

INSERT INTO `private_data` (`data_id`, `user_id`, `name`, `surname`, `pesel_number`, `address_street`, `address_zipcode`, `address_city`, `phone_number`) VALUES
(1, 0, 'Dawid', 'Coperfield', '960530450783', 'Piękna 12/A', '42-600', 'Tarnowskie Góry', '728031070'),
(2, 1, 'Adam', 'Nowak', '98112504531', 'Brzydka 22', '42-600', 'Tarnowskie Góry', '547821321'),
(3, 2, 'Sebastian', 'Alozik', '98050146752', 'Rynek 5/A', '42-600', 'Tarnowskie Góry', '762144753'),
(5, 15, 'Dariusz', 'Gorzelczyk', '75061307327', 'Poniatowskiego 13', '22-856', 'Pogorzelice', '544732850'),
(6, 16, 'Admin', 'Admin', '00000000000', 'Admin', '00-000', 'Admin', '000000000'),
(9, 19, 'Henryk', 'Sienkiewicz', '90040567124', 'Młodości 12', '41-940', 'Piekary Śląskie', '887456950'),
(10, 20, 'Janusz', 'Polak', '76113054067', 'Bytomska 1A', '41-900', 'Kamień Śląski', '650744398'),
(11, 21, 'Maria', 'Taszewicz', '88122457851', 'Bukowa 22', '73-540', 'Wałbrzych', '650477115'),
(12, 22, 'Katarzyna', 'Jaworowicz', '90052607834', 'Warszawska 15/22', '43-860', 'Katowice', '698557465');

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

--
-- Zrzut danych tabeli `recipts`
--

INSERT INTO `recipts` (`id_recipt`, `user_id`, `doctor_id`, `medicine_id`, `description`, `creation_date`) VALUES
(1, 1, 15, 6, '2 razy po 1 tabletce, jedna rano i jedna wieczorem', '2020-08-26'),
(3, 3, 15, 7, '1 tabletka po wystąpieniu objawów, jeśli nawrócą to jeszcze jedna', '2020-08-26'),
(7, 0, 20, 2, 'Dużo bierz bo się zesrasz', '2020-09-14');

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

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id_user`, `login`, `password`, `is_active`) VALUES
(0, 'dejvL651', 'qwerty123', 1),
(1, 'adaNow1024', 'qwerty123', 1),
(2, 'sebaBeksa221', 'qwerty123', 1),
(15, 'leszczu10221', 'zaq12wsx', 1),
(16, 'admin2020', 'zaq1@WSX', 1),
(19, 'heniek123', 'qwerty123', 0),
(20, 'klim321', 'twojastara', 1),
(21, 'mariacka7762', 'qwertyzaq1', 1),
(22, 'kaska202020', 'qwerty123', 1);

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
-- Zrzut danych tabeli `visits`
--

INSERT INTO `visits` (`id_visit`, `patient_id`, `doctor_id`, `description`, `visit_date`) VALUES
(1, 0, 15, 'Wizyta kontrolna. Powód: objawy COVID-19', '2020-08-26'),
(2, 1, 15, 'Wizyta kontrolna. Objawy zapalenia krtani.', '2020-08-27'),
(3, 19, 15, 'Wizyta kontrolna. Objawy zapalenia opon mózgowych i ogólnie chyba nie żyje typ.', '2020-08-28');

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
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `medicines`
--
ALTER TABLE `medicines`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT dla tabeli `messages`
--
ALTER TABLE `messages`
  MODIFY `id_message` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `patients_dim`
--
ALTER TABLE `patients_dim`
  MODIFY `id_dim` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `private_data`
--
ALTER TABLE `private_data`
  MODIFY `data_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `recipts`
--
ALTER TABLE `recipts`
  MODIFY `id_recipt` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT dla tabeli `visits`
--
ALTER TABLE `visits`
  MODIFY `id_visit` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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

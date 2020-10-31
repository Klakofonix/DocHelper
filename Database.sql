-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Czas generowania: 28 Paź 2020, 13:23
-- Wersja serwera: 10.3.22-MariaDB-0+deb10u1-log
-- Wersja PHP: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `michael2424_healthcare`
--
CREATE DATABASE IF NOT EXISTS `michael2424_healthcare` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `michael2424_healthcare`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `chronicdiseases`
--

CREATE TABLE `chronicdiseases` (
  `idChronicDiseases` int(11) NOT NULL,
  `idPatient` int(11) NOT NULL,
  `Disease` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `chronicdiseases`
--

INSERT INTO `chronicdiseases` (`idChronicDiseases`, `idPatient`, `Disease`) VALUES
(1, 1, 'cukrzyca'),
(2, 1, 'nadciśnienie'),
(3, 1, 'nerwica'),
(4, 2, 'nadciśnienie'),
(5, 3, 'cukrzyca'),
(6, 6, 'cukrzyca'),
(7, 6, 'nerwica'),
(19, 13, 'rak trzustki'),
(20, 14, 'zapalenie jelit'),
(21, 15, 'nowa choroba');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `doctor`
--

CREATE TABLE `doctor` (
  `idDoctor` int(11) NOT NULL,
  `Login` varchar(45) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Surname` varchar(45) NOT NULL,
  `Role` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `doctor`
--

INSERT INTO `doctor` (`idDoctor`, `Login`, `Password`, `Name`, `Surname`, `Role`) VALUES
(3, 's', 's2', 's3', 's4', 's5'),
(4, 'Doktor2', 'Doktor2', 'Tomasz', 'Lamerski', 'Rola'),
(5, 'Doktor3', '$2y$10$UNm5eKIOjDs.2iu7dWVCROgl.rL2BDYXlFy2BiQwsUw6kxDUWahZ.', 'Apolonisław', 'Gejmer', 'Rusher'),
(6, 'Doktor4', '$2y$10$8HvfvwNThfiFGQjyFe1QmulQ01vlz.YwZMFzuvPobfKpdSXnvZs7i', 'Penisław', 'Duper', 'Stander'),
(7, 'Doktor5', '$2y$10$h0Gna4wOrEqONldFQWifauZvHnq0aVnixhQi/xFg.VebRF8CzBH0m', 'Bencfał', 'Debilny', 'Gracz WoWa'),
(8, 'login', '$2y$10$t0oG/TF5.Jeody26PmulO.EXguOnMJ52fz/vcziMl.hrLSd.CEelS', 'Imie', 'Nazwisko', 'Rola'),
(9, 'login2', '$2y$10$blp5cUJKmzkR50oHKDstoeHPYZEOrDNsSwhF6G.E2frsAlqCjqrja', 'Imie', 'Nazwisko', 'Rola');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `medicines`
--

CREATE TABLE `medicines` (
  `idMedicines` int(11) NOT NULL,
  `idPatientCard` int(11) NOT NULL,
  `Medicine` varchar(45) NOT NULL,
  `Dose` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `medicines`
--

INSERT INTO `medicines` (`idMedicines`, `idPatientCard`, `Medicine`, `Dose`) VALUES
(1, 1, 'Rutinoscorbin', '2x dzień 1xnoc'),
(2, 1, 'Apap', 'Gdy boli głowa'),
(3, 1, 'Lek na kaszel', '2 łyżeczki na noc, 2 łyżeczki z rana, w drugim tygodniu dawkę zmniejszyć o 1 łyżeczkę'),
(4, 4, 'APAP', '2xdziennie'),
(5, 2, 'APAP', '2xdziennie'),
(6, 5, 'Nivea Men', 'Przed snem'),
(7, 8, 'Xanax', 'Gdy istnienie boli za bardzo'),
(8, 8, 'Ibuprom', 'Jak rączka boli'),
(28, 69, 'apap dzien', 'w dzien'),
(29, 69, 'Apap noc', 'na noc'),
(30, 70, 'Starobabizm', '2x dziennie po 1 tabletka'),
(31, 70, 'Herbata ziolowa ', 'codziennie 2 razy'),
(32, 70, 'Konsekwencizm', 'Brak'),
(33, 73, 'Apap', 'papa'),
(34, 75, 'Apap', 'papapa'),
(35, 75, 'asd', 'asdx'),
(36, 76, 'Nibulosiden', '2x3dni'),
(37, 76, 'Apap', 'papa'),
(38, 77, 'lek', 'lek'),
(39, 81, 'Apap', '2x dziennie'),
(40, 84, 'Lek na ból głowy', '2x dziennie'),
(41, 84, 'Lek na katar', '1 łyżka przed snem');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `patient`
--

CREATE TABLE `patient` (
  `idPatient` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Surname` varchar(45) NOT NULL,
  `BirthDate` date NOT NULL,
  `PESEL` varchar(45) NOT NULL,
  `Adress` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `patient`
--

INSERT INTO `patient` (`idPatient`, `Name`, `Surname`, `BirthDate`, `PESEL`, `Adress`) VALUES
(1, 'Adam', 'Grzejniczak', '1996-01-24', '96012412332', 'Pokojowa 24 Kraków'),
(2, 'Andrzej', 'Hajto', '1921-03-12', '21031212312', 'Nieziemska 21 Kielce'),
(3, 'Edmyndo', 'Eredarus', '1941-11-11', '41111184213', 'ul. Głogowska 11 Katowice'),
(4, 'Stefan', 'Sasin', '1948-04-21', '48042131241', 'Warszawska 12 Poznań'),
(5, 'Elżbieta', 'Wielka', '1971-11-23', '71112318453', 'Elżbietów 12  Poznań'),
(6, 'Janusz', 'Trocki', '1945-01-01', '45010135125', 'ul. Brata Alberta 44 Ruda Śląska'),
(7, 'Paweł', 'Gadacz', '1999-02-21', '99022123472', 'ul. Trocinowa 104 Warszawa'),
(8, 'Katarzyna', 'Wielka', '1931-02-11', '31021132123', 'ul. Akademii Umiejętności 75 Bielsko-Biała'),
(9, 'Aleksander', 'Ulriht', '1953-07-13', '53071346018', 'ul. Kościuszki Tadeusza 4 Kielce'),
(10, 'Kamil', 'Angelo', '1985-07-03', '85070770288', 'ul. Źródlana 72 Kielce'),
(13, 'Limak', 'Trzaskowski', '1998-08-11', '98031000001', 'Warszawa wiejska 10'),
(14, 'Anna', 'Walczak', '1998-09-24', '98092415363', 'Jednorożców 21'),
(15, 'Narcyza', 'Velman', '1996-08-14', '69081424125', 'Velmanowo');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `patientcard`
--

CREATE TABLE `patientcard` (
  `idPatientCard` int(11) NOT NULL,
  `idDoctor` int(11) NOT NULL,
  `idPatient` int(11) NOT NULL,
  `Symptoms` varchar(45) NOT NULL,
  `Date` date NOT NULL,
  `Other` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `patientcard`
--

INSERT INTO `patientcard` (`idPatientCard`, `idDoctor`, `idPatient`, `Symptoms`, `Date`, `Other`) VALUES
(1, 5, 1, 'Bółe głowy, gorączka', '2020-03-21', 'brak'),
(2, 6, 2, 'Ból gardła', '2020-01-22', 'Wypisane skierowanie do orotopedy.'),
(3, 5, 4, 'Złamana noga', '2020-05-20', 'Założony gips'),
(4, 4, 1, 'Kaszel, krew z nosa', '2020-03-30', 'Przeziebienie'),
(5, 4, 5, 'Skręcony nadgarstek', '2020-06-21', 'Założona szyna usztywniająca'),
(6, 3, 1, 'Grypa', '2019-11-20', 'brak'),
(7, 3, 3, 'Bóle pleców', '2020-04-21', 'brak'),
(8, 3, 14, 'Boli rączka', '2020-08-12', 'Bardzo krwawi'),
(43, 4, 5, 'Drgawki', '2020-02-11', 'brak'),
(44, 5, 1, 'Brak smaku i węchu', '2020-03-03', 'Ręki nadal nie ma'),
(69, 5, 14, 'Test koncowy', '2020-08-14', 'nic a nic'),
(70, 5, 2, 'Zlamana kosc udowa', '2020-08-14', 'Nie poniosl żadnych konsekwencji'),
(71, 5, 14, 'aaa', '2020-08-14', 'aaa'),
(72, 5, 14, 'Saa', '2020-08-14', 'aaa'),
(73, 5, 1, 'Mdłości', '2020-08-14', 'Test'),
(74, 5, 14, 'Ból brzucha', '2020-08-14', 'brak'),
(75, 5, 14, 'Ostatecznosc', '2020-08-14', '123123'),
(76, 5, 13, 'Wizyta 1', '2020-08-14', '1 zwolnienie 1 lek'),
(77, 5, 13, '2 wizyta', '2020-08-14', 'Brak zwolnienia 1 lek'),
(78, 5, 13, 'Wizyta 3', '2020-08-14', 'Sama wizyta'),
(79, 6, 15, 'Slaba koncentracja', '2020-08-14', 'brak'),
(80, 5, 15, 'Wizyta kontrolna', '2020-08-15', 'brak'),
(81, 5, 6, 'zlamana ręką', '2020-08-15', 'założony gips'),
(82, 5, 1, 'Ból wątroby', '2020-08-16', 'brak'),
(83, 5, 1, 'Wymioty', '2020-08-23', 'brak'),
(84, 5, 1, 'Katar, bóle glowy, gorączka', '2020-09-07', 'brak');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sensitization`
--

CREATE TABLE `sensitization` (
  `idSensitization` int(11) NOT NULL,
  `idPatient` int(11) NOT NULL,
  `Sensization` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `sensitization`
--

INSERT INTO `sensitization` (`idSensitization`, `idPatient`, `Sensization`) VALUES
(1, 1, 'gluten'),
(2, 1, 'orzechy'),
(3, 2, 'skrobia'),
(4, 2, 'sierść'),
(5, 5, 'orzechy'),
(6, 3, 'sierść'),
(8, 5, 'mleko'),
(11, 1, 'laktoza'),
(12, 14, 'słońce'),
(13, 2, 'laktoza'),
(14, 15, 'zielenina'),
(15, 15, 'kolejna'),
(16, 15, 'test'),
(17, 13, 'sierść'),
(18, 13, 'laktoza'),
(19, 10, 'sierść');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sickleave`
--

CREATE TABLE `sickleave` (
  `idSickLeave` int(11) NOT NULL,
  `idPatientCard` int(11) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `sickleave`
--

INSERT INTO `sickleave` (`idSickLeave`, `idPatientCard`, `StartDate`, `EndDate`) VALUES
(1, 1, '2020-02-02', '2020-03-02'),
(2, 2, '2020-04-21', '2020-04-28'),
(3, 6, '2020-03-20', '2020-03-27'),
(4, 8, '2020-08-12', '2020-09-12'),
(15, 69, '2020-08-14', '2020-09-14'),
(16, 70, '2020-08-14', '2021-08-14'),
(17, 74, '2020-08-14', '2020-08-24'),
(18, 75, '2020-08-14', '2020-09-14'),
(19, 76, '2020-08-14', '2020-08-16'),
(20, 81, '2020-08-15', '2020-09-15'),
(21, 82, '2020-08-16', '2020-10-16'),
(22, 83, '2020-08-23', '2020-09-23'),
(23, 84, '2020-09-07', '2020-09-09');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `chronicdiseases`
--
ALTER TABLE `chronicdiseases`
  ADD PRIMARY KEY (`idChronicDiseases`),
  ADD KEY `DisaeseIdPatient_idx` (`idPatient`);

--
-- Indeksy dla tabeli `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`idDoctor`);

--
-- Indeksy dla tabeli `medicines`
--
ALTER TABLE `medicines`
  ADD PRIMARY KEY (`idMedicines`),
  ADD KEY `MedicinesPatientCard_idx` (`idPatientCard`);

--
-- Indeksy dla tabeli `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`idPatient`);

--
-- Indeksy dla tabeli `patientcard`
--
ALTER TABLE `patientcard`
  ADD PRIMARY KEY (`idPatientCard`),
  ADD KEY `PatientCardDoctor_idx` (`idDoctor`),
  ADD KEY `PatientCardPatient_idx` (`idPatient`);

--
-- Indeksy dla tabeli `sensitization`
--
ALTER TABLE `sensitization`
  ADD PRIMARY KEY (`idSensitization`),
  ADD KEY `SensizationPatient_idx` (`idPatient`);

--
-- Indeksy dla tabeli `sickleave`
--
ALTER TABLE `sickleave`
  ADD PRIMARY KEY (`idSickLeave`),
  ADD KEY `SickLeave_PatientCard_idx` (`idPatientCard`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `chronicdiseases`
--
ALTER TABLE `chronicdiseases`
  MODIFY `idChronicDiseases` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT dla tabeli `doctor`
--
ALTER TABLE `doctor`
  MODIFY `idDoctor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT dla tabeli `medicines`
--
ALTER TABLE `medicines`
  MODIFY `idMedicines` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT dla tabeli `patient`
--
ALTER TABLE `patient`
  MODIFY `idPatient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT dla tabeli `patientcard`
--
ALTER TABLE `patientcard`
  MODIFY `idPatientCard` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=85;

--
-- AUTO_INCREMENT dla tabeli `sensitization`
--
ALTER TABLE `sensitization`
  MODIFY `idSensitization` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT dla tabeli `sickleave`
--
ALTER TABLE `sickleave`
  MODIFY `idSickLeave` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `chronicdiseases`
--
ALTER TABLE `chronicdiseases`
  ADD CONSTRAINT `chronicdiseases_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Ograniczenia dla tabeli `medicines`
--
ALTER TABLE `medicines`
  ADD CONSTRAINT `medicines_ibfk_1` FOREIGN KEY (`idPatientCard`) REFERENCES `patientcard` (`idPatientCard`);

--
-- Ograniczenia dla tabeli `patientcard`
--
ALTER TABLE `patientcard`
  ADD CONSTRAINT `patientcard_ibfk_1` FOREIGN KEY (`idDoctor`) REFERENCES `doctor` (`idDoctor`),
  ADD CONSTRAINT `patientcard_ibfk_2` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Ograniczenia dla tabeli `sensitization`
--
ALTER TABLE `sensitization`
  ADD CONSTRAINT `sensitization_ibfk_1` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`);

--
-- Ograniczenia dla tabeli `sickleave`
--
ALTER TABLE `sickleave`
  ADD CONSTRAINT `sickleave_ibfk_1` FOREIGN KEY (`idPatientCard`) REFERENCES `patientcard` (`idPatientCard`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

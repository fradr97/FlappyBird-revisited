-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 13, 2021 alle 20:16
-- Versione del server: 10.4.17-MariaDB
-- Versione PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `flappybird`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `gioco`
--

CREATE TABLE `gioco` (
  `id` int(10) NOT NULL,
  `nome_gioco` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `gioco`
--

INSERT INTO `gioco` (`id`, `nome_gioco`) VALUES
(1, 'FlappyClassic'),
(3, 'FlappyRun'),
(2, 'FlappyShoot');

-- --------------------------------------------------------

--
-- Struttura della tabella `player`
--

CREATE TABLE `player` (
  `id` int(10) NOT NULL,
  `nome` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `player`
--

INSERT INTO `player` (`id`, `nome`) VALUES
(1, 'Giocatore');

-- --------------------------------------------------------

--
-- Struttura della tabella `punteggio`
--

CREATE TABLE `punteggio` (
  `id` int(50) NOT NULL,
  `id_player` int(50) DEFAULT NULL,
  `id_gioco` int(50) DEFAULT NULL,
  `punteggio` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `punteggio`
--

INSERT INTO `punteggio` (`id`, `id_player`, `id_gioco`, `punteggio`) VALUES
(1, 1, 1, 30);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `gioco`
--
ALTER TABLE `gioco`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nome_gioco` (`nome_gioco`);

--
-- Indici per le tabelle `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Indici per le tabelle `punteggio`
--
ALTER TABLE `punteggio`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `player`
--
ALTER TABLE `player`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `punteggio`
--
ALTER TABLE `punteggio`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

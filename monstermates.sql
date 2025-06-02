-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Jun 2025 pada 17.11
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `monstermates`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `daily_assignments`
--

CREATE TABLE `daily_assignments` (
  `id_assignment` int(11) NOT NULL,
  `id_player` int(11) NOT NULL,
  `id_mission` int(11) NOT NULL,
  `date_given` date NOT NULL,
  `completed` tinyint(1) NOT NULL DEFAULT 0,
  `claimed` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `inventory`
--

CREATE TABLE `inventory` (
  `id_inventory` int(11) NOT NULL,
  `id_player` int(11) NOT NULL,
  `id_property` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `missions`
--

CREATE TABLE `missions` (
  `id_mission` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `type` enum('daily','long_term') NOT NULL,
  `gold_reward` int(11) DEFAULT 0,
  `exp_reward` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `missions`
--

INSERT INTO `missions` (`id_mission`, `title`, `description`, `type`, `gold_reward`, `exp_reward`) VALUES
(1, 'Sarapan Yuk', 'Beri makanan 1x ke friend', 'daily', 50, 10),
(2, 'Main Bareng', 'Gunakan 1 mainan untuk bermain', 'daily', 50, 10),
(3, 'Sapa Teman', 'Login dan buka halaman friend', 'daily', 20, 0),
(4, 'Cek Toko', 'Buka halaman toko minimal 1x', 'daily', 30, 0),
(5, 'Penuh Cinta', 'Beri makanan dan mainan ke 1 friend', 'daily', 100, 0),
(6, 'Teman Pertama', 'Pilih friend pertama kali', 'long_term', 100, 0),
(7, 'Naik Level!', 'Capai level 2', 'long_term', 150, 0),
(8, 'Kolektor Mainan', 'Miliki 5 mainan berbeda', 'long_term', 200, 0),
(9, 'Koleksi Makanan', 'Miliki 5 makanan berbeda', 'long_term', 200, 0),
(10, 'Kaya Raya', 'Miliki lebih dari 1000 gold', 'long_term', 300, 0),
(11, 'Teman Baru', 'Dapatkan friend ke-2', 'long_term', 200, 0),
(12, 'Sering Main', 'Login selama 7 hari berturut-turut', 'long_term', 500, 0),
(13, 'Pecinta Semua', 'Maksimalkan exp semua pet ke level 3', 'long_term', 1000, 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `monsters`
--

CREATE TABLE `monsters` (
  `id_monster` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `happy` int(11) NOT NULL,
  `food` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `monsters`
--

INSERT INTO `monsters` (`id_monster`, `name`, `happy`, `food`) VALUES
(1, 'Reaper', 20, 300),
(2, 'Egg', 25, 320),
(3, 'Pear', 30, 400),
(4, 'Ghost', 40, 500);

-- --------------------------------------------------------

--
-- Struktur dari tabel `owner`
--

CREATE TABLE `owner` (
  `id_owner` int(11) NOT NULL,
  `id_player` int(11) DEFAULT NULL,
  `id_monster` int(11) NOT NULL,
  `point_food` int(11) NOT NULL,
  `point_happy` int(11) NOT NULL,
  `point_levelup` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `players`
--

CREATE TABLE `players` (
  `id_player` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `level` int(11) NOT NULL,
  `point_to_level_up` int(11) NOT NULL,
  `gold` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `player_missions`
--

CREATE TABLE `player_missions` (
  `id_player_mission` int(11) NOT NULL,
  `id_player` int(11) NOT NULL,
  `id_mission` int(11) NOT NULL,
  `completed` tinyint(1) NOT NULL DEFAULT 0,
  `claimed` tinyint(1) NOT NULL DEFAULT 0,
  `completed_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `property`
--

CREATE TABLE `property` (
  `id_property` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `price` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `point` int(11) NOT NULL,
  `exp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `property`
--

INSERT INTO `property` (`id_property`, `name`, `price`, `type`, `point`, `exp`) VALUES
(1, 'food1', 10, 'food', 8, 5),
(2, 'food2', 10, 'food', 6, 5),
(3, 'food3', 15, 'food', 16, 8),
(4, 'food4', 20, 'food', 30, 12),
(5, 'food5', 20, 'food', 30, 12),
(6, 'food6', 60, 'food', 100, 80),
(7, 'toy1', 15, 'toy', 5, 20),
(8, 'toy2', 20, 'toy', 8, 30),
(9, 'toy3', 20, 'toy', 15, 40),
(10, 'toy4', 40, 'toy', 20, 100),
(11, 'toy5', 50, 'toy', 30, 150),
(12, 'toy6', 80, 'toy', 50, 250);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `daily_assignments`
--
ALTER TABLE `daily_assignments`
  ADD PRIMARY KEY (`id_assignment`),
  ADD KEY `id_player` (`id_player`),
  ADD KEY `id_mission` (`id_mission`);

--
-- Indeks untuk tabel `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id_inventory`),
  ADD KEY `id_player` (`id_player`),
  ADD KEY `id_property` (`id_property`);

--
-- Indeks untuk tabel `missions`
--
ALTER TABLE `missions`
  ADD PRIMARY KEY (`id_mission`);

--
-- Indeks untuk tabel `monsters`
--
ALTER TABLE `monsters`
  ADD PRIMARY KEY (`id_monster`);

--
-- Indeks untuk tabel `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`id_owner`),
  ADD KEY `id_monster` (`id_monster`),
  ADD KEY `fk_id_player` (`id_player`);

--
-- Indeks untuk tabel `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id_player`);

--
-- Indeks untuk tabel `player_missions`
--
ALTER TABLE `player_missions`
  ADD PRIMARY KEY (`id_player_mission`),
  ADD KEY `id_player` (`id_player`),
  ADD KEY `id_mission` (`id_mission`);

--
-- Indeks untuk tabel `property`
--
ALTER TABLE `property`
  ADD PRIMARY KEY (`id_property`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `daily_assignments`
--
ALTER TABLE `daily_assignments`
  MODIFY `id_assignment` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `missions`
--
ALTER TABLE `missions`
  MODIFY `id_mission` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT untuk tabel `player_missions`
--
ALTER TABLE `player_missions`
  MODIFY `id_player_mission` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `daily_assignments`
--
ALTER TABLE `daily_assignments`
  ADD CONSTRAINT `daily_assignments_ibfk_1` FOREIGN KEY (`id_player`) REFERENCES `players` (`id_player`) ON DELETE CASCADE,
  ADD CONSTRAINT `daily_assignments_ibfk_2` FOREIGN KEY (`id_mission`) REFERENCES `missions` (`id_mission`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `id_player` FOREIGN KEY (`id_player`) REFERENCES `players` (`id_player`),
  ADD CONSTRAINT `id_property` FOREIGN KEY (`id_property`) REFERENCES `property` (`id_property`);

--
-- Ketidakleluasaan untuk tabel `owner`
--
ALTER TABLE `owner`
  ADD CONSTRAINT `fk_id_player` FOREIGN KEY (`id_player`) REFERENCES `players` (`id_player`),
  ADD CONSTRAINT `id_monster` FOREIGN KEY (`id_monster`) REFERENCES `monsters` (`id_monster`);

--
-- Ketidakleluasaan untuk tabel `player_missions`
--
ALTER TABLE `player_missions`
  ADD CONSTRAINT `player_missions_ibfk_1` FOREIGN KEY (`id_player`) REFERENCES `players` (`id_player`) ON DELETE CASCADE,
  ADD CONSTRAINT `player_missions_ibfk_2` FOREIGN KEY (`id_mission`) REFERENCES `missions` (`id_mission`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

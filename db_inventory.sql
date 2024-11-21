-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 29 Jun 2024 pada 15.07
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_inventory`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `audit_log`
--

CREATE TABLE `audit_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` varchar(50) NOT NULL,
  `table_name` varchar(50) NOT NULL,
  `record_id` int(11) NOT NULL,
  `old_value` text DEFAULT NULL,
  `new_value` text DEFAULT NULL,
  `action_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `audit_log`
--

INSERT INTO `audit_log` (`log_id`, `user_id`, `action`, `table_name`, `record_id`, `old_value`, `new_value`, `action_date`) VALUES
(1, 1, 'Menambah Data Barang', 'items', 727, NULL, 'laptop asus vivobook', '2024-06-27 20:37:46'),
(2, 1, 'UPDATE', 'items', 790, 'Name: Televisi Samsung 1, Price: 100000.00, Quantity: 300', 'Name: Televisi Samsung 1, Price: 200000.00, Quantity: 0', '2024-06-27 23:13:48'),
(3, 1, 'UPDATE', 'items', 915, 'Name: Televisi Sony, Price: 5300000.00, Quantity: 20', 'Name: TV Sony, Price: 5300000.00, Quantity: 0', '2024-06-27 23:16:12');

-- --------------------------------------------------------

--
-- Struktur dari tabel `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `categories`
--

INSERT INTO `categories` (`category_id`, `category_name`, `description`) VALUES
(1, 'Televisi', 'Perangkat elektronik untuk menonton siaran TV'),
(2, 'Ponsel', 'Perangkat komunikasi seluler'),
(3, 'Laptop', 'Komputer portabel untuk berbagai keperluan'),
(4, 'Tablet', 'Perangkat elektronik berbentuk tablet'),
(5, 'Kamera', 'Perangkat untuk mengambil foto dan video'),
(6, 'Audio', 'Perangkat untuk mendengarkan musik dan audio'),
(7, 'Gaming', 'Perangkat elektronik untuk bermain game'),
(8, 'Smart Home', 'Perangkat pintar untuk rumah tangga'),
(9, 'Wearable', 'Perangkat elektronik yang dapat dikenakan'),
(10, 'Aksesoris', 'Aksesoris elektronik tambahan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `items`
--

CREATE TABLE `items` (
  `item_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `kode_item` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `total_price` decimal(50,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `items`
--

INSERT INTO `items` (`item_id`, `category_id`, `item_name`, `quantity`, `price`, `created_at`, `updated_at`, `kode_item`, `total_price`) VALUES
(11, 1, 'Televisi Samsung 1', 85, 200000.00, '2024-06-23 23:31:55', '2024-06-29 00:09:03', 'TVS001', 17000000),
(13, 2, 'iPhone 13', 0, 15000000.00, '2024-06-23 23:31:55', '2024-06-26 20:41:01', 'IPN013', 0),
(14, 2, 'Samsung Galaxy S21', 0, 12000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SGS021', 0),
(15, 3, 'Laptop Dell', 0, 8000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LTD001', 0),
(16, 3, 'Laptop HP', 0, 7500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LTH002', 0),
(17, 4, 'iPad Pro', 0, 13000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'IPD003', 0),
(18, 4, 'Samsung Galaxy Tab', 0, 10000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SGT004', 0),
(19, 5, 'Kamera Canon', 0, 7000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KMC005', 0),
(20, 5, 'Kamera Nikon', 0, 7500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KMN006', 0),
(21, 6, 'Headphone Sony', 0, 1500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HDS007', 0),
(22, 6, 'Speaker JBL', 0, 2000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SPJ008', 0),
(23, 7, 'PlayStation 5', 0, 8000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PS5009', 0),
(24, 7, 'Xbox Series X', 0, 7500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'XBS010', 0),
(25, 8, 'Smart Bulb Philips', 0, 500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SBP011', 0),
(26, 8, 'Smart Lock Xiaomi', 0, 1500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SLX012', 0),
(27, 9, 'Apple Watch', 0, 6000000.00, '2024-06-23 23:31:55', '2024-06-26 20:41:05', 'AW013', 0),
(28, 9, 'Fitbit Charge', 0, 2000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'FC014', 0),
(29, 10, 'Mouse Logitech', 0, 300000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'ML015', 0),
(30, 10, 'Keyboard Razer', 0, 1500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KR016', 0),
(31, 1, 'TV Sony', 80, 5300000.00, '2024-06-23 23:31:55', '2024-06-29 00:09:31', 'TVS017', 424000000),
(32, 2, 'Ponsel Oppo', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-28 01:05:27', 'PO018', 0),
(33, 3, 'Laptop Asus', 0, 9000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LA019', 0),
(34, 4, 'Tablet Lenovo', 0, 8000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TL020', 0),
(35, 5, 'Kamera Sony', 0, 9000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KMS021', 0),
(36, 6, 'Headphone Bose', 0, 2500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HDB022', 0),
(37, 7, 'Nintendo Switch', 0, 5000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'NS023', 0),
(38, 8, 'Smart Plug TP-Link', 0, 300000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SPT024', 0),
(39, 9, 'Smartwatch Garmin', 0, 5000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SG025', 0),
(40, 10, 'Webcam Logitech', 0, 700000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WL026', 0),
(41, 1, 'Televisi TCL', 100, 4800000.00, '2024-06-23 23:31:55', '2024-06-29 00:08:53', 'TVT027', 480000000),
(42, 2, 'Ponsel Vivo', 0, 3200000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PV028', 0),
(43, 3, 'Laptop Acer', 0, 8500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LA029', 0),
(44, 4, 'Tablet Huawei', 0, 7000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TH030', 0),
(45, 5, 'Kamera Panasonic', 0, 6500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KMP031', 0),
(46, 6, 'Headphone Jabra', 0, 1700000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HDJ032', 0),
(47, 7, 'Console Sega', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'CS033', 0),
(48, 8, 'Smart Thermostat Nest', 0, 2500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'STN034', 0),
(49, 9, 'Smartwatch Samsung', 0, 4000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SS035', 0),
(50, 10, 'Webcam Microsoft', 0, 800000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WM036', 0),
(51, 1, 'Televisi Panasonic', 0, 4500000.00, '2024-06-23 23:31:55', '2024-06-28 02:44:42', 'TVP037', 0),
(52, 2, 'Ponsel OnePlus', 0, 5500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PO038', 0),
(53, 3, 'Laptop Microsoft Surface', 0, 12000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LMS039', 0),
(54, 4, 'Tablet Amazon Fire', 0, 4000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TAF040', 0),
(55, 5, 'Kamera Fujifilm', 0, 7000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KF041', 0),
(56, 6, 'Headphone Beats', 0, 2300000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HDB042', 0),
(57, 7, 'Gaming PC Alienware', 0, 25000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'GPCA043', 0),
(58, 8, 'Smart Doorbell Ring', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SDR044', 0),
(59, 9, 'Fitness Tracker Xiaomi', 0, 500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'FTX045', 0),
(60, 10, 'Webcam Razer', 0, 1500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WR046', 0),
(61, 1, 'Televisi Philips', 0, 4300000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TVP047', 0),
(62, 2, 'Ponsel Google Pixel', 0, 9000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PGP048', 0),
(63, 3, 'Laptop Lenovo ThinkPad', 0, 10000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LLT049', 0),
(64, 4, 'Tablet Microsoft Surface', 0, 11000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TMS050', 0),
(65, 5, 'Kamera GoPro', 0, 6000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KGP051', 0),
(66, 6, 'Headphone Sennheiser', 0, 2100000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HDS052', 0),
(67, 7, 'VR Headset Oculus', 0, 7000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'VRO053', 0),
(68, 8, 'Smart Light Switch', 0, 400000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SLS054', 0),
(69, 9, 'Wearable Fitbit', 0, 1500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WF055', 0),
(70, 10, 'Webcam Asus', 0, 600000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WA056', 0),
(71, 1, 'Televisi Xiaomi', 100, 3000000.00, '2024-06-23 23:31:55', '2024-06-28 03:50:55', 'TVX057', 300000000),
(72, 2, 'Ponsel Realme', 0, 2500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PR058', 0),
(73, 3, 'Laptop MSI', 0, 13000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LM059', 0),
(74, 4, 'Tablet Asus ZenPad', 0, 5000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TAZ060', 0),
(75, 5, 'Kamera Leica', 0, 15000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KL061', 0),
(76, 6, 'Headphone AKG', 0, 1400000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HAK062', 0),
(77, 7, 'Gaming Laptop Razer', 0, 20000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'GLR063', 0),
(78, 8, 'Smart Security Camera', 0, 3500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SSC064', 0),
(79, 9, 'Wearable Garmin', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WG065', 0),
(80, 10, 'Webcam HP', 0, 900000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WH066', 0),
(81, 1, 'Televisi Hisense', 0, 3400000.00, '2024-06-23 23:31:55', '2024-06-27 05:47:58', 'TVH067', 0),
(82, 2, 'Ponsel Sony Xperia', 0, 7000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PSX068', 0),
(83, 3, 'Laptop Gigabyte', 0, 12000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LG069', 0),
(84, 4, 'Tablet Huawei MediaPad', 0, 6000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'THM070', 0),
(85, 5, 'Kamera Pentax', 0, 8000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KP071', 0),
(86, 6, 'Headphone Marshall', 0, 1700000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HM072', 0),
(87, 7, 'Gaming Console NeoGeo', 0, 15000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'GCN073', 0),
(88, 8, 'Smart Outlet Belkin', 0, 500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SOB074', 0),
(89, 9, 'Wearable Samsung', 0, 2500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WS075', 0),
(91, 1, 'Televisi Vizio', 0, 2800000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TVV077', 0),
(92, 2, 'Ponsel LG Velvet', 0, 6000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PLV078', 0),
(93, 3, 'Laptop Huawei MateBook', 0, 9000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LHM079', 0),
(94, 4, 'Tablet Samsung Galaxy', 0, 8500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TSG080', 0),
(95, 5, 'Kamera Sigma', 0, 11000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KS081', 0),
(96, 6, 'Headphone Grado', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HG082', 0),
(97, 7, 'Gaming Chair Secretlab', 0, 6000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'GCS083', 0),
(98, 8, 'Smart TV Box Roku', 0, 1000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'STB084', 0),
(99, 9, 'Wearable Apple', 0, 6000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WA085', 0),
(101, 1, 'Televisi Toshiba', 0, 4500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TVT087', 0),
(102, 2, 'Ponsel HTC', 80, 4500000.00, '2024-06-23 23:31:55', '2024-06-28 21:06:05', 'PHT088', 360000000),
(103, 3, 'Laptop Samsung', 0, 11000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LS089', 0),
(104, 4, 'Tablet Sony Xperia', 0, 7500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TSX090', 0),
(105, 5, 'Kamera Kodak', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'KK091', 0),
(106, 6, 'Headphone Philips', 0, 1200000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'HP092', 0),
(107, 7, 'Gaming Monitor Asus', 0, 5000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'GMA093', 0),
(108, 8, 'Smart Fridge LG', 0, 20000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'SFL094', 0),
(109, 9, 'Wearable Fossil', 0, 2500000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WF095', 0),
(110, 10, 'Webcam Lenovo', 0, 700000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'WL096', 0),
(111, 1, 'Televisi JVC', 0, 4000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TVJ097', 0),
(112, 2, 'Ponsel Nokia', 0, 3000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'PN098', 0),
(113, 3, 'Laptop Alienware', 0, 20000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'LA099', 0),
(114, 4, 'Tablet Amazon Kindle', 0, 2000000.00, '2024-06-23 23:31:55', '2024-06-23 23:31:55', 'TAK100', 0),
(115, 6, 'JBL ORIGINAL', 0, 500000.00, '2024-06-24 15:19:29', '2024-06-24 15:19:29', 'BRGJO697', 0),
(120, 10, 'KIPAS ANGIN PANASONIC', 0, 200000.00, '2024-06-26 01:21:11', '2024-06-26 01:21:11', 'BRGKAP605', 0),
(121, 2, 'SAMSUNG GALAXIY J2 PRIME', 0, 1000000.00, '2024-06-26 01:31:38', '2024-06-26 01:31:38', 'BRGSGJP898', 0),
(122, 3, 'laptop asus vivobook', 0, 7000000.00, '2024-06-27 20:37:46', '2024-06-27 20:38:27', 'BRG-LAV849', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `transaction_type` enum('in','out') NOT NULL,
  `transaction_date` datetime DEFAULT current_timestamp(),
  `note` text DEFAULT NULL,
  `total_price` decimal(50,0) NOT NULL,
  `kode_transaction` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `user_id`, `item_id`, `quantity`, `transaction_type`, `transaction_date`, `note`, `total_price`, `kode_transaction`) VALUES
(56, 1, 11, 100, 'in', '2024-06-28 02:46:12', 'Barang Masuk', 20000000, 'TRS-TS1206'),
(57, 1, 11, 20, 'out', '2024-06-28 02:46:48', 'Barang keluar', 4000000, 'TRS-TS1279'),
(58, 1, 11, 20, 'in', '2024-06-28 03:29:07', 'Barang Masuk', 4000000, 'TRS-TS1196'),
(59, 1, 31, 50, 'in', '2024-06-28 03:49:25', 'Barang Masuk', 265000000, 'TRS-TS492'),
(60, 1, 31, 20, 'out', '2024-06-28 03:50:28', 'Barang keluar', 106000000, 'TRS-TS597'),
(61, 1, 71, 100, 'in', '2024-06-28 03:50:55', 'Barang Masuk', 300000000, 'TRS-TX914'),
(62, 1, 11, 20, 'out', '2024-06-28 04:14:09', 'Barang keluar', 4000000, 'TS1341'),
(63, 1, 11, 12, 'out', '2024-06-28 20:21:37', 'Barang keluar', 2400000, 'TS1338'),
(64, 1, 11, 12, 'out', '2024-06-28 20:22:14', 'Barang keluar', 2400000, 'TS1221'),
(65, 1, 11, 1, 'out', '2024-06-28 21:04:53', 'Barang keluar', 200000, 'TS1532'),
(66, 1, 102, 100, 'in', '2024-06-28 21:05:17', 'Barang Masuk', 450000000, 'PH174'),
(67, 1, 102, 20, 'out', '2024-06-28 21:06:05', 'Barang keluar', 90000000, 'PH725'),
(68, 1, 11, 20, 'out', '2024-06-28 23:29:07', 'Barang keluar', 4000000, 'TS1224'),
(69, 1, 11, 100, 'in', '2024-06-29 00:08:16', 'Barang Masuk', 20000000, 'TS1101'),
(70, 1, 31, 100, 'in', '2024-06-29 00:08:36', 'Barang Masuk', 530000000, 'TS840'),
(71, 1, 41, 100, 'in', '2024-06-29 00:08:53', 'Barang Masuk', 480000000, 'TT873'),
(72, 1, 11, 50, 'out', '2024-06-29 00:09:03', 'Barang keluar', 10000000, 'TS1489'),
(73, 1, 31, 50, 'out', '2024-06-29 00:09:31', 'Barang keluar', 265000000, 'TS442');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `VerifyCode` varchar(255) DEFAULT NULL,
  `Status` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`user_id`, `username`, `password_hash`, `email`, `created_at`, `updated_at`, `VerifyCode`, `Status`) VALUES
(1, 'iqbal', '123', 'idiscrus@gmail.com', '2024-06-14 16:18:10', '2024-06-14 16:18:51', '', 'Verified'),
(2, 'hikam', '123', 'jancoks2280@gmail.com', '2024-06-14 21:26:56', '2024-06-14 21:27:42', '', 'Verified');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `audit_log`
--
ALTER TABLE `audit_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indeks untuk tabel `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indeks untuk tabel `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indeks untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `audit_log`
--
ALTER TABLE `audit_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `items`
--
ALTER TABLE `items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;

--
-- AUTO_INCREMENT untuk tabel `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `audit_log`
--
ALTER TABLE `audit_log`
  ADD CONSTRAINT `audit_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Ketidakleluasaan untuk tabel `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `items_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Ketidakleluasaan untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

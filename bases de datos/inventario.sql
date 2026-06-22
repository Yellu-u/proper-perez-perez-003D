-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 02:21:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `id_inventario` bigint(20) NOT NULL,
  `fecha_actualizacion` date DEFAULT NULL,
  `pedido_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `stock_actual` int(11) NOT NULL,
  `stock_minimo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`id_inventario`, `fecha_actualizacion`, `pedido_id`, `producto_id`, `stock_actual`, `stock_minimo`) VALUES
(1, '2026-06-21', 10, 1, 52, 20),
(2, '2026-06-21', 10, 2, 68, 20),
(3, '2026-06-21', 8, 3, 96, 20),
(4, '2026-06-21', NULL, 4, 100, 20),
(5, '2026-06-21', NULL, 5, 100, 20),
(6, '2026-06-21', NULL, 6, 100, 20),
(7, '2026-06-21', NULL, 7, 100, 20),
(8, '2026-06-21', 4, 8, 65, 20),
(9, '2026-06-21', NULL, 9, 100, 20),
(10, '2026-06-21', NULL, 10, 100, 20),
(11, '2026-06-21', NULL, 11, 100, 20),
(12, '2026-06-21', NULL, 12, 100, 20),
(13, '2026-06-21', NULL, 13, 100, 20),
(14, '2026-06-21', NULL, 14, 100, 20),
(15, '2026-06-21', NULL, 15, 100, 20),
(16, '2026-06-21', NULL, 16, 100, 20),
(17, '2026-06-21', NULL, 17, 100, 20),
(18, '2026-06-21', NULL, 18, 100, 20),
(19, '2026-06-21', NULL, 19, 100, 20),
(20, '2026-06-21', NULL, 20, 100, 20),
(21, '2026-06-21', NULL, 21, 100, 20),
(22, '2026-06-21', NULL, 22, 100, 20),
(23, '2026-06-21', NULL, 23, 100, 20),
(24, '2026-06-21', NULL, 24, 100, 20),
(25, '2026-06-21', NULL, 25, 100, 20);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`id_inventario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `id_inventario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

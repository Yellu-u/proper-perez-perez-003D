-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 02:21:46
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
-- Base de datos: `db_reporte`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reportes`
--

CREATE TABLE `reportes` (
  `reporte_id` bigint(20) NOT NULL,
  `despachos_entregados` int(11) DEFAULT NULL,
  `despachos_pendientes` int(11) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_generacion` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `pagos_pagados` int(11) DEFAULT NULL,
  `pagos_pendientes` int(11) DEFAULT NULL,
  `total_bonificaciones` double DEFAULT NULL,
  `total_pedidos` int(11) DEFAULT NULL,
  `total_ventas` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reportes`
--

INSERT INTO `reportes` (`reporte_id`, `despachos_entregados`, `despachos_pendientes`, `fecha_fin`, `fecha_generacion`, `fecha_inicio`, `pagos_pagados`, `pagos_pendientes`, `total_bonificaciones`, `total_pedidos`, `total_ventas`) VALUES
(1, 0, 2, '2026-06-30', '2026-06-21', '2026-06-01', 2, 0, 7740, 4, 77400);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `reportes`
--
ALTER TABLE `reportes`
  ADD PRIMARY KEY (`reporte_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reportes`
--
ALTER TABLE `reportes`
  MODIFY `reporte_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

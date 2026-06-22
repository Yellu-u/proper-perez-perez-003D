-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 02:21:50
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
-- Estructura de tabla para la tabla `estadistica_vendedor`
--

CREATE TABLE `estadistica_vendedor` (
  `estadistica_id` bigint(20) NOT NULL,
  `nombre_vendedor` varchar(255) DEFAULT NULL,
  `promedio_venta` double DEFAULT NULL,
  `total_bonificaciones` double DEFAULT NULL,
  `total_pedidos` int(11) DEFAULT NULL,
  `total_ventas` double DEFAULT NULL,
  `vendedor_id` bigint(20) DEFAULT NULL,
  `reporte_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estadistica_vendedor`
--

INSERT INTO `estadistica_vendedor` (`estadistica_id`, `nombre_vendedor`, `promedio_venta`, `total_bonificaciones`, `total_pedidos`, `total_ventas`, `vendedor_id`, `reporte_id`) VALUES
(1, 'Felipe Gutiérrez', 12900, 1290, 1, 12900, 1, 1),
(2, 'Diego Campos', 64500, 6450, 3, 193500, 3, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `estadistica_vendedor`
--
ALTER TABLE `estadistica_vendedor`
  ADD PRIMARY KEY (`estadistica_id`),
  ADD KEY `FKbw5rdcchmmp17pjr5bc7spdj9` (`reporte_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `estadistica_vendedor`
--
ALTER TABLE `estadistica_vendedor`
  MODIFY `estadistica_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `estadistica_vendedor`
--
ALTER TABLE `estadistica_vendedor`
  ADD CONSTRAINT `FKbw5rdcchmmp17pjr5bc7spdj9` FOREIGN KEY (`reporte_id`) REFERENCES `reportes` (`reporte_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

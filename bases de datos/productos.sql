-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 04:12:16
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
-- Base de datos: `db_productos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `producto_id` bigint(20) NOT NULL,
  `precio` float NOT NULL,
  `producto_nombre` varchar(255) DEFAULT NULL,
  `linea_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`producto_id`, `precio`, `producto_nombre`, `linea_id`) VALUES
(1, 1500, 'MATIC', 1),
(2, 1800, 'CLORO 500', 1),
(3, 1200, 'CRISTAL CLEAN', 1),
(4, 2000, 'CAR WASH', 2),
(5, 2200, 'ECO WASH', 2),
(6, 1600, 'SANI HOME', 3),
(7, 1200, 'CLORO GEL', 3),
(8, 1200, 'LAVALOZA SUPER PLUS', 4),
(9, 1800, 'FULL EASY CLEAN', 4),
(10, 2500, 'ACRYLIC WAX 18', 5),
(11, 2400, 'EMUL WAX', 5),
(12, 1000, 'SUPER PLUS', 1),
(13, 1900, 'FULLGRASS', 1),
(14, 1300, 'CLORO GEL', 1),
(15, 2500, 'CERA SPRAY', 2),
(16, 2100, 'RENO BRIGHT', 2),
(17, 2300, 'MOTOR GRASS', 2),
(18, 1600, 'SANI CAR', 2),
(19, 1700, 'AROM FRESH CAR', 2),
(20, 1700, 'SARRO LIMP', 3),
(21, 1400, 'CLORO FOAM', 3),
(22, 1500, 'JABÓN PERLADO', 3),
(23, 1300, 'ALCOHOL GEL', 3),
(24, 1200, 'ALCOHOL DE LIMPIEZA', 3),
(25, 1600, 'AROM FRESH', 3),
(26, 2000, 'HIDROSAN', 4),
(27, 1900, 'REMANTEX', 4),
(28, 2100, 'DETERGENTE ROPA BLANCA', 4),
(29, 2200, 'DETERGENTE LÍQUIDO', 4),
(30, 2300, 'POWERFUL CLEAN', 4),
(31, 2200, 'REMOFLOOR', 5),
(32, 2000, 'AROMATIC FLOOR', 5),
(33, 2100, 'BRIGHT FLOOR', 5),
(34, 1900, 'FLOOR CLEANER', 5),
(35, 1700, 'ATRAPOL', 5),
(37, 4900, 'CACHUPIN SHAMPOO CANINO', 6),
(38, 3500, 'CACHUPIN ELIMINA OLORES', 6),
(39, 2700, 'CACHUPIN LIMPIA PATITAS', 6),
(40, 4200, 'CACHUPIN DESINFECTANTE PET', 6),
(41, 3900, 'CACHUPIN LIMPIADOR DE ORINA', 6),
(42, 5600, 'CACHUPIN SHAMPOO ANTIPULGAS', 6),
(43, 3100, 'CACHUPIN LIMPIADOR DE CAMAS', 6),
(44, 2800, 'CACHUPIN AROMA PET FRIENDLY', 6),
(45, 4500, 'CACHUPIN LIMPIADOR MULTISUPERFICIE', 6),
(46, 3300, 'CACHUPIN TOALLITAS HIGIÉNICAS', 6),
(47, 10000, 'PRODUCTO DE PRUEBA', 9);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`producto_id`),
  ADD KEY `FKsa44ixm23um6oyx11huxafclc` (`linea_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `producto_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `FKsa44ixm23um6oyx11huxafclc` FOREIGN KEY (`linea_id`) REFERENCES `linea` (`linea_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

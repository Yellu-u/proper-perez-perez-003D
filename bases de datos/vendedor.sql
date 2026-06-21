-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 04:11:58
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
-- Base de datos: `db_vendedor`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

CREATE TABLE `vendedor` (
  `vendedor_id` bigint(20) NOT NULL,
  `apellido_vendedor` varchar(255) DEFAULT NULL,
  `correo_vendedor` varchar(255) DEFAULT NULL,
  `nombre_vendedor` varchar(255) DEFAULT NULL,
  `run_vendedor` varchar(255) DEFAULT NULL,
  `telefono_vendedor` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vendedor`
--

INSERT INTO `vendedor` (`vendedor_id`, `apellido_vendedor`, `correo_vendedor`, `nombre_vendedor`, `run_vendedor`, `telefono_vendedor`) VALUES
(1, 'Gutiérrez', 'felipe.gutierrez@ventas.cl', 'Felipe', '18.345.678-1', '+56991112233'),
(2, 'Moreno', 'valeria.moreno@ventas.cl', 'Valeria', '19.456.789-2', '+56992223344'),
(3, 'Campos', 'diego.campos@ventas.cl', 'Diego', '17.567.890-3', '+56993334455'),
(4, 'Lagos', 'camila.lagos@ventas.cl', 'Camila', '20.678.901-4', '+56994445566'),
(5, 'Riquelme', 'matias.riquelme@ventas.cl', 'Matías', '16.789.012-5', '+56995556677'),
(6, 'Mendoza', 'fernanda.mendoza@ventas.cl', 'Fernanda', '21.890.123-6', '+56996667788'),
(7, 'Escobar', 'sebastian.escobar@ventas.cl', 'Sebastián', '18.901.234-7', '+56997778899'),
(8, 'Tapia', 'martina.tapia@ventas.cl', 'Martina', '19.012.345-8', '+56998889900'),
(9, 'Figueroa', 'vicente.figueroa@ventas.cl', 'Vicente', '17.123.456-9', '+56999990011'),
(10, 'Bravo', 'antonia.bravo@ventas.cl', 'Antonia', '20.234.567-0', '+56990001122'),
(12, 'Prueba', 'vend.pru@ventas.cl', 'Vendedor', '20.000.000-0', '+56900000122');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`vendedor_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  MODIFY `vendedor_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

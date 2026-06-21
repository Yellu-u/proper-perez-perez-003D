-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 04:12:37
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
-- Base de datos: `db_cliente`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `empresa_id` bigint(20) NOT NULL,
  `direccion_empresa` varchar(255) DEFAULT NULL,
  `razon_social` varchar(255) DEFAULT NULL,
  `telefono_empresa` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`empresa_id`, `direccion_empresa`, `razon_social`, `telefono_empresa`) VALUES
(1, 'Av. Apoquindo 4501, Las Condes, Santiago', 'TechNova SpA', '+56987654321'),
(2, 'Calle Blanco 245, Valparaíso', 'Mar Azul Logística Ltda.', '+56991234567'),
(3, 'Av. Libertador Bernardo O Higgins 1020, Santiago', 'Andes Retail S.A.', '+56999887766'),
(4, 'Camino El Alba 3344, Puente Alto', 'Constructora Horizonte Ltda.', '+56993456789'),
(5, 'Av. Grecia 876, Ñuñoa', 'Soluciones Médicas Vida SpA', '+56994561234'),
(6, 'Av. San Martín 1500, Viña del Mar', 'Turismo Pacífico S.A.', '+56995672345'),
(7, 'Ruta 5 Sur Km 250, Talca', 'AgroCampo Ltda.', '+56996783456'),
(8, 'Av. Costanera 2200, Puerto Montt', 'Pesquera Austral S.A.', '+56997894567'),
(9, 'Av. Alemania 455, Temuco', 'Innovatek SpA', '+56998905678'),
(10, 'Calle Prat 789, Antofagasta', 'Minería del Norte Ltda.', '+56999016789'),
(12, 'Esquina blanca 777, Maipú', 'Cachupin prueba S.A', '+569683736777');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`empresa_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `empresa_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

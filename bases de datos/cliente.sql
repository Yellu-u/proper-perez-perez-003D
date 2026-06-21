-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 04:12:33
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
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `cliente_id` bigint(20) NOT NULL,
  `apellido_cliente` varchar(255) DEFAULT NULL,
  `correo_cliente` varchar(255) DEFAULT NULL,
  `nombre_cliente` varchar(255) DEFAULT NULL,
  `run_cliente` varchar(255) DEFAULT NULL,
  `telefono_cliente` varchar(255) DEFAULT NULL,
  `empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`cliente_id`, `apellido_cliente`, `correo_cliente`, `nombre_cliente`, `run_cliente`, `telefono_cliente`, `empresa_id`) VALUES
(1, 'González', 'matias.gonzalez@technova.cl', 'Matías', '18.234.567-1', '+56981112233', 1),
(2, 'Rojas', 'camila.rojas@marazul.cl', 'Camila', '19.876.543-2', '+56982223344', 2),
(3, 'Pérez', 'sebastian.perez@andesretail.cl', 'Sebastián', '17.654.321-3', '+56983334455', 3),
(4, 'Muñoz', 'valentina.munoz@horizonte.cl', 'Valentina', '20.345.678-4', '+56984445566', 4),
(5, 'Silva', 'diego.silva@vidamedica.cl', 'Diego', '16.789.123-5', '+56985556677', 5),
(6, 'Contreras', 'fernanda.contreras@turismopacifico.cl', 'Fernanda', '21.123.456-6', '+56986667788', 6),
(7, 'Torres', 'benjamin.torres@agrocampo.cl', 'Benjamín', '18.999.888-7', '+56987778899', 7),
(8, 'Flores', 'antonia.flores@austral.cl', 'Antonia', '19.222.333-8', '+56988889900', 8),
(9, 'Vargas', 'vicente.vargas@innovatek.cl', 'Vicente', '17.111.222-9', '+56989990011', 9),
(10, 'Herrera', 'martina.herrera@minerianorte.cl', 'Martina', '20.777.666-0', '+56990001122', 10),
(11, 'Navarro', 'jorge.navarro@technova.cl', 'Jorge', '15.456.789-1', '+56971112233', 1),
(12, 'Castillo', 'paula.castillo@technova.cl', 'Paula', '22.345.678-2', '+56972223344', 1),
(13, 'Sepúlveda', 'cristobal.sepulveda@marazul.cl', 'Cristóbal', '18.765.432-3', '+56973334455', 2),
(14, 'Araya', 'javiera.araya@marazul.cl', 'Javiera', '19.654.321-4', '+56974445566', 2),
(15, 'Fuentes', 'felipe.fuentes@andesretail.cl', 'Felipe', '16.123.789-5', '+56975556677', 3),
(16, 'Morales', 'daniela.morales@andesretail.cl', 'Daniela', '21.456.123-6', '+56976667788', 3),
(17, 'Reyes', 'tomas.reyes@horizonte.cl', 'Tomás', '17.888.999-7', '+56977778899', 4),
(18, 'Soto', 'isidora.soto@horizonte.cl', 'Isidora', '20.222.444-8', '+56978889900', 4),
(19, 'Ramírez', 'alonso.ramirez@vidamedica.cl', 'Alonso', '18.111.555-9', '+56979990011', 5),
(20, 'Vega', 'constanza.vega@vidamedica.cl', 'Constanza', '19.333.666-0', '+56970001122', 5),
(21, 'Pino', 'gabriel.pino@turismopacifico.cl', 'Gabriel', '17.444.777-1', '+56971113344', 6),
(22, 'Cortés', 'sofia.cortes@turismopacifico.cl', 'Sofía', '22.111.888-2', '+56972224455', 6),
(23, 'Maldonado', 'nicolas.maldonado@agrocampo.cl', 'Nicolás', '16.555.999-3', '+56973335566', 7),
(24, 'Salinas', 'trinidad.salinas@agrocampo.cl', 'Trinidad', '18.666.111-4', '+56974446677', 7),
(25, 'Ortega', 'martin.ortega@austral.cl', 'Martín', '20.777.222-5', '+56975557788', 8),
(26, 'Henríquez', 'amparo.henriquez@austral.cl', 'Amparo', '19.888.333-6', '+56976668899', 8),
(27, 'Leiva', 'lucas.leiva@innovatek.cl', 'Lucas', '17.999.444-7', '+56977779900', 9),
(28, 'Carrasco', 'emilia.carrasco@innovatek.cl', 'Emilia', '21.000.555-8', '+56978880011', 9),
(29, 'Valdés', 'ignacio.valdes@minerianorte.cl', 'Ignacio', '18.101.606-9', '+56979991122', 10),
(30, 'Bustamante', 'florencia.bustamante@minerianorte.cl', 'Florencia', '20.202.707-0', '+56970002233', 10),
(32, 'Prueba', 'cachupin.prueba@cachupinSA.cl', 'Cachupin', '77.777.777-7', '+56977777778', 12);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`cliente_id`),
  ADD KEY `FKkbui05oidjdj4nb0283u4t319` (`empresa_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `cliente_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FKkbui05oidjdj4nb0283u4t319` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`empresa_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

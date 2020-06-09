-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 07-06-2020 a las 18:17:56
-- Versión del servidor: 5.7.11
-- Versión de PHP: 5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectodam`
--

CREATE DATABASE proyectodam;
-- --------------------------------------------------------
USE proyectodam;

--
-- Estructura de tabla para la tabla `centros`
--

CREATE TABLE `centros` (
  `id_centro` int(50) NOT NULL,
  `direccion_centro` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `centros`
--

INSERT INTO `centros` (`id_centro`, `direccion_centro`, `borradoLogico`) VALUES
(1, 'direccion centro 1', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contenidoorden`
--

CREATE TABLE `contenidoorden` (
  `id_contenidoorden` int(50) NOT NULL,
  `id_orden` int(50) NOT NULL,
  `id_producto` int(50) NOT NULL,
  `cantidadorden` int(50) NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;



-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes`
--

CREATE TABLE `ordenes` (
  `id_orden` int(50) NOT NULL,
  `centro_salida` int(50) NOT NULL,
  `centro_destino` int(50) NOT NULL,
  `fecha` date NOT NULL,
  `id_usuario` int(50) NOT NULL,
  `estado` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(50) NOT NULL,
  `nombre_producto` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `id_proveedor` int(50) NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `productos`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id_proveedor` int(50) NOT NULL,
  `nombre_proveedor` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `direccion_proveedor` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `proveedores`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock`
--

CREATE TABLE `stock` (
  `id_stock` int(50) NOT NULL,
  `id_centro` int(50) NOT NULL,
  `id_producto` int(50) NOT NULL,
  `cantidad` int(50) NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `stock`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(50) NOT NULL,
  `nombre_usuario` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `apellido1` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `apellido2` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `usuario` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `pass` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `id_centro` int(50) NOT NULL,
  `borradoLogico` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `apellido1`, `apellido2`, `usuario`, `pass`, `id_centro`, `borradoLogico`) VALUES
(1, 'eduardo', 'eduardo', 'eduardo', 'eduardo', md5('eduardo'), 1, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `centros`
--
ALTER TABLE `centros`
  ADD PRIMARY KEY (`id_centro`);

--
-- Indices de la tabla `contenidoorden`
--
ALTER TABLE `contenidoorden`
  ADD PRIMARY KEY (`id_contenidoorden`),
  ADD KEY `id_orden` (`id_orden`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD PRIMARY KEY (`id_orden`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_usuario_2` (`id_usuario`),
  ADD KEY `centro_salida` (`centro_salida`),
  ADD KEY `centro_destino` (`centro_destino`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `id_proveedor` (`id_proveedor`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id_proveedor`);

--
-- Indices de la tabla `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id_stock`),
  ADD KEY `id_producto` (`id_producto`),
  ADD KEY `id_centro` (`id_centro`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `id_centro` (`id_centro`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `centros`
--
ALTER TABLE `centros`
  MODIFY `id_centro` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `contenidoorden`
--
ALTER TABLE `contenidoorden`
  MODIFY `id_contenidoorden` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
--
-- AUTO_INCREMENT de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  MODIFY `id_orden` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;
--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id_proveedor` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT de la tabla `stock`
--
ALTER TABLE `stock`
  MODIFY `id_stock` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `contenidoorden`
--
ALTER TABLE `contenidoorden`
  ADD CONSTRAINT `contenidoorden_ibfk_1` FOREIGN KEY (`id_orden`) REFERENCES `ordenes` (`id_orden`) ON UPDATE CASCADE,
  ADD CONSTRAINT `contenidoorden_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`) ON DELETE CASCADE;

--
-- Filtros para la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD CONSTRAINT `ordenes_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ordenes_ibfk_2` FOREIGN KEY (`centro_salida`) REFERENCES `centros` (`id_centro`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ordenes_ibfk_3` FOREIGN KEY (`centro_destino`) REFERENCES `centros` (`id_centro`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`) ON UPDATE CASCADE,
  ADD CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`id_centro`) REFERENCES `centros` (`id_centro`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_centro`) REFERENCES `centros` (`id_centro`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

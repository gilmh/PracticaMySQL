PracticaMySQL
=============

Este proyecto es una aplicación de gestión basada en mySQL que cumple los siguientes puntos:

  -	La aplicación se conecta con la base de datos de forma transparente para el usuario de forma que los datos de conexión     pueden ser configurados desde la aplicación
  
  -	Da de alta nuevos registros de los objetos de la aplicación
  
  -	Da de baja registros de los objetos de la aplicación
  
  -	Modifica los registros de los objetos de la aplicación
  
  -	Añade alguna forma de búsqueda sobre los objetos de la aplicación
  
  -	Realiza funciones almacenadas de alguna forma útil
  
  -	Realiza procedimientos almacenados de alguna forma útil
  
  -	Usa transacciones para realizar operaciones complejas
  
  -	La aplicación puede configurarse para conectar con MySQL
  
  -	Tiene un sistema de privilegios para los usuarios que utilizan la aplicación, limitando el acceso a algunas partes


Hay 2 tipos de privilegios: administrador, que puede ver todo y usuario que está limitado a trabajar sobre las tablas

La base de datos sobre la que trabajamos es la siguiente (exportada directamente de phpmyadmin):

    -- phpMyAdmin SQL Dump
    -- version 4.0.4.1
    -- http://www.phpmyadmin.net
    --
    -- Servidor: 127.0.0.1
    -- Tiempo de generación: 08-02-2014 a las 15:01:39
    -- Versión del servidor: 5.6.11
    -- Versión de PHP: 5.5.3
    
    SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
    SET time_zone = "+00:00";
    
    
    /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
    /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
    /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
    /*!40101 SET NAMES utf8 */;
    
    --
    -- Base de datos: `hectordatos`
    --
    CREATE DATABASE IF NOT EXISTS `hectordatos` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
    USE `hectordatos`;
    
    DELIMITER $$
    --
    -- Procedimientos
    --
    CREATE DEFINER=`root`@`localhost` PROCEDURE `elimina_usuario`(nombre_usuario varchar(200))
    proc: begin
    	declare v_id_usuario int;
    	
    	select get_id_usuario(nombre_usuario) into v_id_usuario;
    	
    	delete from usuarios where id = v_id_usuario;	
    end proc$$
    
    --
    -- Funciones
    --
    CREATE DEFINER=`root`@`localhost` FUNCTION `get_id_usuario`(nombre_usuario varchar(200)) RETURNS int(11)
    begin
    	declare v_id_usuario int;
    	
    	select id into v_id_usuario from usuarios where nombre = nombre_usuario;
    	
    	return v_id_usuario;	
    end$$
    
    DELIMITER ;
    
    -- --------------------------------------------------------
    
    --
    -- Estructura de tabla para la tabla `batallas`
    --
    
    CREATE TABLE IF NOT EXISTS `batallas` (
      `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      `duracion` float DEFAULT '0',
      `historial` varchar(200) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
    
    -- --------------------------------------------------------
    
    --
    -- Estructura de tabla para la tabla `login`
    --
    
    CREATE TABLE IF NOT EXISTS `login` (
      `usuario` varchar(200) NOT NULL,
      `contrasena` varchar(200) NOT NULL,
      `nivel` varchar(200) NOT NULL,
      UNIQUE KEY `usuario` (`usuario`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    --
    -- Volcado de datos para la tabla `login`
    --
    
    INSERT INTO `login` (`usuario`, `contrasena`, `nivel`) VALUES
    ('pepe', 'nose', 'administrador'),
    ('usuario', 'nose', 'usuario');
    
    -- --------------------------------------------------------
    
    --
    -- Estructura de tabla para la tabla `personajes`
    --
    
    CREATE TABLE IF NOT EXISTS `personajes` (
      `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `nombre` varchar(200) DEFAULT NULL,
      `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      `clase` varchar(200) DEFAULT NULL,
      `raza` varchar(200) DEFAULT NULL,
      `id_usuario` int(10) unsigned NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `nombre` (`nombre`),
      KEY `id_usuario` (`id_usuario`)
    ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;
    
    --
    -- Volcado de datos para la tabla `personajes`
    --
    
    INSERT INTO `personajes` (`id`, `nombre`, `fecha`, `clase`, `raza`, `id_usuario`) VALUES
    (2, 'aaa', '2007-12-12 23:00:00', 'Paladin', 'Enano', 1);
    
    -- --------------------------------------------------------
    
    --
    -- Estructura de tabla para la tabla `usuarios`
    --
    
    CREATE TABLE IF NOT EXISTS `usuarios` (
      `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `nombre` varchar(100) NOT NULL,
      `correo` varchar(100) NOT NULL,
      `pais` varchar(100) NOT NULL,
      `fecha_nacimiento` date NOT NULL,
      `contrasena` varchar(100) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `nombre` (`nombre`)
    ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;
    
    --
    -- Volcado de datos para la tabla `usuarios`
    --
    
    INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `pais`, `fecha_nacimiento`, `contrasena`) VALUES
    (1, 'aa', 'aa', 'aa', '2006-12-13', 'aa');
    
    -- --------------------------------------------------------
    
    --
    -- Estructura de tabla para la tabla `usuarios_batallas`
    --
    
    CREATE TABLE IF NOT EXISTS `usuarios_batallas` (
      `id_usuario` int(10) unsigned NOT NULL,
      `id_batalla` int(10) unsigned NOT NULL,
      KEY `id_usuario` (`id_usuario`),
      KEY `id_batalla` (`id_batalla`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    --
    -- Restricciones para tablas volcadas
    --
    
    --
    -- Filtros para la tabla `personajes`
    --
    ALTER TABLE `personajes`
      ADD CONSTRAINT `personajes_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON         UPDATE CASCADE;
    
    --
    -- Filtros para la tabla `usuarios_batallas`
    --
    ALTER TABLE `usuarios_batallas`
      ADD CONSTRAINT `usuarios_batallas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE             CASCADE ON UPDATE CASCADE,
      ADD CONSTRAINT `usuarios_batallas_ibfk_2` FOREIGN KEY (`id_batalla`) REFERENCES `batallas` (`id`) ON DELETE             CASCADE ON UPDATE CASCADE;
    
    /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
    /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
    /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

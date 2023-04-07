-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema siget
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema siget
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `siget` DEFAULT CHARACTER SET utf8 ;
USE `siget` ;

-- -----------------------------------------------------
-- Table `siget`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`rol` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_rol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`persona` (
  `id_persona` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `primer_apellido` VARCHAR(45) NOT NULL,
  `segundo_apellido` VARCHAR(45) NULL,
  PRIMARY KEY (`id_persona`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `enabled` TINYINT NOT NULL,
  `rol_id` INT NOT NULL,
  `persona_id` INT NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_usuario_rol1_idx` (`rol_id` ASC) VISIBLE,
  INDEX `fk_usuario_persona1_idx` (`persona_id` ASC) VISIBLE,
  UNIQUE INDEX `persona_id_persona_UNIQUE` (`persona_id` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_rol1`
    FOREIGN KEY (`rol_id`)
    REFERENCES `siget`.`rol` (`id_rol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `siget`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`tipo_telefono`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`tipo_telefono` (
  `id_tipo_telefono` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_telefono`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`telefono`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`telefono` (
  `id_telefono` INT NOT NULL AUTO_INCREMENT,
  `telefono` VARCHAR(15) NOT NULL,
  `persona_id` INT NOT NULL,
  `tipo_telefono_id` INT NOT NULL,
  PRIMARY KEY (`id_telefono`),
  UNIQUE INDEX `id_correo_UNIQUE` (`id_telefono` ASC) VISIBLE,
  INDEX `fk_telefono_persona1_idx` (`persona_id` ASC) VISIBLE,
  INDEX `fk_telefono_tipo_telefono1_idx` (`tipo_telefono_id` ASC) VISIBLE,
  CONSTRAINT `fk_telefono_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `siget`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_telefono_tipo_telefono1`
    FOREIGN KEY (`tipo_telefono_id`)
    REFERENCES `siget`.`tipo_telefono` (`id_tipo_telefono`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`empleado` (
  `id_empleado` INT NOT NULL AUTO_INCREMENT,
  `numero_empleado` VARCHAR(20) NOT NULL,
  `persona_id` INT NOT NULL,
  PRIMARY KEY (`id_empleado`),
  INDEX `fk_empleado_persona1_idx` (`persona_id` ASC) VISIBLE,
  UNIQUE INDEX `persona_id_UNIQUE` (`persona_id` ASC) VISIBLE,
  UNIQUE INDEX `numero_empleado_UNIQUE` (`numero_empleado` ASC) VISIBLE,
  CONSTRAINT `fk_empleado_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `siget`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`dia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`dia` (
  `id_dia` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_dia`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`ventanilla`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`ventanilla` (
  `id_ventanilla` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_ventanilla`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`horario` (
  `id_horario` INT NOT NULL AUTO_INCREMENT,
  `hora_inicio` TIME NOT NULL,
  `hora_fin` TIME NOT NULL,
  `dia_id` INT NOT NULL,
  `ventanilla_id` INT NOT NULL,
  `empleado_id` INT NOT NULL,
  PRIMARY KEY (`id_horario`),
  INDEX `fk_horario_dia1_idx` (`dia_id` ASC) VISIBLE,
  INDEX `fk_horario_ventanilla1_idx` (`ventanilla_id` ASC) VISIBLE,
  INDEX `fk_horario_empleado1_idx` (`empleado_id` ASC) VISIBLE,
  CONSTRAINT `fk_horario_dia1`
    FOREIGN KEY (`dia_id`)
    REFERENCES `siget`.`dia` (`id_dia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_ventanilla1`
    FOREIGN KEY (`ventanilla_id`)
    REFERENCES `siget`.`ventanilla` (`id_ventanilla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `siget`.`empleado` (`id_empleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`tipo_servicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`tipo_servicio` (
  `id_tipo_servicio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_servicio`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`servicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`servicio` (
  `id_servicio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(500) NULL,
  `costo` DECIMAL(19,4) NULL,
  `tipo_servicio_id` INT NOT NULL,
  `estatus` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_servicio`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE,
  INDEX `fk_servicio_tipo_servicio1_idx` (`tipo_servicio_id` ASC) VISIBLE,
  CONSTRAINT `fk_servicio_tipo_servicio1`
    FOREIGN KEY (`tipo_servicio_id`)
    REFERENCES `siget`.`tipo_servicio` (`id_tipo_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`tipo_estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`tipo_estado` (
  `id_tipo_estado` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_estado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`estado` (
  `id_estado` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `tipo_estado_id` INT NOT NULL,
  `color` VARCHAR(20) NULL,
  PRIMARY KEY (`id_estado`),
  INDEX `fk_estado_tipo_estado1_idx` (`tipo_estado_id` ASC) VISIBLE,
  CONSTRAINT `fk_estado_tipo_estado1`
    FOREIGN KEY (`tipo_estado_id`)
    REFERENCES `siget`.`tipo_estado` (`id_tipo_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`documento` (
  `id_documento` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `servicio_id` INT NOT NULL,
  PRIMARY KEY (`id_documento`),
  INDEX `fk_documento_servicio1_idx` (`servicio_id` ASC) VISIBLE,
  CONSTRAINT `fk_documento_servicio1`
    FOREIGN KEY (`servicio_id`)
    REFERENCES `siget`.`servicio` (`id_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`division_academica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`division_academica` (
  `id_division_academica` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_division_academica`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`carrera`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`carrera` (
  `id_carrera` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `division_academica_id` INT NOT NULL,
  PRIMARY KEY (`id_carrera`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE,
  INDEX `fk_carrera_division_academica1_idx` (`division_academica_id` ASC) VISIBLE,
  CONSTRAINT `fk_carrera_division_academica1`
    FOREIGN KEY (`division_academica_id`)
    REFERENCES `siget`.`division_academica` (`id_division_academica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`alumno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`alumno` (
  `id_alumno` INT NOT NULL AUTO_INCREMENT,
  `matricula` VARCHAR(12) NOT NULL,
  `persona_id` INT NOT NULL,
  `carrera_id` INT NOT NULL,
  PRIMARY KEY (`id_alumno`),
  UNIQUE INDEX `matricula_UNIQUE` (`matricula` ASC) VISIBLE,
  INDEX `fk_alumno_persona1_idx` (`persona_id` ASC) VISIBLE,
  UNIQUE INDEX `persona_id_persona_UNIQUE` (`persona_id` ASC) VISIBLE,
  INDEX `fk_alumno_carrera1_idx` (`carrera_id` ASC) VISIBLE,
  CONSTRAINT `fk_alumno_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `siget`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alumno_carrera1`
    FOREIGN KEY (`carrera_id`)
    REFERENCES `siget`.`carrera` (`id_carrera`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`cita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`cita` (
  `id_cita` INT NOT NULL AUTO_INCREMENT,
  `fecha_cita` DATE NOT NULL,
  `hora_inicio` TIME NOT NULL,
  `hora_fin` TIME NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  `alumno_id` INT NOT NULL,
  `servicio_id` INT NOT NULL,
  `estado_id` INT NOT NULL,
  `ventanilla_id` INT NULL,
  `empleado_id` INT NULL,
  PRIMARY KEY (`id_cita`),
  INDEX `fk_cita_alumno1_idx` (`alumno_id` ASC) VISIBLE,
  INDEX `fk_cita_servicio1_idx` (`servicio_id` ASC) VISIBLE,
  INDEX `fk_cita_ventanilla1_idx` (`ventanilla_id` ASC) VISIBLE,
  INDEX `fk_cita_estado1_idx` (`estado_id` ASC) VISIBLE,
  INDEX `fk_cita_empleado1_idx` (`empleado_id` ASC) VISIBLE,
  CONSTRAINT `fk_cita_alumno1`
    FOREIGN KEY (`alumno_id`)
    REFERENCES `siget`.`alumno` (`id_alumno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_servicio1`
    FOREIGN KEY (`servicio_id`)
    REFERENCES `siget`.`servicio` (`id_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_ventanilla1`
    FOREIGN KEY (`ventanilla_id`)
    REFERENCES `siget`.`ventanilla` (`id_ventanilla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_estado1`
    FOREIGN KEY (`estado_id`)
    REFERENCES `siget`.`estado` (`id_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `siget`.`empleado` (`id_empleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`documento_anexo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`documento_anexo` (
  `id_documento_anexo` INT NOT NULL AUTO_INCREMENT,
  `documento_anexo` LONGBLOB NOT NULL,
  `cita_id` INT NOT NULL,
  `documento_id` INT NOT NULL,
  PRIMARY KEY (`id_documento_anexo`),
  INDEX `fk_documento_anexo_cita1_idx` (`cita_id` ASC) VISIBLE,
  INDEX `fk_documento_anexo_documento1_idx` (`documento_id` ASC) VISIBLE,
  CONSTRAINT `fk_documento_anexo_cita1`
    FOREIGN KEY (`cita_id`)
    REFERENCES `siget`.`cita` (`id_cita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_documento_anexo_documento1`
    FOREIGN KEY (`documento_id`)
    REFERENCES `siget`.`documento` (`id_documento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`pago` (
  `id_pago` INT NOT NULL AUTO_INCREMENT,
  `monto` DECIMAL(19,4) NOT NULL,
  `cita_id` INT NOT NULL,
  `estado_id` INT NOT NULL,
  PRIMARY KEY (`id_pago`),
  INDEX `fk_pago_cita1_idx` (`cita_id` ASC) VISIBLE,
  INDEX `fk_pago_estado1_idx` (`estado_id` ASC) VISIBLE,
  CONSTRAINT `fk_pago_cita1`
    FOREIGN KEY (`cita_id`)
    REFERENCES `siget`.`cita` (`id_cita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pago_estado1`
    FOREIGN KEY (`estado_id`)
    REFERENCES `siget`.`estado` (`id_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`comprobante_pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`comprobante_pago` (
  `id_comprobante_pago` INT NOT NULL AUTO_INCREMENT,
  `comprobante` LONGBLOB NOT NULL,
  `pago_id` INT NOT NULL,
  PRIMARY KEY (`id_comprobante_pago`),
  INDEX `fk_comprobante_pago_pago1_idx` (`pago_id` ASC) VISIBLE,
  UNIQUE INDEX `pago_id_pago_UNIQUE` (`pago_id` ASC) VISIBLE,
  CONSTRAINT `fk_comprobante_pago_pago1`
    FOREIGN KEY (`pago_id`)
    REFERENCES `siget`.`pago` (`id_pago`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`configuracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`configuracion` (
  `id_configuracion` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(45) NOT NULL,
  `value` VARCHAR(255) NULL,
  `imagen` BLOB NULL,
  PRIMARY KEY (`id_configuracion`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `siget`.`bitacora_acceso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `siget`.`bitacora_acceso` (
  `id_bitacora_acceso` INT NOT NULL AUTO_INCREMENT,
  `fecha_inicio` DATETIME NOT NULL,
  `fecha_fin` DATETIME NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id_bitacora_acceso`),
  INDEX `fk_bitacora_acceso_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_bitacora_acceso_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `siget`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO persona (id_persona, nombre, primer_apellido, segundo_apellido) 
VALUES 
(1, 'Juan', 'Pérez', 'García'),
(2, 'María', 'González', 'Fernández'),
(3, 'Pedro', 'Sánchez', 'López'),
(4, 'Luis', 'Hernández', 'Gómez'),
(5, 'Ana', 'Martínez', 'Rodríguez'),


(6, 'Pablo', 'García', 'Ramírez'),
(7, 'Lucía', 'Fernández', 'Pérez'),
(8, 'Carlos', 'López', 'González'),
(9, 'Elena', 'Gómez', 'Sánchez'),
(10, 'David', 'Rodríguez', 'Hernández');


INSERT INTO servicio (id_servicio, nombre, descripcion, costo, tipo_servicio_id, estatus) VALUES
  (1, 'Inscripción', 'Pago de inscripción para el ciclo escolar 2023', 2500.00, 1, 'Disponible'),
  (2, 'Reinscripción', 'Pago de reinscripción para el ciclo escolar 2023', 2000.00, 1, 'Disponible'),
  (3, 'Examen de admisión', 'Pago de examen de admisión para el ciclo escolar 2023', 500.00, 2, 'Disponible'),
  (4, 'Certificado', 'Pago de certificado para el ciclo escolar 2023', 1500.00, 3, 'Disponible'),
  (5, 'Trámite de titulación', 'Pago de trámite de titulación para el ciclo escolar 2023', 5000.00, 4, 'Disponible');
  
  INSERT INTO telefono (id_telefono, telefono, persona_id, tipo_telefono_id) VALUES
  (1, '1234567890', 1, 1),
  (2, '9876543210', 1, 2),
  (3, '5554443333', 2, 1),
  (4, '2221110000', 3, 2),
  (5, '3332221111', 4, 1);
(6, '4446789012', 3, 2),
  (7, '4447890123', 4, 1),
  (8, '4448901234', 4, 2),
  (9, '4449012345', 5, 1),
  (10, '4440123456', 5, 2);
  
  
  
  INSERT INTO horario (id_horario, hora_inicio, hora_fin, dia_id, ventanilla_id, empleado_id) VALUES
  (1, '08:00:00', '10:00:00', 1, 1, 1),
  (2, '10:00:00', '12:00:00', 1, 2, 2),
  (3, '12:00:00', '14:00:00', 1, 3, 3),
  (4, '08:00:00', '10:00:00', 2, 4, 4),
  (5, '10:00:00', '12:00:00', 2, 5, 5);
  
  
  
  INSERT INTO alumno (id_alumno, matricula, persona_id, carrera_id) VALUES
  (1, '20230001', 1, 1),
  (2, '20230002', 2, 1),
  (3, '20230003', 3, 2),
  (4, '20230004', 4, 2),
  (5, '20230005', 5, 3),
  (6, '20230006', 6, 3),
  (7, '20230007', 7, 4),
  (8, '20230008', 8, 4),
  (9, '20230009', 9, 5),
  (10, '20230010', 10, 5);
  
  
  
  INSERT INTO empleado (id_empleado, numero_empleado, persona_id) VALUES
  (1, 'E20230001', 1),
  (2, 'E20230002', 2),
  (3, 'E20230003', 3),
  (4, 'E20230004', 4),
  (5, 'E20230005', 5),
  (6, 'E20230006', 6),
  (7, 'E20230007', 7),
  (8, 'E20230008', 8),
  (9, 'E20230009', 9),
  (10, 'E20230010', 10);
  
  
  
  INSERT INTO cita (id_cita, fecha_cita, hora_inicio, hora_fin, fecha_registro, alumno_id, servicio_id, estado_id, ventanilla_id, empleado_id) VALUES
  (1, '2023-04-07', '10:00:00', '11:00:00', '2023-04-06', 1, 1, 1, 1, 1),
  (2, '2023-04-08', '11:00:00', '12:00:00', '2023-04-06', 2, 1, 1, 1, 1),
  (3, '2023-04-09', '12:00:00', '13:00:00', '2023-04-06', 3, 2, 1, 1, 2),
  (4, '2023-04-10', '13:00:00', '14:00:00', '2023-04-06', 4, 2, 1, 2, 2),
  (5, '2023-04-11', '14:00:00', '15:00:00', '2023-04-06', 5, 3, 1, 2, 3),
  (6, '2023-04-12', '15:00:00', '16:00:00', '2023-04-06', 6, 3, 1, 2, 3),
  (7, '2023-04-13', '16:00:00', '17:00:00', '2023-04-06', 7, 4, 1, 3, 4),
  (8, '2023-04-14', '17:00:00', '18:00:00', '2023-04-06', 8, 4, 1, 3, 4),
  (9, '2023-04-15', '18:00:00', '19:00:00', '2023-04-06', 9, 5, 1, 4, 5),
  (10, '2023-04-16', '19:00:00', '20:00:00', '2023-04-06', 10, 5, 1, 4, 5);
  
  
  
  





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

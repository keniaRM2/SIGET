-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sate
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sate
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sate` DEFAULT CHARACTER SET utf8 ;
USE `sate` ;

-- -----------------------------------------------------
-- Table `sate`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`rol` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_rol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`persona` (
  `id_persona` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `primer_apellido` VARCHAR(45) NOT NULL,
  `segundo_apellido` VARCHAR(45) NULL,
  PRIMARY KEY (`id_persona`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`usuario` (
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
    REFERENCES `sate`.`rol` (`id_rol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sate`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`tipo_telefono`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`tipo_telefono` (
  `id_tipo_telefono` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_telefono`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`tipo_correo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`tipo_correo` (
  `id_tipo_correo` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_correo`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`correo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`correo` (
  `id_correo` INT NOT NULL AUTO_INCREMENT,
  `correo` VARCHAR(100) NOT NULL,
  `tipo_correo_id` INT NOT NULL,
  `persona_id` INT NOT NULL,
  PRIMARY KEY (`id_correo`),
  UNIQUE INDEX `id_correo_UNIQUE` (`id_correo` ASC) VISIBLE,
  INDEX `fk_correo_tipo_correo1_idx` (`tipo_correo_id` ASC) VISIBLE,
  INDEX `fk_correo_persona1_idx` (`persona_id` ASC) VISIBLE,
  CONSTRAINT `fk_correo_tipo_correo1`
    FOREIGN KEY (`tipo_correo_id`)
    REFERENCES `sate`.`tipo_correo` (`id_tipo_correo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_correo_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sate`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`telefono`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`telefono` (
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
    REFERENCES `sate`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_telefono_tipo_telefono1`
    FOREIGN KEY (`tipo_telefono_id`)
    REFERENCES `sate`.`tipo_telefono` (`id_tipo_telefono`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`empleado` (
  `id_empleado` INT NOT NULL AUTO_INCREMENT,
  `numero_empleado` VARCHAR(20) NOT NULL,
  `persona_id` INT NOT NULL,
  PRIMARY KEY (`id_empleado`),
  INDEX `fk_empleado_persona1_idx` (`persona_id` ASC) VISIBLE,
  UNIQUE INDEX `persona_id_UNIQUE` (`persona_id` ASC) VISIBLE,
  UNIQUE INDEX `numero_empleado_UNIQUE` (`numero_empleado` ASC) VISIBLE,
  CONSTRAINT `fk_empleado_persona1`
    FOREIGN KEY (`persona_id`)
    REFERENCES `sate`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`dia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`dia` (
  `id_dia` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_dia`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`ventanilla`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`ventanilla` (
  `id_ventanilla` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_ventanilla`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`horario` (
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
    REFERENCES `sate`.`dia` (`id_dia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_ventanilla1`
    FOREIGN KEY (`ventanilla_id`)
    REFERENCES `sate`.`ventanilla` (`id_ventanilla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_horario_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sate`.`empleado` (`id_empleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`tipo_servicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`tipo_servicio` (
  `id_tipo_servicio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_servicio`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`servicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`servicio` (
  `id_servicio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(500) NULL,
  `costo` DECIMAL(19,4) NULL,
  `tipo_servicio_id` INT NOT NULL,
  PRIMARY KEY (`id_servicio`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE,
  INDEX `fk_servicio_tipo_servicio1_idx` (`tipo_servicio_id` ASC) VISIBLE,
  CONSTRAINT `fk_servicio_tipo_servicio1`
    FOREIGN KEY (`tipo_servicio_id`)
    REFERENCES `sate`.`tipo_servicio` (`id_tipo_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`servicio_ventanilla`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`servicio_ventanilla` (
  `servicio_id` INT NOT NULL,
  `ventanilla_id` INT NOT NULL,
  PRIMARY KEY (`servicio_id`, `ventanilla_id`),
  INDEX `fk_servicio_has_ventanilla_ventanilla1_idx` (`ventanilla_id` ASC) VISIBLE,
  INDEX `fk_servicio_has_ventanilla_servicio1_idx` (`servicio_id` ASC) VISIBLE,
  CONSTRAINT `fk_servicio_has_ventanilla_servicio1`
    FOREIGN KEY (`servicio_id`)
    REFERENCES `sate`.`servicio` (`id_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_servicio_has_ventanilla_ventanilla1`
    FOREIGN KEY (`ventanilla_id`)
    REFERENCES `sate`.`ventanilla` (`id_ventanilla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`tipo_estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`tipo_estado` (
  `id_tipo_estado` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_estado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`estado` (
  `id_estado` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `tipo_estado_id` INT NOT NULL,
  PRIMARY KEY (`id_estado`),
  INDEX `fk_estado_tipo_estado1_idx` (`tipo_estado_id` ASC) VISIBLE,
  CONSTRAINT `fk_estado_tipo_estado1`
    FOREIGN KEY (`tipo_estado_id`)
    REFERENCES `sate`.`tipo_estado` (`id_tipo_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`documento` (
  `id_documento` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `servicio_id` INT NOT NULL,
  PRIMARY KEY (`id_documento`),
  INDEX `fk_documento_servicio1_idx` (`servicio_id` ASC) VISIBLE,
  CONSTRAINT `fk_documento_servicio1`
    FOREIGN KEY (`servicio_id`)
    REFERENCES `sate`.`servicio` (`id_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`carrera`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`carrera` (
  `id_carrera` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_carrera`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`alumno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`alumno` (
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
    REFERENCES `sate`.`persona` (`id_persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alumno_carrera1`
    FOREIGN KEY (`carrera_id`)
    REFERENCES `sate`.`carrera` (`id_carrera`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`cita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`cita` (
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
    REFERENCES `sate`.`alumno` (`id_alumno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_servicio1`
    FOREIGN KEY (`servicio_id`)
    REFERENCES `sate`.`servicio` (`id_servicio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_ventanilla1`
    FOREIGN KEY (`ventanilla_id`)
    REFERENCES `sate`.`ventanilla` (`id_ventanilla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_estado1`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sate`.`estado` (`id_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `sate`.`empleado` (`id_empleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`documento_anexo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`documento_anexo` (
  `id_documento_anexo` INT NOT NULL AUTO_INCREMENT,
  `documento_anexo` LONGBLOB NOT NULL,
  `cita_id` INT NOT NULL,
  `documento_id` INT NOT NULL,
  PRIMARY KEY (`id_documento_anexo`),
  INDEX `fk_documento_anexo_cita1_idx` (`cita_id` ASC) VISIBLE,
  INDEX `fk_documento_anexo_documento1_idx` (`documento_id` ASC) VISIBLE,
  CONSTRAINT `fk_documento_anexo_cita1`
    FOREIGN KEY (`cita_id`)
    REFERENCES `sate`.`cita` (`id_cita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_documento_anexo_documento1`
    FOREIGN KEY (`documento_id`)
    REFERENCES `sate`.`documento` (`id_documento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`pago` (
  `id_pago` INT NOT NULL AUTO_INCREMENT,
  `monto` DECIMAL(19,4) NOT NULL,
  `cita_id` INT NOT NULL,
  `estado_id` INT NOT NULL,
  PRIMARY KEY (`id_pago`),
  INDEX `fk_pago_cita1_idx` (`cita_id` ASC) VISIBLE,
  INDEX `fk_pago_estado1_idx` (`estado_id` ASC) VISIBLE,
  CONSTRAINT `fk_pago_cita1`
    FOREIGN KEY (`cita_id`)
    REFERENCES `sate`.`cita` (`id_cita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pago_estado1`
    FOREIGN KEY (`estado_id`)
    REFERENCES `sate`.`estado` (`id_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`comprobante_pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`comprobante_pago` (
  `id_comprobante_pago` INT NOT NULL AUTO_INCREMENT,
  `comprobante` LONGBLOB NOT NULL,
  `pago_id` INT NOT NULL,
  PRIMARY KEY (`id_comprobante_pago`),
  INDEX `fk_comprobante_pago_pago1_idx` (`pago_id` ASC) VISIBLE,
  UNIQUE INDEX `pago_id_pago_UNIQUE` (`pago_id` ASC) VISIBLE,
  CONSTRAINT `fk_comprobante_pago_pago1`
    FOREIGN KEY (`pago_id`)
    REFERENCES `sate`.`pago` (`id_pago`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sate`.`configuracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sate`.`configuracion` (
  `id_configuracion` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(45) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_configuracion`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

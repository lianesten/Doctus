SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `DoctusDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `DoctusDB` ;

-- -----------------------------------------------------
-- Table `DoctusDB`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DoctusDB`.`usuario` (
  `nombres` VARCHAR(60) NOT NULL,
  `numeroId` VARCHAR(20) NOT NULL,
  `apellidos` VARCHAR(60) NOT NULL,
  `privilegio` INT(11) NOT NULL,
  `username` VARCHAR(60) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `token` VARCHAR(600) NULL,
  PRIMARY KEY (`numeroId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DoctusDB`.`actividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DoctusDB`.`actividad` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(512) NOT NULL,
  `numeroId` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
    FOREIGN KEY (`numeroId`)
    REFERENCES `DoctusDB`.`usuario` (`numeroId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DoctusDB`.`hora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DoctusDB`.`hora` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `hora` INT NOT NULL,
  `actividad` INT NOT NULL,
  PRIMARY KEY (`id`),
    FOREIGN KEY (`actividad`)
    REFERENCES `DoctusDB`.`actividad` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

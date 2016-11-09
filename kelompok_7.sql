SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`menus`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`menus` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `kode` VARCHAR(45) NULL ,
  `nama` VARCHAR(45) NULL ,
  `harga` FLOAT NULL ,
  `status` TINYINT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `kode_UNIQUE` (`kode` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Orders`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Orders` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `kode` VARCHAR(45) NULL ,
  `tanggal order` DATETIME NULL ,
  `tagihan` FLOAT NULL ,
  `pembayaran` FLOAT NULL ,
  `kembalian` FLOAT NULL ,
  `no meja` INT NULL ,
  `status` VARCHAR(45) NULL ,
  `id_customer` INT(10) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `kode_UNIQUE` (`kode` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order_details`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`order_details` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `kode_order` VARCHAR(45) NULL ,
  `kode_menu` VARCHAR(45) NULL ,
  `nama` VARCHAR(45) NULL ,
  `qty` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`customers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nama` VARCHAR(50) NULL ,
  `no_hp` VARCHAR(25) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

USE `mydb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

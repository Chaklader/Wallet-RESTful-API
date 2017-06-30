# -- MySQL Workbench Synchronization
# -- Generated: 2017-06-13 16:24
# -- Model: New Model
# -- Version: 1.0
# -- Project: Name of the project
# -- Author: Chaklader
#
# SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
# SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
# SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
#
# CREATE SCHEMA IF NOT EXISTS `wallet` DEFAULT CHARACTER SET utf8 ;
#
# CREATE TABLE IF NOT EXISTS `wallet`.`wallet_info` (
#   `id` INT(11) NOT NULL AUTO_INCREMENT,
#   `name` VARCHAR(50) NOT NULL,
#   `address` VARCHAR(40) NOT NULL,
#   PRIMARY KEY (`id`),
#   UNIQUE KEY (name),
#   UNIQUE KEY (address))
# ENGINE = InnoDB
# DEFAULT CHARACTER SET = utf8;
#
# CREATE TABLE IF NOT EXISTS `wallet`.`users` (
#   `name` VARCHAR(45) NOT NULL,
#   `id` INT(11) NOT NULL AUTO_INCREMENT,
#   PRIMARY KEY (`id`))
# ENGINE = InnoDB
# DEFAULT CHARACTER SET = utf8;
#
# INSERT INTO users VALUES ('default_user', null);
#
# CREATE TABLE IF NOT EXISTS `wallet`.`status` (
#   `id` INT(11) NOT NULL AUTO_INCREMENT,
#   `balance` FLOAT(11) NOT NULL,
#   `transaction` VARCHAR(90) NULL DEFAULT NULL,
#   `address` VARCHAR(90) NOT NULL,
#   `user_id` INT(11) NOT NULL,
#   `wallet_id` INT(11) NOT NULL,
#   PRIMARY KEY (`id`),
#   INDEX `id_idx` (`user_id` ASC),
#   CONSTRAINT `id`
#     FOREIGN KEY (`user_id`)
#     REFERENCES `wallet`.`users` (`id`)
#     ON DELETE NO ACTION
#     ON UPDATE NO ACTION)
# ENGINE = InnoDB
# DEFAULT CHARACTER SET = utf8;
#
#
# SET SQL_MODE=@OLD_SQL_MODE;
# SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
# SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
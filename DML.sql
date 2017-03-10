-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema schooldb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema schooldb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `schooldb` DEFAULT CHARACTER SET utf8 ;
USE `schooldb` ;

-- -----------------------------------------------------
-- Table `schooldb`.`building`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`building` (
  `building_id` INT NOT NULL  AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `admin` VARCHAR(45) NOT NULL,
  `rooms` INT NOT NULL,
  PRIMARY KEY (`building_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`room` (
  `room_id` INT NOT NULL  AUTO_INCREMENT,
  `occupancy` INT NOT NULL,
  `building_id` INT NOT NULL,
  PRIMARY KEY (`room_id`),
  INDEX `fk_room_building1_idx` (`building_id` ASC),
  CONSTRAINT `fk_room_building1`
    FOREIGN KEY (`building_id`)
    REFERENCES `schooldb`.`building` (`building_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`course` (
  `course_id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `credit` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`course_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`major`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`major` (
  `major_id` INT NOT NULL  AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`major_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`lecturer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`lecturer` (
  `lecturer_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `major_id` INT NOT NULL,
  PRIMARY KEY (`lecturer_id`),
  INDEX `fk_lecturer_major1_idx` (`major_id` ASC),
  CONSTRAINT `fk_lecturer_major1`
    FOREIGN KEY (`major_id`)
    REFERENCES `schooldb`.`major` (`major_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`student` (
  `student_id` INT NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `sex` VARCHAR(45) NOT NULL,
  `major_id` INT NOT NULL,
  `tutor_id` INT NOT NULL,
    `year` INT NOT NULL,
  PRIMARY KEY (`student_id`),
  INDEX `fk_student_major1_idx` (`major_id` ASC),
  INDEX `fk_student_lecturer1_idx` (`tutor_id` ASC),
  CONSTRAINT `fk_student_major1`
    FOREIGN KEY (`major_id`)
    REFERENCES `schooldb`.`major` (`major_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_lecturer1`
    FOREIGN KEY (`tutor_id`)
    REFERENCES `schooldb`.`lecturer` (`lecturer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`class` (
  `class_id` INT NOT NULL  AUTO_INCREMENT,
  `class_no` INT NOT NULL,
  `course_id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `major_id` INT NOT NULL,
  `year` INT NOT NULL,
  `credit` INT NOT NULL,
  `lecturer_id` INT NOT NULL,
  `person_max` INT NOT NULL,
  `opened` INT NOT NULL,
  `room_id` INT NULL,
  PRIMARY KEY (`class_id`),
  INDEX `fk_class_course1_idx` (`course_id` ASC),
  INDEX `fk_class_major1_idx` (`major_id` ASC),
  INDEX `fk_class_lecturer1_idx` (`lecturer_id` ASC),
  INDEX `fk_class_room1_idx` (`room_id` ASC),
  CONSTRAINT `fk_class_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `schooldb`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_major1`
    FOREIGN KEY (`major_id`)
    REFERENCES `schooldb`.`major` (`major_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_lecturer1`
    FOREIGN KEY (`lecturer_id`)
    REFERENCES `schooldb`.`lecturer` (`lecturer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `schooldb`.`room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`time`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`time` (
  `time_id` INT NOT NULL  AUTO_INCREMENT,
  `class_id` INT NOT NULL,
  `period` INT NOT NULL,
  `begin` varchar(45) NOT NULL,
  `end` varchar(45) NOT NULL,
  PRIMARY KEY (`time_id`),
  INDEX `fk_time_class1_idx` (`class_id` ASC),
  CONSTRAINT `fk_time_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `schooldb`.`class` (`class_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schooldb`.`credits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`credits` (
  `credits_id` INT NOT NULL  AUTO_INCREMENT,
  `student_id` INT NOT NULL,
  `class_id` INT NOT NULL,
  `grade` VARCHAR(45) NULL,
  PRIMARY KEY (`credits_id`),
  INDEX `fk_credits_student1_idx` (`student_id` ASC),
  INDEX `fk_credits_class1_idx` (`class_id` ASC),
  CONSTRAINT `fk_credits_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `schooldb`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_credits_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `schooldb`.`class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

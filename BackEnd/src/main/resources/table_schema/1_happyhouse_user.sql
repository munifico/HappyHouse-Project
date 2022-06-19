CREATE SCHEMA IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG` DEFAULT CHARACTER SET utf8 ;
USE `happyhouse_final_GUMI05_LJH_HJG` ;

-- -----------------------------------------------------
-- Table `happyhouse_final_GUMI05_LJH_HJG`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`member` ;
CREATE TABLE IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`member` (
  `userid` VARCHAR(16) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `userpwd` VARCHAR(16) NOT NULL,
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `joindate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB;

insert into member values ('ssafy', '김싸피', 'ssafy', 'ssafy@ssafy.com', now());
insert into member values ('admin', '관리자', 'admin', 'admin@ssafy.com', now());
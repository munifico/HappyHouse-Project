CREATE SCHEMA IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG` DEFAULT CHARACTER SET utf8 ;
USE `happyhouse_final_GUMI05_LJH_HJG` ;
drop table if exists tradeInfo;
drop table if exists savedCode;

-- -----------------------------------------------------
-- Table `happyhouse_final_GUMI05_LJH_HJG`.`tradeInfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`tradeInfo` (
  `tradeCode` VARCHAR(20) NOT NULL,
  `dong` VARCHAR(20) NOT NULL,
  `aptName` VARCHAR(45) NOT NULL,
  `price` int NULL,
  `size` double NULL,
  `dealDate` VARCHAR(30) NULL,
  `address` VARCHAR(100) NULL,
  `buildYear` int NULL)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `happyhouse_final_GUMI05_LJH_HJG`.`savedCode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`savedCode` (
  `tradeCode` VARCHAR(20) NOT NULL)
ENGINE = InnoDB;

-- insert into tradeInfo values("100231", "무슨동", "아트이름", 10000, 32.133, "2011-31-31", "대구광역시 북구 읍내동 칠곡초등학교", 2022);
-- insert into tradeInfo values("100231", "무슨동", "아트이름", 1000, 32.13, "2011-3-31", "대구광역시 북구 읍내동 칠곡초등학교", 2022);
-- insert into savedCode values("100231");
-- select * from tradeInfo where tradeCode = '100231';
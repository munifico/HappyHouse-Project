CREATE SCHEMA IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG` DEFAULT CHARACTER SET utf8 ;
USE `happyhouse_final_GUMI05_LJH_HJG` ;

-- -----------------------------------------------------
-- Table `happyhouse_final_GUMI05_LJH_HJG`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`review` ;
CREATE TABLE IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`review` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `regtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`))
ENGINE = InnoDB;

insert into review(userid, subject, content) values('익명이', '요즘 집값 너무 비싸다 ㅠㅠ', '내집 마련 언제 해 ㅠㅠㅠㅠㅠ');
insert into review(userid, subject, content) values('지방이', '프로젝트 너무 힘들다 ㅠ.ㅠ', '살려줘어 따따따 따 따 땃 따따따');
insert into review(userid, subject, content) values('서울이', '그래도 끝이 보여!!!', '끝났다곤 안 했다!!');

-- -----------------------------------------------------
-- Table `happyhouse_final_GUMI05_LJH_HJG`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`notice` ;
CREATE TABLE IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`notice` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `regtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`))
ENGINE = InnoDB;

insert into notice(userid, subject, content) values('admin', '[공지] 2학기 수업 방식', '2학기 때는 대면수업을 격일로 실시합니다. 각 반의 조별 대면수업 일자를 확인해주세요.');
insert into notice(userid, subject, content) values('admin', '[공지] 프로젝트 마감 기한', '럭키 7기 여러분들께서는 2022년 05월 27일까지 프로젝트 완성 파일과 문서들을 제출해주세요.');
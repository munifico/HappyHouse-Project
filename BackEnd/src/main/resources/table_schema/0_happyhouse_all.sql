-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema happyhouse_final_GUMI05_LJH_HJG
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema happyhouse_final_GUMI05_LJH_HJG
-- -----------------------------------------------------
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

-- -----------------------------------------------------
-- Table `happyhouse_final_GUMI05_LJH_HJG`.`qna`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`qna` ;

CREATE TABLE IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG`.`qna` (
  `qnano` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(16) NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `answer` VARCHAR(2000) NULL DEFAULT NULL,
  `regtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`qnano`))
ENGINE = InnoDB;

insert into qna(userid, title, content, answer) values('ssafy', '서비스 이용 질문', '서비스 이용 어떻게 해요?', '상단 Manual 메뉴를 참고해서 이용해주세요.');
insert into qna(userid, title, content, answer) values('admin', '최근 매물 질문', '매물 검색 어떻게 해요?', '상단 Manual 메뉴를 참고해서 검색해주세요.');

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

select * from tradeInfo;
select * from savedCode;
select * from qna;
select * from review;
select * from notice;
select * from member;
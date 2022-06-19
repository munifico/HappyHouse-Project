CREATE SCHEMA IF NOT EXISTS `happyhouse_final_GUMI05_LJH_HJG` DEFAULT CHARACTER SET utf8 ;
USE `happyhouse_final_GUMI05_LJH_HJG` ;

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
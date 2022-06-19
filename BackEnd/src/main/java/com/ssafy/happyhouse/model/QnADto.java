package com.ssafy.happyhouse.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnADto {
	private Integer qnano;
	private String userid;
	private String title;
	private String content;
	private String answer;
	private String regtime;
	public Integer getQnano() {
		return qnano;
	}
	public void setQnano(Integer qnano) {
		this.qnano = qnano;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	
}

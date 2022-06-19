package com.ssafy.happyhouse.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeParameterDto {
	private String sido;
	private String gugun;
	private String regionCode;
	private String dong;
	private String year;
	private String month;
	private String searchword;
	

	public String getSido() {
		return sido;
	}


	public void setSido(String sido) {
		this.sido = sido;
	}


	public String getGugun() {
		return gugun;
	}


	public void setGugun(String gugun) {
		this.gugun = gugun;
	}


	public String getRegionCode() {
		return regionCode;
	}


	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}


	public String getDong() {
		return dong;
	}


	public void setDong(String dong) {
		this.dong = dong;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getSearchword() {
		return searchword;
	}


	public void setSearchword(String searchword) {
		this.searchword = searchword;
	}


	public String getTradeCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.regionCode).append(this.year).append(this.month);
		return sb.toString();
	}
}

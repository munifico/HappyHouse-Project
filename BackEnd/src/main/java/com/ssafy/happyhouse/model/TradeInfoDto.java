package com.ssafy.happyhouse.model;

public class TradeInfoDto {
	private String tradeCode;
	private String dong;
	private String aptName;
	private int price;
	private double size;
	private String dealDate;
	private String address;
	private int buildYear;
	
	public TradeInfoDto() {
		super();
	}

	public TradeInfoDto(String tradeCode, String dong, String aptName, int price, double size, 
			String dealDate, String address, int buildYear) {
		super();
		this.tradeCode = tradeCode;
		this.dong = dong;
		this.aptName = aptName;
		this.price = price;
		this.size = size;
		this.dealDate = dealDate;
		this.address = address;
		this.buildYear = buildYear;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getAptName() {
		return aptName;
	}

	public void setAptName(String aptName) {
		this.aptName = aptName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	
	
}

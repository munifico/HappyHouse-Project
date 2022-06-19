package com.ssafy.happyhouse.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ssafy.happyhouse.model.TradeInfoDto;
import com.ssafy.happyhouse.model.TradeParameterDto;

public interface TradeInfoService {
	void enrollInfo(TradeInfoDto dto) throws SQLException;
	boolean checkInfo(String tradeCode) throws SQLException;
	List<TradeInfoDto> getTradeList(TradeParameterDto params) throws SQLException, IOException, SAXException, ParserConfigurationException;
	void enrollCode(String tradeCode) throws SQLException;
	String dtoListToXMLString(List<TradeInfoDto> list) throws IOException, ParserConfigurationException;
}

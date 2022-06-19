package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.TradeInfoDto;
import com.ssafy.happyhouse.model.TradeParameterDto;

@Mapper
public interface TradeMapper {
	void enrollInfo(TradeInfoDto dto) throws SQLException;
	boolean checkInfo(String tradeCode) throws SQLException;
	List<TradeInfoDto> getTradeList(TradeParameterDto params) throws SQLException;
	void enrollCode(String tradeCode) throws SQLException;
}

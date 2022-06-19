package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.ssafy.happyhouse.model.TradeInfoDto;
import com.ssafy.happyhouse.model.TradeParameterDto;
import com.ssafy.happyhouse.service.TradeInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/tradeInfo")
public class TradeInfo_RestController {
	@Autowired
	TradeInfoService tradeInfoService;

	@ApiOperation(value = "비어있는 리스트를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<TradeInfoDto>> getInfoList() throws IOException, ParserConfigurationException {
		return new ResponseEntity<>(new ArrayList<TradeInfoDto>(), HttpStatus.OK);
	}

	@ApiOperation(value = "검색 조건에 따른 검색 결과를 반환한다. 단, 검색되지 않은 것이면 API요청 후 DB 저장까지 한다.", response = List.class)
	@GetMapping("/{finalCode}/{sido}/{gugun}/{dong}/{year}/{month}/{searchword}")
	@Transactional
	public ResponseEntity<List<TradeInfoDto>> getInfoList(@PathVariable String finalCode, @PathVariable String sido,
			@PathVariable String gugun, @PathVariable String dong, @PathVariable String year,
			@PathVariable String month, @PathVariable String searchword) throws SQLException, IOException, ParserConfigurationException, SAXException {
		/**
		 * 검색 조건에 따른 검색 결과 반환 메소드
		 * 5자리 finalCode
		 * 시도
		 * 구군
		 * 동 (없으면 null)
		 * 거래년도
		 * 거래월
		 * 을 입력받아서
		 * 해당 조건에 맞는 데이터를 DB에서 반환한다.
		 * 단, 검색 기록이 없는 경우에는 API요청으로 DB에 저장하고 데이터를 반환한다.
		 */
		// 입력받은 정보로 파라미터를 편집한다.
		TradeParameterDto params = new TradeParameterDto();
		params.setRegionCode(finalCode);
		params.setSido(sido);
		params.setGugun(gugun);
		if (!dong.equals("동선택")) {
			params.setDong(dong);
		}
		params.setYear(year);
		params.setMonth(month);
		params.setSearchword(searchword.trim());
		if (params.getSearchword().equals("_")) {
			params.setSearchword("");
		}
		return new ResponseEntity<List<TradeInfoDto>>(tradeInfoService.getTradeList(params), HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색 결과 DtoList를 XML로 변환받는다.", response = String.class)
	@PostMapping("/getXml")
	public ResponseEntity<String> getXML(@RequestParam List<TradeInfoDto> infolist) throws IOException, ParserConfigurationException{
		return new ResponseEntity<String>(tradeInfoService.dtoListToXMLString(infolist), HttpStatus.OK);
	}

}

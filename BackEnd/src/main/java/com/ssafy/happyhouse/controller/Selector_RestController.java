package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.RegionCodeDto;
import com.ssafy.happyhouse.service.SelectorService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/selector")
public class Selector_RestController {
	@Autowired
	private SelectorService selectorService;

	@ApiOperation(value = "전체 시/도 리스트를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<RegionCodeDto>> getSidoList() throws IOException{
		return new ResponseEntity<List<RegionCodeDto>>(selectorService.getSidoList(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택한 시/도의 구/군 리스트를 반환한다.", response = List.class)
	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<List<RegionCodeDto>> getGugunList(@PathVariable String sidoCode) throws IOException{
		return new ResponseEntity<List<RegionCodeDto>>(selectorService.getGugunList(sidoCode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택한 구/군의 동 리스트를 반환한다.", response = List.class)
	@GetMapping("/dong/{gugunCode}")
	public ResponseEntity<List<RegionCodeDto>> getDongList(@PathVariable String gugunCode) throws IOException{
		return new ResponseEntity<List<RegionCodeDto>>(selectorService.getDongList(gugunCode), HttpStatus.OK);
	}
	
}

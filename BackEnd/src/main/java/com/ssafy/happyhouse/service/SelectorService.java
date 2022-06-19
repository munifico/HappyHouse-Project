package com.ssafy.happyhouse.service;

import java.io.IOException;
import java.util.List;

import com.ssafy.happyhouse.model.RegionCodeDto;

public interface SelectorService {
	List<RegionCodeDto> getSidoList() throws IOException;
	List<RegionCodeDto> getGugunList(String sidoCode) throws IOException;
	List<RegionCodeDto> getDongList(String gugunCode) throws IOException;
}

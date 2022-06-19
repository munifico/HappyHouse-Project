package com.ssafy.happyhouse.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.RegionCodeDto;

@Service
public class SelectorServiceImpl implements SelectorService{
	
	@Override
	public List<RegionCodeDto> getSidoList() throws IOException {
		return getSido();
	}
	
	@Override
	public List<RegionCodeDto> getGugunList(String sidoCode) throws IOException {
		if (Long.parseLong(sidoCode) == 0) {
			// 시도선택을 선택한 경우
			RegionCodeDto dto = new RegionCodeDto();
			dto.setGugunCode("0000000000");
			dto.setGugunName("구군선택");
			List<RegionCodeDto> list = new ArrayList<>();
			list.add(dto);
			return list;
		}
		return getGugun(sidoCode);
	}
	
	@Override
	public List<RegionCodeDto> getDongList(String gugunCode) throws IOException {
		if (Long.parseLong(gugunCode) == 0) {
			// 시도선택을 선택한 경우
			RegionCodeDto dto = new RegionCodeDto();
			dto.setDongCode("0000000000");
			dto.setDongName("동선택");
			List<RegionCodeDto> list = new ArrayList<>();
			list.add(dto);
			return list;
		}
		return getDong(gugunCode);
	}

	private List<RegionCodeDto> getSido() throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes");
		urlBuilder.append("?regcode_pattern=*00000000");
//		System.out.println(urlBuilder.toString());
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());
		
		List<RegionCodeDto> sidoList = new ArrayList<>();
		RegionCodeDto emptyDto = new RegionCodeDto();
		emptyDto.setSidoCode("0000000000");
		emptyDto.setSidoName("시도선택");
		sidoList.add(emptyDto);
		JSONObject jsonObject = new JSONObject(sb.toString());
		JSONArray resultArray = (JSONArray) jsonObject.get("regcodes");
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject result = (JSONObject) resultArray.get(i);
			RegionCodeDto tempDto = new RegionCodeDto();
			tempDto.setSidoCode(result.getString("code"));
			tempDto.setSidoName(result.getString("name"));
			sidoList.add(tempDto);
		}
		return sidoList;
	}
	
	private List<RegionCodeDto> getGugun(String sidoCode) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes");
		urlBuilder.append("?regcode_pattern=")
				.append(sidoCode.substring(0, 2)).append("*00000");
//		System.out.println(urlBuilder.toString());
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());
		
		List<RegionCodeDto> gugunList = new ArrayList<>();
		RegionCodeDto emptyDto = new RegionCodeDto();
		emptyDto.setGugunCode("0000000000");
		emptyDto.setGugunName("구군선택");
		gugunList.add(emptyDto);
		JSONObject jsonObject = new JSONObject(sb.toString());
		JSONArray resultArray = (JSONArray) jsonObject.get("regcodes");
		// 서울특별시와 같은 경우 nameLen이 1이다.
		// xx도 xx시 같은 경우 nameLen이 2이다. (사실 이건 없긴 하다.
		int nameLen = ((JSONObject) resultArray.get(0)).getString("name").split(" ").length;
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject result = (JSONObject) resultArray.get(i);
			String[] gugunNameSplit = result.getString("name").split(" ");
			String gugunName = "";
			for(int j = nameLen; j < gugunNameSplit.length; j++) {
				gugunName += gugunNameSplit[j] + " ";
			}
			gugunName = gugunName.trim();
			if (gugunName.equals("")) {
				continue;
			}
			RegionCodeDto tempDto = new RegionCodeDto();
			tempDto.setGugunCode(((JSONObject) resultArray.get(i)).getString("code"));
			tempDto.setGugunName(gugunName);
			gugunList.add(tempDto);
		}
		return gugunList;
	}
	
	private List<RegionCodeDto> getDong(String gugunCode) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes");
		urlBuilder.append("?regcode_pattern=")
				.append(gugunCode.substring(0, 4)).append("*");
//		System.out.println(urlBuilder.toString());
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());
		
		List<RegionCodeDto> dongList = new ArrayList<>();
		RegionCodeDto emptyDto = new RegionCodeDto();
		emptyDto.setDongCode("0000000000");
		emptyDto.setDongName("동선택");
		dongList.add(emptyDto);
		JSONObject jsonObject = new JSONObject(sb.toString());
		JSONArray resultArray = (JSONArray) jsonObject.get("regcodes");
		// 서울특별시 용산구 와 같은 경우 nameLen이 2이다.
		// xx광역시 yy yy구 와 같은 경우 nameLen이 3이다.
		int nameLen = ((JSONObject) resultArray.get(0)).getString("name").split(" ").length;
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject result = (JSONObject) resultArray.get(i);
			String[] dongNameSplit = result.getString("name").split(" ");
			String dongName = "";
			for(int j = nameLen; j < dongNameSplit.length; j++) {
				dongName += dongNameSplit[j] + " ";
			}
			dongName = dongName.trim();
			if (dongName.equals("")) {
				continue;
			}
			RegionCodeDto tempDto = new RegionCodeDto();
			tempDto.setDongCode(((JSONObject) resultArray.get(i)).getString("code"));
			tempDto.setDongName(dongName);
			dongList.add(tempDto);
		}
		return dongList;
	}
	
}

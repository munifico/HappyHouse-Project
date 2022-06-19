package com.ssafy.happyhouse.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ssafy.happyhouse.model.TradeInfoDto;
import com.ssafy.happyhouse.model.TradeParameterDto;
import com.ssafy.happyhouse.model.mapper.TradeMapper;

@Service
public class TradeInfoServiceImpl implements TradeInfoService {

	@Autowired
	private TradeMapper tradeMapper;

	@Transactional
	@Override
	public void enrollInfo(TradeInfoDto dto) throws SQLException {
		tradeMapper.enrollInfo(dto);
	}

	@Transactional
	@Override
	public void enrollCode(String tradeCode) throws SQLException {
		tradeMapper.enrollCode(tradeCode);
	}

	@Override
	@Transactional
	public List<TradeInfoDto> getTradeList(TradeParameterDto params)
			throws SQLException, IOException, SAXException, ParserConfigurationException {
		if (!checkInfo(params.getTradeCode())) {
			// 검색한 적이 없다면 API에 요청해서 데이터를 갖고온 다음 저장하고 검색 기록을 저장한다.
			List<TradeInfoDto> list = getNewInfo(params);
			// 받아온 정보를 저장한다.
			for (TradeInfoDto dto : list) {
				tradeMapper.enrollInfo(dto);
			}
			// 검색 기록을 저장한다.
			enrollCode(params.getTradeCode());
		}
		return tradeMapper.getTradeList(params);
	}

	@Override
	public boolean checkInfo(String tradeCode) throws SQLException {
		return tradeMapper.checkInfo(tradeCode);
	}

	private List<TradeInfoDto> getNewInfo(TradeParameterDto params)
			throws IOException, SAXException, ParserConfigurationException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*
																															 * URL
																															 */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=SvZ3siClSK9VL8vLv1r2PD4J/QJtZdZIxT4Ktyl33eXa4Rv37LbBfvojAOYUNFC83hRl2ZhheqeE6xeYjaPVqg=="); /*
																												 * Service
																												 * Key
																												 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("1000", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "="
				+ URLEncoder.encode(params.getRegionCode(), "UTF-8")); /* 지역코드 */
		urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "="
				+ URLEncoder.encode(params.getYear() + params.getMonth(), "UTF-8")); /* 계약월 */
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
		return XMLStringToDtoList(sb.toString(), params);
	}

	private List<TradeInfoDto> XMLStringToDtoList(String XML, TradeParameterDto params)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(XML)));

		List<TradeInfoDto> list = new ArrayList<>();
		NodeList nodelist = document.getElementsByTagName("item");
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);
			Element element = (Element) node;
			TradeInfoDto dto = new TradeInfoDto();
			dto.setTradeCode(
					getValue("지역코드", element).trim() + getValue("년", element) + String.format("%02d", Integer.parseInt(getValue("월", element))));
			dto.setDong(getValue("법정동", element).trim());
			dto.setAptName(getValue("아파트", element).trim());
			dto.setPrice(Integer.parseInt(getValue("거래금액", element).trim().replaceAll("\\,", "")));
			dto.setSize(Double.parseDouble(getValue("전용면적", element)));
			dto.setDealDate(getValue("년", element) + "-" + String.format("%02d", Integer.parseInt(getValue("월", element))) + "-"
					+ String.format("%02d", Integer.parseInt(getValue("일", element))));
			String address = params.getSido() + " " + params.getGugun() + " " + getValue("법정동", element).trim() + " "
					+ getValue("도로명", element).trim();
			if (!getValue("도로명건물본번호코드", element).trim().equals("00000")) {
				address += Integer.parseInt(getValue("도로명건물본번호코드", element).trim());
				if (!getValue("도로명건물부번호코드", element).trim().equals("00000")) {
					address += " " + Integer.parseInt(getValue("도로명건물부번호코드", element).trim());
				}
			}
			dto.setAddress(address);
			dto.setBuildYear(Integer.parseInt(getValue("건축년도", element)));
			list.add(dto);
		}
		return list;
	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	@Override
	public String dtoListToXMLString(List<TradeInfoDto> list) throws IOException, ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("data");
		doc.appendChild(rootElement);

		for (TradeInfoDto dto : list) {
			Element itemElement = doc.createElement("item");
			rootElement.appendChild(itemElement);
			Element e1 = doc.createElement("tradeCode");
			e1.setTextContent(dto.getTradeCode());
			itemElement.appendChild(e1);
			Element e2 = doc.createElement("dong");
			e2.setTextContent(dto.getDong());
			itemElement.appendChild(e2);
			Element e3 = doc.createElement("aptName");
			e3.setTextContent(dto.getAptName());
			itemElement.appendChild(e3);
			DecimalFormat df = new DecimalFormat("###,###");
			Element e4 = doc.createElement("price");
			e4.setTextContent(df.format(dto.getPrice()));
			itemElement.appendChild(e4);
			Element e5 = doc.createElement("size");
			e5.setTextContent(Double.toString(dto.getSize()));
			itemElement.appendChild(e5);
			Element e6 = doc.createElement("dealDate");
			e6.setTextContent(dto.getDealDate());
			itemElement.appendChild(e6);
			Element e7 = doc.createElement("address");
			e7.setTextContent(dto.getAddress());
			itemElement.appendChild(e7);
			Element e8 = doc.createElement("buildYear");
			e8.setTextContent(Integer.toString(dto.getBuildYear()));
			itemElement.appendChild(e8);
		}
		return toString(doc);
	}

	private static String toString(Document newDoc) {
		try {
			DOMSource domSource = new DOMSource(newDoc);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}

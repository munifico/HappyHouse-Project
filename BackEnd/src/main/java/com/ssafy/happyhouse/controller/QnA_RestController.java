package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.QnADto;
import com.ssafy.happyhouse.service.QnAService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/board/qna")
public class QnA_RestController {
	@Autowired
	private QnAService qnaService;

	@ApiOperation(value = "전체 QnA 리스트를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<QnADto>> list(@RequestParam Map<String, String> map) throws Exception {
		
		String spp = map.get("spp"); // size per page (페이지당 글갯수)
		map.put("spp", spp != null ? spp : "10");
		List<QnADto> list = qnaService.listQnA(map);
//		PageNavigation pageNavigation = noticeService.makePageNavigation(map);
		return new ResponseEntity<List<QnADto>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "QnA를 작성한다.(임시로 URL로 userid를 받는다. 추후 시큐리티 적용 예정)")
	@PostMapping("/write")
	@Transactional
	public ResponseEntity<String> writeQnA(@RequestBody QnADto qnaDto) throws SQLException {
		if(qnaService.registerQnA(qnaDto) > 0) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "QnA를 조회한다.", response = QnADto.class)
	@GetMapping("/get/{qnaNo}")
	public QnADto getQnAbyNo(@PathVariable String qnaNo) throws NumberFormatException, SQLException {
		return qnaService.getQnAbyNo(Integer.parseInt(qnaNo));
	}

	@ApiOperation(value = "QnA를 수정하거나 답변을 단다.")
	@PutMapping("/modify")
	@Transactional
	public ResponseEntity<String> modifyQnA(@RequestBody QnADto qnaDto) throws SQLException {
		if(qnaService.updateQnA(qnaDto) > 0) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "QnA를 삭제한다.")
	@DeleteMapping("/delete/{qnaNo}")
	@Transactional
	public ResponseEntity<String> updateQnA(@PathVariable int qnaNo) throws SQLException {
		if(qnaService.deleteQnAbyNo(qnaNo) > 0) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

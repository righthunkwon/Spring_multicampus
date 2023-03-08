package com.multi.campus.controller;

import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;
import com.multi.campus.service.BoardService;

// Controller : ��� ��� ��� �Ұ�, ��ũ��Ʈ �ʿ� �� jsp ������ ���� �� ����
// RestController : ����Ʈ�� �鿣�忡�� ����� �� �ִ� ��� ����, ��ȯ���� String���� �ϸ� �������� ���ϸ��� �ƴ϶� ������ �������� ó���Ѵ�
@RestController
@RequestMapping("/board") // ���� �� ��� �ּҿ� /board�� ���δ�.
public class BoardController {
	@Inject
	BoardService service;
	
	// �Խ��� ���
	@GetMapping("/boardList")
	public ModelAndView boardList(PagingVO vo) {
		ModelAndView mav = new ModelAndView();
		
		// �� ���ڵ� ��
		vo.setTotalRecord(service.totalRecord());
		System.out.println(vo.toString());
		// DB ��ȸ
		// �ش� ������ ���ڵ� ����
		mav.addObject("list", service.pageSelect(vo));
		
		mav.addObject("vo", vo);
		mav.setViewName("board/boardList");
		return mav;
	}
	
	// �۾�����
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	// �۾���(DB���)
	@PostMapping("/boardWriteOk")
	public ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		dto.setIp(request.getRemoteAddr()); // ip
		dto.setUserid((String)request.getSession().getAttribute("logId")); // �α����� ���̵� ���ϱ�
		
		// �� ��� �� �����ϸ� ���� �߻�
		// �۵�� ����: location.href="boardList";
		// �۵�� ����: alert("�۵�Ͻ���"), history.back();
		String htmlTag = "<script>";
		try {
			int result = service.boardInsert(dto);
			htmlTag += "location.href='boardList'";
		} catch(Exception e) {
			htmlTag += "alert('�� ��Ͽ� �����߽��ϴ�');";
			htmlTag += "history.back();";
		}
		htmlTag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		return new ResponseEntity<String>(htmlTag, headers, HttpStatus.OK);
	}
	// ������
	
	// �������
	
	// ����
}

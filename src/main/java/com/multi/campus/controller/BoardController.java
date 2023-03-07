package com.multi.campus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// Controller : ��� ��� ��� �Ұ�, ��ũ��Ʈ �ʿ� �� jsp ������ ���� �� ����
// RestController : ����Ʈ�� �鿣�忡�� ����� �� �ִ� ��� ����, ��ȯ���� String���� �ϸ� �������� ���ϸ��� �ƴ϶� ������ �������� ó���Ѵ�
@RestController
public class BoardController {
	@GetMapping("/board/boardList")
	public ModelAndView boardList() {
		ModelAndView mav = new ModelAndView();
		// DB ��ȸ
		mav.setViewName("board/boardList");
		return mav;
	}
}

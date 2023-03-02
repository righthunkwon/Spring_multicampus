package com.multi.campus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.service.RegisterService;

@Controller
public class RegisterController {
	
	@Autowired
	RegisterService service;
	
	// �α�����
	@GetMapping("/loginForm")
	public String login() {
		return "register/loginForm"; // WEB-INF/views/register/loginForm.jsp
	}
	
	// �α���(DB)
	@PostMapping("/loginOk")
	public ModelAndView loginOk(String userid, String userpwd, HttpServletRequest request, HttpSession session) {
		System.out.println("userid->"+userid);
		RegisterDTO dto = service.loginOk(userid, userpwd);
		// ���÷��ڵ尡 ���� ��� dto�� null�� �Ǿ� �α��ο� �����Ѵ�.
		// ���÷��ڵ尡 ���� ��쿡�� �α����� �����Ѵ�.
		ModelAndView mav = new ModelAndView();
		if(dto!=null) { // �α��� ����
			session.setAttribute("logId", dto.getUserid());
			session.setAttribute("logName", dto.getUsername());
			session.setAttribute("logStatus", "Y");
			mav.setViewName("redirect:/");
		} else { // �α��� ����
			mav.setViewName("redirect:loginForm");
		}
		return mav;
	}
	
	// �α׾ƿ�(���� ����)
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// ȸ������ ��
	@GetMapping("/join")
	public String join() {
		return "register/join";
	}
}

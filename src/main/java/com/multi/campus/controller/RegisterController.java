package com.multi.campus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;
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
	
	// ���̵� �ߺ��˻� ��
	@GetMapping("/idCheck")
	public String idCheck(String userid, Model model) {
		// DB ��ȸ -> ���̵��� ���� Ȯ��(0 �Ǵ� 1)
		int result = service.idCheckCount(userid);
		
		// �信�� ����ϱ� ���� �𵨿� ����
		model.addAttribute("userid", userid);
		model.addAttribute("result", result);
		
		return "register/idCheck";
	}
	
	// �����ȣ �˻�
	@RequestMapping(value="/zipcodeSearch", method=RequestMethod.GET)
	public ModelAndView zipcodeSearch(String doroname) {
		ModelAndView mav = new ModelAndView();
		// ������ �ּҰ� ������ return�� null
		List<ZipcodeDTO> zipList = null;
		if(doroname!=null) { 
			zipList = service.zipSearch(doroname);
		}
		mav.addObject("zipList", zipList);
		mav.setViewName("register/zipcodeSearch");
		return mav;
	}
}

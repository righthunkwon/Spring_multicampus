package com.multi.campus.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.multi.campus.dto.DataDTO;
import com.multi.campus.dto.DataFileDTO;
import com.multi.campus.service.DataService;

@Controller
public class DataController {
	@Inject
	DataService service;
	
	// �ڷ�� ����Ʈ
	@RequestMapping("/data/dataList")
	public ModelAndView dataList() {
		ModelAndView mav = new ModelAndView();
		List<DataDTO> list = service.dataAllSelect();
		
		mav.addObject("list", list);
		mav.setViewName("data/dataList");
		
		return mav;
	}
	
	// �ڷ�� �� ��� ��
	@RequestMapping("/data/dataWrite")
	public String dataWrite() {
		return "data/dataWrite";
	}
	
	// �ڷ�� �۵��(DB)
	@PostMapping("/data/dataWriteOk")
	public ModelAndView dataWriteOk(HttpServletRequest request, DataDTO dto) {
		// request���� ���� ����, �۳���, ÷�������� ����ִ�.
		// session���� �۾���(logId)�� ����ִ�.
		dto.setUserid((String)request.getSession().getAttribute("logId"));
		
		// ������ ip
		dto.setIp(request.getRemoteAddr());
		
		// MultipartHttpServletRequest�� request�� �̿��Ͽ� ���Ѵ�.
		// (1) ���� ���ε�
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
		
		// (2) mr���� MultipartFile ��ü�� ������ (���ε��� ������ ����ŭ ����)
		List<MultipartFile> files = mr.getFiles("filename");
		System.out.println(files.size());
		// (3) ������ ������ ���ε��� ��ġ�� �����ּҰ� �ʿ�
		String path = request.getSession().getServletContext().getRealPath("/uploadfile");
		System.out.println("path-->" + path);
		
		// ���ε� ���� (��, ������ ���ϸ��� ������ ������ ���� ���� ���ϸ��� �������� �Ѵ�)
		List<DataFileDTO> fileList = new ArrayList<DataFileDTO>();// ���ε��� ���ϸ���� ���� �÷��� 
		if(files!=null) { // ���ε��� ������ ���� ��
			for(int i=0; i<files.size(); i++) {// ���ε��� ������ ����ŭ �ݺ�����
				MultipartFile mf = files.get(i);
				String orgFilename = mf.getOriginalFilename(); // Ŭ���̾�Ʈ�� ���ε��� ���� ���ϸ��� ���Ѵ�.
				if(orgFilename != null && !orgFilename.equals("")) { // ���� ���ϸ��� �����ϸ� rename�� �����Ѵ�.
					// ���ϰ�ü�� �ִ��� Ȯ�� �� ���� ������ ������ ���ϸ��� �����Ѵ�.
					File f = new File(path, orgFilename);
					
					if(f.exists()) { // file�� ������ true, ������ false
						// abc.gif -> abc (1).gif -> abc (2).gif -> ...
						for(int renameNum=1; ; renameNum++ ) { // 1, 2, 3, 4, ...
							// ������ .�� ��ġ�� �������ν� ���ϸ�� Ȯ���ڸ� ������.
							int point = orgFilename.lastIndexOf(".");
							String orgFile = orgFilename.substring(0, point); // Ȯ���ڸ� �� ���ϸ�
							String orgExt = orgFilename.substring(point+1); // Ȯ����
							
							String newFilename = orgFile+ " (" + renameNum + ")." + orgExt; // ���� ���� ���ϸ�
							f = new File(path, newFilename);
							if(!f.exists()) { // ���� ���� ������ �������� ���� ��� �ݺ����� �ߴ��Ѵ�.
								orgFilename = newFilename;
								break;
							}
						}
					}
					// ���ο� ���ϸ��� ã���� �� ���ε带 ����, ���ϸ� ����
					try {
						mf.transferTo(new File(path, orgFilename));
					} catch(Exception e) {
						
					}
					System.out.println(orgFilename);
					DataFileDTO dfDTO = new DataFileDTO();
					dfDTO.setFilename(orgFilename);
					fileList.add(dfDTO);
				}
			}
		}
		ModelAndView mav = new ModelAndView();
		try {
			// (4) ���� �� insert �����ϱ� (������ �������� �Բ� ���ϴ� ���)
			int result = service.dataInsert(dto);
			// ������ �������� ���ϸ��� �ִ� dto�� ����
			for(DataFileDTO fDTO : fileList) {
				fDTO.setNo(dto.getNo());
			}
			
			// (5) ���� �������� ���ϸ��� DB�� �߰�
			int fileResult = service.dataFileInsert(fileList);
			mav.setViewName("redirect:dataList");
		} catch(Exception e) {
			// ���ϻ���
			for(DataFileDTO fDTO : fileList) {
				fileDelete(path, fDTO.getFilename());
			}
			// (6) ���ڵ� �߰��� �� ������ �߻��� ��� : ���� �� ���ڵ带 �����ϰ� �۾���� �̵�(history)
			service.dataDelete(dto.getNo());
			service.dataFileDelete(dto.getNo());
			
			mav.addObject("msg", "�ڷ�� �� ��Ͽ� �����Ͽ����ϴ�.");
			mav.setViewName("data/dataResult");
		}
		return mav;
	}
	// ���ε� ���� ����
	public void fileDelete(String path, String filename) {
		File f = new File(path, filename);
		f.delete();
	}
	
	// �ڷ�� �۳��뺸��
	@GetMapping("/data/dataView/{no}")
	public ModelAndView dataView(@PathVariable("no") int no) {
		// no�� ���� ���ڵ� ����
		DataDTO dto = service.dataSelect(no);
		
		// no�� �ش��ϴ� ÷������ ����(������ �������̹Ƿ� �÷��� ���)
		List<DataFileDTO> fileList = service.dataFileSelect(no);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.addObject("fileList", fileList);
		mav.setViewName("data/dataView");
		
		return mav;
	}
}

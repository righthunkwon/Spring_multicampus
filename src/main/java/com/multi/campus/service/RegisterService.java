package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

public interface RegisterService {
	// DAO�� �ִ� �޼ҵ�� ������ �޼ҵ�
	// �α��� Ȯ��
	public RegisterDTO loginOk(String userid, String userpwd);
	// ���̵��ߺ��˻�
	public int idCheckCount(String userid);
	// �����ȣ�˻�
	public List<ZipcodeDTO> zipSearch(String doroname);
}

package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.RegisterDTO;
import com.multi.campus.dto.ZipcodeDTO;

public interface RegisterDAO {
	// �α��� Ȯ��
	public RegisterDTO loginOk(String userid, String userpwd);
	
	// ���̵��ߺ��˻� (���̵��� ������ ���Ͽ� Ȯ��)
	public int idCheckCount(String userid);
	
	// ���θ� �˻�
	public List<ZipcodeDTO> zipSearch(String doroname);
}

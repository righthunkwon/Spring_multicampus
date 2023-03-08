package com.multi.campus.dao;

import java.util.List;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;

public interface BoardDAO {
	// �۵��
	public int boardInsert(BoardDTO dto);
	// �� ���ڵ� ��
	public int totalRecord();
	// �ش� ������ ����
	public List<BoardDTO> pageSelect(PagingVO vo);
}

package com.multi.campus.service;

import java.util.List;

import com.multi.campus.dto.BoardDTO;
import com.multi.campus.dto.PagingVO;

public interface BoardService {
	// �۵��
	public int boardInsert(BoardDTO dto);
	// �� ���ڵ� ��
	public int totalRecord();
	// �ش� ������ ����
	public List<BoardDTO> pageSelect(PagingVO vo);
}

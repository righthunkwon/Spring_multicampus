package com.multi.campus.dto;

public class PagingVO {
	// ����¡ ó�� �� �ʿ��� ������
	private int nowPage = 1; // ���� ������
	private int onePageRecord = 5; // �� �������� ǥ���� ���ڵ� ��
	private int totalRecord; // �� ���ڵ� ��
	private int totalPage; // �� ������ ��
	private int lastPageRecord; // ������ �������� �����ִ� ���ڵ� ��
	
	private int onePageNumCount = 5; // �� ������ �� ������ ����
	private int startPageNum = 1;
	
	// �˻��� �ʿ��� ������
	
	

	@Override
	public String toString() {
		return "PagingVO [nowPage=" + nowPage + ", onePageRecord=" + onePageRecord + ", totalRecord=" + totalRecord
				+ ", totalPage=" + totalPage + "]";
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		
		// ����� ������ ���۹�ȣ�� ���
		// = ((����������-1)/ǥ������������)*ǥ������������+1
		startPageNum = ((nowPage-1)/onePageNumCount)*onePageNumCount+1;
	}
	public int getOnePageRecord() {
		return onePageRecord;
	}
	public void setOnePageRecord(int onePageRecord) {
		this.onePageRecord = onePageRecord;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		// �� ���ڵ� ���� �̿��Ͽ� �� ������ ���� ���ϱ�
		totalPage = (int)Math.ceil((double)totalRecord/onePageRecord);
		
		// ������ �������� �����ִ� ���ڵ� ��
		lastPageRecord = onePageRecord;
		if(nowPage == totalPage) { // ������ �������� ���
			if(totalRecord % onePageRecord == 0) { // �������� 0�� ��� onePageRecord�� lastPageRecord
				lastPageRecord = onePageRecord;
			} else { // �������� ���� ��� lastPageRecord�� ������ ���Ѵ�.
				lastPageRecord = totalRecord % onePageRecord;
			}
		} 
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
package com.mycommunity.common.vo;

public class PageCriteria {

	//현재 보고있는 페이지
	private int page;
	//한 페이지당 몇개의 게시글을 검색해서 model로 보낼지 정함
	private int perPageNum;
	//1~5, 6~10.. 등 전체 페이지 중 몇번째 구간 게시글 출력
	private int rowStart;
	private int rowEnd;
	
	//생성자
	public PageCriteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	public int getRowStart() {
		rowStart = ((page - 1) * perPageNum) + 1;
		return rowStart;
	}
	
	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}
	
	public int getRowEnd() {
		rowEnd = rowStart + perPageNum - 1;
		return rowEnd;
	}
	
	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}
	
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}

	@Override
	public String toString() {
		return "PageCriteria [page=" + page + ", perPageNum=" + perPageNum + ", rowStart=" + rowStart + ", rowEnd="
				+ rowEnd + "]";
	}
	
}

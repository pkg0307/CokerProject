package com.mycommunity.common.vo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	
	//게시글 전체 개수
	private int totalCount;
	//시작 페이지 (1-10 이면 1, 11-20이면 11 이런식?)
	private int startPage;
	//끝페이지(이게 꼭 10, 20, 30으로 끝나지 않음. 4나 7, 8등으로 끝날 수 있음.)
	private int endPage;
	//이전페이지
	private boolean prev;
	//다음페이지
	private boolean next;
	//PageBlock 한번 보여줄때 블럭 몇개 생성할 것인가.
	//10 = [prev] 1 2 3 4 5 6 7 8 9 10 [next]
	//5 = [prev] 1 2 3 4 5 [next]
	private int displayPageNum = 10;
	//Criteria :)
	private PageCriteria cri;
	//category Name
	private String category;

	public PageMaker(){}

	public void setCri(PageCriteria cri) {
		this.cri = cri;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		//prev, next block
		calcData();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public PageCriteria getCri() {
		return cri;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private void calcData() {
		endPage = (int) (Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;

		int tempEndPage = (int) (Math.ceil(totalCount / (double)cri.getPerPageNum()));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	//pageMaker
	public String makeMaker(int page)
	{
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("category", category)
				.build(); 
		return uriComponents.toUriString();  
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + ", category="
				+ category + "]";
	}


}

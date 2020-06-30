package com.mycommunity.board.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

public class ReplyVO {
	private int boardNO;
	private int replyNO;
	private String content;
	private String writer;
	private Date regDate;
	private int progress;
	private String imgName;
	private int reports;
	
	public int getBoardNO() {
		return boardNO;
	}
	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}
	public int getReplyNO() {
		return replyNO;
	}
	public void setReplyNO(int replyNO) {
		this.replyNO = replyNO;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	@Override
	public String toString() {
		return "ReplyVO [boardNO=" + boardNO + ", replyNO=" + replyNO + ", content=" + content
				+ ", writer=" + writer + ", regDate=" + regDate + "]";
	}
	public int getReports() {
		return reports;
	}
	public void setReports(int reports) {
		this.reports = reports;
	}
	
	
}

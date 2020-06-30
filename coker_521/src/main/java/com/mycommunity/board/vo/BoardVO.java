package com.mycommunity.board.vo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component("boardVO")
public class BoardVO {
	private String topic;
	private int boardNO;
	private int parentNO;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private int likes;
	private int progress;
	private int level;
	private int reports;
	
	@Override
	public String toString() {
		return "BoardVO [boardNO=" + boardNO + ", parentNO=" + parentNO + ", title=" + title + ", content=" + content
				+ ", writer=" + writer + ", regDate=" + regDate + ", likes=" + likes + ", progress=" + progress
				+ ", level=" + level + "]";
	}
	
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getBoardNO() {
		return boardNO;
	}
	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}
	public int getParentNO() {
		return parentNO;
	}
	public void setParentNO(int parentNO) {
		this.parentNO = parentNO;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public void setRegDate(Date regDate) throws ParseException {
		this.regDate = regDate;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getReports() {
		return reports;
	}


	public void setReports(int reports) {
		this.reports = reports;
	}
	

}

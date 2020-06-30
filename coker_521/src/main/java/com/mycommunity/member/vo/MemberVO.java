package com.mycommunity.member.vo;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Component("memberVO")
public class MemberVO {
	
	@NotEmpty
	@Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
	private String userEmail;
	
	@NotEmpty
	@Pattern(regexp="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
	private String userPw;
	
	private String nickname;
	
	private String userName;
	
	private String tel;
	
	private String enable;
	
	private int point;
	
	private int totalPoint;
	
	private int lvl;
	
	private String imgName;
	
	private Date disable_date;
	
	private Date enable_date;
	
	private String disable_reason;
	
	private int rank;

	private String authCode;
	
	private String insertCode;
	
	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getInsertCode() {
		return insertCode;
	}
	public void setInsertCode(String insertCode) {
		this.insertCode = insertCode;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public Date getDisable_date() {
		return disable_date;
	}
	public void setDisable_date(Date disable_date) {
		this.disable_date = disable_date;
	}
	public Date getEnable_date() {
		return enable_date;
	}
	public void setEnable_date(Date enable_date) {
		this.enable_date = enable_date;
	}
	public String getDisable_reason() {
		return disable_reason;
	}
	public void setDisable_reason(String disable_reason) {
		this.disable_reason = disable_reason;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
}


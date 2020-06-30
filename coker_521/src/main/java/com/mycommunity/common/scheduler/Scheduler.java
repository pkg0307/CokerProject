package com.mycommunity.common.scheduler;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mycommunity.admin.member.service.AdminMemberService;
import com.mycommunity.member.vo.MemberVO;

@Component
public class Scheduler {
	
	@Autowired 
	 AdminMemberService adminMemberService;
	
		@Scheduled(cron = "0 52 0 * * *" )
		public void autoUpdateEnable() throws Exception{
			System.out.println("스케쥴러 시작");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ArrayList<MemberVO> disMemberList = adminMemberService.checkDisMemberList();
			Date serverDate = adminMemberService.checkServerDate();
			System.out.println("서버 시간 : " + serverDate);
			int disMemberTotal = 0;
			String disUserEmail = null;
			Date memberEnableDate = null;
			if(disMemberList.isEmpty()) {
				System.out.println("금일 차단 예정인 회원이 없습니다.");
			} else {
				for(MemberVO memberVO : disMemberList) {
					System.out.println("========================================");
					System.out.println("차단계정 아이디 : " + memberVO.getUserEmail());
					System.out.println("차단일자 : " + memberVO.getDisable_date());
					memberEnableDate = memberVO.getEnable_date();
					System.out.println("해제일자 : " + memberEnableDate);
					if(sdf.format(serverDate).equals(sdf.format(memberEnableDate))) {
						disUserEmail = memberVO.getUserEmail();
						adminMemberService.autoEnableMember(disUserEmail);
						System.out.println(serverDate + " 계정차단 자동해제 완료");
						disMemberTotal += 1;
						System.out.println("========================================");
					}
				}
				System.out.println("금일 차단해제 회원 수 : " + disMemberTotal);
			}
			System.out.println("스케쥴러 종료");
		}
}

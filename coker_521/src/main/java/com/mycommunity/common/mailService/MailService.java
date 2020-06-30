package com.mycommunity.common.mailService;

public interface MailService {
	
	public boolean send (String subject, String text, String to, String filePath) throws Exception;
	
}

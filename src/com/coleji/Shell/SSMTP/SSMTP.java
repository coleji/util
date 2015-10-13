package com.coleji.Shell.SSMTP;

import com.coleji.Shell.ShellManager;

// I'd make this a static class if that were a thing...
public final class SSMTP {
	
	private SSMTP() {
		// cannot be instantiated
	}
	
	public static void send(String emailTo, String fromName, String subject, String message) {
		ShellManager s = ShellManager.getInstance();
		String command = "ssmtp -vvvv " + emailTo + " -F" + fromName;
		s.execute(command, null, 5000, null, "Subject: " + subject + "\n\n" + message);	
	}
}
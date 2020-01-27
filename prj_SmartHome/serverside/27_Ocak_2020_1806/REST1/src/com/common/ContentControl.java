package com.common;

public class ContentControl {
	/**
	 * Content control. (içerik kontrolu).
	 * -İçerik Email mı?
	 * -İçerik Telefon numarası mı?
	 * -..
	 */
	//[email/phone number content determine]:@{	
		public static String IS_PHONE_NUMBER  = "is_phone_number";
		public static String IS_EMAIL = "is_email";
	//[email/phone number content determine]:@}
	
	/**
	 * Content is email or phone number?
	 * @param content  contains phone number or email.
	 * @return return "IS_PHONE_NUMBER" if content appropriate to phone number,or return "IS_EMAIL" if content appropriate to email.
	 */
	public static String isEmailOrPhone(String email_or_phone) {
		if(email_or_phone.matches("[0-9]+"))
			return IS_PHONE_NUMBER;
		
		if(email_or_phone.contains("@"))
			return IS_EMAIL;
			
		return "null";
	}
}

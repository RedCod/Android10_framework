package com.service.sub;

import java.sql.SQLException;

import com.common.ContentControl;
import com.common.Email;
import com.common.Log;
import com.database.Database;

public class Account {
	/**
	 * Hesap/Giriş işlemleri. Ortak işlemler.
	 * used by:
	 * 		-WS_Account.java
	 * 
	 */
	private static String TAG = "Account";
///[account label]:@{
	/**
	 * Mobil App. tarafından "Hesap Bilgilerinin" güncellenme isteğiyle ilgili gelen içeriği etiketlerle belirler.
	 * used in this class and also used in "WS_Account.java"
	 */
	public static String NICKNAME		         = "NickName";			//Takma ad
	public static String BIND_MOBILE_PHONE       = "BindMobilePhone";	//Cep Telefonu (telefonu bağla)
	public static String ACCOUNT_LOCATION        = "AccountLocation";	//Hesap Konumu
	public static String CHANGE_LOGIN_PASSWORD   = "ChangeLoginPassword";	//Oturum açma password
	public static String TEMPERATURE_UNIT        = "TemperatureUnit";	//Sıcaklık birimi
	public static String TIME_ZONE		         = "TimeZone";		//Saat dilimi
///[account label]:@}
	
///[verification code label]:@{
	/**
	 * Mobil App. tarafından "Onay kodu" alma isteğiyle ilgili gelen içeriği etiketlerle belirler.
	 * used in this class and also used in "WS_Account.java"
	 */
	public static String GET_VERIFICATION_CODE_FOR_NEW_ACCOUNT     = "NewAccount";    //Talep:Yeni hesap oluşturmak.
	public static String GET_VERIFICATION_CODE_FOR_FORGOT_PASSWORD = "Forgotpassword";//Talep:Şifremi unuttum.
	public static String GET_VERIFICATION_CODE_FOR_CHANGE_PASSWORD = "ChangePassword";//Talep:Oturum/giriş şifresini değiştirmek için.
///[verification code label]:@}
	
	public Account() {
		//
	}
	
	/*
1:tblAccount
CREATE TABLE IF NOT EXISTS tblAccount (
    Id INT AUTO_INCREMENT,
    Email VARCHAR(255) DEFAULT '',
    PhoneNumber VARCHAR(100) DEFAULT '',
    Password VARCHAR(255) DEFAULT '',
    Nickname VARCHAR(100) DEFAULT '',
    AccountLocation VARCHAR(50) DEFAULT '',
    TemperatureUnit VARCHAR(10) DEFAULT 'C',
    TimeZone VARCHAR(50) DEFAULT '',
    VerificationCode INT,
    IsActive INT DEFAULT 1,
    PRIMARY KEY (Id)
);
*/
//#############################################EXTERNAL FUNCTIONS:@{	
	/**  ///[>OK<]
	 * Hesap İşlemleri-Login
	 * 
	 * @param email_or_phone	//account name "email or phone number".
	 * @param password			//account password for login.As md5 value
	 * @return					//return account result(all row. ID,etc.) as JSON.
	 */
	public String login(String email_or_phone,String password) {
		/*
		 * FIXME: SORGU ÇIKTISINI STANDART BELİRLEDİĞİMİZ JSON İÇİNE GÖM.
		 */
		//NOTE: Check "email_or_phone" content if all charachter is number(for phone number login) or email(if contains @).
		String sql = "SELECT JSON_OBJECT('Id',Id,'Email',Email,'PhoneNumber',PhoneNumber) FROM tblAccount WHERE Email='" + email_or_phone + "' AND Password='" + password + "'";//Default Email
		if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_PHONE_NUMBER))
			sql = "SELECT JSON_OBJECT('Id',Id,'Email',Email,'PhoneNumber',PhoneNumber) FROM tblAccount WHERE PhoneNumber='" + email_or_phone + "' AND Password='" + password + "'";//PhoneNumber
		else if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_EMAIL))
			sql = "SELECT JSON_OBJECT('Id',Id,'Email',Email,'PhoneNumber',PhoneNumber) FROM tblAccount WHERE Email='" + email_or_phone + "' AND Password='" + password + "'";//Email
		String result  ="";
		Database database = new Database();
		try {
			database.connect();
			result = "{\"Login\":[" + database.query(sql) + "]}"; //set in the json structure.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;//as JSON. 
	}
	
	
	/**   ///[>OK<]
	 * Hesap İşlemleri-Yeni Kullanıcı Hesap Oluştur.
	 * @param email_or_phone	//email or phone number.
	 * @param verification_code	//for verification.
	 * @param password			//account password for login.As md5 value
	 * @return					// return true(as JSON) if create account is successful.
	 */
	public String createNewAccount(String email_or_phone,String verification_code,String password,String account_location) {
		/*
		 *Yeni hesap oluştururken öncelikle "getVerificationCode" funksiyonu çağrılır.
		 *Dolayısıyla bu fonk. içinden tekrar hesap kontrolu(hesap var mı?) yapmaya gerek yok.  
		 */
		int exec_state = 0;
		String update_sql ="";
		String query_sql_for_settings = "";//tblSettings'a ayarlar eklemek için sorgu oluştur.
		String response = "{\"CreateNewAccount\":[{ \"response\":false}]}"; //default
/*
	-tblAccount
	Id
	Country		//örn: Turkey+90
	Email
	PhoneNumber
	Password
	Nickname
	AccountLocation		//Hesap Konumu. Hesabın kayıtlı olduğu sunucu. Avrupa,Türkiye,
	TemperatureUnit		//Sıcaklık Birimi: C,F
	TimeZone
	VerificationCode
	IsActive
 */
		if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_PHONE_NUMBER)) {
			update_sql = "UPDATE tblAccount SET PhoneNumber='" + email_or_phone +"',Password='" + password +"',AccountLocation='"+ account_location + "' WHERE PhoneNumber='" + email_or_phone + "' AND VerificationCode='" + verification_code +"'";//tblAccount
			query_sql_for_settings = "SELECT Id FROM tblAccount WHERE PhoneNumber='" + email_or_phone +"'";
		}else if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_EMAIL)) { 
			update_sql = "UPDATE tblAccount SET Email='" + email_or_phone +"',Password='" + password +"',AccountLocation='"+ account_location +"' WHERE Email='" + email_or_phone + "' AND VerificationCode='" + verification_code +"'";//tblAccount
			query_sql_for_settings = "SELECT Id FROM tblAccount WHERE Email='" + email_or_phone +"'";
		}
		Database database = new Database();
		try {
			database.connect();
			exec_state = database.execSQL(update_sql);
			//debug:
			if(Log.DEBUG) {
				Log.println(TAG, "createNewAccount: exec_state1:" + exec_state);
			}
			if(exec_state > 0) {
				response = "{\"CreateNewAccount\":[{ \"response\":true}]}";
				//query from tblAccount for settings:
				String accountId = database.query(query_sql_for_settings);
				com.service.sub.Settings settings = new com.service.sub.Settings();
				//insert default value to tblSettings.
				settings.addSettings(Integer.valueOf(accountId), 1, 1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return response;
	}//createNewAccount(..)
	
	/** ///[>OK<]
	 * Hesap İşlemleri-Kullanıcı Hesap güncelle. Bu süreçte Hesap'a ait her güncellenecek bilgi bir etiketle bu fonksiyona gönderilir.
	 * @param label	//Account label: com.common.Account.java
	 * @param accountId //Belong to Account "Id".
	 * @param accountName //account(email_or_phone). 
	 * @param contentValue // content for updating.
	 * @param where2 	//Use for  "WHERE VerificationCode=where2" 
	 * @return	    	//return true(as JSON) if account update is successful.
	 */
	public String editAccount(String label,int accountId,String accountName,String contentValue,int where2) {
		if(Log.DEBUG)System.out.println("database:" + accountId + ","+label + ","+ contentValue);
		
		//Complete Sql Query For Update:
		String complete_sqlquery = "";
		if(ContentControl.isEmailOrPhone(accountName).equals(ContentControl.IS_EMAIL))
             complete_sqlquery = " AND Email='" + accountName +"'";
		else if(ContentControl.isEmailOrPhone(accountName).equals(ContentControl.IS_PHONE_NUMBER))
			 complete_sqlquery = " AND PhoneNumber='" + accountName +"'";
		///
		String response = "{\"EditAccount\":[{ \"response\":false}]}";
		Database database = new Database();
		String sql_for_update  ="";
        if(label.equals(Account.NICKNAME)){
        	//Nickname güncelle.
        	sql_for_update = "UPDATE tblAccount SET NickName= '" + contentValue +"' WHERE Id='" + accountId +"' " + complete_sqlquery;
		}else if(label.equals(Account.BIND_MOBILE_PHONE)){
			/*Note:
			 * Telefon numarasını ekle/güncelle('cep telefonu bağla' işlemi). 
			 * Kullanıcı daha önce email ile kayıt olmuş olabilir.
			 * Bu durumda bir telefon numarası ekleyerek hesabına telefon numarası ile de giriş yapabilir.
			 */
			sql_for_update = "UPDATE tblAccount SET PhoneNumber='" + contentValue +"' WHERE Id='" + accountId + "' " + complete_sqlquery;
		}else if(label.equals(Account.ACCOUNT_LOCATION)){
			//Hesap konumu güncelle. örn:Avrupa etc.
			sql_for_update = "UPDATE tblAccount SET AccountLocation='" + contentValue + "' WHERE Id='" + accountId + "' " + complete_sqlquery;
		}else if(label.equals(Account.CHANGE_LOGIN_PASSWORD)){
			//oturum açma Parolasını değiştir/güncelle
			sql_for_update = "UPDATE tblAccount SET Password='" + contentValue + "' WHERE VerificationCode ='" + where2 + "' AND  Id='" +  accountId  +"' " + complete_sqlquery;			
		}else if(label.equals(Account.TEMPERATURE_UNIT)){
			//sıcaklık birimi güncelle.
			sql_for_update = "UPDATE tblAccount SET TemperatureUnit ='" + contentValue + "' WHERE Id ='" + accountId + "' " + complete_sqlquery;
		}else if(label.equals(Account.TIME_ZONE)){
			//TimeZone saat dilimi güncelle.
				sql_for_update = "UPDATE tblAccount SET TimeZone='" + contentValue + "' WHERE Id ='" + accountId + "' " + complete_sqlquery;
		}else {
			//do nothing...
		}
        try {
			database.connect();
			int s = database.execSQL(sql_for_update);
		    if(s > 0)
		    	response = "{\"EditAccount\":[{ \"response\":true}]}";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return response;
	}//editAccount(..)
	
	/**  ///[>OK<]
	 * Hesap İşlemleri -Doğrulama kodu al: "Parolamı Unuttum,Yeni Hesap,Şifre Değişikliği" için onay kodu talep ediliyor.
	 * @param label			 verification code label: com.common.Account.java
	 * @param email_or_phone email or phone number.
	 * @return	3=account not exists. 2=account already exists. 1=successful. 0=unsuccessful.  as JSON
	 */
	public String getVerificationCode(String label,String email_or_phone) {
		/**
		 * DİKKAT 1:Kullanıcı ilk kez üye oluyorsa doğrulama kodu gönder. Eğer zaten üye ise login sayfasına gitmesi için uyarı gönder.
		 * DİKKAT 2:Eğer login haldeyse ve doğrulama kodu talep etmişse,demekki şifre değiştirecek.O halde doğrulama kodu gönder ve yeni şifre giriş sayfasına yönlendir.
		 * Bunları ayırt etmek için Mobil APP tarafından webservisle nerden(label) geldiğini belirt.<<<<<<<<!!!!!!!!!!
		 */
		String sql  = "";
		int random_verification_code = generateVerificationCode(); //6 rakamdan oluşur.
		if(label.equals(GET_VERIFICATION_CODE_FOR_NEW_ACCOUNT)) {
			//for new account:
			//DİKKAT 1:Kullanıcı ilk kez üye oluyorsa doğrulama kodu gönder. Eğer zaten üye ise login sayfasına gitmesi için uyarı gönder.
			if(accountIsExists(email_or_phone))
				return "{\"GetVerificationCode\":[{ \"response\":2}]}";//account already exists.
			else
				sql = "INSERT INTO tblAccount(Email,VerificationCode) VALUES('" + email_or_phone +"','" + random_verification_code +"')";
		}else if(label.equals(GET_VERIFICATION_CODE_FOR_FORGOT_PASSWORD) || label.equals(GET_VERIFICATION_CODE_FOR_CHANGE_PASSWORD)) {
			//for new password. Because user forgot own login pasword.
			//for change password.Because user want to change own login password.
			//for both cases if account is exists,then send verification code:
			if(accountIsExists(email_or_phone)) {
				if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_PHONE_NUMBER))
					sql = "UPDATE tblAccount SET VerificationCode='"+ random_verification_code + "' WHERE PhoneNumber='" + email_or_phone + "'";
				else if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_EMAIL))
					sql = "UPDATE tblAccount SET VerificationCode='"+ random_verification_code + "' WHERE Email='" + email_or_phone + "'";
			}else {
				return "{\"GetVerificationCode\":[{ \"response\":3}]}";//account not exists.
			}
		}
		/*
		 * NOT:
		 * 1-Eğer label "ForgotPassword" ise:
		 *  |_Üyeliğinin var olup olmadığını kontrol et. Eğer üyelik var ise doğrulama kodu gönder. Aksi halde "Yeni Hesap oluştur" sayfasına gitmelidir.
		 *  
		 * 2-Eğer label "NewAccount" ise:
		 *  |_Üyeliğin var olup olmadığını kontrol et. Eğer üyelik var ise login sayfasına yönlendir. Üyelik yok ise doğrulama kodu gönder.
		 *   
		 * 3-Eğer label "ChangePassword" ise:
		 *  |_Zaten kullanıcı login durumundayken bu işlemi yapacağı için "1"'i uygula.
		 */
		//Controlleri yap:
        //..
		//NOT: save/keep to db user email or phone,verification and then send the verification code to user email/phone.
		//and then:
		//NOT: send verification Code to user email address or phone number,according to "email_or_phone".
		String response = "{\"GetVerificationCode\":[{ \"response\":0}]}";
		Database database = new Database();
		try {
			database.connect();
			int s = database.execSQL(sql);
			if(s > 0) {
				response = "{\"GetVerificationCode\":[{ \"response\":1}]}";
				if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_PHONE_NUMBER)) {
					//send verification code to phone number.
					//V2.
				}else if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_EMAIL)) {
					//send verification code to email:
					boolean send_state = sendVerificationCodeToEmail(email_or_phone,random_verification_code);
					if(Log.DEBUG)Log.println(TAG, "send_state:"+send_state);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(Log.DEBUG) {
			Log.println(TAG, "sql:" + sql);
			Log.println(TAG, "getVerificationCode:->exec_state:" + response );
		}
		return response;
	}//getVerificationCode()
	
	/**
	 * Get Account item.
	 * @param accountId  //which Acccount?
	 * @param accountName //account(email_or_phone)
	 * @return			//return all account info as JSON.
	 */
	public String getAccount(int accountId,String accountName) {
		//Complete Sql Query for Select:
		String complete_sqlquery = "";
		if(ContentControl.isEmailOrPhone(accountName).equals(ContentControl.IS_EMAIL))
             complete_sqlquery = " AND Email='" + accountName +"'";
		else if(ContentControl.isEmailOrPhone(accountName).equals(ContentControl.IS_PHONE_NUMBER))
			 complete_sqlquery = " AND PhoneNumber='" + accountName +"'";
		//
		String select_sql = "SELECT JSON_OBJECT('Id',Id,'Email',Email,'PhoneNumber',PhoneNumber,'Nickname',Nickname,'AccountLocation',AccountLocation,'TemperatureUnit',TemperatureUnit,'TimeZone',TimeZone) FROM tblAccount WHERE Id='" + accountId + "' " + complete_sqlquery;
		String result  ="";
		Database database = new Database();
		try {
			database.connect();
			result = "{\"GetAccount\":[" + database.query(select_sql) + "]}"; //set in the json structure.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}//getAccount()
	
	/**
	 * Remove account.
	 * @param accountId //which account
	 * @param accountName //account(email_or_phone)
	 * @return			//return true(as JSON) if remove account successful.
	 */
	public String removeAccount(int accountId,String accountName) {
		String response = "{\"RemoveAccount\":[{ \"response\":false}]}";
		Database database = new Database();
		try {
			/*
			 * Tüm tabloları dolaş ve Account'a ait tüm kayıtları sil. 
			 * DİKKAT:YENİ BİR TABLO OLUŞTURULDUĞUNDA BURAYA EKLE.
			 */
				/*Sil:
				 *tblAccount
				 *tblDevice
				 *tblDeviceGroup
				 *tblDeviceGroupSub
				 *tblFamily
				 *tblFamilyMember
				 *tblRoom
				 *tblDontDisturbDevices
				 *tblMessageCenter
				 *tblHelpCenter
				 *tblFeedback
				 *tblSchedule
				 *tblScenario
				 *tblScenarioSub
				 *tblAutomation
				 *tblAutomationOperations
				 *tblAutomationCondition
				 *tblSettings
				 */
              String[] arrTables = {
            		  //"tblAccount",
            		  "tblDevice",
            		  "tblDeviceGroup",
            		  "tblDeviceGroupSub",
            		  "tblFamily",
            		  "tblFamilyMember",
            		  "tblRoom",
            		  "tblDontDisturbDevices",
            		  "tblMessageCenter",
            		  "tblFeedback",
            		  "tblSchedule",
            		  "tblScenario",
            		  "tblScenarioSub",
            		  "tblAutomation",
            		  "tblAutomationOperations", 
            		  "tblAutomationCondition",
            		  "tblSettings"
              };
              
      		//Complete Sql qery for delete:
      		String complete_sqlquery = "";
      		if(ContentControl.isEmailOrPhone(accountName).equals(ContentControl.IS_EMAIL))
                 complete_sqlquery = " AND Email='" + accountName +"'";
      		else if(ContentControl.isEmailOrPhone(accountName).equals(ContentControl.IS_PHONE_NUMBER))
      			 complete_sqlquery = " AND PhoneNumber='" + accountName +"'";
              ////
            String delete_sql = "DELETE FROM tblAccount WHERE Id='" + accountId +"' " + complete_sqlquery; 
            database.connect();  
            int s = database.execSQL(delete_sql);
			if(s > 0)
				response = "{\"RemoveAccount\":[{ \"response\":true}]}";
            for(int i=0;i<arrTables.length;i++) {  
				delete_sql = "DELETE FROM "+arrTables[i] +" WHERE AccountId='" + accountId +"'";//tblAccount
				s = database.execSQL(delete_sql);
				if(Log.DEBUG)
					Log.println(TAG, "removeAccount->Delete " + arrTables[i]+" state:" + s);
            }//for

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				database.close();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return response;
	}//removeAccount()
	
	
//#############################################external functions:@}
//######################################################################################################################################
//#############################################INTERNAL FUNCTIONS:@{	


	
	/**
	 * Account control. If account is exists?.(Hesap var mı(mevcut mu)?)
	 * @param email_or_phone
	 * @return true if is account exists
	 */
	private boolean accountIsExists(String email_or_phone) {
		/**
		 * TODO: Hesabın olup olmadığını kontrol et.
		 */
		 String sql = "";
		 if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_PHONE_NUMBER)) {
			 sql = "SELECT PhoneNumber FROM tblAccount WHERE PhoneNumber='" + email_or_phone +"'";
		 }else if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_EMAIL)) {
			 sql = "SELECT Email FROM tblAccount WHERE Email='" + email_or_phone +"'";
		 }
			String result  ="";
			Database database = new Database();
			try {
				database.connect();
				result = database.query(sql);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					database.close();
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//FOR LOG:
			if(Log.DEBUG) {
				if(result.length() > 0)
					Log.println(TAG, "accountIsExists:-> account is exists.");
			}
			if(result.length() > 0)
				return true;//retrun true if is account exists.
		return false;  
	}
	
	
	/**
	 * for improving security.
	 * Account control with accountId and AccountName(email_or_phone). If account is exists?.(Hesap var mı(mevcut mu)?)
	 * @param accountId     //account id value.
	 * @param email_or_phone //account(email_or_phone)
	 * @return true if is account exists
	 */
	public static boolean accountIsExists(int accountId,String email_or_phone) {
		/**
		 * TODO: Hesabın olup olmadığını kontrol et.
		 */
		 String sql = "";
		 if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_PHONE_NUMBER)) {
			 sql = "SELECT PhoneNumber FROM tblAccount WHERE Id='"+accountId+"' AND PhoneNumber='" + email_or_phone +"'";
		 }else if(ContentControl.isEmailOrPhone(email_or_phone).equals(ContentControl.IS_EMAIL)) {
			 sql = "SELECT Email FROM tblAccount WHERE Id='"+accountId+"' AND Email='" + email_or_phone +"'";
		 }
			String result  ="";
			Database database = new Database();
			try {
				database.connect();
				result = database.query(sql);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					database.close();
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//FOR LOG:
			if(Log.DEBUG) {
				if(result.length() > 0)
					Log.println(TAG, "accountIsExists:-> account is exists.");
			}
			if(result.length() > 0)
				return true;//retrun true if is account exists.
		return false;  
	}
	
	
	/**
	 * Send verification code to user's email address.
	 * @param email_or_phone			//email value.
	 * @param verification_code  //verification code value.
	 * @return true if sent verification code is successful.
	 */
	private boolean sendVerificationCodeToEmail(String email_or_phone,int verification_code) {
		/*
		 * TODO: Hesap sahibine(E-Mail) "Doğrulama kodu(verification code)" gönder.
		 */
		Email email = new Email(email_or_phone,verification_code);
		boolean state = email.send();
		return state;
	}
	
	/**
	 * Send verification code to user's phone number as SMS.
	 * @param email_or_phone
	 * @return true if sent verification code is successful.
	 */
	private boolean sendVerificationCodeToPhonenumber(String email_or_phone) {
		/*
		 * TODO: Hesap sahibine(Phone) "doğrulama kodu(verification code)" gönder.
		 */
		return true;
	}
	
	/**
	 * Generate verification code. 
	 * Generate just six(6) digit.
	 * @return 
	 */
	private int generateVerificationCode() {
		int r1 = (int)(Math.random()*((9-0)+1))+0;
		int r2 = (int)(Math.random()*((9-0)+1))+0;
		int r3 = (int)(Math.random()*((9-0)+1))+0;
		int r4 = (int)(Math.random()*((9-0)+1))+0;
		int r5 = (int)(Math.random()*((9-0)+1))+0;
		int r6 = (int)(Math.random()*((9-0)+1))+0;
		String strR = r1 +""+r2+""+r3+""+r4+""+r5+""+r6;
		return Integer.valueOf(strR);
	}
	
//#############################################internal functions:@}
	
	
}

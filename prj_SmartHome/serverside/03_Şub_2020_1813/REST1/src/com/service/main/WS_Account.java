package com.service.main;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.service.sub.Account;
import com.database.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * -WEB SERVICE-
 * @author raptiye
 * 
 * *Kullanıcı Hesap İşlemleri*
 * +Login     //e-mail veya telefon numarası ile login.
 * +Create    //Hesap oluştur.
 * +Edit	  //Hesap bilgilerini düzenle.
 * +Forgot Password //Unutulmuş login şifresini sıfırla.
 * +
 */

@Path("/Account")       ////http://localhost:8080/REST1/Account
public class WS_Account {

	/**
	 * Kullanıcı Login işlemi.
	 * @param EmailOrPhone //must contain the "@" character if content is email.Otherwise all must contain digits for telephone number. These will be controlled by MobilApplication.
	 * @param Password		 //user password. As md5 value
	 * @return				//return account info(Id,Email,PhoneNumber) if login is successfuly as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/Login/{EmailOrPhone}/{Password}")
	public Response Login(@PathParam("EmailOrPhone") String EmailOrPhone,
						  @PathParam("Password") String Password){
		/*Test WebService URL:
		 * http://localhost:8080/REST1/Account/Login/kerimfirat@gmail.com/123456
		 */
		 Account account = new Account();
	     String login_response= account.login(EmailOrPhone,Password);
		 return Response.status(200).entity(login_response).build();
	}//login
	
	
	/**
	 * Hesap Oluştur.
	 * @param EmailOrPhone		//must contain the "@" character if content is email.Otherwise all must contain digits for telephone number. These will be controlled by MobilApplication.
	 * @param VerificationCode		//verification code
	 * @param Password				//password for be login.As md5 value
	 * @param AccountLocation		//for account location and determine Time Zone.
	 * @return						//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/CreateAccount/{EmailOrPhone}/{VerificationCode}/{Password}/{AccountLocation}")
	public Response CreateNewAccount(@PathParam("EmailOrPhone")String EmailOrPhone,
			                         @PathParam("VerificationCode")String VerificationCode,
			                         @PathParam("Password")String Password,
			                         @PathParam("AccountLocation")String AccountLocation) {
		/*Test WebService URL:
		 * http://localhost:8080/REST1/Account/CreateAccount/kertenkele@test.com/554106/password123/Turkey
		 */
		Account account = new Account();
		String create_state = account.createNewAccount(EmailOrPhone, VerificationCode, Password,AccountLocation);
		String response = create_state;//return true if create account is succesfuly.
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Hesap Değiştir/Güncelle
	 * @param AccountId		//For edit. exp: "where AccountId = 44"
	 * @param AccountName   //Account(email_or_phone)
	 * @param Nickname		// user's nickname.
	 * @return				//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditNickName/{AccountId}/{AccountName}/{Nickname}")
	public Response EditNickname(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
								 @PathParam("Nickname")String Nickname) {
		/*
		 * http://localhost:8080/REST1/Account/EditNickName/44/kerimfirat@gmail.com/my_nickname
		 */
		Account account = new Account();
		String response = account.editAccount(Account.NICKNAME, AccountId, AccountName, Nickname, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**  
	 *  For bind mobile phone to account(Cep telefonu bağla). !!!!ATTENTION!!!:checks if the number is a member
	 * @param AccountId		//For edit. exp: "where AccountId = 44"
	 * @param AccountName	//Account(email_or_phone)
	 * @param PhoneNumber	//Phone number for bind.
	 * @return				//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/BindMobilePhone/{AccountId}/{AccountName}/{PhoneNumber}")
	public Response BindMobilePhone(@PathParam("AccountId")int AccountId,
			                        @PathParam("AccountName")String AccountName,
									@PathParam("PhoneNumber")String PhoneNumber) {
		/*
		 *  http://localhost:8080/REST1/Account/BindMobilePhone/44/kerimfirat@gmail.com/05439728313
		 */
		Account account = new Account();
		String response = account.editAccount(Account.BIND_MOBILE_PHONE, AccountId, AccountName, PhoneNumber, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit account location(Hesap konumu). exp: Turkey
	 * @param AccountId			//For edit. exp: "where AccountId = 44"
	 * @param AccountName 		//Account(email_or_phone)
	 * @param AccountLocation	//for Account location. exp:Turkey
	 * @return					//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAccountLocation/{AccountId}/{AccountName}/{AccountLocation}")
	public Response EditAccountLocation(@PathParam("AccountId")int AccountId,
			                            @PathParam("AccountName")String AccountName,
										@PathParam("AccountLocation")String AccountLocation) {
		/*
		 *  http://localhost:8080/REST1/Account/EditAccountLocation/44/kerimfirat@gmail.com/Turkiye
		 */
		Account account = new Account();
		String response = account.editAccount(Account.ACCOUNT_LOCATION, AccountId,AccountName, AccountLocation, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Change account(login) password. 
	 * @param AccountId			//For edit. exp: "where AccountId = 44"
	 * @param AccountName       //Account(email_or_phone)
	 * @param Password			//new Account login password.As md5 value
	 * @param VerificationCode  //because we requested a Verification code for to change the password.
	 * @return					//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/ChangeLoginPassword/{AccountId}/{AccountName}/{Password}/{VerificationCode}")
	public Response ChangeLoginPassword(@PathParam("AccountId")int AccountId,
										@PathParam("AccountName")String AccountName,
										@PathParam("Password")String Password,
										@PathParam("VerificationCode")int VerificationCode) {
		/*
		 *  http://localhost:8080/REST1/Account/ChangeLoginPassword/44/kerimfirat@gmail.com/yenisifrem123/554106
		 */
		
		Account account = new Account();
		String response = account.editAccount(Account.CHANGE_LOGIN_PASSWORD, AccountId,AccountName, Password, VerificationCode);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * 	For edit "Temperature Unit". 
	 * @param AccountId			//For edit. exp: "where AccountId = 44"
	 * @param AccountName		//Account(email_or_phone)
	 * @param TemperatureUnit	//temperature unit (sıcaklık birimi)
	 * @return					//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditTemperatureUnit/{AccountId}/{AccountName}/{TemperatureUnit}")
	public Response EditTemperatureUnit(@PathParam("AccountId")int AccountId,
				 						@PathParam("AccountName")String AccountName,
										@PathParam("TemperatureUnit")String TemperatureUnit) {
		/*
		 * http://localhost:8080/REST1/Account/EditTemperatureUnit/44/kerimfirat@gmail.com/F
		 */
		Account account = new Account();
		String response = account.editAccount(Account.TEMPERATURE_UNIT, AccountId,AccountName, TemperatureUnit, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * For edit "Time Zone".
	 * @param AccountId	//For edit. exp: "where AccountId = 44"
	 * @param AccountName //Account(email_or_phone)
	 * @param TimeZone	//Time zone. exp: "Turkey"
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditTimeZone/{AccountId}/{AccountName}/{TimeZone}")
	public Response EditTimeZone(@PathParam("AccountId")int AccountId,
								 @PathParam("AccountName")String AccountName,
								 @PathParam("TimeZone")String TimeZone) {
		/*
		 * http://localhost:8080/REST1/Account/EditTimeZone/44/kerimfirat@gmail.com/TurkeyS
		 */
		Account account = new Account();
		String response = account.editAccount(Account.TIME_ZONE, AccountId,AccountName, TimeZone, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * "Hesap İşlemleri" Get verification code for "New Account".
	 * @param EmailOrPhone	//must contain the "@" character if content is email.Otherwise all must contain digits for telephone number. These will be controlled by MobilApplication.
	 * @return				//3=account not exists. 2=account already exists. 1=successful. 0=unsuccessful.  as JSON
	 */
	@GET
	@Produces("application/json")
	@Path("/GetVerificationCode-NewAccount/{EmailOrPhone}")
	public Response GetVerificationCode_NewAccount(@PathParam("EmailOrPhone")String EmailOrPhone) {
		/*Webservice test URL:
		 * http://localhost:8080/REST1/Account/GetVerificationCode-NewAccount/test@test
		 */
        Account account = new Account();
        String response =  account.getVerificationCode(Account.GET_VERIFICATION_CODE_FOR_NEW_ACCOUNT,EmailOrPhone);
		return Response.status(200).entity(response).build();//look at the subfunction.
	}
	
	/** 
	 * "Hesap İşlemleri" Get verification code for "Forgot Password".
	 * @param EmailOrPhone	//must contain the "@" character if content is email.Otherwise all must contain digits for telephone number. These will be controlled by MobilApplication.
	 * @return				//3=account not exists. 2=account already exists. 1=successful. 0=unsuccessful.  as JSON
	 */
	@GET
	@Produces("application/json")
	@Path("/GetVerificationCode-ForgotPassword/{EmailOrPhone}")
	public Response GetVerificationCode_ForgotPassword(@PathParam("EmailOrPhone")String EmailOrPhone){
		/*Webservice test URL:
		 * http://localhost:8080/REST1/Account/GetVerificationCode-ForgotPassword/test@test
		 */
        Account account = new Account();
        String response =  account.getVerificationCode(Account.GET_VERIFICATION_CODE_FOR_FORGOT_PASSWORD,EmailOrPhone);
		return Response.status(200).entity(response).build();//look at the subfunction.
	}
	
	/**
	 * "Hesap İşlemleri" Get verification code for "Change Password".	
	 * @param EmailOrPhone	//must contain the "@" character if content is email.Otherwise all must contain digits for telephone number. These will be controlled by MobilApplication.
	 * @return				//3=account not exists. 2=account already exists. 1=successful. 0=unsuccessful.  as JSON
	 */
	@GET
	@Produces("application/json")
	@Path("/GetVerificationCode-ChangePassword/{EmailOrPhone}")
	public Response GetVerificationCode_ChangePassword(@PathParam("EmailOrPhone")String EmailOrPhone) {
		/*Webservice test URL:
		 * http://localhost:8080/REST1/Account/GetVerificationCode-ChangePassword/test@test
		 * 
		 */
		Account account = new Account();
		String response = account.getVerificationCode(Account.GET_VERIFICATION_CODE_FOR_CHANGE_PASSWORD, EmailOrPhone);
		return Response.status(200).entity(response).build();//look at the subfunction.
	}
	
	/**
	 * Get account Item.
	 * @param AccountId	//which account?
	 * @param AccountName //Account(email_or_phone)
	 * @return			//return account info as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAccount/{AccountId}/{AccountName}")
	public Response GetAccount(@PathParam("AccountId")int AccountId,
			                   @PathParam("AccountName")String AccountName) {
		/*
		 * http://localhost:8080/REST1/Account/GetAccount/1/kerimfirat@gmail.com
		 */
		Account account = new Account();
		String response = account.getAccount(AccountId,AccountName);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove account.
	 * @param AccountId //which account
	 * @param AccountName //Account(email_or_phone)
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveAccount/{AccountId}/{AccountName}")
	public Response RemoveAccount(@PathParam("AccountId")int AccountId,
			                      @PathParam("AccountName")String AccountName) {
		/*
		 *http://localhost:8080/REST1/Account/RemoveAccount/6/kerimfirat@gmail.com
		 */
		Account account = new Account();
		String response = account.removeAccount(AccountId,AccountName);
		return Response.status(200).entity(response).build();
	}
	
}//class

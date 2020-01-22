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
	 * @param Password		 //user password.
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
	 * @param Password				//password for be login.
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
	 * @param Nickname		// user's nickname.
	 * @return				//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditNickName/{AccountId}/{Nickname}")
	public Response EditNickname(@PathParam("AccountId")int AccountId,
								 @PathParam("Nickname")String Nickname) {
		/*
		 * http://localhost:8080/REST1/Account/EditNickName/44/my_nickname
		 */
		Account account = new Account();
		String response = account.editAccount(Account.NICKNAME, AccountId, Nickname, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**  
	 *  For bind mobile phone to account(Cep telefonu bağla). !!!!ATTENTION!!!:checks if the number is a member
	 * @param AccountId		//For edit. exp: "where AccountId = 44"
	 * @param PhoneNumber	//Phone number for bind.
	 * @return				//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/BindMobilePhone/{AccountId}/{PhoneNumber}")
	public Response BindMobilePhone(@PathParam("AccountId")int AccountId,
									@PathParam("PhoneNumber")String PhoneNumber) {
		/*
		 *  http://localhost:8080/REST1/Account/BindMobilePhone/44/05439728313
		 */
		Account account = new Account();
		String response = account.editAccount(Account.BIND_MOBILE_PHONE, AccountId, PhoneNumber, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Edit account location(Hesap konumu). exp: Turkey
	 * @param AccountId			//For edit. exp: "where AccountId = 44"
	 * @param AccountLocation	//for Account location. exp:Turkey
	 * @return					//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditAccountLocation/{AccountId}/{AccountLocation}")
	public Response EditAccountLocation(@PathParam("AccountId")int AccountId,
										@PathParam("AccountLocation")String AccountLocation) {
		/*
		 *  http://localhost:8080/REST1/Account/EditAccountLocation/44/Turkiye
		 */
		Account account = new Account();
		String response = account.editAccount(Account.ACCOUNT_LOCATION, AccountId, AccountLocation, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Change account(login) password. 
	 * @param AccountId			//For edit. exp: "where AccountId = 44"
	 * @param Password			//new Account login password.
	 * @param VerificationCode  //because we requested a Verification code for to change the password.
	 * @return					//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/ChangeLoginPassword/{AccountId}/{Password}/{VerificationCode}")
	public Response ChangeLoginPassword(@PathParam("AccountId")int AccountId,
										@PathParam("Password")String Password,
										@PathParam("VerificationCode")int VerificationCode) {
		/*
		 *  http://localhost:8080/REST1/Account/ChangeLoginPassword/44/yenisifrem123/554106
		 */
		
		Account account = new Account();
		String response = account.editAccount(Account.CHANGE_LOGIN_PASSWORD, AccountId, Password, VerificationCode);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * 	For edit "Temperature Unit". 
	 * @param AccountId			//For edit. exp: "where AccountId = 44"
	 * @param TemperatureUnit	//temperature unit (sıcaklık birimi)
	 * @return					//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditTemperatureUnit/{AccountId}/{TemperatureUnit}")
	public Response EditTemperatureUnit(@PathParam("AccountId")int AccountId,
										@PathParam("TemperatureUnit")String TemperatureUnit) {
		/*
		 * http://localhost:8080/REST1/Account/EditTemperatureUnit/44/F
		 */
		Account account = new Account();
		String response = account.editAccount(Account.TEMPERATURE_UNIT, AccountId, TemperatureUnit, 0/*ignore*/);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * For edit "Time Zone".
	 * @param AccountId	//For edit. exp: "where AccountId = 44"
	 * @param TimeZone	//Time zone. exp: "Turkey"
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/EditTimeZone/{AccountId}/{TimeZone}")
	public Response EditTimeZone(@PathParam("AccountId")int AccountId,
								 @PathParam("TimeZone")String TimeZone) {
		/*
		 * http://localhost:8080/REST1/Account/EditTimeZone/44/TurkeyS
		 */
		Account account = new Account();
		String response = account.editAccount(Account.TIME_ZONE, AccountId, TimeZone, 0/*ignore*/);
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
		 * 
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
		 * 
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
	 * @return			//return account info as JSON.
	 */
	@GET
	@Produces("application/json")
	@Path("/GetAccount/{AccountId}")
	public Response GetAccount(@PathParam("AccountId")int AccountId) {
		/*
		 * http://localhost:8080/REST1/Account/GetAccount/1
		 */
		Account account = new Account();
		String response = account.getAccount(AccountId);
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Remove account.
	 * @param AccountId //which account
	 * @return			//return true(as JSON) if successful.
	 */
	@GET
	@Produces("application/json")
	@Path("/RemoveAccount/{AccountId}")
	public Response RemoveAccount(@PathParam("AccountId")int AccountId) {
		/*
		 *http://localhost:8080/REST1/Account/RemoveAccount/6
		 */
		Account account = new Account();
		String response = account.removeAccount(AccountId);
		return Response.status(200).entity(response).build();
	}
	
}//class

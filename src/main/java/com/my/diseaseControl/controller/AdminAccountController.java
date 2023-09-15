package com.my.diseaseControl.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.AdminAccountDao;
import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.Project;
import com.my.diseaseControl.vo.ProjectInfo;




@Controller
public class AdminAccountController {

	@Resource(name="AdminAccountDao")
	private AdminAccountDao adminAccountDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;	
	
	
	
	
	//AdminAccountTable로부터 모든 admin의 정보를 가져오기
	@RequestMapping(value="/get_all_admin_account", method=RequestMethod.GET)
	public @ResponseBody List<AdminAccount> get_all_admin_account(
			Model model,
			HttpSession session
			) {
//		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
//		System.out.println(adminUser);
//		if(adminUser != null) {
//			System.out.println("not empty!");
//		} else {
//			System.out.println("empty!");
//		}
		
//		List<PilotAccount> list = new ArrayList<>(); //빈 배열 생성
		
		List<AdminAccount> adminAccountAll = adminAccountDao.getAll();
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		 
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 관리자들의 정보를 불러왔습니다");
			log.setActionType("관리자 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 관리자들의 정보를 불러왔습니다");
			log.setActionType("관리자 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return adminAccountAll;
	}	
	
	
	
	//AdminAccountTable에 새로운 관리자계정을 만들어주기전에 동일한 아이디가 이미 있는지 확인하기 
	@RequestMapping(value="checkAdminID", method=RequestMethod.GET)
	public @ResponseBody String checkAdminID(
			@RequestParam(value="AdminID") String AdminID
			
			) {
				
		AdminAccount adminAccount = new AdminAccount();
		adminAccount.setAdminID(AdminID);
		
		AdminAccount adminIDchecked = adminAccountDao.selectAdminAccount(adminAccount); //해당 이메일이나 아이디가 없으면, 즉 값이 비어있으면 js의 API 요청으로 insertUser로 넘어가기
		
		if(adminIDchecked != null) {
			return "existed already";
		} else {
			return "not existed";
		}		
		
	}	
	
	
	//AdminAccountTable에 새로운 관리자계정 넣어주기
	@RequestMapping(value="/insert_admin_account", method=RequestMethod.GET)
	public @ResponseBody String insert_admin_account(
				@RequestParam(value="AdminID") String AdminID,
				@RequestParam(value="Password") String Password,
				@RequestParam(value="Name") String Name,
				@RequestParam(value="Position") String Position,
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				@RequestParam(value="Email") String Email,
				@RequestParam(value="Type") String Type,
				@RequestParam(value="AccountCreationDeletionAbility") Boolean AccountCreationDeletionAbility,
				HttpSession session
			) throws NoSuchAlgorithmException {
		
//		AdminAccount loginAdminAccount = (AdminAccount) session.getAttribute("loginUser1");
//		if (loginAdminAccount != null) {
//			System.out.println("something!!");
//	    } else {
//	    	System.out.println("nothing!!");
//	    }
		
		String sha_256_pwd_salt = "";
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		// SALT 생성     
		String salt_key = new String(Base64.getEncoder().encode(bytes));
        System.out.println(salt_key);
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// password 암호화
		md.update(Password.getBytes());
		Password = String.format("%064x", new BigInteger(1, md.digest())); //password의 해시값
		String passwordAndSalt = Password + salt_key;
		System.out.println(passwordAndSalt);
		// password + salt 암호화
		md.update(passwordAndSalt.getBytes());
		sha_256_pwd_salt = String.format("%064x", new BigInteger(1, md.digest())); //password+salt 해시값		
		
		
		AdminAccount newAdmin = new AdminAccount();
		newAdmin.setAdminID(AdminID);
		newAdmin.setPassword(Password);
		newAdmin.setSalt_key(salt_key);
		newAdmin.setSha_256_pwd_salt(sha_256_pwd_salt);
		newAdmin.setName(Name);
		newAdmin.setPosition(Position);
		newAdmin.setPhoneNumber(PhoneNumber);
		newAdmin.setEmail(Email);
		newAdmin.setType(Type);
		newAdmin.setAccountCreationDeletionAbility(AccountCreationDeletionAbility);

		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		
		if((adminUser != null && adminUser.getAccountCreationDeletionAbility() == true) || adminUser.getType() == "마스터") { //관리자의 타입이 마스터거나 생성및삭제 항목이 true일때 
			//새로운 관리자 추가해주기
			adminAccountDao.insertAdminAccount(newAdmin);		
			
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 새로운 관리자" + AdminID + " 를 생성하였습니다");
			log.setActionType("관리자 셍성하기"); 
			log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
			
			return "ok";
						
		}
 				
		
		return "no";
	}
	
	
	
	
	
	//AdminAccountTable에서 관리자계정 update해주기
	@RequestMapping(value="/update_admin_account", method=RequestMethod.GET)
	public @ResponseBody String update_admin_account(
//				@RequestParam(value="AdminID") String AdminID,
				@RequestParam(value="Password") String Password,
				@RequestParam(value="Name") String Name,
				@RequestParam(value="Position") String Position,
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				@RequestParam(value="Email") String Email,
				@RequestParam(value="Type") String Type,
				@RequestParam(value="AccountCreationDeletionAbility") Boolean AccountCreationDeletionAbility,
				HttpSession session
			) throws NoSuchAlgorithmException {
		
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
//		System.out.println("adminUser: " + adminUser);
//		if(adminUser != null) {
//			System.out.println("something!");
//		} else {
//			System.out.println("empty!");
//		}
	
		if(adminUser != null) {  		
			
			String sha_256_pwd_salt = ""; 
			// "SHA1PRNG"은 알고리즘 이름
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			// DB에 저장된 SALT를 해당 유저로 부터 가져오기      
			String salt_key = adminUser.getSalt_key();
	        System.out.println(salt_key);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// password 암호화
			md.update(Password.getBytes());
			Password = String.format("%064x", new BigInteger(1, md.digest())); //password의 해시값
			String passwordAndSalt = Password + salt_key;
			System.out.println(passwordAndSalt);
			// password + salt 암호화
			md.update(passwordAndSalt.getBytes());
			sha_256_pwd_salt = String.format("%064x", new BigInteger(1, md.digest())); //password+salt 해시값		
			
			AdminAccount updatedAdmin = new AdminAccount();
			updatedAdmin.setAdminID(adminUser.getAdminID());
			updatedAdmin.setPassword(Password);
			updatedAdmin.setSha_256_pwd_salt(sha_256_pwd_salt);
			updatedAdmin.setName(Name);
			updatedAdmin.setPosition(Position);
			updatedAdmin.setPhoneNumber(PhoneNumber);
			updatedAdmin.setEmail(Email);
			updatedAdmin.setType(Type);
			updatedAdmin.setAccountCreationDeletionAbility(AccountCreationDeletionAbility);			
			
			
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 자신의 계정 정보를 업데이트 하였습니다");
			log.setActionType("계정 업데이트"); 
			log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
			
			//관리자정보 엡테이트해주기
			adminAccountDao.updateAdminAccount(updatedAdmin);
			
			return "ok";
						
		}
		
		return "no";
	}	
	
	

	//관리자 계정 삭제하기  **DB에서 AdminAccountTable의 AdminAccountIndex가 다른 테이블의 AdminAccountIndex와 Foreign key와 연결되어있어 삭제가 곤란한 상테이다. 만약 실행시 500에러 발생**
	@RequestMapping(value="/delete_admin_account", method=RequestMethod.GET)
	public @ResponseBody String delete_admin_account(
				@RequestParam(value="AdminID") String AdminID,
				@RequestParam(value="Name") String Name,
				HttpSession session
			) {
		
		AdminAccount admin = (AdminAccount) session.getAttribute("loginAdminUser"); 
		
		Optional<AdminAccount> adminloggedin = Optional.ofNullable(admin);
		System.out.println("adminloggedin: " + adminloggedin);
		
		if(adminloggedin != null) {
			System.out.println(admin.getType());
			if(admin.getType() == "인턴" || admin.getType() == "행정인" ) {
				return "not permitted";
			}
			
			AdminAccount adminAccount = new AdminAccount();
			adminAccount.setAdminID(AdminID);
			adminAccount.setName(Name);
			
			adminAccountDao.deleteAdminAccount(adminAccount);
			
			return "ok";			
		} else {
			return "not ok";
		}
		
	
	}
	
	
	
	
	//로그인 요청하기 
	@RequestMapping(value="/adminLogin", method=RequestMethod.GET)
	public @ResponseBody int adminLogin (
				@RequestParam(value="AdminID") String AdminID,
				@RequestParam(value="Password") String Password,
				HttpSession session
			) throws NoSuchAlgorithmException {
		
		//암호화에  사용되는 코드
		MessageDigest md = MessageDigest.getInstance("SHA-256");  
		
		// password 암호화
		md.update(Password.getBytes());
		Password = String.format("%064x", new BigInteger(1, md.digest())); //password의 해시값
		System.out.println(Password); //유저의 비번 암호화

		
		AdminAccount adminAccount = new AdminAccount();
		adminAccount.setAdminID(AdminID);
		adminAccount.setPassword(Password);
		//user.setSalt_key(salt_key);
		//user.setSha_256_pwd_salt(sha256_pwd_salt);
		
		AdminAccount loginAdminUser = adminAccountDao.selectAdminAccountByAdminIDAndPassword(adminAccount); 
		//System.out.println(loginUser.getSha_256_pwd_salt()); //db로부터 받아온 해당 유저의 암호+솔트의 sha256
		//System.out.println(loginUser.getSalt_key()); //db로부터 받아온 해당 유저의 고정 솔트 
		System.out.println("loginAdminUser: " + loginAdminUser);
		
		
		String sha_256_pwd_salt1 = ""; 
		
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);

		
		Optional<AdminAccount> loginUser1 = Optional.ofNullable(loginAdminUser);
		System.out.println("loginUser1: " + loginUser1);
		
		if(loginUser1.isPresent()) {
			String passwordAndSalt = Password + loginAdminUser.getSalt_key();  //유저의 암호화된 비번 + db로부터 받아온 해당 유저의 고정 솔트
			md.update(passwordAndSalt.getBytes());
			sha_256_pwd_salt1 = String.format("%064x", new BigInteger(1, md.digest())); //password+salt를 sha256으로
			System.out.println("sha_256_pwd_salt1: " + sha_256_pwd_salt1);
			
			if(loginAdminUser.getSha_256_pwd_salt().equals(sha_256_pwd_salt1)) { //받아온 loginUser.sha_256_pwd_salt와 유저가 로그인 요청으로 입력한  password + loginUser.salt_key의 해시값이 동일하다면 로그인 해준다
				
				//로그인 헤주기
				session.setAttribute("loginAdminUser", loginAdminUser); 

				//로그입력 
				Log log = new Log();
				log.setLogContent(loginAdminUser.getAdminID() + " 관리자가 로그인 하였습니다");
				log.setActionType("로그인"); 
				log.setActivator(loginAdminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
				
				logDao.insertLog(log); //db에 로그정보 삽입
				
				return 111; 
			}
	    }	
		return 101;

	}	
	
	
	
	//관리자 로그아웃 
	@RequestMapping(value="/admin_logout", method=RequestMethod.GET)
	public @ResponseBody String admin_logout(
				HttpSession session
			) {
		
		//로그인 헤주기
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser"); 		
		
		//로그아웃
		session.invalidate(); 
			
		//로그입력 
		Log log = new Log();
		log.setLogContent(adminUser.getAdminID() + " 관리자가 로그아웃 하였습니다");
		log.setActionType("로그아웃"); 
		log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
		
		logDao.insertLog(log); //db에 로그정보 삽입	
		
		return "ok";
	}
	
	
	
//	//방역자의 licenseNumber로 요청시 진행중인 프로젝트들의 마감기한, 주소, 프로젝트를 할당한 관리자의 소속과 이름을 가져오기
//	@RequestMapping(value="/projectInfo_adminInfo_byLicenseNumber", method=RequestMethod.GET)
//	public @ResponseBody List<Project> projectInfo_adminInfo_byLicenseNumber(
//				@RequestParam(value="LicenseNumber") String LicenseNumber
//			) {
//		
//		//프로젝트로부터 일치하는 해당 라이센스번호와 ongoing인 컬럼들을 모두 가져오기 
//		List<Project> projectInfo_adminInfo_byLicenseNumber = adminAccountDao.getAllprojectInfo_adminInfo_byLicenseNumber(LicenseNumber);
//		//for문으로 접근하기pro
//		for (Project project : projectInfo_adminInfo_byLicenseNumber) {
//			int adminAccountIndex = project.getAdminAccountIndex();
//			AdminAccount adminInfo = adminAccountDao.getAdminInfoByIndex(adminAccountIndex);
//			//System.out.println(adminInfo);
//			//System.out.println(adminInfo.getName());
//			project.setName(adminInfo.getName());
//			project.setPosition(adminInfo.getPosition());
//		}
//		//List<Project> list = new ArrayList<Project>(); //빈 배열 생성
//		//System.out.println(list);
//		
//		return projectInfo_adminInfo_byLicenseNumber;
//	}	
	
	
	//방역자의 Licensenumber로 요청시 진행중인 프로젝트들의 1)마감기한, 2)방역지주소, 그리고 프로젝트를 할당한 3)관리자의 소속, 4)이름을 가져오기
	@RequestMapping(value="/projectInfo_adminInfo_byLicenseNumber", method=RequestMethod.GET)
	public @ResponseBody List<ProjectInfo> projectInfo_adminInfo_byLicenseNumber(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				HttpSession session
			) {
		
		// 프로젝트로부터 일치하는 해당 라이센스번호와 ongoing인 컬럼들을 모두 가져오기 
		List<Project> projectInfo_adminInfo_byLicenseNumber = adminAccountDao.getAllprojectInfo_adminInfo_byLicenseNumber(LicenseNumber);
		
		List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>(); // 새로운 리스트 생성
		
		// for문으로 접근하기
		for (Project project : projectInfo_adminInfo_byLicenseNumber) {
			int adminAccountIndex = project.getAdminAccountIndex();
			AdminAccount adminInfo = adminAccountDao.getAdminInfoByIndex(adminAccountIndex);
			
			// 필요한 정보만 가지고 있는 객체 생성
			ProjectInfo projectInfo = new ProjectInfo();
			projectInfo.setProjectDeadLine(project.getProjectDeadLine());
			projectInfo.setAddress(project.getAddress());
			projectInfo.setPosition(adminInfo.getPosition());
			projectInfo.setName(adminInfo.getName());
			
			projectInfos.add(projectInfo); // 리스트에 추가
		}
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자가 맡은 프토렉트들의 1)마감기한, 2)방역지주소, 그리고 프로젝트를 할당한 3)관리자의 소속, 4)이름을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + LicenseNumber + " 방역자가 맡은 프토렉트들의 1)마감기한, 2)방역지주소, 그리고 프로젝트를 할당한 3)관리자의 소속, 4)이름을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return projectInfos;
	}


	
	//특정 AdminAccount에 대한 정보를 특정 AdminID로 가져오기
	@RequestMapping(value="/get_adminAccount_by_adminID", method=RequestMethod.GET)
	public @ResponseBody AdminAccount get_adminAccount_by_adminID(
				@RequestParam(value="AdminID") String AdminID,
				HttpSession session
			) {
		
		AdminAccount adminAccountByProjectID = adminAccountDao.getAdminAccountByProjectID(AdminID);		
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + AdminID + " 아이디를 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + AdminID + " 아이디를 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
				
		
		return adminAccountByProjectID;
	}
	

	//특정 PhoneNumber을 가진 특정 AdminAccount에 대한 정보를 가져오기
	@RequestMapping(value="/get_adminAccount_by_phoneNumber", method=RequestMethod.GET)
	public @ResponseBody AdminAccount get_adminAccount_by_phoneNumber(
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				HttpSession session
			) {
		
		AdminAccount adminAccountByPhoneNumber = adminAccountDao.getAdminAccountByPhoneNumber(PhoneNumber);		
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + PhoneNumber + " 전화번호를 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + PhoneNumber + " 전화번호를 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
		
		return adminAccountByPhoneNumber;
	}
	
	
	//특정 Named을 가진 AdminAccount에 대한 정보를 가져오기
	@RequestMapping(value="/get_adminAccount_by_name", method=RequestMethod.GET)
	public @ResponseBody List<AdminAccount> get_adminAccount_by_name(
				@RequestParam(value="Name") String Name,
				HttpSession session
			) {
		
		List<AdminAccount> adminAccountByName = adminAccountDao.getAdminAccountByName(Name);		
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Name + " 이름을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Name + " 이름을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
		
		return adminAccountByName;
	}	
	
	
	//특정 Position을 가진 AdminAccount에 대한 정보를 가져오기 
	@RequestMapping(value="/get_adminAccount_by_position", method=RequestMethod.GET)
	public @ResponseBody List<AdminAccount> get_adminAccount_by_position(
				@RequestParam(value="Position") String Position,
				HttpSession session
			) {
		
		List<AdminAccount> adminAccountByPosition = adminAccountDao.getAdminAccountByPosition(Position);		
		

		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Position + " 직위을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Position + " 직위을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return adminAccountByPosition;
	}	
	
	
	//특정 Type을 가진 AdminAccount에 대한 정보를 가져오기 
	@RequestMapping(value="/get_adminAccount_by_type", method=RequestMethod.GET)
	public @ResponseBody List<AdminAccount> get_adminAccount_by_type(
				@RequestParam(value="Type") String Type,
				HttpSession session
			) {
		
		List<AdminAccount> adminAccountByType = adminAccountDao.getAdminAccountByType(Type);	
		

		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Type + " 소속을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Type + " 소속을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return adminAccountByType;
	}
	
	
	//AdminAccount를 생성하거나 삭제 가능 여부 권한에 관해 AdminAccount에 대한 정보를 가져오기 //넘겨주는 파라미터는 true 혹은 false로 입력 //DB에서는 1(true)과 0(false)로 저장되어있음
	@RequestMapping(value="/get_adminAccount_by_accountCreationDeletionAbility", method=RequestMethod.GET)
	public @ResponseBody List<AdminAccount> get_adminAccount_by_accountCreationDeletionAbility(
				@RequestParam(value="AccountCreationDeletionAbility") Boolean AccountCreationDeletionAbility,
				HttpSession session
			) {
		
		List<AdminAccount> adminAccountByAccountCreationDeletionAbility = adminAccountDao.getAdminAccountByAccountCreationDeletionAbility(AccountCreationDeletionAbility);	
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + AccountCreationDeletionAbility + " 계정생성 권한을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + AccountCreationDeletionAbility + " 계정생성 권한을 가진 관리자의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
		
		return adminAccountByAccountCreationDeletionAbility;
	}	
	
}
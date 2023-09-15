package com.my.diseaseControl.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.dao.PilotAccountDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.Project;




@Controller
public class PilotAccountController {
	
	@Resource(name="PilotAccountDao")
	private PilotAccountDao pilotAccountDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;	

	
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		
		return "home";
	}
	
	
	//PilotAccountTable로부터 모든 pilot의 정보를 가져오기
	@RequestMapping(value="/get_all_pilot_account", method=RequestMethod.GET)
	public @ResponseBody List<PilotAccount> get_all_pilot_account(
			Model model,
			HttpSession session
			) {
		
		List<PilotAccount> pilotAccountAll = pilotAccountDao.getAll();
		
		//List<PilotAccount> list = new ArrayList<>(); //빈 배열 생성
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 방역자들의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 방역자들의 계정 정보를 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}
		
		
		return pilotAccountAll;
	} 
	
	
	
	
	//PilotAccountTable에 새로운 방역자 계정을 만들어주기전에 동일한 라이센스 혹은 전화번호가 이미 있는지 확인하기 
	@RequestMapping(value="checkLicenseNumber", method=RequestMethod.GET)
	public @ResponseBody String checkLicenseNumber(	
	        @RequestParam(value="LicenseNumber") String LicenseNumber,
	        @RequestParam(value="PhoneNumber") String PhoneNumber,
	        HttpSession session
			) {
	    PilotAccount pilotAccount = new PilotAccount();
	    pilotAccount.setLicenseNumber(LicenseNumber);
	    pilotAccount.setPhoneNumber(PhoneNumber);
	    
	    PilotAccount pilotAccountChecked = pilotAccountDao.selectPilotAccountByLicenseNumberORPhoneNumber(pilotAccount);
	    
	    if (pilotAccountChecked != null) {
	        if (pilotAccountChecked.getLicenseNumber().equals(LicenseNumber) && !pilotAccountChecked.getPhoneNumber().equals(PhoneNumber)) {
	            return "LicenseNumber already exists";
	        } else if (!pilotAccountChecked.getLicenseNumber().equals(LicenseNumber) && pilotAccountChecked.getPhoneNumber().equals(PhoneNumber)) {
	            return "PhoneNumber already exists";
	        } else {
	            return "not existing";
	        }
	    }
	    
	    return "not existing";
	}

	
	

	//새로운 방역자를 추가해주기
	@RequestMapping(value="/insert_pilot_account_by_admin", method=RequestMethod.GET)
	public @ResponseBody String insert_pilot_account_by_admin(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				@RequestParam(value="Name") String Name,
				@RequestParam(value="CarNumber") String CarNumber,
				@RequestParam(value="Drone") String Drone,
				HttpSession session
			) {
		
		PilotAccount newPilotAccount = new PilotAccount();
		newPilotAccount.setLicenseNumber(LicenseNumber);
		newPilotAccount.setPhoneNumber(PhoneNumber);
		newPilotAccount.setName(Name);
		newPilotAccount.setCarNumber(CarNumber);
		newPilotAccount.setDrone(Drone);
		
		//새로운 파일럿 추가해주기
		pilotAccountDao.insertNewPilotAccount(newPilotAccount); 
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		
		if(adminUser != null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자의 계정을 생성 하였습니다");
			log.setActionType("계정 생성하기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null) { //만약 관리자가 로그인하여 새로운 계정을 생성하지 않고, 방역자 스스로 가입해서 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(LicenseNumber + " 방역자가 자신의 계정을 생성 하였습니다");
			log.setActionType("계정 생성하기"); 
			log.setActivator(LicenseNumber);   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return "ok";
	}
	
	
	//특정 라이센스번호를 가진 방역자의 정보를 update 해주기. //실행전 관리자가 미리 로그인 해야함
	@RequestMapping(value="/update_pilot_account_by_admin", method=RequestMethod.GET)
	public @ResponseBody String update_pilot_account_by_admin(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				@RequestParam(value="Name") String Name,
				@RequestParam(value="CarNumber") String CarNumber,
				@RequestParam(value="Drone") String Drone,
				HttpSession session
			) {
		PilotAccount pilotAccount = new PilotAccount();
		pilotAccount.setLicenseNumber(LicenseNumber);
		pilotAccount.setPhoneNumber(PhoneNumber);
		pilotAccount.setName(Name);
		pilotAccount.setCarNumber(CarNumber);
		pilotAccount.setDrone(Drone);	
		
		pilotAccountDao.updatePilotAccount(pilotAccount);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자의 계정을 업데이트 하였습니다");
			log.setActionType("계정 업데이트"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
			
			return "ok";
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 자신의 계정을 업데이트 하였습니다");
			log.setActionType("계정 업데이트"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
			
			return "ok";
		}
		
		return "must login first";
		
	}
	
	
	//관리자가 방역자의 게정을 삭제 시키기
	@RequestMapping(value="/delete_pilot_account", method=RequestMethod.GET)
	public @ResponseBody String delete_pilot_account(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="Name") String Name,
				HttpSession session
			) {
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		if(adminUser != null) {
			PilotAccount pilotAccount = new PilotAccount();
			pilotAccount.setLicenseNumber(LicenseNumber);
			pilotAccount.setName(Name);
			
			pilotAccountDao.deletePilotAccount(pilotAccount);
			
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자의 계정을 삭제 하였습니다");
			log.setActionType("계정 삭제"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
			
			return "ok";				
		}
		
		return "no";

	}
	
	
	
	//방역자의 계정을 잠그기
	@RequestMapping(value="/lock_pilot_account", method=RequestMethod.GET)
	public @ResponseBody String lock_pilot_account(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="Name") String Name,
				HttpSession session
			) {
		
		PilotAccount pilotAccount = new PilotAccount();
		pilotAccount.setLicenseNumber(LicenseNumber);
		pilotAccount.setName(Name);
		
		pilotAccountDao.lockPilotAccount(pilotAccount);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자의 계정을 잠궜습니다");
			log.setActionType("계정 잠금"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 자신의 계정을 잠궜습니다");
			log.setActionType("계정 잠금"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}
		
		return "ok";

	}	
	
	
	//잠긴 방역자의 계정을 풀기
	@RequestMapping(value="/unlock_pilot_account", method=RequestMethod.GET)
	public @ResponseBody String unlock_pilot_account(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="Name") String Name,
				HttpSession session
			) {
		
		PilotAccount pilotAccount = new PilotAccount();
		pilotAccount.setLicenseNumber(LicenseNumber);
		pilotAccount.setName(Name);
		
		pilotAccountDao.unlockPilotAccount(pilotAccount);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자의 잠긴 계정을 풀었습니다");
			log.setActionType("계정 잠금 풀기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 자신의 잠긴 계정을 풀었습니다");
			log.setActionType("계정 잠금 풀기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}
		
		return "ok";

	}	
	
	

	//로그인 요청하기 - 휴대폰인증
	@RequestMapping(value="/pilot_login_verification_process", method=RequestMethod.GET)
	public @ResponseBody int pilot_login_verification_process (
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="PhoneNumber") String PhoneNumber,
//				@RequestParam(value="Name") String Name,
				HttpSession session
			) {  
		
		PilotAccount pilotAccount = new PilotAccount();
//		pilotAccount.setLicenseNumber(LicenseNumber);
//		pilotAccount.setName(Name);
		pilotAccount.setLicenseNumber(LicenseNumber);
		pilotAccount.setPhoneNumber(PhoneNumber);

		
		PilotAccount loginPilotAccount = pilotAccountDao.selectPilotAccountByLicenseNumberAndPhoneNumber(pilotAccount);
		//System.out.println(loginPilotAccount.getLicenseNumber());
		//System.out.println(loginUser.getSha_256_pwd_salt()); //db로부터 받아온 해당 유저의 암호+솔트의 sha256
		//System.out.println(loginUser.getSalt_key()); //db로부터 받아온 해당 유저의 고정 솔트 

		
		//해당 파일럿의 전화번호를 가져와서 휴대폰인증을 실행하기. 인증번호가 맞다면 조건식으로 로그인 작동하기And
		//String pilotPhoneNumber = loginPilotAccount.getPhoneNumber();

		
		Optional<PilotAccount> loginUser1 = Optional.ofNullable(loginPilotAccount);
		System.out.println("loginUser1: " + loginUser1);
		
		if(loginUser1.isPresent()) {
			//session.setAttribute("loginUser", loginPilotAccount); //로그인 헤주기
			

//			AccountTable user1 = new AccountTable();
//			user1.setId(loginUser.getId());
//			user1.setName(loginUser.getName());
//			user1.setPermission(loginUser.getPermission());
//			user1.setActivity("login");
//			
//			accountTableDao.insertLog(user1); //로그인시 db에 로그인한 시간정보 넣어주기

			
			return 111; 
			}
			
		return 101; //로그인실패

	}	

	
	

    //로그인 요청하기 //해당 로직은 js로직에 의해 인증번호가 통과되었을때 자동 실행된다
	@RequestMapping(value="/pilot_login", method=RequestMethod.GET)
	public @ResponseBody String pilot_login (
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				HttpSession session
			) {
		
		PilotAccount pilotAccount = new PilotAccount();
		pilotAccount.setLicenseNumber(LicenseNumber);
		pilotAccount.setPhoneNumber(PhoneNumber);
		
		PilotAccount loginPilot = pilotAccountDao.selectPilotAccountByLicenseNumberAndPhoneNumber(pilotAccount);
//		System.out.println(loginPilot);
//		System.out.println(loginPilot.getLicenseNumber());
//		System.out.println(loginPilot.getName());


		// 계정이 존재하지 않을 경우
	    if (loginPilot == null) {
	        return "either LicenseNumber or PhoneNumber is wrong";
	    }

	    // 계정이 잠겨있는 경우
	    if (loginPilot.getIsLocked()) {
	        return "the account is locked";
	    }

	    
	    // 방역자 로그인 처리
	    session.setAttribute("loginPilot", loginPilot);

	    // 로그 입력
	    Log log = new Log();
	    log.setLogContent(loginPilot.getLicenseNumber() + " 방역자가 로그인 하였습니다");
	    log.setActionType("로그인");
	    log.setActivator(loginPilot.getLicenseNumber());

	    logDao.insertLog(log); // db에 로그 정보 삽입

	    return "ok";
			
	}
	
	
	
	//로그인 요청하기 //해당 로직은 js로직에 의해 인증번호가 통과되었을때에만 실행된다
		@RequestMapping(value="/pilot_login_test", method=RequestMethod.GET)
		public @ResponseBody Map<String, Object> pilot_login_test (
					@RequestParam(value="LicenseNumber") String LicenseNumber,
					@RequestParam(value="Name") String Name,
					HttpSession session
				) {
			
			PilotAccount pilotAccount = new PilotAccount();
			pilotAccount.setLicenseNumber(LicenseNumber);
			pilotAccount.setName(Name);
			
			PilotAccount loginPilot = pilotAccountDao.selectPilotAccountByLicenseNumberAndName(pilotAccount);
			
			//방역자 로그인 헤주기
			session.setAttribute("loginPilot", loginPilot);
				
			PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
			System.out.println(pilot);
		    if(pilot != null) {
		    	System.out.println("something is here");
		    	// JSON 형식으로 응답 생성
	            Map<String, Object> response = new HashMap<String, Object>();
	            response.put("status", "ok");
	            response.put("message", "Login successful");
	            return response;		    	
		    	//return "ok";
		    }
		    
			//logTable에 기록 넣어주기
//			AccountTable user1 = new AccountTable();
//			user1.setId(loginUser.getId());
//			user1.setName(loginUser.getName());
//			user1.setPermission(loginUser.getPermission());
//			user1.setActivity("login");
//				
//			accountTableDao.insertLog(user1); //로그인시 db에 로그인한 시간정보 넣어주기

	        // JSON 형식으로 응답 생성
	        Map<String, Object> response = new HashMap<String, Object>();
	        response.put("status", "not ok");
	        response.put("message", "Login failed");
	        return response;				
			//return "not ok"; 		

		}
	

	
	
	//방역자 로그아웃 
	@RequestMapping(value="/pilot_logout", method=RequestMethod.GET)
	public @ResponseBody String logout(
				HttpSession session
			) {
		
		//로그인 헤주기
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot"); 		
			
		//로그아웃
		session.invalidate(); 
			
		//로그입력 
		Log log = new Log();
		log.setLogContent(pilot.getLicenseNumber() + " 방역자가 로그아웃 하였습니다");
		log.setActionType("로그아웃"); 
		log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 관리자의 아이디	
		
		logDao.insertLog(log); //db에 로그정보 삽입	
		
		return "ok";
	}
	
	
	
	//특정 PilotAccout의 정보를 특정 LicenseNumber로 불러오기
	@RequestMapping(value="/get_pilotAccount_by_licenseNumber", method=RequestMethod.GET)
	public @ResponseBody PilotAccount get_pilotAccount_by_licenseNumber(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				HttpSession session
			) {
		
		PilotAccount pilotAccountByLicenseNumber = pilotAccountDao.getPilotAccountByLicenseNumber(LicenseNumber);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + LicenseNumber + " 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
	
		return pilotAccountByLicenseNumber;
	}	
	
	
	//특정 PilotAccount의 정보를 해당 PhoneNumber로 불러오기
	@RequestMapping(value="/get_pilotAccount_by_phoneNumber", method=RequestMethod.GET)
	public @ResponseBody PilotAccount get_pilotAccount_by_phoneNumber(
				@RequestParam(value="PhoneNumber") String PhoneNumber,
				HttpSession session
			) {
		
		PilotAccount pilotAccountByPhoneNumber = pilotAccountDao.getPilotAccountByPhoneNumber(PhoneNumber);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + PhoneNumber + " 전화번호를 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + PhoneNumber + " 전화번호를 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return pilotAccountByPhoneNumber;
	}		

	
	//특정 PilotAccout의 정보를 해당 방역사의 Name으로 불러오기 *동명이인이 있을 가능성 고려해서 리스트형식으로 돌려받기
	@RequestMapping(value="/get_pilotAccount_by_name", method=RequestMethod.GET)
	public @ResponseBody List<PilotAccount> get_pilotAccount_by_name(
				@RequestParam(value="Name") String Name,
				HttpSession session
			) {
		
		List<PilotAccount> pilotAccountByName = pilotAccountDao.getPilotAccountByName(Name);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Name + " 이름을 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Name + " 이름을 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return pilotAccountByName;
	}	
	
	
	//특정 PilotAccout의 정보를 해당 방역사의 CarNumber으로 불러오기
	@RequestMapping(value="/get_pilotAccount_by_carNumber", method=RequestMethod.GET)
	public @ResponseBody List<PilotAccount> get_pilotAccount_by_carNumber(
				@RequestParam(value="CarNumber") String CarNumber,
				HttpSession session
			) {
		
		List<PilotAccount> pilotAccountByCarNumber = pilotAccountDao.getPilotAccountByCarNumber(CarNumber);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + CarNumber + " 자동차 번호를 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + CarNumber + " 자동차 번호를 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return pilotAccountByCarNumber;
	}	
	
	
	//특정 PilotAccout의 정보를 헤딩 방역사의 Drone으로 불러오기
	@RequestMapping(value="/get_pilotAccount_by_drone", method=RequestMethod.GET)
	public @ResponseBody List<PilotAccount> get_pilotAccount_by_drone(
				@RequestParam(value="Drone") String Drone,
				HttpSession session
			) {
		
		List<PilotAccount> pilotAccountByDrone = pilotAccountDao.getPilotAccountByDrone(Drone);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Drone + " 드론 번호를 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Drone + " 드론 번호를 가진 방역자의 계정을 불러왔습니다");
			log.setActionType("계정 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return pilotAccountByDrone;
	}		
	
}



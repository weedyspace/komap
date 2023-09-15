package com.my.diseaseControl.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.ConfirmerDao;
import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Confirmer;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;





@Controller
public class ConfirmerController {
	
	@Resource(name="ConfirmerDao")
	private ConfirmerDao confirmerDao;	
	
	@Resource(name="LogDao")
	private LogDao logDao;
	
	
	
	
	//방역자가 confirm 요청 올리기  *ConfirmTable의 ProjectID눈 고유키이므로 중복시 500에러 발생 
	@RequestMapping(value="/add_confirmer", method=RequestMethod.GET)
	public @ResponseBody String add_confirmer(
				@RequestParam(value="AdminAccountIndex") int AdminAccountIndex,
//				@RequestParam(value="IsApproved") Boolean IsApproved,
				@RequestParam(value="ProjectID") int ProjectID,
				HttpSession session
		) {
	
		Confirmer confirmer = new Confirmer();
		confirmer.setAdminAccountIndex(AdminAccountIndex);
//		confirmer.setIsApproved(IsApproved);
		confirmer.setProjectID(ProjectID);

		confirmerDao.insertConfirmer(confirmer);
		
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(pilot != null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectID + " 번 프로잭트아이디에 해당하는 프로젝트 컨펌 요청을 하였습니다 ");
			log.setActionType("컨펌 요청"); 
			log.setActivator(pilot.getLicenseNumber());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}
		
		
		return "ok";
	}	
	
	
	
	//Confirmer 엄데이트 
	
	
	
	//Confirmer 리스트  불러오기
	@RequestMapping(value="/get_all_confirmer", method=RequestMethod.GET)
	public @ResponseBody List<Confirmer> get_all_confirmer(
			HttpSession session
			) {
		
		List<Confirmer> confirmerAll = confirmerDao.getAll();	
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return confirmerAll;
	}
	
	
	//confirm이 된 confirmer만 불러오기
	@RequestMapping(value="/get_approved_confirmer", method=RequestMethod.GET)
	public @ResponseBody List<Confirmer> get_approved_confirmer(
			HttpSession session
			) {
		
		List<Confirmer> approvedConfirmer = confirmerDao.getApprovedConfirmer();	
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 컨펌된 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 컨펌된 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return approvedConfirmer;
	}	
	
	
	//confirm이 되지않은 Confirmer만 불러오기
	@RequestMapping(value="/get_unapproved_confirmer", method=RequestMethod.GET)
	public @ResponseBody List<Confirmer> get_unapproved_confirmer(
			HttpSession session
			) {
		
		List<Confirmer> unApprovedConfirmer = confirmerDao.getUnapprovedConfirmer();		
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 컨펌이 안된 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 컨펌이 안된 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return unApprovedConfirmer;
	}	
	
	
	//특정 AdminAccountIndex로 인한 Confirmer만 불러오기
	@RequestMapping(value="/get_confirmer_by_adminAccountIndex", method=RequestMethod.GET)
	public @ResponseBody List<Confirmer> get_confirmer_by_adminAccountIndex(
			@RequestParam(value="AdminAccountIndex") int AdminAccountIndex,
			HttpSession session
			) {
		
		List<Confirmer> confirmerByAdminAccountIndex = confirmerDao.getConfirmerByAdminAccountIndex(AdminAccountIndex);		
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + AdminAccountIndex + " 번 관리자계정 번호에 의한 컨펌 리스트들을 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + AdminAccountIndex + " 번 관리자계장 번호에 의한 컨펌 리스트들을 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return confirmerByAdminAccountIndex;
	}		
	
	
	//특정 ProjectID로 인한 Confirmer만 불러오기  *ProjectID는 고유값 
	@RequestMapping(value="/get_confirmer_by_projectID", method=RequestMethod.GET)
	public @ResponseBody List<Confirmer> get_confirmer_by_projectID(
			@RequestParam(value="ProjectID") int ProjectID,
			HttpSession session
			) {
		
		List<Confirmer> confirmerByProjectID = confirmerDao.getConfirmerByProjectID(ProjectID);		
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectID + " 번 프로젝트 아이디에 해당하는 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectID + " 번 프로젝트 아이디에 해당하는 컨펌 리스트를 불러왔습니다");
			log.setActionType("컨펌 리스트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
		
		
		return confirmerByProjectID;
	}	
	
}
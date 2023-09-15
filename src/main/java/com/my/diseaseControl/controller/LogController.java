package com.my.diseaseControl.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.WorkTextData;




@Controller
public class LogController {
	
	@Resource(name="LogDao")
	private LogDao logDao;
	
	
	
	//로그기록 추가하기
	@RequestMapping(value="/add_log", method=RequestMethod.GET)
	public @ResponseBody String add_log(
				@RequestParam(value="LogContent") String LogContent,
				@RequestParam(value="ActionType") String ActionType, 
				@RequestParam(value="Activator") String Activator,
				@RequestParam(value="Responder") String Responder
		) {
	
		Log log = new Log();
		log.setLogContent(LogContent);
		log.setActionType(ActionType);
		log.setActivator(Activator);
		log.setResponder(Responder);

	
		logDao.insertLog(log);
		
		return "ok";
	}		
	
	
	//모든 로그기록 불러오기
	@RequestMapping(value="/get_all_log", method=RequestMethod.GET)
	public @ResponseBody List<Log> get_all_log(
			HttpSession session
		) {
	
		List<Log> logAll = logDao.getAll();
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
		
		return logAll;
	}
	
	
	//LogIndex에 대한 Log기록 불러오기
	@RequestMapping(value="/get_log_by_logIndex", method=RequestMethod.GET)
	public @ResponseBody List<Log> get_log_by_logIndex(
				@RequestParam(value="LogIndex") String LogIndex,
				HttpSession session
			) {
		
		List<Log> logByLogIndex = logDao.getLogByActionType(LogIndex);

		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LogIndex + " 번 로그 인덱스에 해당하는 로그기록을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + LogIndex + " 번 로그 인덱스에 해당하는 로그기록을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return logByLogIndex;
	}	
	
	
	//ActionType에 대한 Log기록 불러오기
	@RequestMapping(value="/get_log_by_actionType", method=RequestMethod.GET)
	public @ResponseBody List<Log> get_log_by_actionType(
				@RequestParam(value="ActionType") String ActionType,
				HttpSession session
			) {
		
		List<Log> workTextDataByActionType = logDao.getLogByActionType(ActionType);

		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ActionType + " 분류에 해당하는 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ActionType + " 분류에 해당하는 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return workTextDataByActionType;
	}
	
	
	//Activator에 대한 Log기록 불러오기
	@RequestMapping(value="/get_log_by_activator", method=RequestMethod.GET)
	public @ResponseBody List<Log> get_log_by_activator(
				@RequestParam(value="Activator") String Activator,
				HttpSession session
			) {
		
		List<Log> logByActivator = logDao.getLogByActivator(Activator);
	
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Activator + " 실행자가 행한 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Activator + " 실행자가 행한 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		return logByActivator;
	}
	

	//Responder에 대한 Log기록 불러오기
	@RequestMapping(value="/get_log_by_Responder", method=RequestMethod.GET)
	public @ResponseBody List<Log> get_log_by_Responder(
				@RequestParam(value="Responder") String Responder,
				HttpSession session
			) {
		
		List<Log> logByResponder = logDao.getLogByResponder(Responder);
				

		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + Responder + " 응답자가 행한 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + Responder + " 응답자가 행한 로그기록들을 불러왔습니다");
			log.setActionType("로그기록 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return logByResponder;
	}
	
	
	
//	//Activator가 Responder에게 메시지 전달 로그기록
//	@RequestMapping(value="/message_from_activator_to_responder", method=RequestMethod.GET)
//	public @ResponseBody String message_from_activator_to_responder(
//				@RequestParam(value="Responder") String Responder,
//				HttpSession session
//			) {
//
//		//관리자 로그인상태 확인	
//		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
//		
//		//방역자 로그인상태 확인	
//		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
//				
//		
//		if(adminUser != null && pilot == null) {	
//			//로그입력 
//			Log log = new Log();
//			log.setLogContent(adminUser.getAdminID() + " 대화자가 " + Responder + " 응답자가에게 메시지를 보냈습니다");
//			log.setActionType("대화하기"); 
//			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
//			log.setResponder(Responder);
//			
//			logDao.insertLog(log); //db에 로그정보 삽입		
//		}else if(adminUser == null && pilot != null) {
//			//로그입력 
//			Log log = new Log();
//			log.setLogContent(pilot.getLicenseNumber() + " 대화자가 " + Responder + " 응답자에게 메시지를 보냈습니다");
//			log.setActionType("대화하기"); 
//			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
//			log.setResponder(Responder);
//			
//			logDao.insertLog(log); //db에 로그정보 삽입
//		}		
//		
//		
//		return "ok";
//	}
	
	
	
}
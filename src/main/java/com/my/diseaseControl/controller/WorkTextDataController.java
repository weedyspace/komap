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
import com.my.diseaseControl.dao.WorkTextDataDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.GIS;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.WorkImageData;
import com.my.diseaseControl.vo.WorkTextData;




@Controller
public class WorkTextDataController {
	
	@Resource(name="WorkTextDataDao")
	private WorkTextDataDao workTextDataDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;	
	
	
	//WorkTextData 추가하기
	@RequestMapping(value="/add_work_text_data", method=RequestMethod.GET)
	public @ResponseBody String add_work_text_data(
				@RequestParam(value="ProjectID") int ProjectID ,
				@RequestParam(value="DisinfectantName") String DisinfectantName,
				@RequestParam(value="MixingRatio") String MixingRatio,
				HttpSession session
		) {
	
		WorkTextData workTextData = new WorkTextData();
		workTextData.setProjectID(ProjectID);
		workTextData.setDisinfectantName(DisinfectantName);
		workTextData.setMixingRatio(MixingRatio);

		workTextDataDao.insertWorkTextData(workTextData);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 프로젝트에 텍스트를 추가 하였습니다 ");
			log.setActionType("텍스트 추가"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 프로젝트에 텍스트를 추가 하였습니다 ");
			log.setActionType("텍스트 추가"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return "ok";
	}
	
	
	//WorkTextData 모두 불러오기
	@RequestMapping(value="/get_all_work_text_data", method=RequestMethod.GET)
	public @ResponseBody List<WorkTextData> get_all_work_text_data(
			HttpSession session
			) {
		
		List<WorkTextData> workTextDataAll = workTextDataDao.getAll();
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가  모든 텍스트 데이터를 불러왔습니다");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 텍스트 데이터를  불러왔습니다");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return workTextDataAll;
	}	
	
	
	//특정  ProjectID로 해당 WorkTextData 불러오기
	@RequestMapping(value="/get_work_text_data_by_projectID", method=RequestMethod.GET)
	public @ResponseBody List<WorkTextData> get_work_text_data_by_projectID(
				@RequestParam(value="ProjectID") int ProjectID,
				HttpSession session
			) {
		
		List<WorkTextData> workTextDataByProjectID = workTextDataDao.getWorkTextDataByProjectID(ProjectID);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 텍스트 데이터를 불러왔습니다");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 텍스트 데이터를 불러왔습니다 ");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return workTextDataByProjectID;
	}
	
	
	//특정  DisinfectantName로 해당 WorkTextData 불러오기
	@RequestMapping(value="/get_work_text_data_by_disinfectantName", method=RequestMethod.GET)
	public @ResponseBody List<WorkTextData> get_work_text_data_by_disinfectantName(
				@RequestParam(value="DisinfectantName") String DisinfectantName,
				HttpSession session
			) {
		
		List<WorkTextData> workTextDataByDisinfectantName = workTextDataDao.getWorkTextDataByDisinfectantName(DisinfectantName);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + DisinfectantName + " 라는 소독약의 이름을 가진 텍스트 데이터를 불러왔습니다");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + DisinfectantName + " 라는 소독약의 이름을 가진 텍스트 데이터를 불러왔습니다 ");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return workTextDataByDisinfectantName;
	}	
	

	//특정  MixingRatio로 해당 WorkTextData 불러오기
	@RequestMapping(value="/get_work_text_data_by_mixingRatio", method=RequestMethod.GET)
	public @ResponseBody List<WorkTextData> get_work_text_data_by_mixingRatio(
				@RequestParam(value="MixingRatio") String MixingRatio,
				HttpSession session
			) {
		
		List<WorkTextData> workTextDataByMixingRatio = workTextDataDao.getWorkTextDataByMixingRatio(MixingRatio);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + MixingRatio + " 비율을 가진 가진 텍스트 데이터를 불러왔습니다");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + MixingRatio + " 비율을 가진 가진 텍스트 데이터를 불러왔습니다 ");
			log.setActionType("텍스트 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}				
		
		
		return workTextDataByMixingRatio;
	}
	
}
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
import com.my.diseaseControl.dao.WorkImageDataDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Icon;
import com.my.diseaseControl.vo.Issue;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.WorkImageData;



@Controller
public class WorkImageDataController {
	
	@Resource(name="WorkImageDataDao")
	private WorkImageDataDao workImageDataDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;
	
	
	//workImage 추가하기
	@RequestMapping(value="/add_work_image_data", method=RequestMethod.GET)
	public @ResponseBody String add_work_image_data(
				@RequestParam(value="ProjectID") int ProjectID,
				@RequestParam(value="ImageUrl") String ImageUrl, 
				@RequestParam(value="ImageType") String ImageType,
				HttpSession session
		) {
	
		WorkImageData workImageData = new WorkImageData();
		workImageData.setProjectID(ProjectID);
		workImageData.setImageUrl(ImageUrl);
		workImageData.setImageType(ImageType);

	
		workImageDataDao.insertWorkImageData(workImageData);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 프로젝트에 이미지를 추가 하였습니다 ");
			log.setActionType("이미지 추가"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 프로젝트에 이미지를 추가 하였습니다 ");
			log.setActionType("이미지 추가"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return "ok";
	}
	
	
	//WorkImageData 모두 불러오기
	@RequestMapping(value="/get_all_work_image_data", method=RequestMethod.GET)
	public @ResponseBody List<WorkImageData> get_all_work_image_data(
			HttpSession session
			) {
		
		List<WorkImageData> workImageDataAll = workImageDataDao.getAll();
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가  모든 이미지 데이터를 불러왔습니다");
			log.setActionType("이미지 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 이미지 데이터를  불러왔습니다");
			log.setActionType("이미지 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return workImageDataAll;
	}	
	
	
	//특정 ProjectId로 해당 WorkImageData 불러오기
	@RequestMapping(value="/get_work_image_data_by_projectID", method=RequestMethod.GET)
	public @ResponseBody List<WorkImageData> get_work_image_data_by_projectID(
				@RequestParam(value="ProjectID") int ProjectID,
				HttpSession session
			) {
		
		List<WorkImageData> workImageDataByProjectID = workImageDataDao.getWorkImageDataByProjectID(ProjectID);
		

		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 프로젝트의 이미지 정보를 불러왔습니다");
			log.setActionType("이미지 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectID + " 번 프로젝트 아이디 번호를 가진 프로젝트의 이미지 정보를 불러왔습니다");
			log.setActionType("이미지 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}			
		
		
		return workImageDataByProjectID;
	}
	
	
	//특정 ImageType으로 해당 WorkImageData 불러오기
	@RequestMapping(value="/get_work_image_data_by_imageType", method=RequestMethod.GET)
	public @ResponseBody List<WorkImageData> get_work_image_data_by_imageType(
				@RequestParam(value="ImageType") String ImageType,
				HttpSession session
			) {
		
		List<WorkImageData> workImageDataByImageType = workImageDataDao.getWorkImageDataByImageType(ImageType);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ImageType + " 이미지타입의 이미지 정보들을 불러왔습니다");
			log.setActionType("이미지 데이터 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ImageType + " 이미지타입의 이미지 정보들을 불러왔습니다");
			log.setActionType("이미지 데이터 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return workImageDataByImageType;
	}
	
	
}
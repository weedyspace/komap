package com.my.diseaseControl.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.dao.ProjectDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.Project;
import com.my.diseaseControl.vo.ProjectInfo1;


@Controller
public class ProjectController {
	
	
	@Resource(name="ProjectDao")
	private ProjectDao projectDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;

	

	//ProjectTable로부터 모든 Project의 정보를 가져오기
	@RequestMapping(value="/get_all_project", method=RequestMethod.GET)
	public @ResponseBody List<Project> get_all_project(
			Model model,
			HttpSession session
			) {
				
		List<Project> projectAll = projectDao.getAll();
		
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
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 프로젝트들을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 프로젝트들을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}
		
							
		return projectAll;
	} 	
	
	
	//새로운 Project 추가하기  *실행전 관리자 로그인 필수 
	@RequestMapping(value="/add_new_project_by_admin", method=RequestMethod.GET)
	public @ResponseBody String add_new_project_by_admin(
			@RequestParam(value="ProjectName") String ProjectName,
			@RequestParam(value="ProjectDeadLine") String ProjectDeadLine,
			@RequestParam(value="GISID") int GISID,
			@RequestParam(value="LicenseNumber") String LicenseNumber,
			@RequestParam(value="ProjectStartTime") String ProjectStartTime,
			@RequestParam(value="ProjectEndTime") String ProjectEndTime,
			@RequestParam(value="Address") String Address,
//			@RequestParam(value="AdminAccountIndex") int AdminAccountIndex,
			@RequestParam(value="DroneFlyRouteJsonUrl") String DroneFlyRouteJsonUrl,
			@RequestParam(value="GeojsonIndex") int GeojsonIndex,
			@RequestParam(value="Status") String Status,
			Model model, 
			HttpSession session
			) {
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		Project newProject = new Project();
		newProject.setProjectName(ProjectName);
		newProject.setProjectDeadLine(ProjectDeadLine);
		newProject.setGISID(GISID);
		newProject.setLicenseNumber(LicenseNumber);
		newProject.setProjectStartTime(ProjectStartTime);
		newProject.setProjectEndTime(ProjectEndTime);
		newProject.setAddress(Address);
		newProject.setAdminAccountIndex(adminUser.getAdminAccountIndex());
		newProject.setDroneFlyRouteJsonUrl(DroneFlyRouteJsonUrl);
		newProject.setGeojsonIndex(GeojsonIndex);
		newProject.setStatus(Status);
		
		projectDao.insertNewProject(newProject); //새로운 프로젝트 추가

		//로그입력 
		Log log = new Log();
		log.setLogContent(adminUser.getAdminID() + " 관리자가" + ProjectName + " 프로젝트를 생성하였습니다"); //(MASTER)관리자가 (test)프로젝트를 생성하였습니다
		log.setActionType("프로젝트 생성"); //프로젝트생성
		log.setActivator(adminUser.getAdminID());   //이 프로젝트를 생성하는 관리자의 아이디		
	
		logDao.insertLog(log); //db에 로그정보 삽입
		
		return "ok";
	}	
	

//	// 방역사의 라이센스로 해당 방역사가 진행전인 프로젝트 불러오기
//	@RequestMapping(value="/pre_ongoing_project_by_pilot", method=RequestMethod.GET)
//	public @ResponseBody List<Project> pre_ongoing_project_by_pilot(
//				@RequestParam(value="LicenseNumber") String LicenseNumber,
//				HttpSession session
//			) {
//		
////		PilotAccount pilotAccount = (PilotAccount) session.getAttribute("loginPilot");
////		System.out.println(pilotAccount);
////	    if(pilotAccount != null) {
////	    	System.out.println("a pilot is here");
////	    } 
//		
//	    Project licenseAndStatus = new Project(); 
//	    licenseAndStatus.setLicenseNumber(LicenseNumber);
//	    licenseAndStatus.setStatus("pre-ongoing");
//	    
//	    List<Project> ongoingProjectsByPilot = projectDao.getPreOngoingProjectsByPilot(licenseAndStatus);
//		
//		
//		return ongoingProjectsByPilot;
//	}
//	
//	
//	// 방역사의 라이센스로 해당 방역사가 진행중인 프로젝트 불러오기
//	@RequestMapping(value="/ongoing_project_by_pilot", method=RequestMethod.GET)
//	public @ResponseBody List<Project> ongoing_project_by_pilot(
//				@RequestParam(value="LicenseNumber") String LicenseNumber,
//				HttpSession session
//			) {
//		
////		PilotAccount pilotAccount = (PilotAccount) session.getAttribute("loginPilot");
////		System.out.println(pilotAccount);
////	    if(pilotAccount != null) {
////	    	System.out.println("a pilot is here");
////	    } 
//		
//	    Project licenseAndStatus = new Project(); 
//	    licenseAndStatus.setLicenseNumber(LicenseNumber);
//	    licenseAndStatus.setStatus("ongoing");
//	    
//	    List<Project> ongoingProjectsByPilot = projectDao.getOngoingProjectsByPilot(licenseAndStatus);
//		
//		
//		return ongoingProjectsByPilot;
//	}	
//	
//	
//	// 방역사의 라이센스로 해당 방역사가 끝마친 프로젝트 불러오기
//	@RequestMapping(value="/completed_project_by_pilot", method=RequestMethod.GET)
//	public @ResponseBody List<Project> completed_project_by_pilot(
//				@RequestParam(value="LicenseNumber") String LicenseNumber,
//				HttpSession session
//			) {
//		
////		PilotAccount pilotAccount = (PilotAccount) session.getAttribute("loginPilot");
////		System.out.println(pilotAccount);
////	    if(pilotAccount != null) {
////	    	System.out.println("a pilot is here");
////	    } 
//		
//	    Project licenseAndStatus = new Project(); 
//	    licenseAndStatus.setLicenseNumber(LicenseNumber);
//	    licenseAndStatus.setStatus("completed");
//	    
//	    List<Project> completedProjectsByPilot = projectDao.getCompletedProjectsByPilot(licenseAndStatus);
//		
//		
//		return completedProjectsByPilot;
//	}	
//	
//	
//	// 방역사의 라이센스로 해당 방역사가 보류중인 프로젝트 불러오기
//	@RequestMapping(value="/on_hold_project_by_pilot", method=RequestMethod.GET)
//	public @ResponseBody List<Project> on_hold_project_by_pilot(
//				@RequestParam(value="LicenseNumber") String LicenseNumber,
//				HttpSession session
//			) {
//		
////		PilotAccount pilotAccount = (PilotAccount) session.getAttribute("loginPilot");
////		System.out.println(pilotAccount);
////	    if(pilotAccount != null) {
////	    	System.out.println("a pilot is here");
////	    } 
//		
//	    Project licenseAndStatus = new Project(); 
//	    licenseAndStatus.setLicenseNumber(LicenseNumber);
//	    licenseAndStatus.setStatus("on-hold");
//	    
//	    List<Project> completedProjectsByPilot = projectDao.getOnholdProjectsByPilot(licenseAndStatus);
//		
//		
//		return completedProjectsByPilot;
//	}
//	
//	
//
//	// 방역사의 라이센스로 해당 방역사가 맡았던 취소된 프로젝트 불러오기
//	@RequestMapping(value="/canceled_project_by_pilot", method=RequestMethod.GET)
//	public @ResponseBody List<Project> canceled_project_by_pilot(
//				@RequestParam(value="LicenseNumber") String LicenseNumber,
//				HttpSession session
//			) {
//		
////		PilotAccount pilotAccount = (PilotAccount) session.getAttribute("loginPilot");
////		System.out.println(pilotAccount);
////	    if(pilotAccount != null) {
////	    	System.out.println("a pilot is here");
////	    } 
//		
//	    Project licenseAndStatus = new Project(); 
//	    licenseAndStatus.setLicenseNumber(LicenseNumber);
//	    licenseAndStatus.setStatus("canceled");
//	    
//	    List<Project> completedProjectsByPilot = projectDao.getCanceledProjectsByPilot(licenseAndStatus);
//		
//		
//		return completedProjectsByPilot;
//	}	
	
	
	//방역사의 라이센스와 연동된 프로젝트의 진행상태를 이용하여 해당 방역사와 연결된 프로젝트 불러오기. Status => 진행전:pre-ongoing 진행중:ongoing 완료:completed 보류중:on-hold 취소:canceled
	@RequestMapping(value = "/projects_by_pilot_status", method = RequestMethod.GET)
	public @ResponseBody List<Project> projects_by_pilot_status(
	        @RequestParam(value = "LicenseNumber") String LicenseNumber,
	        @RequestParam(value = "Status") String Status, 
	        HttpSession session
	) {
	    Project licenseAndStatus = new Project();
	    licenseAndStatus.setLicenseNumber(LicenseNumber);


	    licenseAndStatus.setStatus(Status);


	    List<Project> projectsByPilotAndStatus = projectDao.getProjectsByPilotAndStatus(licenseAndStatus);
	    
	    
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		 
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자가 맡은 " + Status + " 상태의 모든 프로젝트들을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + LicenseNumber + " 방역자가 맡은 " + Status + " 상태의 모든 프로젝트들을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}	    

	    return projectsByPilotAndStatus;
	}
	

//	//진행전(pre-ongoing)에 있는 프로젝트 모두 불러오기
//	@RequestMapping(value="/pre_ongoing_project", method=RequestMethod.GET)
//	public @ResponseBody List<Project> pre_ongoing_project(
//				
//			) {
//	    
//	    List<Project> preOngoingProject = projectDao.getAllPreOngoingProject();
//		
//		
//		return preOngoingProject;
//	}
//	
//	
//	//완료된(completed) 프로젝트 모두 불러오기
//	@RequestMapping(value="/completed_project", method=RequestMethod.GET)
//	public @ResponseBody List<Project> completed_project(
//				
//			) {
//	    
//	    List<Project> completedProject = projectDao.getAllCompletedProject();
//		
//		
//		return completedProject;
//	}	
//	
//	//보류중인(on-hold) 프로젝트 모두 불러오기
//	@RequestMapping(value="/on_hold_project", method=RequestMethod.GET)
//	public @ResponseBody List<Project> on_hold_project(
//				
//			) {
//	    
//	    List<Project> onHoldProject = projectDao.getAllOnHoldProject();
//		
//		
//		return onHoldProject;
//	}	
//	
//	//취소된(canceled) 프로젝트 모두 불러오기
//	@RequestMapping(value="/canceled_project", method=RequestMethod.GET)
//	public @ResponseBody List<Project> canceled_project(
//				
//			) {
//	    
//	    List<Project> canceledProject = projectDao.getAllCanceledProject();
//		
//		
//		return canceledProject;
//	}		
//	
//	//진행중에 있는 프로젝트 모두 불러오기
//	@RequestMapping(value="/ongoing_project", method=RequestMethod.GET)
//	public @ResponseBody List<Project> ongoing_project(
//				HttpSession session
//			) {
//		
////		AdminAccount adminAccount = (AdminAccount) session.getAttribute("loginPilot");
////		System.out.println(adminAccount);
////	    if(adminAccount != null) {
////	    	System.out.println("a admin is here");
////	    }
////	    
////		session.getAttribute("loginAdminUser");
//	    
//	    List<Project> ongoingProject = projectDao.getAllOngoingProject();
//		
//		
//		return ongoingProject;
//	}	
	
	
	//방역사들이 진행중에 있는 프로젝트 + 완료한 프로젝트를 모두 불러오기
	@RequestMapping(value="/ongoing_plus_completed_project", method=RequestMethod.GET)
	public @ResponseBody List<Project> ongoing_plus_completed_project(
				HttpSession session
			) {
		
//		AdminAccount adminAccount = (AdminAccount) session.getAttribute("loginPilot");
//		System.out.println(adminAccount);
//	    if(adminAccount != null) {
//	    	System.out.println("a admin is here");
//	    }
//	    
//		session.getAttribute("loginAdminUser");
	    
	    List<Project> ongoingPlusCompletedProject = projectDao.getAllOngoingPlusCompletedProject();
	   
	    
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
		System.out.println(pilot);		
		 
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 진행중에 있는 프로젝트와 완료한 모든 프로젝트들을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 진행중에 있는 프로젝트와 완료한 모든 프로젝트들을 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}	    
		
		
		return ongoingPlusCompletedProject;
	}	
	

	//방역사 라이센스에 의헤 진행중에 있는 프로젝트 + 완료한 프로젝트를 모두 불러오기 - ProjectMakedDate, ProjectEndTime, Address 리턴해주기
	@RequestMapping(value="/ongoing_plus_completed_project_by_License", method=RequestMethod.GET)
	public @ResponseBody List<ProjectInfo1> ongoing_plus_completed_project_by_License(
				@RequestParam(value="License") String License,
				HttpSession session
			) {
		
//		AdminAccount adminAccount = (AdminAccount) session.getAttribute("loginPilot");
//		System.out.println(adminAccount);
//	    if(adminAccount != null) {
//	    	System.out.println("a admin is here");
//	    }
//	    
//		session.getAttribute("loginAdminUser");
		
		
	    List<Project> ongoingPlusCompletedProjectByLicense = projectDao.getAllOngoingPlusCompletedProjectByLicense(License);

	    List<ProjectInfo1> projectInfoList = new ArrayList<ProjectInfo1>(); //빈배열 생성
	    
	    for (Project project : ongoingPlusCompletedProjectByLicense) {
	        ProjectInfo1 projectInfo = new ProjectInfo1();
	        projectInfo.setProjectMakedDate(project.getProjectMakedDate());
	        projectInfo.setProjectEndTime(project.getProjectEndTime());
	        projectInfo.setAddress(project.getAddress());
	        
	        projectInfoList.add(projectInfo);
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
			log.setLogContent(adminUser.getAdminID() + " 관리자가 진행중에 있는 프로젝트들과 완료한 모든 프로젝트들의 ProjectMakedDate, ProjectEndTime, Address를 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 진행중에 있는 프로젝트들과 완료한 모든 프로젝트들의 ProjectMakedDate, ProjectEndTime, Address를 불러왔습니다");
			log.setActionType("프로젝트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}
		
			
		return projectInfoList;
	}	
	
	
	//ProjectName으로 특정Project 불러오기
	@RequestMapping(value="/get_project_by_projectName", method=RequestMethod.GET)
	public @ResponseBody Project get_project_by_projectName(
				@RequestParam(value="ProjectName") String ProjectName,
				HttpSession session
			) {
	    
	    Project projectByProjectName = projectDao.getProjectByProjectName(ProjectName);
	    System.out.println(projectByProjectName.getProjectName());
	    
	    //로그기록
	    if(projectByProjectName.getProjectName() != null || !projectByProjectName.getProjectName().isEmpty()) { //불러오고자 하는 프로젝트가 존재하는 경우에 한해서 로그기록 하기
	    	//관리자 로그인상태 확인	
			AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
			System.out.println(adminUser);
			
			//방역자 로그인상태 확인	
			PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
			System.out.println(pilot);		
			 
			if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
				//로그입력 
				Log log = new Log();
				log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectName + " 프로젝트를 불러왔습니다");
				log.setActionType("프로젝트 불러오기"); 
				log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
				
				logDao.insertLog(log); //db에 로그정보 삽입
			} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
				//로그입력 
				Log log = new Log();
				log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + ProjectName + " 프로젝트를 불러왔습니다");
				log.setActionType("프로젝트 불러오기"); 
				log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
				
				logDao.insertLog(log); //db에 로그정보 삽입
			}	    	
	    }
		
		
		return projectByProjectName;
	}
	
	
	//ProjectDeadLine에 해당하는 특정Project 모두 불러오기
	@RequestMapping(value="/get_project_by_projectDeadLine", method=RequestMethod.GET)
	public @ResponseBody List<Project> get_project_by_projectDeadLine(
				@RequestParam(value="ProjectDeadLine") String ProjectDeadLine,
				HttpSession session
			) {
	    
		List<Project> projectByProjectDeadLine = projectDao.getProjectByProjectDeadLine(ProjectDeadLine);

	    
	    for (Project project : projectByProjectDeadLine) {

		    if(project.getProjectDeadLine() != null || !project.getProjectDeadLine().isEmpty()) { //불러오고자 하는 프로젝트의 데드라인이 존재하는 경우에 한해서 로그기록 하기
		    	//관리자 로그인상태 확인	
				AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
				System.out.println(adminUser);
				
				//방역자 로그인상태 확인	
				PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				System.out.println(pilot);		
				 
				if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
					//로그입력 
					Log log = new Log();
					log.setLogContent(adminUser.getAdminID() + " 관리자가 데드라인이 " + ProjectDeadLine + " 까지인 프로젝트들를 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져니오기 
				} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
					//로그입력 
					Log log = new Log();
					log.setLogContent(pilot.getLicenseNumber() + " 방역자가 데드라인이 " + ProjectDeadLine + " 까지인 프로젝트들를 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져나오기
				}	    	
		    }	    	
	    }		
			
		
		return projectByProjectDeadLine;
	}	
	
	
	//특정GISID로 특정Project 불러오기
	@RequestMapping(value="/get_project_by_GISID", method=RequestMethod.GET)
	public @ResponseBody Project get_project_by_GISID(
				@RequestParam(value="GISID") int GISID,
				HttpSession session
			) {
	    
		Project projectByGISID = projectDao.getProjectByGISID(GISID);
		
	    //로그기록
	    if(projectByGISID.getGISID() != 0) { //불러오고자 하는 프로젝트가 존재하는 경우에 한해서 로그기록 하기
	    	//관리자 로그인상태 확인	
			AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
			System.out.println(adminUser);
			
			//방역자 로그인상태 확인	
			PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
			System.out.println(pilot);		
			 
			if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
				//로그입력 
				Log log = new Log();
				log.setLogContent(adminUser.getAdminID() + " 관리자가 GISID가" + GISID + "인 "+ projectByGISID.getProjectName() + " 프로젝트를 불러왔습니다");
				log.setActionType("프로젝트 불러오기"); 
				log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
				
				logDao.insertLog(log); //db에 로그정보 삽입
			} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
				//로그입력 
				Log log = new Log();
				log.setLogContent(pilot.getLicenseNumber() + " 방역자가 GISID가" + GISID + "인 "+ projectByGISID.getProjectName() + " 프로젝트를 불러왔습니다");
				log.setActionType("프로젝트 불러오기"); 
				log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
				
				logDao.insertLog(log); //db에 로그정보 삽입
			}	    	
	    }		
		
		
		return projectByGISID;
	}		
	
	
	
	//특정LicenseNumber와 연관된 Project 모두 불러오기
	@RequestMapping(value="/get_project_by_licenseNumber", method=RequestMethod.GET)
	public @ResponseBody List<Project> get_project_by_licenseNumber(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				HttpSession session
			) {
	    
		List<Project> projectByLicenseNumber = projectDao.getProjectByLicenseNumber(LicenseNumber);

		
	    for (Project project : projectByLicenseNumber) {

		    if(project.getLicenseNumber() != null || !project.getLicenseNumber().isEmpty()) { //불러오고자 하는 프로젝트의 데드라인이 존재하는 경우에 한해서 로그기록 하기
		    	//관리자 로그인상태 확인	
				AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
				System.out.println(adminUser);
				
				//방역자 로그인상태 확인	
				PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				System.out.println(pilot);		
				 
				if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
					//로그입력 
					Log log = new Log();
					log.setLogContent(adminUser.getAdminID() + " 관리자가 LicenseNumber가" + LicenseNumber + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져니오기 
				} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
					//로그입력
					Log log = new Log();
					log.setLogContent(pilot.getLicenseNumber() + " 방역자가 LicenseNumber가" + LicenseNumber + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져나오기
				}	    	
		    }	    	
	    }		
		
	    
		return projectByLicenseNumber;
	}	
	
	

	//특정ProjectStartTime으로 해당Project 불러오기
	@RequestMapping(value="/get_project_by_projectStartTime", method=RequestMethod.GET)
	public @ResponseBody List<Project> get_project_by_projectStartTime(
				@RequestParam(value="ProjectStartTime") String ProjectStartTime,
				HttpSession session
			) {
	    
		List<Project> projectByProjectStartTime = projectDao.getProjectByProjectStartTime(ProjectStartTime);
		
		
	    for (Project project : projectByProjectStartTime) {

		    if(project.getProjectStartTime() != null || !project.getProjectStartTime().isEmpty()) { //불러오고자 하는 프로젝트의 스타트타임이 존재하는 경우에 한해서 로그기록 하기
		    	//관리자 로그인상태 확인	
				AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
				System.out.println(adminUser);
				
				//방역자 로그인상태 확인	
				PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				System.out.println(pilot);		
				 
				if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
					//로그입력 
					Log log = new Log();
					log.setLogContent(adminUser.getAdminID() + " 관리자가 ProjectStartTime이" + ProjectStartTime + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져니오기 
				} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
					//로그입력
					Log log = new Log();
					log.setLogContent(pilot.getLicenseNumber() + " 방역자가 ProjectStartTime이" + ProjectStartTime + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져나오기
				}	    	
		    }	    	
	    }		
		
		
		return projectByProjectStartTime;
	}	
	
	
	
	//AdminAccountIndex로 해당Project 불러오기
	@RequestMapping(value="/get_project_by_adminAccountIndex", method=RequestMethod.GET)
	public @ResponseBody List<Project> get_project_by_adminAccountIndex(
				@RequestParam(value="AdminAccountIndex") int AdminAccountIndex,
				HttpSession session
			) { 
	    
		List<Project> projectByAdminAccountIndex = projectDao.getProjectByAdminAccountIndex(AdminAccountIndex);
		
		
	    for (Project project : projectByAdminAccountIndex) {

		    if(project.getAdminAccountIndex() != 0) { //불러오고자 하는 프로젝트의 스타트타임이 존재하는 경우에 한해서 로그기록 하기
		    	//관리자 로그인상태 확인	
				AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
				System.out.println(adminUser);
				
				//방역자 로그인상태 확인	
				PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				System.out.println(pilot);		
				 
				if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
					//로그입력 
					Log log = new Log();
					log.setLogContent(adminUser.getAdminID() + " 관리자가 AdminAccountIndex가" + AdminAccountIndex + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져니오기 
				} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
					//로그입력
					Log log = new Log();
					log.setLogContent(pilot.getLicenseNumber() + " 방역자가 AdminAccountIndex가" + AdminAccountIndex + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져나오기
				}	    	
		    }	    	
	    }		
		
		
		return projectByAdminAccountIndex;
	}	
	
	
	
	//GeojsonIndex로 해당Project 불러오기
	@RequestMapping(value="/get_project_by_geojsonIndex", method=RequestMethod.GET)
	public @ResponseBody List<Project> get_project_by_geojsonIndex(
				@RequestParam(value="GeojsonIndex") int GeojsonIndex,
				HttpSession session
			) { 
	    
		List<Project> projectByGeojsonIndex = projectDao.getProjectByGeojsonIndex(GeojsonIndex);


	    for (Project project : projectByGeojsonIndex) {

		    if(project.getGeojsonIndex() != 0) { //불러오고자 하는 프로젝트의 스타트타임이 존재하는 경우에 한해서 로그기록 하기
		    	//관리자 로그인상태 확인	
				AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
				System.out.println(adminUser);
				
				//방역자 로그인상태 확인	
				PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				System.out.println(pilot);		
				 
				if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
					//로그입력 
					Log log = new Log();
					log.setLogContent(adminUser.getAdminID() + " 관리자가 GeojsonIndex가" + GeojsonIndex + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(adminUser.getAdminID());   //모든 프로젝트들을 불러오는 관리자의 아이디	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져니오기 
				} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
					//로그입력
					Log log = new Log();
					log.setLogContent(pilot.getLicenseNumber() + " 방역자가 GeojsonIndex가" + GeojsonIndex + "인 프로젝트들을 불러왔습니다");
					log.setActionType("프로젝트 불러오기"); 
					log.setActivator(pilot.getLicenseNumber());   //모든 프로젝트들을 불러오는 방역자의 라이센스 번호	
					
					logDao.insertLog(log); //db에 로그정보 삽입
					break; //반복문 빠져나오기
				}	    	
		    }	    	
	    }
	    
		
		return projectByGeojsonIndex;
	}
	
	
	
}
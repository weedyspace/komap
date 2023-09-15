package com.my.diseaseControl.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.IssueDao;
import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Issue;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;






@Controller
public class IssueController {
	
	@Resource(name="IssueDao")
	private IssueDao issueDao;	
	
	@Resource(name="LogDao")
	private LogDao logDao;
	
	
	
	
	//IssueTable로부터 모든 Issue 정보를 가져오기
	@RequestMapping(value="/get_all_issue", method=RequestMethod.GET)
	public @ResponseBody List<Issue> get_all_issue(
			Model model,
			HttpSession session
			) {
		
		List<Issue> issueAll = issueDao.getAll();
		
		//List<PilotAccount> list = new ArrayList<>(); //빈 배열 생성
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 이슈들을 불러왔습니다");
			log.setActionType("이슈 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 이슈들을 불러왔습니다");
			log.setActionType("이슈 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}
		
		
		return issueAll;
	} 	
	

	//새로운 Issue 등록하기 
	@RequestMapping(value="/insert_new_issue", method=RequestMethod.GET)
	public @ResponseBody String insert_new_issue(
			@RequestParam(value="LicenseNumber") String LicenseNumber,
			@RequestParam(value="ImageUrl") String ImageUrl,
			@RequestParam(value="Content") String Content,
			@RequestParam(value="lat") int lat,
			@RequestParam(value="lng") int lng,
			@RequestParam(value="IssueOccurredTime") String IssueOccurredTime,
			@RequestParam(value="ProjectID") int ProjectID ,
			@RequestParam(value="IsCompleted") Boolean IsCompleted,
			Model model, 
			HttpSession session
			) {
		
		Issue newIssue = new Issue();
		newIssue.setLicenseNumber(LicenseNumber);
		newIssue.setImageUrl(ImageUrl);
		newIssue.setContent(Content);
		newIssue.setLat(lat);
		newIssue.setLng(lng);
		newIssue.setIssueOccurredTime(IssueOccurredTime);
		newIssue.setProjectID(ProjectID);
		newIssue.setIsCompleted(IsCompleted);
			
		issueDao.insertNewIssue(newIssue);
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 새로운 이슈를 등록 했습니다");
			log.setActionType("이슈 등록"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 새로운 이슈를 등록 했습니다");
			log.setActionType("이슈 등록"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}	
		
		
		return "ok";
	}
	
	
	
	//Issue내용 방역자가 업데이트 하기  *등록되어진 이슈의 IssueIndex와 ProjectID에 맞춰서 업데이트 요청하지 않으면 업데이트 되지 않음 
	@RequestMapping(value="/update_issue", method=RequestMethod.GET)
	public @ResponseBody String update_issue(
			@RequestParam(value="IssueIndex") int IssueIndex,
			@RequestParam(value="ImageUrl") String ImageUrl,
			@RequestParam(value="Content") String Content,
			@RequestParam(value="lat") int lat,
			@RequestParam(value="lng") int lng,
			@RequestParam(value="ProjectID") int ProjectID,
			@RequestParam(value="IsCompleted") Boolean IsCompleted,
			Model model, 
			HttpSession session
			) {
		
		Issue updatedIssue = new Issue();
		updatedIssue.setIssueIndex(IssueIndex);
		updatedIssue.setImageUrl(ImageUrl);
		updatedIssue.setContent(Content);
		updatedIssue.setLat(lat);
		updatedIssue.setLng(lng);
		updatedIssue.setProjectID(ProjectID);
		updatedIssue.setIsCompleted(IsCompleted); 
		
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(pilot != null) { 
			//이슈 엡테이트해주기
			issueDao.updateIssue(updatedIssue);
			
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + IssueIndex + " 번 이슈를 업데이트 헸습니다");
			log.setActionType("이슈 업데이트"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
			
			return "ok";
		}
		
		
		return "no";
	}	
	
	
	
	//특정 LicenseNumber로  해당 Issue의 정보를 모두 가져오기
	@RequestMapping(value="/get_issue_by_licenseNumber", method=RequestMethod.GET)
	public @ResponseBody List<Issue> get_issue_by_licenseNumber(
				@RequestParam(value="LicenseNumber") String LicenseNumber,
				HttpSession session
			) {
		
		List<Issue> issueByLicenseNumber = issueDao.getIssueByLicenseNumber(LicenseNumber);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + LicenseNumber + " 방역자가 올린 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 빙역자가 " + LicenseNumber + " 방역자가 올린 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return issueByLicenseNumber;
	}	
	
	
	
	//특정 lat, lng(위도, 경도)로  해당 Issue에 대한 정보를 모두 가져오기
	@RequestMapping(value="/get_issue_by_lat_and_lng", method=RequestMethod.GET)
	public @ResponseBody List<Issue> get_issue_by_lat_and_lng(
				@RequestParam(value="lat") int lat,
				@RequestParam(value="lng") int lng,
				HttpSession session
			) {
		
		Issue issue = new Issue();
		issue.setLat(lat);
		issue.setLng(lng);
		
		List<Issue> issueByLatAndLng = issueDao.getIssueByLatAndLng(issue);

		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가  위도:" + lat + "경도:" + lng + " 에 해당하는 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가  위도:" + lat + "경도:" + lng + " 에 해당하는 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return issueByLatAndLng;
	}	
	

	//IssueOccurredTime으로  해당 Issue에 대한 정보를 모두 가져오기
	@RequestMapping(value="/get_issue_by_issueOccurredTime", method=RequestMethod.GET)
	public @ResponseBody List<Issue> get_issue_by_issueOccurredTime(
			@RequestParam(value="IssueOccurredTime") String IssueOccurredTime,
			HttpSession session
			) {
		
		List<Issue> issueByIssueOccurredTime = issueDao.getIssueByIssueOccurredTime(IssueOccurredTime);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + IssueOccurredTime + " 시간에 등록된 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 빙역자가 " + IssueOccurredTime + " 시간에 등록된 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return issueByIssueOccurredTime;
	}
	
	
	//ProjectID로  해당 Issue에 대한 정보를 모두 가져오기
	@RequestMapping(value="/get_issue_by_projectID", method=RequestMethod.GET)
	public @ResponseBody List<Issue> get_issue_by_projectID(
			@RequestParam(value="ProjectID") int ProjectID,
			HttpSession session
			) {
		
		List<Issue> issueByProjectID = issueDao.getIssueByProjectID(ProjectID);


		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + ProjectID + " 번 프로젝트 아이디에 해당하는 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 빙역자가 " + ProjectID + " 번 프로젝트 아이디에 해당하는 이슈들을 불러왔습니다");
			log.setActionType("이슈 가져오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return issueByProjectID;
	}	
	
	
}

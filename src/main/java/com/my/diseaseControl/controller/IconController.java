package com.my.diseaseControl.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.IconDao;
import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Icon;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;




@Controller
public class IconController {
	
	@Resource(name="IconDao")
	private IconDao iconDao;	
	
	@Resource(name="LogDao")
	private LogDao logDao;	
	
	
	
	
	//Icon 추가하기
	@RequestMapping(value="/add_icon", method=RequestMethod.GET)
	public @ResponseBody String add_icon(
				@RequestParam(value="IconImageUrl") String IconImageUrl,
				@RequestParam(value="Explanation") String Explanation, 
				@RequestParam(value="AdminAccountIndex") int AdminAccountIndex,
				HttpSession session
		) {
	
		Icon icon = new Icon();
		icon.setIconImageUrl(IconImageUrl);
		icon.setExplanation(Explanation);
		icon.setAdminAccountIndex(AdminAccountIndex);

		iconDao.insertIcon(icon);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 " + AdminAccountIndex + " 번 관리자계정 번호에 의해 생성된 프로젝트에 아이콘을 추가 하였습니다 ");
			log.setActionType("아이콘 추가"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 " + AdminAccountIndex + " 번 프로젝트 아이디 번호에 의해 생성된 프로젝트에 아이콘을 추가 하였습니다 ");
			log.setActionType("아이콘 추가"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return "ok";
	}	
	
	
	//Icon 모두 불러오기
	@RequestMapping(value="/get_all_icon", method=RequestMethod.GET)
	public @ResponseBody List<Icon> get_all_icon(
			HttpSession session
			) {
		
		List<Icon> iconAll = iconDao.getAll();
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		System.out.println(adminUser);
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");		
		
		if(adminUser != null && pilot == null) { //만약 adminUser가 null이 아니라면. 즉, 관리자로 로그인하고 방역자는 로그인되지 않은 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 아이콘 리스트를 불러왔습니다");
			log.setActionType("아이콘 리스트 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디	
			
			logDao.insertLog(log); //db에 로그정보 삽입
		} else if(adminUser == null && pilot != null) { //만약 pilot이 null이 아니라면. 즉, 관리자로 로그인되지 않고 방역자로 로그인한 상태라면
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 아이콘 리스트를 불러왔습니다");
			log.setActionType("아이콘 리스트 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 라이센스번호		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return iconAll;
	}	
	
}
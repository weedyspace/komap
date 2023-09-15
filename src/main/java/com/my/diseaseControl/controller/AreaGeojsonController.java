package com.my.diseaseControl.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.AreaGeojsonDao;
import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.AreaGeojson;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;




@Controller
public class AreaGeojsonController {
	
	@Resource(name="AreaGeojsonDao")
	private AreaGeojsonDao areaGeojsonDao;	
	
	@Resource(name="LogDao")
	private LogDao logDao;	
	
	
	//새로운 geojson 정보를 생성하기
	@RequestMapping(value="/add_new_area_geojson", method=RequestMethod.GET)
	public @ResponseBody String add_new_area_geojson(
				@RequestParam(value="GeojsonUrl") String GeojsonUrl,
				@RequestParam(value="AreaMeasure") BigDecimal AreaMeasure,
				HttpSession session
			) {
		
		AreaGeojson areaGeojson = new AreaGeojson();
		areaGeojson.setGeojsonUrl(GeojsonUrl);
		areaGeojson.setAreaMeasure(AreaMeasure);

		
		areaGeojsonDao.insertAreaGeojson(areaGeojson);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 새로운 geojson 정보를 생성하였습니다");
			log.setActionType("geojson 정보생성"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 새로운 geojson 정보를 생성하였습니다");
			log.setActionType("geojson 정보생성"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return "ok";
	}		
	
	
	//모든 geojson 정보를 불러오기
	@RequestMapping(value="/get_all_area_geojson", method=RequestMethod.GET)
	public @ResponseBody List<AreaGeojson> get_all_area_geojson(
			HttpSession session
			) {
		
		List<AreaGeojson> areaGeojsonAll = areaGeojsonDao.getAll();

		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 geojson 정보를 불러왔습니다");
			log.setActionType("geojson 정보 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 geojson 정보를 불러왔습니다");
			log.setActionType("geojson 정보 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return areaGeojsonAll;
	}		
	
}
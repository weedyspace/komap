package com.my.diseaseControl.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.GISDao;
import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Confirmer;
import com.my.diseaseControl.vo.GIS;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.PilotAccount;



@Controller
public class GISController {
	
	@Resource(name="GISDao")
	private GISDao gisDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;		
	
	
	//GIS 추가하기
	@RequestMapping(value="/add_gis", method=RequestMethod.GET)
	public @ResponseBody String add_gis(
				@RequestParam(value="GISImageUrl") String GISImageUrl,
				@RequestParam(value="SHPFileUrl") String SHPFileUrl,
				HttpSession session
		) {
	
		GIS gis = new GIS();
		gis.setGISImageUrl(GISImageUrl);
		gis.setSHPFileUrl(SHPFileUrl);
	
		gisDao.insertGIS(gis);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 새로운 GIS 정보를 생성하였습니다");
			log.setActionType("GIS 정보 생성"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 새로운 GIS 정보를 생성하였습니다");
			log.setActionType("GIS 정보 생성"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return "ok";
	}		
	
	
	//GIS 모두 블러오기
	@RequestMapping(value="/get_all_GIS", method=RequestMethod.GET)
	public @ResponseBody List<GIS> get_all_GIS(
			HttpSession session
			) {
		
		List<GIS> GISAll = gisDao.getAll();	
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 관리자가 모든 GIS 정보를 불러왔습니다");
			log.setActionType("geojson 정보 불러오기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		} else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 방역자가 모든 GIS 정보를 불러왔습니다");
			log.setActionType("geojson 정보 불러오기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return GISAll;
	}	
	
}
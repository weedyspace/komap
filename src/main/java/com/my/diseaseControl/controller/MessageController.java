package com.my.diseaseControl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.diseaseControl.dao.LogDao;
import com.my.diseaseControl.dao.MessageDao;
import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.Message;
import com.my.diseaseControl.vo.PilotAccount;




@Controller
public class MessageController {
	
	@Resource(name="MessageDao")
	private MessageDao messageDao;
	
	@Resource(name="LogDao")
	private LogDao logDao;	
	
	
	
	//메시지 보내기
	@RequestMapping(value="/add_message", method=RequestMethod.GET)
	public @ResponseBody String add_message(
				@RequestParam(value="MessageContent") String MessageContent,
				@RequestParam(value="Activator") String Activator,
				@RequestParam(value="Responder") String Responder,
				HttpSession session
		) {
	
		Message message = new Message();
		message.setMessageContent(MessageContent);
		message.setActivator(Activator);
		message.setResponder(Responder);

	
		messageDao.insertMessage(message);
		
		
		//관리자 로그인상태 확인	
		AdminAccount adminUser = (AdminAccount) session.getAttribute("loginAdminUser");
		
		//방역자 로그인상태 확인	
		PilotAccount pilot = (PilotAccount) session.getAttribute("loginPilot");
				
		
		if(adminUser != null && pilot == null) {	
			//로그입력 
			Log log = new Log();
			log.setLogContent(adminUser.getAdminID() + " 대화자가 " + Responder + " 응답자가에게 메시지를 보냈습니다");
			log.setActionType("메시지 보내기"); 
			log.setActivator(adminUser.getAdminID());   //관리자의 아이디		
			log.setResponder(Responder);
			
			logDao.insertLog(log); //db에 로그정보 삽입		
		}else if(adminUser == null && pilot != null) {
			//로그입력 
			Log log = new Log();
			log.setLogContent(pilot.getLicenseNumber() + " 대화자가 " + Responder + " 응답자에게 메시지를 보냈습니다");
			log.setActionType("메시지 보내기"); 
			log.setActivator(pilot.getLicenseNumber());   //방역자의 아이디		
			log.setResponder(Responder);
			
			logDao.insertLog(log); //db에 로그정보 삽입
		}		
		
		
		return "ok";
	}	
	
}
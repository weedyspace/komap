package com.my.komap.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.komap.dao.BoardDao;
import com.my.komap.dao.MapInfoDao;
import com.my.komap.dao.UserDao;
import com.my.komap.vo.Board;
import com.my.komap.vo.MapInfo;
import com.my.komap.vo.User;



@Controller
public class MainController {
	
	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Resource(name="BoardDao")
	private BoardDao boardDao;
	
	@Resource(name="MapInfoDao")
	private MapInfoDao mapInfoDao;	
	
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String fafsdfasdfas(

			) {
		
		
			
		return "test";
	}
	
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "home");
		return "home";
	}
	
	
	@RequestMapping(value="/intro", method=RequestMethod.GET)
	public String intro(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "intro");
		
		List<MapInfo> mapInfo = mapInfoDao.getAll();
		model.addAttribute("mapInfo", mapInfo);
		
		return "intro";
	}
	
	
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public String board(
				Model model, 
				HttpSession session, 
				@RequestParam(value="start") int start
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "board");

		
		//List<Board> list = boardDao.getAll(start);
		//model.addAttribute("writingList", list);
			
		return "board";
	}
	
	@RequestMapping(value="/boardList", method=RequestMethod.GET)
	public @ResponseBody List<Board> boardList(
				Model model, 
				HttpSession session, 
				@RequestParam(value="start") int start
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "board");

		
		List<Board> boardList = boardDao.getAll(start);
		model.addAttribute("writingList", boardList);
	
		
		return boardList;
	}
	
	
	
	@RequestMapping(value="/faq", method=RequestMethod.GET)
	public String faq(
				Model model, 
				HttpSession session,
				@RequestParam(value="start") int start
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "faq");

		
		List<Board> list = boardDao.getFaqBoardAll(start);
		model.addAttribute("writingList", list);
	
		return "faq";
	}
	
	
	
	@RequestMapping(value="/faqList", method=RequestMethod.GET)
	public @ResponseBody List<Board> faqList(
				Model model, 
				HttpSession session, 
				@RequestParam(value="start") int start
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		List<Board> faqList = boardDao.getFaqBoardAll(start);
		model.addAttribute("writingList", faqList);
	
		
		return faqList;
	}
	
	
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "register");
		return "register";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser); 
		
		model.addAttribute("menu", "login");
		return "login";
	}
	
	@RequestMapping(value="/account", method=RequestMethod.GET)
	public String account(
			Model model, 
			HttpSession session,
			@RequestParam(value="user_idx") int user_idx
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		User user = new User();
		user.setUser_idx(user_idx);
		
		User checkedUser = userDao.selectUserByUserIdx(user);
		System.out.println(checkedUser);
		model.addAttribute("checkedUser", checkedUser);
		
		
		model.addAttribute("menu", "account");
		return "account";
		
	}
	
	@RequestMapping(value="/boardWrite", method=RequestMethod.GET)
	public String boardWrite(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "board");
		return "boardWrite";
		
	}
	
	@RequestMapping(value="/faqWrite", method=RequestMethod.GET)
	public String faqWrite(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "faq");
		return "faqWrite";
		
	}	
	
	
	@RequestMapping(value="/boardRevise", method=RequestMethod.GET)
	public String boardRevise(
			Model model,
			HttpSession session,
			@RequestParam(value="board_idx") int board_idx
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("idx", board_idx);
		
		model.addAttribute("menu", "board");
		
		Board board = new Board();
		board.setBoard_idx(board_idx);
		
		Board boardDetail = boardDao.selectBoardByIndex(board);
		model.addAttribute("boardDetail", boardDetail);
		
		return "boardRevise";
		
	}
	
	@RequestMapping(value="/faqBoardRevise", method=RequestMethod.GET)
	public String faqBoardRevise(
			Model model,
			HttpSession session,
			@RequestParam(value="board_idx") int board_idx
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("idx", board_idx);
		
		model.addAttribute("menu", "faq");
		
		Board board = new Board();
		board.setBoard_idx(board_idx);
		
		Board boardDetail = boardDao.selectFaqBoardByIndex(board);
		model.addAttribute("boardDetail", boardDetail);
		
		return "faqBoardRevise";
		
	}
	
	
	@RequestMapping(value="/imageUpload", method=RequestMethod.GET)
	public String imageUpload(Model model, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		model.addAttribute("menu", "board");
		return "imageUpload";
		
	}
	
}
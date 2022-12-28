package com.my.komap.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.komap.dao.BoardDao;
import com.my.komap.vo.Board;
import com.my.komap.vo.Reply;
import com.my.komap.vo.User;

@Controller
public class BoardController {

	
	@Resource(name="BoardDao")
	private BoardDao boardDao;
	
	
	
	@RequestMapping(value="/getBoardCount" , method=RequestMethod.GET)
	public @ResponseBody int getBoardCount() {
		
		int boardTotalCount = boardDao.getBoardCount();
		
		return boardTotalCount;
	}
	
	
//	@RequestMapping(value="/getBoardSearch", method=RequestMethod.GET)
//	public @ResponseBody List<Board> getBoardSearch(
//				@RequestParam(value="word") String word
//			) {
//		
//		List<Board> list = boardDao.getBoardSearch(word);
//		
//		return list;
//	}
	
	
	@RequestMapping(value="/getFaqBoardCount" , method=RequestMethod.GET)
	public @ResponseBody int getFaqBoardCount() {
		
		int faqBoardTotalCount = boardDao.getFaqBoardCount();
		
		return faqBoardTotalCount;
	}
	
	
	
	
	
	@RequestMapping(value="/boardSearchResult", method=RequestMethod.GET)
	public String boardSearchResult(
				@RequestParam(value="word") String word,
				Model model,
				HttpSession session
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		List<Board> searchList = boardDao.boardSearchResult(word);
		model.addAttribute("searchList", searchList);
		
		model.addAttribute("menu", "board");
		return "boardSearchResult";
	}
	
	
	
	
	@RequestMapping(value="/faqBoardSearchResult", method=RequestMethod.GET)
	public String faqBoardSearchResult(
				@RequestParam(value="word") String searchContent,
				Model model,
				HttpSession session
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		List<Board> searchList = boardDao.faqBoardSearchResult(searchContent);
		model.addAttribute("searchList", searchList);
		
		model.addAttribute("menu", "faq");
		return "faqBoardSearchResult";
	}
	

//	@RequestMapping(value="/faqBoardSearchResultList", method=RequestMethod.GET)
//	public @ResponseBody List<Board> faqBoardSearchResultList(
//				@RequestParam(value="word") String word,
//				Model model,
//				HttpSession session
//			) {
//		
//		User loginUser = (User) session.getAttribute("loginUser");
//		model.addAttribute("loginUser", loginUser);
//		
//		List<Board> searchList = boardDao.faqBoardSearchResult(word);
//		model.addAttribute("searchList", searchList);
//		
//		model.addAttribute("menu", "faq");
//		return searchList;
//	}
	

	
}

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

import com.my.komap.dao.ReReplyDao;
import com.my.komap.dao.ReplyDao;
import com.my.komap.vo.Board;
import com.my.komap.vo.ReReply;
import com.my.komap.vo.Reply;
import com.my.komap.vo.User;


@Controller
public class ReReplyController {
	
	
	
	@Resource(name="ReReplyDao")
	private ReReplyDao reReplyDao; 	
	

	
	
	
	@RequestMapping(value="/insertReReply", method=RequestMethod.GET)
	public @ResponseBody String insertReReply(
			    @RequestParam(value="userIdx") int userIdx,
			    @RequestParam(value="replyIdx") int replyIdx,
			    @RequestParam(value="replyWritingIdx") int replyWritingIdx,
			    @RequestParam(value="replyBoxContent") String replyBoxContent,
			    @RequestParam(value="unix") int unix,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setReReply_user_idx(userIdx);
		reReply.setReReply_idx(replyIdx);
		reReply.setReReply_writing_idx(replyWritingIdx);
		reReply.setReReply_box_content(replyBoxContent);
		reReply.setUnix(unix);
		
		reReplyDao.insertReReply(reReply);
		
		return "ok";
	}	
	
	
	@RequestMapping(value="/insertFaqReReply", method=RequestMethod.GET)
	public @ResponseBody String insertFaqReReply(
			    @RequestParam(value="userIdx") int userIdx,
			    @RequestParam(value="replyIdx") int replyIdx,
			    @RequestParam(value="replyWritingIdx") int replyWritingIdx,
			    @RequestParam(value="replyBoxContent") String replyBoxContent,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setReReply_user_idx(userIdx);
		reReply.setReReply_idx(replyIdx);
		reReply.setReReply_writing_idx(replyWritingIdx);
		reReply.setReReply_box_content(replyBoxContent);
		
		reReplyDao.insertFaqReReply(reReply);
		
		return "ok";
	}
	

	
//	@RequestMapping(value="/getReReplyResult", method=RequestMethod.GET)
//	public @ResponseBody List<ReReply> getReReplyResult(
//			    @RequestParam(value="board_idx") int board_idx,
//				HttpSession session,
//				Model model
//			) {
//		
//		User loginUser = (User) session.getAttribute("loginUser");
//		model.addAttribute("loginUser", loginUser);
//		
//		List<ReReply> reReplyList = reReplyDao.getAll(board_idx);
//		model.addAttribute("reReplyList", reReplyList);
//		
//		return reReplyList;
//	}
	
	
	
//	@RequestMapping(value="/getFaqReReplyResult", method=RequestMethod.GET)
//	public @ResponseBody List<ReReply> getFaqReReplyResult(
//			    @RequestParam(value="board_idx") int board_idx,
//				HttpSession session,
//				Model model
//			) {
//		
//		User loginUser = (User) session.getAttribute("loginUser");
//		model.addAttribute("loginUser", loginUser);
//		
//		List<ReReply> reReplyList = reReplyDao.getFaqAll(board_idx);
//		model.addAttribute("reReplyList", reReplyList);
//		
//		//System.out.println(reReplyList);
//		return reReplyList;
//	}
	
	
	
	
	@RequestMapping(value="/updateReReplyContent", method=RequestMethod.GET)
	public @ResponseBody String uploadReReplyContent(
			    @RequestParam(value="idx") int reReply_idx,
			    @RequestParam(value="reReply_box_content") String reReply_box_content,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setIdx(reReply_idx);
		reReply.setReReply_box_content(reReply_box_content);
		
		reReplyDao.updateReReplyContent(reReply);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/updateFaqReReplyContent", method=RequestMethod.GET)
	public @ResponseBody String uploadFaqReReplyContent(
			    @RequestParam(value="idx") int reReply_idx,
			    @RequestParam(value="reReply_box_content") String reReply_box_content,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setIdx(reReply_idx);
		reReply.setReReply_box_content(reReply_box_content);
		
		reReplyDao.updateFaqReReplyContent(reReply);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/updateReReplyThumbsUp", method=RequestMethod.GET)
	public @ResponseBody String updateReReplyThumbsUp(
			    @RequestParam(value="idx") int reReplyThumbsUpIdx,
				@RequestParam(value="reReply_recomm_count") int totalReReplyThumbsUpCount,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setIdx(reReplyThumbsUpIdx);
		reReply.setReReply_recomm_count(totalReReplyThumbsUpCount);
		
		reReplyDao.updateThumbsUp(reReply);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/updateFaqReReplyThumbsUp", method=RequestMethod.GET)
	public @ResponseBody String updateFaqReReplyThumbsUp(
			    @RequestParam(value="idx") int reReplyThumbsUpIdx,
				@RequestParam(value="reReply_recomm_count") int totalReReplyThumbsUpCount,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setIdx(reReplyThumbsUpIdx);
		reReply.setReReply_recomm_count(totalReReplyThumbsUpCount);
		
		reReplyDao.updateFaqThumbsUp(reReply);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/updateReReplyThumbsDown", method=RequestMethod.GET)
	public @ResponseBody String updateReReplyThumbsDown(
			    @RequestParam(value="idx") int reReplyThumbsDownIdx,
				@RequestParam(value="reReply_decomm_count") int totalReReplyThumbsDownCount,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setIdx(reReplyThumbsDownIdx);
		reReply.setReReply_decomm_count(totalReReplyThumbsDownCount);
		
		reReplyDao.updateThumbsDown(reReply);
		
		return "ok";
	}	
	
	
	@RequestMapping(value="/updateFaqReReplyThumbsDown", method=RequestMethod.GET)
	public @ResponseBody String updateFaqReReplyThumbsDown(
			    @RequestParam(value="idx") int reReplyThumbsDownIdx,
				@RequestParam(value="reReply_decomm_count") int totalReReplyThumbsDownCount,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		ReReply reReply = new ReReply();
		reReply.setIdx(reReplyThumbsDownIdx);
		reReply.setReReply_decomm_count(totalReReplyThumbsDownCount);
		
		reReplyDao.updateFaqThumbsDown(reReply);
		
		return "ok";
	}	
	
	
	
	@RequestMapping(value="/reReReplyDelete", method=RequestMethod.GET)
	public @ResponseBody String reReReplyDelete(
			    @RequestParam(value="idx") int idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		reReplyDao.reReReplyDelete(idx); //댓글의 답글의 답글 삭제 
		
		
		return "ok";
	}	
	
	
	@RequestMapping(value="/faqReReReplyDelete", method=RequestMethod.GET)
	public @ResponseBody String faqReReReplyDelete(
			    @RequestParam(value="idx") int idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		reReplyDao.faqReReReplyDelete(idx); //댓글의 답글의 답글 삭제 
		
		
		return "ok";
	}
		

	
}
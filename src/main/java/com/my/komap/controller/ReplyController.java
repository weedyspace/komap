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
public class ReplyController {
	
	
	@Resource(name="ReplyDao")
	private ReplyDao replyDao; 
	
	@Resource(name="ReReplyDao")
	private ReReplyDao reReplyDao; 
	 	

	@RequestMapping(value="/uploadReply", method=RequestMethod.GET)
	public @ResponseBody String uploadReply(
			    @RequestParam(value="userIdx") int userIdx,
			    @RequestParam(value="replyWritingIdx") int replyWritingIdx,
			    @RequestParam(value="replyBoxContent") String replyBoxContent,
			    @RequestParam(value="unix") int unix,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_user_idx(userIdx);
		reply.setReply_writing_idx(replyWritingIdx);
		reply.setReply_box_content(replyBoxContent);
		reply.setUnix(unix);
		
		replyDao.insertReply(reply);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/boardReplyList", method=RequestMethod.GET)
	public @ResponseBody List<Reply> boardReplyList(
				@RequestParam(value="replyWritingIdx") int replyWritingIdx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		List<Reply> replyList = replyDao.getAll(replyWritingIdx);
		model.addAttribute("replyList", replyList);
			
		model.addAttribute("menu", "board");
		return replyList;
	}	
	
	
	@RequestMapping(value="/boardReReplyList", method=RequestMethod.GET)
	public @ResponseBody List<Reply> boardReReplyList(
				@RequestParam(value="replyWritingIdx") int replyWritingIdx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		List<Reply> reReplyList = replyDao.getAll(replyWritingIdx);
		model.addAttribute("reReplyList", reReplyList);
			
		model.addAttribute("menu", "board");
		return reReplyList;
	}	
	
	
	
	
	@RequestMapping(value="/uploadFaqReply", method=RequestMethod.GET)
	public @ResponseBody String uploadFaqReply(
			    @RequestParam(value="userIdx") int userIdx,
			    @RequestParam(value="replyWritingIdx") int replyWritingIdx,
			    @RequestParam(value="replyBoxContent") String replyBoxContent,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_user_idx(userIdx);
		reply.setReply_writing_idx(replyWritingIdx);
		reply.setReply_box_content(replyBoxContent);
		
		replyDao.insertFaqReply(reply);
		
		return "ok";
	}

	
	
	@RequestMapping(value="/updateReplyContent", method=RequestMethod.GET)
	public @ResponseBody String uploadReplyContent(
			    @RequestParam(value="reply_idx") int reply_idx,
			    @RequestParam(value="reply_box_content") String reply_box_content,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_idx(reply_idx);
		reply.setReply_box_content(reply_box_content);
		
		replyDao.updateReplyContent(reply);
		
		return "ok";
	}	
	
	

	@RequestMapping(value="/updateFaqReplyContent", method=RequestMethod.GET)
	public @ResponseBody String uploadFaqReplyContent(
			    @RequestParam(value="reply_idx") int reply_idx,
			    @RequestParam(value="reply_box_content") String reply_box_content,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_idx(reply_idx);
		reply.setReply_box_content(reply_box_content);
		
		replyDao.updateFaqReplyContent(reply);
		
		return "ok";
	}
	
			
	
	
	
	@RequestMapping(value="/updateReplyThumbsUp", method=RequestMethod.GET)
	public @ResponseBody String updateReplyThumbsUp(
			    @RequestParam(value="reply_idx") int reply_idx,
			    @RequestParam(value="reply_recomm_count") int reply_recomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_idx(reply_idx);
		reply.setReply_recomm_count(reply_recomm_count);
	
		
		replyDao.updateReplyThumbsUp(reply);
		
		return "ok";
	}
	
	
	
	
	@RequestMapping(value="/updateFaqReplyThumbsUp", method=RequestMethod.GET)
	public @ResponseBody String updateFaqReplyThumbsUp(
			    @RequestParam(value="reply_idx") int reply_idx,
			    @RequestParam(value="reply_recomm_count") int reply_recomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_idx(reply_idx);
		reply.setReply_recomm_count(reply_recomm_count);
	
		
		replyDao.updateFaqReplyThumbsUp(reply);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/updateReplyThumbsDown", method=RequestMethod.GET)
	public @ResponseBody String updateReplyThumbsDown(
			    @RequestParam(value="reply_idx") int reply_idx,
			    @RequestParam(value="reply_decomm_count") int reply_decomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_idx(reply_idx);
		reply.setReply_decomm_count(reply_decomm_count);
	
		
		replyDao.updateReplyThumbsDown(reply);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/updateFaqReplyThumbsDown", method=RequestMethod.GET)
	public @ResponseBody String updateFaqReplyThumbsDown(
			    @RequestParam(value="reply_idx") int reply_idx,
			    @RequestParam(value="reply_decomm_count") int reply_decomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Reply reply = new Reply();
		reply.setReply_idx(reply_idx);
		reply.setReply_decomm_count(reply_decomm_count);
	
		
		replyDao.updateFaqReplyThumbsDown(reply);
		
		return "ok";
	}
	
	

	@RequestMapping(value="/replyDelete", method=RequestMethod.GET)
	public @ResponseBody String replyDelete(
			    @RequestParam(value="reply_idx") int reply_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

//		Reply reply = new Reply();
//		reply.setReply_idx(reply_idx);
		
		replyDao.replyDelete(reply_idx); //댓글삭제 
		
		reReplyDao.reReplyDelete(reply_idx); //댓글의 답글 삭제
		
		return "ok";
	}	
	
	
	
	@RequestMapping(value="/faqReplyDelete", method=RequestMethod.GET)
	public @ResponseBody String faqReplyDelete(
			    @RequestParam(value="reply_idx") int reply_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

//		Reply reply = new Reply();
//		reply.setReply_idx(reply_idx);
		
		replyDao.faqReplyDelete(reply_idx); //댓글삭제 
		
		reReplyDao.faqReReplyDelete(reply_idx); //댓글의 답글 삭제
		
		return "ok";
	}	
	
	
	
	
	
//	@RequestMapping(value="/getReply", method=RequestMethod.GET)
//	public List<Reply> getReply(
//				HttpSession session,
//				Model model
//			) {
//		
//		User loginUser = (User) session.getAttribute("loginUser");
//		model.addAttribute("loginUser", loginUser);
//		
//
//		List<Reply> replyList = replyDao.getAll();
//		model.addAttribute("replyList", replyList);
//		System.out.println(replyList);
//		
//		return replyList;
//	}
	
//	@RequestMapping(value="/getReply", method=RequestMethod.GET)
//	public @ResponseBody List<Reply> getReply(
//			   @RequestParam(value="board_idx") int board_idx,
//				HttpSession session,
//				Model model
//			) {
//		
//		User loginUser = (User) session.getAttribute("loginUser");
//		model.addAttribute("loginUser", loginUser);
//
//		List<Reply> replyList = replyDao.getAll();
//		model.addAttribute("replyList", replyList);
//		System.out.println(replyList);
//		
//		return replyList;
//	}	
	
	
	
}

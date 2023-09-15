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
import org.springframework.web.multipart.MultipartFile;

import com.my.komap.dao.BoardDao;
import com.my.komap.dao.ReReplyDao;
import com.my.komap.dao.ReplyDao;
import com.my.komap.dao.UserDao;
import com.my.komap.vo.Board;
import com.my.komap.vo.ReReply;
import com.my.komap.vo.Reply;
import com.my.komap.vo.User;


@Controller
public class UserController {
	 
	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Resource(name="BoardDao")
	private BoardDao boardDao;
	
	@Resource(name="ReplyDao")
	private ReplyDao replyDao;	
	
	@Resource(name="ReReplyDao")
	private ReReplyDao reReplyDao;	
	
	
	
	@RequestMapping(value="/addUser", method=RequestMethod.GET)
	public @ResponseBody User addUser(
				@RequestParam(value="id") String id,
				@RequestParam(value="email") String email,
				@RequestParam(value="password") String password,
				@RequestParam(value="password1") String password1,
				@RequestParam(value="user_image") String userImage
			) {
		
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setUser_image(userImage);
		
		User checkedByEmailOrId = userDao.selectUserByEmailOrId(user);
		
				
		return checkedByEmailOrId;
	
		
	}
	
	
	
	@RequestMapping(value="/selectId", method=RequestMethod.GET)
	public @ResponseBody User selectId(
				@RequestParam(value="id") String id
			) {
		
		User user = new User();
		user.setId(id);
		
		User selectUserById = userDao.selectUserById(user);
			
		
		return selectUserById;
		
	}
	
	
	@RequestMapping(value="/selectEmail", method=RequestMethod.GET)
	public @ResponseBody User selectEmail(
				@RequestParam(value="email") String email
			) {
		
		User user = new User();
		user.setEmail(email);
		
		User selectUserByEmail = userDao.selectUserByEmail(user);
			
		
		return selectUserByEmail;
		
	}
	
	
	
	@RequestMapping(value="/insertUser", method=RequestMethod.GET)
	public @ResponseBody String insertUser(
				@RequestParam(value="id") String id,
				@RequestParam(value="email") String email,
				@RequestParam(value="password") String password,
				@RequestParam(value="password1") String password1,
				@RequestParam(value="user_image") String userImage
			) {
		
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setUser_image(userImage);
		
		userDao.insertUser(user);
		
		
		return "ok";

		
	}
	
	
	@RequestMapping(value="/googleLoginNewUser", method=RequestMethod.GET)
	public @ResponseBody String googleLoginNewUser(
				@RequestParam(value="id") String id,
				@RequestParam(value="email") String email,
				@RequestParam(value="user_image") String user_image
			) {
		
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setUser_image(user_image);
		
		User checkedUser = userDao.selectUserByEmailAndId(user);
		
		if(checkedUser == null) { //코맵에 가입이 되어있지 않은 경우
			userDao.insertEmailAndIdAndUserImage(user); //구글회원정보를 user의 데이터베이스에 넣어 가입시키기 
			return "ok";
		}else { //코맵에 이미 가입되어 있는 경우  
			return "dup";
		}
		
	}
	
	
	@RequestMapping(value="/googleLogin", method=RequestMethod.GET)
	public @ResponseBody User googleLogin(
				@RequestParam(value="id") String id,
				@RequestParam(value="email") String email,
				HttpSession session
			) {
		
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		
		User resultUser = userDao.selectUserByEmailAndId(user);
		session.setAttribute("loginUser", resultUser);
		
		return resultUser;
	}
	
	
	
	@RequestMapping(value="/checkWhenLogin", method=RequestMethod.GET)
	public @ResponseBody User login(
				@RequestParam(value="email") String email,
				@RequestParam(value="password") String password,
				HttpSession session
			) {
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		
		User resultUser = userDao.selectUserByEmailAndPassword(user);
		if(resultUser != null) {  //로그인 성공시
			session.setAttribute("loginUser", resultUser);
			System.out.println("resultUser: " + resultUser);
			System.out.println("resultUser.id: " + resultUser.getId());
		} else {
			System.out.println("!!!");
		}
		
		return resultUser;
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public @ResponseBody String logout(
				HttpSession session
			) {
		
		session.invalidate(); 
		
		return "ok";
	}
	
	
	@RequestMapping(value="/boardWriteButton", method=RequestMethod.GET)
	public @ResponseBody String boardWriteButton(
				@RequestParam(value="title") String title,
				@RequestParam(value="content") String content,
				@RequestParam(value="unix") int unix,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setUnix(unix);
		board.setUser_idx(loginUser.getUser_idx());
		
		
		boardDao.insert(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/faqBoardWriteButton", method=RequestMethod.GET)
	public @ResponseBody String faqBoardWriteButton(
				@RequestParam(value="title") String title,
				@RequestParam(value="content") String content,
				@RequestParam(value="unix") int unix,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setUnix(unix);
		board.setUser_idx(loginUser.getUser_idx());
		
		
		boardDao.faqInsert(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/boardWriteButtonRevised", method=RequestMethod.GET)
	public @ResponseBody String boardWriteButtonRevised(
				@RequestParam(value="title") String title,
				@RequestParam(value="content") String content,
				@RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setBoard_idx(board_idx);
		board.setUser_idx(loginUser.getUser_idx());
		
		boardDao.boardWriteButtonRevised(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/faqBoardWriteButtonRevised", method=RequestMethod.GET)
	public @ResponseBody String faqBoardWriteButtonRevised(
				@RequestParam(value="title") String title,
				@RequestParam(value="content") String content,
				@RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setBoard_idx(board_idx);
		board.setUser_idx(loginUser.getUser_idx());
		
		boardDao.faqBoardWriteButtonRevised(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/boardDeleteButton", method=RequestMethod.GET)
	public @ResponseBody String boardDeleteButton(
				@RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
	
		Board board = new Board();
		board.setBoard_idx(board_idx);
		board.setUser_idx(loginUser.getUser_idx());
		
		boardDao.boardDeleteButton(board);
		replyDao.deleteReply(board_idx);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/faqBoardDeleteButton", method=RequestMethod.GET)
	public @ResponseBody String faqBoardDeleteButton(
				@RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
	
		Board board = new Board();
		board.setBoard_idx(board_idx);
		board.setUser_idx(loginUser.getUser_idx());
		
		boardDao.faqBoardDeleteButton(board);
		//replyDao.deleteReply(board_idx);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/boardDetail", method=RequestMethod.GET)
	public String boardDetail(
				@RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		
		Board boardDetail = boardDao.selectBoardByIndex(board); //해당 board_idx가 가진 모든 정보를 가져옴
		model.addAttribute("boardDetail", boardDetail);
		
		List<Reply> replyList = replyDao.getAll(board_idx);
		model.addAttribute("replyList", replyList);
		
		List<ReReply> reReplyList = reReplyDao.getAll(board_idx);
		model.addAttribute("reReplyList", reReplyList);		
		
		model.addAttribute("menu", "board");
		return "boardDetail";
	}	
	
		
	
	
	@RequestMapping(value="/faqBoardDetail", method=RequestMethod.GET)
	public String faqBoardDetail(
				@RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		
		Board boardDetail = boardDao.selectFaqBoardByIndex(board); //해당 board_idx가 가진 모든 정보를 가져옴
		model.addAttribute("boardDetail", boardDetail);
		
		List<Reply> replyList = replyDao.getFaqAll(board_idx);
		model.addAttribute("replyList", replyList);
		
		List<ReReply> reReplyList = reReplyDao.getFaqAll(board_idx);
		model.addAttribute("reReplyList", reReplyList);		
		
		model.addAttribute("menu", "faq");
		return "faqBoardDetail";
	}
	
	
	@RequestMapping(value="/countThumbsUp", method=RequestMethod.GET)
	public @ResponseBody String countThumbsUp(
			    @RequestParam(value="board_idx") int board_idx,
				@RequestParam(value="recomm_count") int recomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		board.setRecomm_count(recomm_count);
		
		boardDao.updateThumbsUp(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/faqCountThumbsUp", method=RequestMethod.GET)
	public @ResponseBody String faqCountThumbsUp(
			    @RequestParam(value="board_idx") int board_idx,
				@RequestParam(value="recomm_count") int recomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		board.setRecomm_count(recomm_count);
		
		boardDao.updateFaqThumbsUp(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/countThumbsDown", method=RequestMethod.GET)
	public @ResponseBody String countThumbsDown(
			    @RequestParam(value="board_idx") int board_idx,
				@RequestParam(value="decomm_count") int decomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		board.setDecomm_count(decomm_count);
		
		boardDao.updateThumbsDown(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/faqCountThumbsDown", method=RequestMethod.GET)
	public @ResponseBody String faqCountThumbsDown(
			    @RequestParam(value="board_idx") int board_idx,
				@RequestParam(value="decomm_count") int decomm_count,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		board.setDecomm_count(decomm_count);
		
		boardDao.updateFaqThumbsDown(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/updateBoardClickCount", method=RequestMethod.GET)
	public @ResponseBody String boardClickCount(
			    @RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		
		boardDao.updateBoardClickCount(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/updateFaqBoardClickCount", method=RequestMethod.GET)
	public @ResponseBody String boardFaqClickCount(
			    @RequestParam(value="board_idx") int board_idx,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		Board board = new Board();
		board.setBoard_idx(board_idx);
		
		boardDao.updateFaqBoardClickCount(board);
		
		return "ok";
	}
	
	
	@RequestMapping(value="/updateUserImage", method=RequestMethod.GET)
	public String updateUserImage(
			    @RequestParam(value="user_idx") int user_idx,
			    @RequestParam(value="user_image") String user_image,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		User user = new User();
		user.setUser_idx(user_idx);
		user.setUser_image(user_image);
		
		userDao.updateUserImage(user);
		
		User checkedUser = userDao.selectUserByUserIdx(user);
		System.out.println(checkedUser);
		model.addAttribute("checkedUser", checkedUser);
		
		return "account";
	}
	
	
	@RequestMapping(value="/accountDelete", method=RequestMethod.GET)
	public @ResponseBody String accountDelte(
			    @RequestParam(value="id") String id,
			    @RequestParam(value="email") String email,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		User user = new User();
		user.setId(id);
		user.setEmail(email);
		
		userDao.accountDelete(user);
		
		return "ok";
	}	
	

	
	
	
	
	
}
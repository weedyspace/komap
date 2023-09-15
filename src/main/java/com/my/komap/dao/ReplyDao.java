package com.my.komap.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.komap.vo.Reply;





public class ReplyDao extends SqlSessionDaoSupport {
	
	public int insertReply(Reply r) {
		return this.getSqlSession().insert("reply.insert", r);
	}
	
	public int insertFaqReply(Reply r) {
		return this.getSqlSession().insert("reply.insertFaq", r);
	}
	
	public int updateReplyContent(Reply r) {
		return this.getSqlSession().update("reply.updateReplyContent",r);
	} 	
	
	public int updateFaqReplyContent(Reply r) {
		return this.getSqlSession().update("reply.updateFaqReplyContent",r);
	} 
	
	public List<Reply> getAll(int board_idx){
		return this.getSqlSession().selectList("reply.getAll",board_idx);
	} 
	
	public List<Reply> getFaqAll(int board_idx){
		return this.getSqlSession().selectList("reply.getFaqAll",board_idx);
	} 
	
	public int updateReplyThumbsUp(Reply r) {
		return this.getSqlSession().update("reply.updateThumbsUp",r);
	} 
	
	public int updateFaqReplyThumbsUp(Reply r) {
		return this.getSqlSession().update("reply.updateFaqThumbsUp",r);
	} 
	
	public int updateReplyThumbsDown(Reply r) {
		return this.getSqlSession().update("reply.updateThumbsDown",r);
	} 
	
	public int updateFaqReplyThumbsDown(Reply r) {
		return this.getSqlSession().update("reply.updateFaqThumbsDown",r);
	} 
	
	public int replyDelete(int r) {
		return this.getSqlSession().delete("reply.replyDelete", r);
	}
	
	public int faqReplyDelete(int r) {
		return this.getSqlSession().delete("reply.faqReplyDelete", r);
	}
	
	public int deleteReply(int r) {
		return this.getSqlSession().delete("reply.deleteReply", r);
	}
		
	
}






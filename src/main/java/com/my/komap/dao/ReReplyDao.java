package com.my.komap.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.komap.vo.ReReply;
import com.my.komap.vo.User;




public class ReReplyDao extends SqlSessionDaoSupport {
	
	public int insertReReply(ReReply r) {
		return this.getSqlSession().insert("reReply.insertRe", r);
	}
	
	
	public int insertFaqReReply(ReReply r) {
		return this.getSqlSession().insert("reReply.insertFaqRe", r);
	}
	
	
	public int reReplyDelete(int r) {
		return this.getSqlSession().delete("reReply.reReplyDelete", r);
	}	
	
	
	public int faqReReplyDelete(int r) {
		return this.getSqlSession().delete("reReply.faqReReplyDelete", r);
	}
	
	
	public List<ReReply> getAll(int b) {
		return this.getSqlSession().selectList("reReply.getAll", b);
	}
	
	
	public List<ReReply> getFaqAll(int b) {
		return this.getSqlSession().selectList("reReply.getFaqAll", b);
	}
	
	
	public int updateReReplyContent(ReReply r) {
		return this.getSqlSession().update("reReply.updateReReplyContent", r);
	}	
	
	public int updateFaqReReplyContent(ReReply r) {
		return this.getSqlSession().update("reReply.updateFaqReReplyContent", r);
	}
	
	
	public int updateThumbsUp(ReReply r) {
		return this.getSqlSession().update("reReply.updateThumbsUp", r);
	}
	
	
	public int updateFaqThumbsUp(ReReply r) {
		return this.getSqlSession().update("reReply.updateFaqThumbsUp", r);
	}
	
	
	public int updateThumbsDown(ReReply r) {
		return this.getSqlSession().update("reReply.updateThumbsDown", r);
	}
	
	
	public int updateFaqThumbsDown(ReReply r) {
		return this.getSqlSession().update("reReply.updateFaqThumbsDown", r);
	}
	
	
	public int reReReplyDelete(int r) {
		return this.getSqlSession().delete("reReply.reReReplyDelete", r);
	}	
	
	
	public int faqReReReplyDelete(int r) {
		return this.getSqlSession().delete("reReply.faqReReReplyDelete", r);
	}	
	
	
}
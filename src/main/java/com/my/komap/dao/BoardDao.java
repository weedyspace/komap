package com.my.komap.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.komap.vo.Board;




public class BoardDao extends SqlSessionDaoSupport {
	
	public List<Board> getAll(int start){
		return this.getSqlSession().selectList("board.getAll", start);
	} 
	
	public List<Board> getFaqBoardAll(int start){
		return this.getSqlSession().selectList("board.getFaqBoardAll", start);
	} 
	
	public int insert(Board b) {
		return this.getSqlSession().insert("board.insert", b);
	}
	
	public int faqInsert(Board b) {
		return this.getSqlSession().insert("board.faqInsert", b);
	}
	
	public int boardWriteButtonRevised(Board b) {
		return this.getSqlSession().update("board.boardWriteButtonRevised", b);
	}
	
	public int faqBoardWriteButtonRevised(Board b) {
		return this.getSqlSession().update("board.faqBoardWriteButtonRevised", b);
	}
	
	public int boardDeleteButton(Board b) {
		return this.getSqlSession().delete("board.boardDeleteButton", b);
	}
	
	public int faqBoardDeleteButton(Board b) {
		return this.getSqlSession().delete("board.faqBoardDeleteButton", b);
	}
	
	public Board selectBoardByIndex(Board b) {
		return this.getSqlSession().selectOne("board.selectBoardByIndex", b);
	}
	
	public Board selectFaqBoardByIndex(Board b) {
		return this.getSqlSession().selectOne("board.selectFaqBoardByIndex", b);
	}
	
	public int getBoardCount() {
		return this.getSqlSession().selectOne("board.getBoardCount");
	}
	
	public int getFaqBoardCount() {
		return this.getSqlSession().selectOne("board.getFaqBoardCount");
	}	
	
	public List<Board> getBoardSearch(String word) {
		return this.getSqlSession().selectList("board.getBoardSearch", word);
	}
	
	public int updateThumbsUp(Board b) {
		return this.getSqlSession().update("board.updateThumbsUp", b);
	}
	
	public int updateFaqThumbsUp(Board b) {
		return this.getSqlSession().update("board.updateFaqThumbsUp", b);
	}
	
	public int updateThumbsDown(Board b) {
		return this.getSqlSession().update("board.updateThumbsDown", b);
	}
	
	public int updateFaqThumbsDown(Board b) {
		return this.getSqlSession().update("board.updateFaqThumbsDown", b);
	}
	
	public int updateBoardClickCount(Board b) {
		return this.getSqlSession().update("board.updateBoardClickCount", b);
	}
	
	public int updateFaqBoardClickCount(Board b) {
		return this.getSqlSession().update("board.updateFaqBoardClickCount", b);
	}

	public List<Board> boardSearchResult(String word) {
		return this.getSqlSession().selectList("board.getBoardSearch", word);
	}

	public List<Board> faqBoardSearchResult(String word) {
		return this.getSqlSession().selectList("board.getFaqBoardSearch", word);
	}

	
}

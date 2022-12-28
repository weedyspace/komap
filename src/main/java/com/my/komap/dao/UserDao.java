package com.my.komap.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.komap.vo.User;



public class UserDao extends SqlSessionDaoSupport {
	
	public int insertUser(User u) {
		return this.getSqlSession().insert("user.insertUser", u);
	}
	
	public int insertEmailAndIdAndUserImage(User u) {
		return this.getSqlSession().insert("user.insertEmailAndIdAndUserImage", u);
	}
	
	public User selectUserByEmail(User u) {
		return this.getSqlSession().selectOne("user.selectUserByEmail", u);	
	}
	
	public User selectUserById(User u) {
		return this.getSqlSession().selectOne("user.selectUserById", u);	
	}	
	
	public User selectUserByEmailAndId(User u) {
		return this.getSqlSession().selectOne("user.selectUserByEmailAndId", u);
	}
	
	public User selectUserByEmailOrId(User u) {
		return this.getSqlSession().selectOne("user.selectUserByEmailOrId", u);
	}
	
	public User selectUserByUserIdx(User u) {
		return this.getSqlSession().selectOne("user.selectUserByUserIdx", u);
	}
	
	public User selectUserByEmailAndPassword(User u) {
		return this.getSqlSession().selectOne("user.selectUserByEmailAndPassword", u);
	}
	
	public int updateUserImage(User u) {
		return this.getSqlSession().update("user.updateUserImage", u);
	}
	
	public List<User> getAll() {
		return this.getSqlSession().selectList("user.getAll");
	}
	
	public int accountDelete(User u) {
		return this.getSqlSession().delete("user.accountDelete", u);
	}
	
}
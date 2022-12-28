package com.my.komap.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.komap.vo.MapInfo;
import com.my.komap.vo.ReReply;




public class MapInfoDao extends SqlSessionDaoSupport {
	
	public int insertMapInfo(MapInfo m) {
		return this.getSqlSession().insert("mapInfo.insertMapInfo", m);
	}	
	
	
	public int updateMapInfo(MapInfo m) {
		return this.getSqlSession().update("mapInfo.updateMapInfo", m);
	}	
	
	
	public List<MapInfo> getAll() {
		return this.getSqlSession().selectList("mapInfo.getAll");
	}	

}

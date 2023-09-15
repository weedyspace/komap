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

import com.my.komap.dao.MapInfoDao;
import com.my.komap.vo.Board;
import com.my.komap.vo.MapInfo;
import com.my.komap.vo.ReReply;
import com.my.komap.vo.Reply;
import com.my.komap.vo.User;






@Controller
public class MapInfoController {
	

	@Resource(name="MapInfoDao")
	private MapInfoDao mapInfoDao; 
	
	
	
	
	@RequestMapping(value="/address_information", method=RequestMethod.GET)
	public @ResponseBody String address_information(
				@RequestParam(value="id") String id,
			    @RequestParam(value="lat") float lat,
			    @RequestParam(value="lng") float lng,
			    @RequestParam(value="address") String address,
			    @RequestParam(value="detail_address") String detail_address,
			    @RequestParam(value="business_name") String business_name,
			    @RequestParam(value="contact_number") String contact_number,
			    @RequestParam(value="email") String email,
			    @RequestParam(value="website") String website,
			    @RequestParam(value="instagram") String instagram,
			    @RequestParam(value="bitcoin") String btc,
			    @RequestParam(value="bitcoin_cash") String bch,
			    @RequestParam(value="ethereum") String eth,
			    @RequestParam(value="cardano") String ada,
			    @RequestParam(value="decentraland") String mana,
			    @RequestParam(value="dogecoin") String doge,
			    @RequestParam(value="usdt") String usdt,			    
			    @RequestParam(value="information") String information,
			    @RequestParam(value="unix") int unix,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		MapInfo mapInfo = new MapInfo();
		mapInfo.setId(id);
		mapInfo.setLat(lat);
		mapInfo.setLng(lng);
		mapInfo.setAddress(address);
		mapInfo.setDetail_address(detail_address);
		mapInfo.setBusiness_name(business_name);
		mapInfo.setContact_number(contact_number);
		mapInfo.setEmail(email);
		mapInfo.setWebsite(website);
		mapInfo.setInstagram(instagram);
		mapInfo.setBitcoin(btc);
		mapInfo.setBitcoin_cash(bch);
		mapInfo.setEthereum(eth);
		mapInfo.setCardano(ada);
		mapInfo.setDecentraland(mana);
		mapInfo.setDogecoin(doge);
		mapInfo.setUsdt(usdt);		
		mapInfo.setInformation(information);
		mapInfo.setUnix(unix);
		
		
		mapInfoDao.insertMapInfo(mapInfo);
		
		return "ok";
	}
	
	
	
	@RequestMapping(value="/address_information_update", method=RequestMethod.GET)
	public @ResponseBody String address_information_update(
				@RequestParam(value="idx") int idx,
				@RequestParam(value="id") String id,
			    @RequestParam(value="lat") float lat,
			    @RequestParam(value="lng") float lng,
			    @RequestParam(value="address") String address,
			    @RequestParam(value="detail_address") String detail_address,
			    @RequestParam(value="business_name") String business_name,
			    @RequestParam(value="contact_number") String contact_number,
			    @RequestParam(value="email") String email,
			    @RequestParam(value="website") String website,
			    @RequestParam(value="instagram") String instagram,
			    @RequestParam(value="bitcoin") String btc,
			    @RequestParam(value="bitcoin_cash") String bch,
			    @RequestParam(value="ethereum") String eth,
			    @RequestParam(value="cardano") String ada,
			    @RequestParam(value="decentraland") String mana,
			    @RequestParam(value="dogecoin") String doge,
			    @RequestParam(value="usdt") String usdt,
			    @RequestParam(value="information") String information,
			    @RequestParam(value="unix") int unix,
				HttpSession session,
				Model model
			) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		

		MapInfo mapInfo = new MapInfo();
		mapInfo.setIdx(idx);
		mapInfo.setId(id);
		mapInfo.setLat(lat);
		mapInfo.setLng(lng);
		mapInfo.setAddress(address);
		mapInfo.setDetail_address(detail_address);
		mapInfo.setBusiness_name(business_name);
		mapInfo.setContact_number(contact_number);
		mapInfo.setEmail(email);
		mapInfo.setWebsite(website);
		mapInfo.setInstagram(instagram);
		mapInfo.setBitcoin(btc);
		mapInfo.setBitcoin_cash(bch);
		mapInfo.setEthereum(eth);
		mapInfo.setCardano(ada);
		mapInfo.setDecentraland(mana);
		mapInfo.setDogecoin(doge);
		mapInfo.setUsdt(usdt);
		mapInfo.setInformation(information);
		mapInfo.setUnix(unix);
		
		
		mapInfoDao.updateMapInfo(mapInfo);
		
		return "ok";
	}
	
	
	

	
	@RequestMapping(value="/get_address_information", method=RequestMethod.GET)
	public @ResponseBody List<MapInfo> getAddressInformation(
				Model model
			) {
		
		List<MapInfo> mapInfo = mapInfoDao.getAll();
		model.addAttribute("mapInfo", mapInfo);
			
		
		return mapInfo;
	}		
	

}

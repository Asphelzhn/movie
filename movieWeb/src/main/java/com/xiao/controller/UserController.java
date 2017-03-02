package com.xiao.controller;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiao.bean.User;
import com.xiao.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login")
	public String login(){
		
		return "user/login";
	}
	
	@RequestMapping(value="/list")
	public String list(){
		return "user/list";
	}
	
	@RequestMapping(value="/load",method=RequestMethod.POST)
	@ResponseBody
	public String load(String sEcho, int iDisplayStart, int iDisplayLength) throws Exception{
		
		System.out.println(sEcho+":"+iDisplayStart+":"+iDisplayLength);
		JSONObject d  = new JSONObject();
		JSONArray rows = new JSONArray();
		JSONArray resultRows = new JSONArray();
		d.put("sEcho", sEcho);
		
		List<User> users = userService.findUser();
		
//		//设置50个数据
//		for(int i=0;i<50;i++){
//			JSONArray cells = new JSONArray();
//			cells.add("xiao");
//			cells.add("jie");
//			cells.add("do");
//			rows.add(cells);
//		}
		
		for(User user : users){
			JSONArray cells = new JSONArray();
			cells.add(user.getFirstname());
			cells.add(user.getLastname());
			cells.add(user.getPhone());
			rows.add(cells);
		}
		
		for(int i=iDisplayStart;i<rows.size()&&i<(iDisplayStart+iDisplayLength);i++){
			resultRows.add(rows.get(i));
		}
		
		d.put("iTotalRecords", users.size());
		d.put("iTotalDisplayRecords",users.size());
		d.put("aaData",resultRows);
		return d.toString();
	}

}

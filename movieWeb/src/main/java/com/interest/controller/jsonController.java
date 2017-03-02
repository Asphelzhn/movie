package com.interest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interest.bean.User;
import com.interest.service.UserService;


@Controller
@RequestMapping(value="/json")
public class jsonController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/json")
	public void index_jsp(Model model){
		
	}
	
	@RequestMapping(value="/listUser",method=RequestMethod.POST)
	@ResponseBody
	public String getListUser(int page, int rows){
		
		System.out.println(page);
		System.out.println(rows);
		
//		System.out.println(userService.findUser().getFirstname());
//		
//		User u = new User();
//		u.setEmail("1222@qq.com");
//		u.setFirstname("mike");
//		u.setLastname("li");
//		u.setPhone("1222222222222");
//		
//		User u1 = new User();
//		u1.setEmail("1222@qq.com");
//		u1.setFirstname("mike");
//		u1.setLastname("li");
//		u1.setPhone("1222222222222");
//		
		List<User> list = new ArrayList<User>();
//		list.add(u);
//		list.add(u1);
//		
//		for(int i =0; i < 20; i++){
//			
//			list.add(u1);
//			
//		}
		
		list = userService.findUser();
		
		Map<String, Object> d = new HashMap<String, Object>();
		d.put("total", list.size());
		d.put("rows", list);
		
		JSONObject json = JSONObject.fromObject(d);
		System.out.println(json);
		
		
		
		return json.toString();
		
	}

	@RequestMapping(value="/getJson",method=RequestMethod.GET)
	@ResponseBody
	public String getJson(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", 200);
		jsonObject.put("message", "你好");
		return jsonObject.toString();
	}
	
}

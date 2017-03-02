package com.xiao.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xiao.bean.User;
import com.xiao.dao.UserDao;


@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> findUser() {
		String sql = "select * from user";
		List<User> list = new ArrayList<User>();
        List rows = jdbcTemplate.queryForList(sql);
        Iterator it = rows.iterator();
        while(it.hasNext()){
        	 User user = new User();
        	 Map userMap = (Map)it.next();
        	 user.setEmail((String)userMap.get("email"));
        	 user.setFirstname((String)userMap.get("firstname"));
        	 user.setLastname((String)userMap.get("lastname"));
        	 user.setPhone((String)userMap.get("phone"));
        	 user.setId((Integer)userMap.get("id"));
        	 list.add(user);
        }
        return list;
	}
	
	

}

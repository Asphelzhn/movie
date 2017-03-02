package com.xiao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiao.bean.User;
import com.xiao.dao.UserDao;
import com.xiao.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> findUser() {
		
		return userDao.findUser();
	}

}

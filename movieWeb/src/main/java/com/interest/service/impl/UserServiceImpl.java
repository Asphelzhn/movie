package com.interest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interest.bean.User;
import com.interest.dao.UserDao;
import com.interest.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> findUser() {
		
		return userDao.findUser();
	}

}

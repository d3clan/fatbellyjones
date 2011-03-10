package com.viviquity.db.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viviquity.core.model.User;
import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.dao.UserDao;
import com.viviquity.db.manager.UserManager;

@Service("userManager")
public class UserManagerImpl extends JpaManagerImpl<Long, User> implements UserManager {

    @Autowired
    public UserManagerImpl(JpaDao<Long, User> userDao) {
	super(userDao);
    }

    public User findByUsername(String username) {
	return ((UserDao) dao).findByUsername(username);
    }

}

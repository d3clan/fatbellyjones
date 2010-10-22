package com.viviquity.db.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viviquity.core.model.Role;
import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.manager.RoleManager;

@Service("roleManager")
public class RoleManagerImpl extends JpaManagerImpl<Long, Role> implements RoleManager {

    @Autowired
    public RoleManagerImpl(JpaDao<Long, Role> eventDao) {
	super(eventDao);
    }

}

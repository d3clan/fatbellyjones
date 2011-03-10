package com.viviquity.db.dao.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.viviquity.core.model.Role;
import com.viviquity.db.dao.JpaDaoImpl;
import com.viviquity.db.dao.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl extends JpaDaoImpl<Long, Role> implements RoleDao {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
	super.setEntityManagerFactory(entityManagerFactory);
    }

}

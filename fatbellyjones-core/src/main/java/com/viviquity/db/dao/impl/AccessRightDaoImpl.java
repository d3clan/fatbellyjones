package com.viviquity.db.dao.impl;

import com.viviquity.db.dao.AccessRightDao;
import com.viviquity.readmy.db.utils.jpa.GenericDaoJpa;
import com.viviquity.readmy.entity.AccessRight;

public class AccessRightDaoImpl extends GenericDaoJpa<AccessRight, Long> implements AccessRightDao {

	public AccessRightDaoImpl(Class<AccessRight> persistentClass) {
		super(persistentClass);
	}

}

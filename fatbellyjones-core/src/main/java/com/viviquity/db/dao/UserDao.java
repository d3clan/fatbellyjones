package com.viviquity.db.dao;

import com.viviquity.core.model.User;

public interface UserDao extends JpaDao<Long, User> {

    public User findByUsername(String username);

}

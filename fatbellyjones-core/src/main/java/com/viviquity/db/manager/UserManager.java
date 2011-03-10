package com.viviquity.db.manager;

import com.viviquity.core.model.User;

public interface UserManager extends JpaManager<Long, User> {

    public User findByUsername(String username);

}

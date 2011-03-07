package com.viviquity.db.dao;

import java.util.List;

import com.viviquity.core.model.News;

public interface NewsDao extends JpaDao<Long, News> {

    public List<News> find(int start, int end);

}

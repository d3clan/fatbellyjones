package com.viviquity.db.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viviquity.core.model.News;
import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.dao.NewsDao;
import com.viviquity.db.manager.NewsManager;

@Service("newsManager")
public class NewsManagerImpl extends JpaManagerImpl<Long, News> implements NewsManager {

    @Autowired
    public NewsManagerImpl(JpaDao<Long, News> newsDao) {
	super(newsDao);
    }

    @Override
    public List<News> find(int start, int end) {
	return ((NewsDao) dao).find(start, end);
    }

}

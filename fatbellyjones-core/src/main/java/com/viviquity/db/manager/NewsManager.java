package com.viviquity.db.manager;

import java.util.List;

import com.viviquity.core.model.News;

public interface NewsManager extends JpaManager<Long, News> {

    public List<News> find(int start, int end);

}

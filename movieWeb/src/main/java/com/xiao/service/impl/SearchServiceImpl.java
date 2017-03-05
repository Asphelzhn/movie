package com.xiao.service.impl;

import com.xiao.bean.Movie;
import com.xiao.dao.SearchDao;
import com.xiao.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiaojie on 17/3/5.
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchDao searchDao;

    public List<Movie> search(String keyWord) {
        return searchDao.search(keyWord);
    }
}

package com.xiao.dao;

import com.xiao.bean.Movie;

import java.util.List;

/**
 * Created by xiaojie on 17/3/5.
 */
public interface SearchDao {
    List<Movie> search(String keyWord);
}

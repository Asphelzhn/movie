package com.xiao.service;

import com.xiao.bean.Movie;

import java.util.List;

/**
 * Created by xiaojie on 17/3/5.
 */
public interface SearchService {
    List<Movie> search(String keyWord);
}

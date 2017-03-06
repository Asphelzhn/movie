package com.xiao.controller;

import com.xiao.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(String keywords) {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("list", searchService.search(keywords));
        return modelAndView;

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }


}

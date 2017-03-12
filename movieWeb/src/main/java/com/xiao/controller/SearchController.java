package com.xiao.controller;

import com.xiao.service.SearchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    private static int validTime = 7 * 24 * 3600;//设置一周的有效期
    private static final Logger logger = Logger.getLogger(SearchController.class);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(@CookieValue(value = "userId", defaultValue = "unknown") String userId, HttpServletResponse response, String keywords) {
        logger.info("userId: " + userId);
        if ("unknown".equals(userId)) {
            Cookie cookie = new Cookie("userId", UUID.randomUUID().toString().replaceAll("-", ""));
            cookie.setMaxAge(validTime);
            response.addCookie(cookie);
        }

        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("list", searchService.search(keywords));
        return modelAndView;

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@CookieValue(value = "userId", defaultValue = "unknown") String userId, HttpServletResponse response) {
        logger.info("userId: " + userId);
        if ("unknown".equals(userId)) {
            Cookie cookie = new Cookie("userId", UUID.randomUUID().toString().replaceAll("-", ""));
            cookie.setMaxAge(validTime);
            response.addCookie(cookie);
        }
        return new ModelAndView("index");
    }


}

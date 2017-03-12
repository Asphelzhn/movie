package com.xiao.controller;

import com.xiao.Singleton.Source;
import com.xiao.service.SearchService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    public String statistics(@CookieValue(value = "userId", defaultValue = "unknown") String userId, String url) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("message", "没有进行数据的统计");
        try {
            if (!"unknown".equals(userId)) {
                jsonObject.put("message", userId + ":统计成功");
                if(StringUtils.isNotBlank(url)) {
                    Source.getInstance().put(userId, url);
                    logger.info("userId: "+ userId + ", url: " + url + ", times: " + Source.getInstance().get(userId).getRecord().get(url));
                }
            }
        }catch (Exception e){
            jsonObject.put("status", 500);
            jsonObject.put("message", e.getLocalizedMessage());
            e.printStackTrace();
        }finally {
            return jsonObject.toString();
        }
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

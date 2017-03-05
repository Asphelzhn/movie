package com.xiao.controller;

import com.xiao.service.SearchService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String search(String keywords) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("message", "");
        jsonObject.put("list", null);
        try {
            jsonObject.put("list", searchService.search(keywords));
        }catch (Exception e){
            System.out.println(e.getMessage());
            jsonObject.put("status", 500);
            jsonObject.put("message", e.getMessage());
        }finally {
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }


}

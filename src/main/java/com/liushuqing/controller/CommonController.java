package com.liushuqing.controller;

import com.liushuqing.dto.SearchTask;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by liushuqing on 15/12/15.
 */
@Controller
@RequestMapping(value = "base")
public class CommonController extends BaseController {

    @RequestMapping(value = "hello")
    public String getHello(@RequestParam(required = false) HttpServletRequest request,Model model) {
        SearchTask task=new SearchTask();
        task.setCreateTime(new Date());
        task.setId(100);
        task.setName("这是一个task");
        model.addAttribute("task",task);
        return "hello";
    }
}

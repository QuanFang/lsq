package com.liushuqing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liushuqing on 15/12/15.
 */
@Controller
@RequestMapping(value = "base")
public class CommonController extends BaseController {

    @RequestMapping(value = "hello")
    public String getHello(@RequestParam(required = false) HttpServletRequest request) {
        return "hello";
    }
}

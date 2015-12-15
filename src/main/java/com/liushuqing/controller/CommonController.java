package com.liushuqing.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liushuqing on 15/12/15.
 */
@Controller
@RequestMapping(value = "base")
public class CommonController extends BaseController {

    @RequestMapping(value = "hello")
    public String getHello(HttpRequest request) {
        return "hello";
    }
}

package com.liushuqing.controller;

/**
 * Created by liushuqing on 15/12/16.
 */


import com.liushuqing.web.ApiResult;
import com.weidian.captcha.util.CaptchaGenerator;
import com.weidian.captcha.util.CaptchaImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class CodeController {

    private static Logger logger = LoggerFactory.getLogger(CodeController.class);

    private CaptchaGenerator captchaGenerator;

    @RequestMapping("/check")
    public
    @ResponseBody
    ApiResult register(HttpServletRequest request, HttpServletResponse response) {
        String result = request.getParameter("result");
        ApiResult apiResult = new ApiResult();
        HttpSession session = request.getSession();
        if (!(result.equalsIgnoreCase(session.getAttribute("result").toString()))) {  //忽略验证码大小写
            apiResult.setMsg("验证码不正确！傻逼么这么简单都不会做！！！ =_=");
            apiResult.setCode(200);

        } else {
            apiResult.setMsg("验证码正确！你真是棒棒的！！！可以给你跳转页面23333333");
            apiResult.setCode(304);
        }
        return apiResult;
    }

    @RequestMapping("/code")
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(captchaGenerator==null){
            captchaGenerator=new CaptchaGenerator(100,30,5,4,"Monaco ",20);//Monaco
        }
        CaptchaImage image=captchaGenerator.generateImage();
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", image.getExpression());
        session.setAttribute("result", image.getResult());
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(image.getBufferedImage(), "jpeg", sos);
        sos.close();
    }

}
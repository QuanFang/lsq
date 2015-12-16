package com.liushuqing.controller;

/**
 * Created by liushuqing on 15/12/16.
 */

import com.liushuqing.util.CaptchaImage;
import com.liushuqing.web.ApiResult;
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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class CodeController {

    private static Logger logger = LoggerFactory.getLogger(CodeController.class);
    private int width = 150;// 定义图片的width
    private int height = 40;// 定义图片的height
    private int xx = 15;
    private int fontHeight = 35;
    private int codeY = 30;
    private final int MIN = 0;
    private final int MAX = 100;

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
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        //getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Comic Sans MS", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);
        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        // 一次画10条位置不一样的干扰线，分不同颜色，画三次
        drawRandomLines(gd, 10, 3);
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 得到随机产生的验证码数字。
        int a, b;
        //设置a和b哪一个大一点
        boolean aIsBigger = random.nextBoolean();
        a = getRandomAB(aIsBigger)[0];
        b = getRandomAB(aIsBigger)[1];
        //设置加法还是减法
        boolean caculate = random.nextBoolean();
        int result = getResult(a, b, caculate);
        while (!check(result)) {
            a = getRandomAB(aIsBigger)[0];
            b = getRandomAB(aIsBigger)[1];
            result = getResult(a, b, caculate);
        }
        randomCode.append(a);
        if (caculate) {
            randomCode.append("+");
        } else {
            randomCode.append("-");
        }
        randomCode.append(b);
        randomCode.append("=");
        randomCode.append("?");
        char[] chars = randomCode.toString().toCharArray();

        //字体消除锯齿
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < chars.length; i++) {
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(String.valueOf(chars[i]), (i + 1) * xx, codeY);
        }
        logger.debug("\n 验证码是: " + randomCode.toString() + "\n 验证码计算结果是: " + result);
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", randomCode.toString());
        session.setAttribute("result", result);
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

    /**
     * 判断结果是否合适
     *
     * @param res 计算结果
     * @return 是否合适
     */
    boolean check(int res) {
        if (MIN <= res && res <= MAX) {
            return true;
        }
        return false;
    }

    /**
     * 得到计算结果
     *
     * @param a   第一个运算数
     * @param b   第二个运算数
     * @param cal 是否是加法运算
     * @return
     */
    int getResult(int a, int b, boolean cal) {
        if (cal)
            return a + b;
        return a - b;
    }

    int[] getRandomAB(boolean aIsBigger) {
        Random random = new Random();
        int[] ab = {0, 0};
        if (aIsBigger) {
            ab[0] = random.nextInt(100);
            ab[1] = random.nextInt(10);
        } else {
            ab[0] = random.nextInt(10);
            ab[1] = random.nextInt(100);
        }
        return ab;
    }

    /**
     * 画干扰线
     *
     * @param gra   画笔
     * @param num   一次化多少条
     * @param times 多少次
     */
    void drawRandomLines(Graphics2D gra, int num, int times) {
        int red, green, blue, x, y, xl, yl;
        Random random = new Random();
        for (int i = 0; i < times; i++) {
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            gra.setColor(new Color(red, green, blue));
            //每一条线都画的位置不一样
            for (int j = 0; j < num; j++) {
                x = random.nextInt(width);
                y = random.nextInt(height);
                xl = random.nextInt(30);
                yl = random.nextInt(30);
                gra.drawLine(x, y, x + xl, y + yl);
            }
        }
    }
}
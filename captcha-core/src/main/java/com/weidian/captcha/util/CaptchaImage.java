package com.weidian.captcha.util;

import java.awt.image.BufferedImage;

/**
 * Created by liushuqing on 15/12/16.
 */
public class CaptchaImage {
    private int result;
    private int a;
    private int b;
    private String expression;
    private BufferedImage bufferedImage;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public String toString() {
        return "验证码内容是： "+this.expression;
    }
}

package com.liushuqing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by liushuqing on 15/12/16.
 * 用来生成验证码，使用两位数的加减运算，默认情况下，a+(-)b=c,a和b之中，必须有一个是个位数，c不能超过100
 */
public class CaptchaGenerator {
    private static Logger logger = LoggerFactory.getLogger(CaptchaGenerator.class);
    private int width = 150;// 定义图片的width
    private int height = 40;// 定义图片的height
    private int fontWidth = 15;//字体宽度
    private int fontHeight = 35;//字体高度
    private int offset = 30;
    private int MIN = 0;
    private int MAX = 100;
    private BufferedImage buffImg;
    Graphics2D gd;//= buffImg.createGraphics();
    private int linesTims = 3;
    private int linesNum = 10;
    private String font = "Comic Sans MS";
    private Font fo;
    private CaptchaImage image;


    /**
     * 获得一张验证码图片
     *
     * @return
     */
    public CaptchaImage generateImage() {
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);
        // 设置字体。
        gd.setFont(fo);
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
            gd.drawString(String.valueOf(chars[i]), (i + 1) * fontWidth, offset);
        }
        logger.debug("\n 验证码是: " + randomCode.toString() + "\n 验证码计算结果是: " + result);
        if (buffImg == null || image == null) {
            throw new NullPointerException("BufferedImage目前为空，无法调用generateImage方法！！！");
        }
        image.setA(a);
        image.setB(b);
        image.setExpression(randomCode.toString());
        image.setResult(result);
        image.setBufferedImage(buffImg);
        return image;
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

    /**
     * 默认的构造方法，
     * 字体为Comic Sans MS
     * 定义图片的宽度 150
     * 定义图片的高度 40
     */
    public CaptchaGenerator() {
        fo = new Font(font, Font.BOLD, fontHeight);
        this.buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.gd = buffImg.createGraphics();
        gd.setFont(fo);
        image = new CaptchaImage();
    }

    /**
     * @param width      宽度
     * @param height     高度
     * @param linesNum   一次画多少干扰线
     * @param linesTims  干扰线画多少次
     * @param font       字体
     * @param fontHeight 字体高度
     */
    public CaptchaGenerator(int width, int height, int linesNum, int linesTims, String font, int fontHeight) {
        this.width = width;
        this.height = height;
        this.linesNum = linesNum;
        this.linesTims = linesTims;
        this.font = font;
        this.fontHeight = fontHeight;
        this.buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.gd = buffImg.createGraphics();

        fo = new Font(font, Font.BOLD, fontHeight);
        gd.setFont(fo);
        fontWidth = width / 9;
        offset = 3 * height / 4;
        image = new CaptchaImage();

    }

    /**
     * @param width      宽度
     * @param height     高度
     * @param linesNum   一次画多少干扰线
     * @param linesTims  干扰线画多少次
     * @param font       字体
     * @param fontHeight 字体高度
     * @param MAX        计算结果最大值
     * @param MIN        计算结果最小值
     * @param fontHeight 字体
     */
    public CaptchaGenerator(int width, int height, String font, int linesNum, int linesTims, int MAX, int MIN, int fontHeight) {
        this.width = width;
        this.height = height;
        this.font = font;
        this.linesNum = linesNum;
        this.linesTims = linesTims;
        this.MAX = MAX;
        this.MIN = MIN;
        this.fontHeight = fontHeight;
        this.buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.gd = buffImg.createGraphics();
        fo = new Font(font, Font.BOLD, fontHeight);
        gd.setFont(fo);
        fontWidth = width / 9;
        offset = 3 * height / 4;
        image = new CaptchaImage();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(int fontHeight) {
        this.fontHeight = fontHeight;
        fo = new Font(font, Font.BOLD, fontHeight);
        gd.setFont(fo);
    }

    public int getMIN() {
        return MIN;
    }

    public int getMAX() {
        return MAX;
    }

    public int getLinesTims() {
        return linesTims;
    }

    public void setLinesTims(int linesTims) {
        this.linesTims = linesTims;
    }

    public int getLinesNum() {
        return linesNum;
    }

    public void setLinesNum(int linesNum) {
        this.linesNum = linesNum;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
        fo = new Font(font, Font.BOLD, fontHeight);
        gd.setFont(fo);
    }

    public Graphics2D getGd() {
        return gd;
    }

    public void setGd(Graphics2D gd) {
        this.gd = gd;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getFontWidth() {
        return fontWidth;
    }

    public void setFontWidth(int fontWidth) {
        this.fontWidth = fontWidth;
    }

    public void setMIN(int MIN) {
        this.MIN = MIN;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
    }
}

/**
 * 
 */
package com.liushuqing.controller.api;

import com.liushuqing.controller.BaseController;
import com.liushuqing.web.ApiResult;
import com.liushuqing.web.DianyingFMRepoPageProcessor;
import com.liushuqing.web.MySqlPipline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author nancheng
 *
 */
@Controller
@RequestMapping(value = "spider")
public class SpiderApiController extends BaseController {

    @Resource
    private MySqlPipline mysqlPipline;

    @Resource
    private DianyingFMRepoPageProcessor pageProcessor;

    private String sb = "http://dianying.fm/search/?text=";

    private static Logger logger = LoggerFactory.getLogger(SpiderApiController.class);

    private Spider spider;

    @RequestMapping(value = "/crawl")
    public @ResponseBody ApiResult crawl(HttpServletRequest request, @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "type") int type) {
        if (spider == null) {
            spider = Spider.create(pageProcessor).addPipeline(mysqlPipline).thread(2);
            logger.debug("\n 是否为空 : " + pageProcessor + mysqlPipline + spider);
        }
        logger.debug("\n type is : " + type);
        spider.addUrl(sb+keyword);
        logger.warn("\n  发出爬取请求");
        spider.start();
        logger.warn("\n  爬虫异步开始");
        ApiResult result = new ApiResult();
        result.setCode(200).setMsg("OK");
        return result;
    }

    @RequestMapping(value = "/stop")
    public @ResponseBody ApiResult stopCrawl() {
        logger.warn("\n  发出停止请求");
        if(spider!=null){
            spider.stop();
            spider=null;
            logger.warn("\n  爬虫成功停止！");
        }else{
            logger.warn("\n  爬虫尚未初始化，不需要停止！");
        }
       
        ApiResult result = new ApiResult();
        result.setCode(200).setMsg("OK");
        return result;
    }

}

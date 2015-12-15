/**
 * 
 */
package com.liushuqing.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author nancheng
 *
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class DianyingFMRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    
    @Override
    public void process(Page page) {
        List<String> hrefs = page.getHtml().css("div.fm-movie-title").$("a", "href").all();
        for (String i : hrefs) {
            i = "http://dianying.fm/" + i;
        }
        page.addTargetRequests(hrefs);
        page.putField("name", page.getHtml().xpath("//h3/a[@name='title']/text()").toString());
        page.putField("content", page.getHtml().xpath("//div[@class='fm-summary']/text()").toString());
        if (page.getResultItems().get("name") == null) {
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}

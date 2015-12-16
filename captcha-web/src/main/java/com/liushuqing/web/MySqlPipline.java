/**
 * 
 */
package com.liushuqing.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import com.liushuqing.meta.entity.*;

/**
 * @author nancheng
 *
 */
@Component
public class MySqlPipline implements Pipeline {


    private static Logger logger = LoggerFactory.getLogger(MySqlPipline.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * us.codecraft.webmagic.pipeline.Pipeline#process(us.codecraft.webmagic.
     * ResultItems, us.codecraft.webmagic.Task)
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems != null && resultItems.get("name") != null) {
            logger.debug("\n 判断是否为空 searchResultResult" );
            SearchResult result = new SearchResult();
            result.setUrl(resultItems.getRequest().getUrl());
            result.setName((String) resultItems.get("name"));
            result.setContent((String) resultItems.get("content"));
            logger.debug("\n 保存一条数据成功: "+result);
        }
    }
}

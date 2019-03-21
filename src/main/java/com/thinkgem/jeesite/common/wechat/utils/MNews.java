/**  
 * @Project: YonYouWeChat2
 * @Title: MNews.java
 * @Package org.wechat.core.constants.util
 * @Description: TODO
 * @author wenxu
 * @date 2015-6-2 下午02:49:18
 * @Copyright: 2015
 * @version V1.0  
 */

package com.thinkgem.jeesite.common.wechat.utils;


/**
 * @ClassName: MNews
 * @Description: 消息类型及数据格式
 * @author wenxu
 * @date 2015-6-2 下午02:49:18
 * 
 */

public class MNews {
    public static String news(String touser, String toparty, String totag, String title, String description, String url,int agentid) {
        String postData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"news\",\"agentid\": \"%s\",\"news\": {\"articles\":[{\"title\": \"%s\",\"description\": \"%s\",\"url\": \"%s\"}]}}";
        postData = String.format(postData, touser, toparty, totag, agentid, title, description, url);
        return postData;
    }

    public static void main(String[] args) {
    }
}

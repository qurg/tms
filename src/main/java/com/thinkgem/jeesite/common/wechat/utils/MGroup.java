package com.thinkgem.jeesite.common.wechat.utils;



import com.thinkgem.jeesite.common.wechat.constants.Constants;

import net.sf.json.JSONObject;

/**
 * 通讯录部门管理类
 * 
 * @author wenxu
 * @date 2014.12.19
 */
public class MGroup {

    // 创建部门地址
    public static String CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
    // 更新部门地址
    public static String UPDATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
    // 删除部门地址
    public static String DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
    // 获取部门列表地址
    public static String GETLIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";

    /**
     * 创建部门
     * 
     * @param name
     *            部门名称。长度限制为1~64个字符
     * @param parentid
     *            父亲部门id。根部门id为1
     * */
    public static String Create(String name, String parentid) {
        String Postjson = "{\"name\": \"%s\",\"parentid\": \"%s\"}";
        return String.format(Postjson, name, parentid);
    }

    /**
     * 更新部门
     * 
     * @param name
     *            更新的部门名称。长度限制为0~64个字符。修改部门名称时指定该参数
     * @param id
     *            部门id
     * */
    public static String Update(String id, String name) {
        String Postjson = "{\"id\": \"%s\",\"name\": \"%s\"}";
        return String.format(Postjson, id, name);
    }

    /**
     * 删除部门
     * 
     * @param id
     *            部门id
     * */
    public static String Delete(String id) {
        String delete_url = DELETE_URL.replace("DELID", id);
        return delete_url;
    }

    // 示例
    public static void main(String[] args) {
    	  String access_token = WeixinUtil.getAccessToken(Constants.CORPID, Constants.SECRETA).getToken();
          System.out.println(access_token);
          
          String out = MGroup.Update("2","测试部门01");
          System.out.println(out);
          
          DELETE_URL = DELETE_URL.replace("ACCESS_TOKEN", access_token).replace("ID","");
		    JSONObject jsonObject = WeixinUtil.HttpRequest(DELETE_URL, "GET", null);
		    //请求成功
		    if(null != jsonObject){
		    	System.out.println(jsonObject.toString());
		    }
    }
}

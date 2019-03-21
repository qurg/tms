package com.thinkgem.jeesite.common.wechat.utils;


import java.util.Iterator;

import com.thinkgem.jeesite.common.wechat.constants.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 通讯录成员管理类
 * 
 * @author wenxu
 * @date 2014.12.19
 */
public class MPerson {
    // 创建成员地址
    public static String CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
    // 更新成员地址
    public static String UPDATA_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
    // 删除成员地址
    public static String DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=ID";
    //批量删除地址
    public static String BATCHDELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
    // 获取成员地址
    public static String GET_PERSON_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
    // 获取部门成员地址
    public static String GET_GROUP_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
    //获取部门成员(详情)
    public static String GET_GROUPlIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
    /**
     * 创建成员
     * 
     * @param userid
     *            员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
     * @param name
     *            成员名称。长度为1~64个字符
     * @param department
     *            成员所属部门id列表 格式： "department": [x, y]
     * @param position
     *            职位信息
     * @param mobile
     *            手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
     * @param tel
     *            办公电话。长度为0~64个字符
     * @param email
     *            邮箱。长度为0~64个字符。企业内必须唯一
     * @param weixinid
     *            微信号。企业内必须唯一
     * */
    public static String Create(String userid, String name, String position, String mobile, String email, String weixinid,String department) {
        String PostData = "{\"userid\": \"%s\",\"name\": \"%s\",\"position\": \"%s\",\"mobile\": \"%s\",\"email\": \"%s\",\"weixinid\": \"%s\",\"department\": ["+department+"]}";
        return String.format(PostData, userid, name, position, mobile, email, weixinid);
    }

    /**
     * 更新成员
     * 
     * @param userid
     *            员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
     * @param name
     *            成员名称。长度为1~64个字符
     * @param department
     *            成员所属部门id列表 格式： "department": [x]
     * @param position
     *            职位信息
     * @param mobile
     *            手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
     * @param gender
     *            性别。gender=0表示男，=1表示女。默认gender=0
     * @param tel
     *            办公电话。长度为0~64个字符
     * @param email
     *            邮箱。长度为0~64个字符。企业内必须唯一
     * @param weixinid
     *            微信号。企业内必须唯一
     * @param enable
     *            启用/禁用成员。1表示启用成员，0表示禁用成员
     * */
    public static String Updata(String userid, String name, String position, String mobile, String email, String weixinid,String department,String enable) {
        String PostData = "{\"userid\": \"%s\",\"name\": \"%s\",\"position\": \"%s\",\"mobile\": \"%s\",\"email\": \"%s\",\"weixinid\": \"%s\",\"department\": ["+department+"],\"enable\":\"%s\"}";
        return String.format(PostData, userid, name, position, mobile, email, weixinid,enable);
    }

    /**
     * 删除成员
     * 
     * @param userid
     *            员工UserID。对应管理端的帐号
     * */
    public static String Delete(String userid) {
        String delete_url = DELETE_URL.replace("ID", userid);
        return delete_url;
    }

    /**
     * 删除成员
     * 
     * @param userid
     *            员工UserID。对应管理端的帐号
     * */
    public static String DeleteBatch(String lst) {
        String PostData = "{\"useridlist\": "+lst+"}";
        return String.format(PostData);
    }
    /**
     * 获取成员
     * 
     * @param userid
     *            员工UserID。对应管理端的帐号
     * */
    public static String GPerson(String userid) {
        String getperson_url = GET_PERSON_URL.replace("ID", userid);
        return getperson_url;
    }

    public static String GPersonLst(String access_token,String department_id){
        String getperson_url = GET_GROUPlIST_URL.replace("ACCESS_TOKEN", access_token).replace("DEPTID", department_id);
        return getperson_url;
    }
    /**
     * 获取部门成员
     * 
     * @param department_id
     *            获取的部门id
     * @param fetch_child
     *            1/0：是否递归获取子部门下面的成员 （可选）
     * @param status
     *            0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加 （可选）
     * */
    public static String GGroup(String access_token,String department_id) {
        String getgroup_url = GET_GROUP_URL.replace("DEPTID", department_id).replace("ACCESS_TOKEN", access_token);
        return getgroup_url;
    }

    // 示例
    public static void main(String[] args) {
    	 String access_token = WeixinUtil.getAccessToken(Constants.CORPID, Constants.SECRETA).getToken();
         System.out.println(access_token);
         //    public static String Create(String userid, String name, String position, String mobile, String email, String weixinid,String department) {

         String out = MPerson.Create("test01","张三","","13899999999","","","2");
         System.out.println(out);
         
         GET_GROUPlIST_URL = GET_GROUPlIST_URL.replace("ACCESS_TOKEN", access_token).replace("DEPARTMENT_ID","1").replace("FETCH_CHILD","1").replace("STATUS","0");
		    JSONObject jsonObject = WeixinUtil.HttpRequest(GET_GROUPlIST_URL, "GET", null);
		    //请求成功
		    if(null != jsonObject){
		    	System.out.println(jsonObject.toString());
		    }
    }

}

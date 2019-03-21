package com.thinkgem.jeesite.common.wechat.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thinkgem.jeesite.common.wechat.constants.Constants;
import com.thinkgem.jeesite.common.wechat.pojo.AccessToken;
import net.sf.json.JSONObject;

public class WeixinUtil {

	/** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
	public static JSONObject HttpRequest(String request , String RequestMethod , String output ){
		@SuppressWarnings("unused")
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			//建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if(output!=null){
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			//流处理
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input,"UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			//关闭连接、释放资源
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
		}
		return jsonObject;
	} 
	/** 
	 * 获取access_token 
	 *  
	 * @param CorpID 企业Id 
	 * @param SECRET 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret 
	 * @return 
	 */  
	public static AccessToken getAccessToken(String corpID, String secret) {  
	    AccessToken accessToken = null;  
	    String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET";  
	    String requestUrl = access_token_url.replace("CorpID", corpID).replace("SECRET", secret);  
	    JSONObject jsonObject = HttpRequest(requestUrl, "GET", null);
	    // 如果请求成功  
	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessToken();  
	            accessToken.setToken(jsonObject.getString("access_token"));  
	            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
	        } catch (Exception e) {  
	            accessToken = null;  
	            // 获取token失败  
	            String error = String.format("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return accessToken;  
	}
	
	public static void getAgentid(String access_token,String agentid){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&agentid=AGENTID";
		url = url.replace("ACCESS_TOKEN", access_token).replace("AGENTID", agentid);
	    JSONObject jsonObject = HttpRequest(url, "GET", null);
	    //请求成功
	    if(null != jsonObject){
	    	System.out.println(jsonObject.toString());
	    }
	}
	
	public static void getAgent(String access_token){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/list?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", access_token);
	    JSONObject jsonObject = HttpRequest(url, "GET", null);
	    //请求成功
	    if(null != jsonObject){
	    	System.out.println(jsonObject.toString());
	    }
	}
	public static void addAgentid(String access_token,String output){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/set?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", access_token);
	    JSONObject jsonObject = HttpRequest(url, "POST", output);
	    //请求成功
	    if(null != jsonObject){
	    	System.out.println(jsonObject.toString());
	    }
	}
	
	public static void main(String[] args) {
		AccessToken access = WeixinUtil.getAccessToken(Constants.CORPID,Constants.ENCODINGAESKEY);
		
		getAgentid(access.getToken(),Constants.AGENTID);
		
		getAgent(access.getToken());
		String postData = "{\"agentid\": \"%s\",\"agentid\": \"%s\"}";
		postData = String.format(postData,"1","2");
		
		addAgentid(access.getToken(),postData);
	}
}

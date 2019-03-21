/**  
* @Project: jeesite
* @Title: CoreController.java
* @Package com.thinkgem.jeesite.modules.wechat.web
* @Description: 微信主服务控制层
* @author wenxu
* @date 2016年3月2日 上午8:57:36
* @Copyright: 2016
* @version V1.0  
*/

package com.thinkgem.jeesite.modules.wechat.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.wechat.aes.AesException;
import com.thinkgem.jeesite.common.wechat.aes.WXBizMsgCrypt;
import com.thinkgem.jeesite.common.wechat.constants.Constants;
import com.thinkgem.jeesite.modules.wechat.service.CoreService;

/**
 * @ClassName: CoreController
 * @Description: 微信主服务控制层
 * @author wenxu
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "${adminPath}/core/core")
public class CoreController extends BaseController {

	private String token = Constants.TOKEN;
	private String encodingAESKey = Constants.ENCODINGAESKEY;
	private String corpId = Constants.CORPID;

	@RequestMapping(value = "coreJoin", method = RequestMethod.GET)
	public void coreJoinGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        System.out.println("request=" + request.getRequestURL());

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            e.printStackTrace();
        }
        if (result == null) {
            result = token;
        }
        out.print(result);
        out.close();
        out = null;
	}

	@RequestMapping(value = "coreJoin", method = RequestMethod.POST)
	public void coreJoinPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        // 从请求中读取整个post数据
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        System.out.println(postData);

        String msg = "";
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            // 解密消息
            msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
        } catch (AesException e) {
            e.printStackTrace();
        }
        System.out.println("msg=" + msg);

        // 调用核心业务类接收消息、处理消息
        String respMessage = CoreService.processRequest(msg);
        System.out.println("respMessage=" + respMessage);

        String encryptMsg = "";
        //如果content中回复消息为空 则不回复
            if (null != respMessage && !"".equals(respMessage)) {
            try {
                // 加密回复消息
                encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
            } catch (AesException e) {
                e.printStackTrace();
            }
    
            // 响应消息
            PrintWriter out = response.getWriter();
            out.print(encryptMsg);
            out.close();
        }
	}
}

package com.safeng.mt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.safeng.mt.order.servcie.UserService;
import com.safeng.mt.pojo.Clientuser;
import com.safeng.mt.uint.Download;
import com.safeng.mt.uint.Gethttpresponse;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("getwxinfo")
public class WxyaoqingController {
	//appid申请的开发
	private static final String appid="wxcfdca1f80c35083a";
	//秘钥
	private static final String secret="45a221b2c3b18b27d481fe1029ba1c97";
	
	@Autowired
	private UserService userservice;
	
	
	
	@RequestMapping("getopenid")
	public void getopenid(String code,HttpServletResponse response,HttpSession session) throws Exception{
		
		Map<String, Object> map=new HashMap<>();
		
		Gethttpresponse gt= new Gethttpresponse();
		
		String url2="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		//获取微信接口数据
		String str = gt.getHttpResponse(url2);
		//是将str转化为相应的JSONObject对象
		JSONObject json1 = JSONObject.fromObject(str);
		//获取json中的值
		String openid = json1.get("openid").toString();
		//入口
		String access_token=json1.get("access_token").toString();
		
		String url1="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		
		String str2 = gt.getHttpResponse(url1);
		// 编码后的string
	    String resultstr = new String(str2.getBytes(), "ISO-8859-1");
	   
	    //将获取值转化为json
		JSONObject json2 = JSONObject.fromObject(resultstr);
		
		//获取昵称
		String nickname=json2.get("nickname").toString();
		//转成json
		String ss = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
		//获取性别
		Integer sex = Integer.parseInt(json2.get("sex").toString());
		//获取图片路径
		String heardurl = json2.get("headimgurl").toString();
		
		System.out.println("图片下载路径为================》"+heardurl);
		//从session中获取用户信息
		Clientuser user=(Clientuser)session.getAttribute("user");
		
		if(user!=null){
			user.setOpenid(openid);
			//微信获取
			String urltx = heardurl;
			
			String picname="wx"+user.getUid()+".jpg";
			
			String path="C:/upload/touxiang/"+picname;
			
			Download.downloadPicture(urltx,path);
			 //存放保存的图片地址
			user.setAvatarid(picname);
			//填写为微信昵称
			user.setNickname(ss);
			//微信接口获取的数据sex1为男0为女
			 if(sex==1){
				 user.setGender(1);
			 }
			 if(sex==2){
				 
				 user.setGender(0); 
				 
			 }	 
			 this.userservice.update(user);
			 map.put("sta", 1);
			 map.put("mobile", user.getMobile());
		}else{
			map.put("sta", 0);
		}

		response.getWriter().write(com.alibaba.fastjson.JSONObject.toJSONString(map));
		response.getWriter().close();

		
	}
	
	
}

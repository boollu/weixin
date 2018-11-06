package com.safeng.mt.uint;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Download {
	
	
	 //链接url下载图片
    public static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
        	//添加网页                 https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcfdca1f80c35083a&redirect_uri=http%3a%2f%2fwww.zshl.net.cn%2flogin.jsp&response_type=code&scope=snsapi_userinfo&state=success#wechat_redirect
            url = new URL("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcfdca1f80c35083a&redirect_uri=http%3a%2f%2fzshl.sxhlsjq.com%2flogin.jsp&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
            System.out.println("URL 为：" + url.toString());
            System.out.println("协议为：" + url.getProtocol());
            System.out.println("验证信息：" + url.getAuthority());
            System.out.println("文件名及请求参数：" + url.getFile());
            System.out.println("主机名：" + url.getHost());
            System.out.println("路径：" + url.getPath());
            System.out.println("端口：" + url.getPort());
            System.out.println("默认端口：" + url.getDefaultPort());
            System.out.println("请求参数：" + url.getQuery());
            System.out.println("定位位置：" + url.getRef());
            System.out.println("请求的code:"+url.hashCode());
        //   url的openStream读取网络资源进行数据转化，写到数据输入流中
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            //-2040290833
            //字符输出流到file文件中
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
         
            //字节数组输出流
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            //字节数组1024为1m
            byte[] buffer = new byte[1024];
            
            int length;
 
            //dataInputStream.read(buffer)写入字节中
            while ((length = dataInputStream.read(buffer)) > 0) {
            	//x写入byte数组中从0开始长度为length
                output.write(buffer, 0, length);
            }
            //通过流把照片加到文件中t
            fileOutputStream.write(output.toByteArray());
            
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    
    public static void main(String[] args) {
        String url = "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIX4GibOqaLwcZCVajHicwsOl0bkNoJKySiclLOXEJXtE3KnpJvOygyTyexazsLViauYPVibibuFkXIopPA/132";
        String path="C:/upload/touxiang/aaa.txt";
        downloadPicture(url,path);
        System.out.println("成功");
    }
	

}

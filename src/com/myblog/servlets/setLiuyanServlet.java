package com.myblog.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.myblog.managers.EmailManager;

public class setLiuyanServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		
		String cet = request.getParameter("cet");
		if(cet == null) return ;
		Map<String,Object> map = new HashMap<>();
		//发送
		int res=EmailManager.sendEmail("smtp.qq.com", "2438974094@qq.com", "iealmaepzwrsecge", "2438974094@qq.com", new String[]{
    			"2438974094@qq.com"//这里就是一系列的收件人的邮箱了
    	}, "MyBlog:你收到了新留言",cet,"text/html;charset=utf-8");
		map.put("isok", res==1?"true":"false");
		map.put("content", cet);
		out.print(JSONArray.toJSONString(map));
		
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	
}

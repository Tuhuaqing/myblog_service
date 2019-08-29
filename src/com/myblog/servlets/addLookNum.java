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
import com.myblog.managers.DBManager;

public class addLookNum extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		
		//获取参数
		Map<String,Object> map = new HashMap<>();
		String toid = request.getParameter("toid");
		if(toid == null || toid.length() <= 0) {
			System.err.println("没有指定toid");
			return;
		}
		boolean isok = DBManager.runChange("UPDATE article SET looknum = looknum + 1 WHERE id = ?", new String[] {toid});
		map.put("toid", toid);
		if(isok) {
			map.put("isok", "true");
		}else
		{
			map.put("isok","false");
		}
		out.print(JSONArray.toJSONString(map));
		
		
		//释放资源
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}

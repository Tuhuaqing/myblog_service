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

public class addCommentServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//获取参数并验证可行与否
		String un = request.getParameter("un");
		String toid = request.getParameter("toid");
		String content = request.getParameter("content");
		String dateTime = request.getParameter("dateTime");
		Map<String,Object> map = new HashMap<>();
		if(un == null || toid == null || content == null || dateTime == null)
		{
			System.err.println("少了参数");
			return ;
		}
		//开始插入
		boolean res = DBManager.runChange("INSERT INTO Comments VALUES(id,?,?,?,?,star);", new String[] {toid,un,content,dateTime});
		if(res) {
			map.put("isok","true");
		} else {
			map.put("isok", "false");
		}
		out.print(JSONArray.toJSONString(map));
		
		DBManager.closeConn();
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}

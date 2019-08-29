package com.myblog.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.myblog.managers.DBManager;
import com.myblog.models.Article;

public class getArticlesServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		
		
		// 将数据库查出来的ResultSet转换成List数据
		int limit = Integer.valueOf(request.getParameter("limit")==null?"-1":request.getParameter("limit"));
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex")==null?"-1":request.getParameter("pageIndex"));
		List<Article> list = new ArrayList<>();
		ResultSet res = null;
		if(limit > 0 && pageIndex > 0 )
			res = DBManager.runQuery("SELECT * FROM article ORDER BY looknum DESC,date DESC LIMIT ?,?", new Object[] {(limit*(pageIndex-1)),limit});
		else
			res = DBManager.runQuery("SELECT * FROM article ORDER BY date DESC", null);
		String json = "[]";
		try {
			while (res.next()) {
				Article a = new Article();
				a.setId(res.getInt(1));
				a.setUn(res.getString(2));
				a.setTitle(res.getString(3));
				a.setFilename(res.getString(4));
				a.setPicname(res.getString(5));
				a.setDate(res.getObject(6).toString());
				a.setType(res.getString(7));
				a.setLooknum(res.getInt(8));
				a.setDescribe(res.getString(9));
				list.add(a);
			}
			json = JSONArray.toJSONString(list);
			out.print(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBManager.closeConn();
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

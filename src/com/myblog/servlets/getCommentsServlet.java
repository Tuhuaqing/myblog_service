package com.myblog.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.myblog.managers.DBManager;
import com.myblog.models.Comment;

public class getCommentsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		// 返回数据
		String toid = request.getParameter("toid");
		String pageIndex = request.getParameter("pageIndex");
		int toid_int = -1, pageIndex_int = -1;
		if (toid == null || pageIndex == null) {
			System.out.println("没有指定toid或者pageIndex");
			return;
		} else {
			toid_int = Integer.valueOf(toid);
			pageIndex_int = Integer.valueOf(pageIndex);
		}
		Map<String, Object> map = new HashMap<>();
		List<Comment> list = new ArrayList<>();
		ResultSet res = null;
		
		
		
		try {
			//获得评论主体数据		
			res = DBManager.runQuery("select * from Comments where toid = ? ORDER BY datetime DESC limit ?,?",
					new Integer[] { toid_int, (pageIndex_int - 1) * 5, 5 });
			while (res.next()) {
				Comment a = new Comment();
				a.setId(res.getInt(1));
				a.setToid(res.getInt(2));
				a.setUn(res.getString(3));
				a.setContent(res.getString(4));
				String dateTime = res.getString(5);
				String dateTime2 = dateTime.replaceAll(dateTime.substring(dateTime.lastIndexOf(":")), "");
				a.setDatetime(dateTime2);
				a.setStar(Integer.valueOf(res.getInt(6)));
				list.add(a);
			}
			map.put("data",list);
			// 获取全部的总数
			res = DBManager.runQuery("SELECT COUNT(*) FROM Comments WHERE toid = ?", new Integer[] { Integer.valueOf(toid) });		
			if (res.next()) {
				int count = res.getInt(1);
				map.put("count", count);
				map.put("pageCount", count % 5 == 0 ? count / 5 : count / 5 + 1);
			}
			out.print(JSONArray.toJSON(map));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 释放
		DBManager.closeConn();
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

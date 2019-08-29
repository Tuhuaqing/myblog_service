package com.myblog.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.myblog.managers.DBManager;
import com.myblog.models.Comment;

public class addCommentStar extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		// 开始动作
		String comment_id = request.getParameter("comment_id");
		Map<String, Object> map = new HashMap<>();
		ResultSet res = null;
		Comment c = null;
		if (comment_id == null || comment_id.length() <= 0) {
			System.err.println("没有指定评论id");
		}
		try {

			boolean isok = DBManager.runChange("UPDATE Comments SET star = star + 1 WHERE id = ?",
					new String[] { comment_id });
			map.put("comment_id", comment_id);
			if (isok) {
				map.put("isok", "true");
				res = DBManager.runQuery("select * from Comments where id = ?", new String[] { comment_id });
				if (res.next()) {
					c = new Comment();
					c.setId(res.getInt(1));
					c.setToid(res.getInt(2));
					c.setUn(res.getString(3));
					c.setContent(res.getString(4));
					c.setDatetime(String.valueOf(res.getObject(5)));
					c.setStar(res.getInt(6));
					map.put("Object", c);	
				}
			} else {
				map.put("isok", "false");
			}
			out.print(JSONArray.toJSONString(map));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

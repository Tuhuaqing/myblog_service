package com.myblog.models;

public class Article {
	
	private int id;
	private String un;
	private String title;
	private String filename;
	private String picname;
	private String date;
	private String type;
	private int looknum;
	private String describe;
	
	public Article() {}	
	
	public Article(int id, String un, String title, String filename, String picname, String date, String type,
			int looknum, String describe) {
		super();
		this.id = id;
		this.un = un;
		this.title = title;
		this.filename = filename;
		this.picname = picname;
		this.date = date;
		this.type = type;
		this.looknum = looknum;
		this.describe = describe;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLooknum() {
		return looknum;
	}
	public void setLooknum(int looknum) {
		this.looknum = looknum;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	

}

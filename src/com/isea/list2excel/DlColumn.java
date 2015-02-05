package com.isea.list2excel;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("column")
public class DlColumn {

	private String name;
	private String header;
	private String width;
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	
}

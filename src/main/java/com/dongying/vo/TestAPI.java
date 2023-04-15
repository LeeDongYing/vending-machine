package com.dongying.vo;



public class TestAPI {
	
	private String 標題;
	private String 內容;
	public String get標題() {
		return 標題;
	}
	public void set標題(String 標題) {
		this.標題 = 標題;
	}
	public String get內容() {
		return 內容;
	}
	public void set內容(String 內容) {
		this.內容 = 內容;
	}
	@Override
	public String toString() {
		return "TestAPI [標題=" + 標題 + ", 內容=" + 內容 + "]";
	}
	
}

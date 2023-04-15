package com.dongying.vo;

public class NewsAttatchFile {
	public String 檔案說明;
	public String 檔案名稱;
	public String 連結位置;
	
	
	public String get檔案說明() {
		return 檔案說明;
	}
	public void set檔案說明(String 檔案說明) {
		this.檔案說明 = 檔案說明;
	}
	public String get檔案名稱() {
		return 檔案名稱;
	}
	public void set檔案名稱(String 檔案名稱) {
		this.檔案名稱 = 檔案名稱;
	}
	public String get連結位置() {
		return 連結位置;
	}
	public void set連結位置(String 連結位置) {
		this.連結位置 = 連結位置;
	}
	@Override
	public String toString() {
		return "NewsAttatchFile [檔案說明=" + 檔案說明 + ", 檔案名稱=" + 檔案名稱 + ", 連結位置=" + 連結位置 + "]";
	}
	
}

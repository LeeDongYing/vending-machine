package com.dongying.vo;

import lombok.Data;

@Data
public class NewsVO {
	public String 標題;
	public String 內容;
	public String 連結網址;
	public String 發布日期;
	public String 修改日期;
	public NewsAttatchFile[] 附加檔案;
	}

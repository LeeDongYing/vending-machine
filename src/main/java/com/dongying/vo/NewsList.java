package com.dongying.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class NewsList {
	private List<NewsVO> newsVOs;
	public NewsList() {
		newsVOs = new ArrayList<>();
	}
	public List<NewsVO> getNewsVOs() {
		return newsVOs;
	}
	public void setNewsVOs(List<NewsVO> newsVOs) {
		this.newsVOs = newsVOs;
	}
	
}

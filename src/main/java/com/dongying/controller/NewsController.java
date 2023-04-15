package com.dongying.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dongying.config.MyMappingJackson2HttpMessageConverter;
import com.dongying.vo.NewsAttatchFile;
import com.dongying.vo.NewsVO;

@RestController
public class NewsController {

	private static Logger logger = LoggerFactory.getLogger(NewsController.class);

	@GetMapping("/all")
	public ResponseEntity<NewsVO[]> getUser() {

		String url = "https://www.hpa.gov.tw/wf/newsapi.ashx?startdate=2023/03/01";
		String url2 = "http://www.fda.gov.tw/DataAction?startdate=2023/03/01";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
		// 發送 GET 請求，並且將回應轉換為對象
		NewsVO[] objects = restTemplate.getForObject(url, NewsVO[].class);
		
		return ResponseEntity.ok(objects);

	}

}

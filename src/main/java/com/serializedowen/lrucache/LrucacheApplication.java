package com.serializedowen.lrucache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LrucacheApplication {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;


	@Bean
	public RestTemplate restTemplate(){
		RestTemplate r = restTemplateBuilder.build();
		r.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		return r;
	};


	@Bean LRUCache lruCache(){
		return new LRUCache();
	}


	public static void main(String[] args) {
		SpringApplication.run(LrucacheApplication.class, args);
	}

}

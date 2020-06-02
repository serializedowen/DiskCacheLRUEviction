package com.serializedowen.lrucache;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;



@RestController
public class CacheController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();


	@Autowired
	private ICacheService cacheService;

	@Autowired
	private RestTemplate restTemplate;

	public static String getRandomString(int length) {

		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	private byte[] buf;




	@RequestMapping(value = "/addToCache")
	public void addToFiles() throws IOException {
		byte[] file = this.restTemplate.getForObject("http://localhost:5000/demo.wav", byte[].class);
		this.cacheService.put(this.getRandomString(11), file.length, file);


	}


	@RequestMapping(value = "/demo.wav", produces = {"audio/wav"})
	public byte[] test(@RequestHeader(value = "Range", defaultValue = "bytes=0-") String range, HttpServletResponse response){


		if (null == this.buf) {

			byte[] file = this.restTemplate.getForObject("http://localhost:5000/demo.wav", byte[].class);
			this.buf = file;
		}


		System.out.println("req");


		if ("bytes=0-" != range){
			int to;
			int from;

			try {
				range = range.substring(6);


				String[] bound = range.split("-");

				from = Integer.valueOf(bound[0]);

				try {
					to = Integer.valueOf(bound[1]);
				} catch (IndexOutOfBoundsException e){
					to = this.buf.length;
				}
			} catch (Exception e) {
//				throw new HttpClientErrorException.BadRequest("invalid Parameters");
				throw new Error("ear");
			}

			response.setStatus(206);
//			response.setContentType("audio/wav");
			return Arrays.copyOfRange(this.buf, from, to);
		}
		else
		{
			return this.buf;
		}


	}
}

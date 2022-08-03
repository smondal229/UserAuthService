package com.suvodip.userservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.suvodip.userservice.models.Post;

@Service
public class PostInfoMicroservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${microservices.posts.name}")
	private String postsMicroService;
	
	@HystrixCommand(fallbackMethod="getPostFallback", 
		threadPoolKey="postsMicroService",
		threadPoolProperties= {
			@HystrixProperty(name="coreSize", value="20"),
			@HystrixProperty(name="maxQueueSize", value="20"),
		}
//		commandProperties= {
//			@HystrixProperty(name="circuitBreaker.requestVolumeThresold", value=requestVolumeThresold),// Hystrix will look into the last 5 requests
//			@HystrixProperty(name="circuitBreaker.errorThresoldPercentage", value=errorThresoldPercent), // Hystrix will take consider 50% requests to be failed in last 5 requests before breakdown
//			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value=sleepWindowTime) // Circuit will go into sleep for the time after a breakdown
//		}
	)
	public ResponseEntity<List<Post>> getAllPosts() {
		final String endpoint = postsMicroService+"/api/v1/posts";
		return restTemplate.exchange(endpoint, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Post>>() {});
	}
	
	public ResponseEntity<List<Post>> getPostFallback() {
		// TODO: send cache data
		return new ResponseEntity<List<Post>>(Arrays.asList(), HttpStatus.OK);
	}
}

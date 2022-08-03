package com.suvodip.userservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suvodip.userservice.models.Post;
import com.suvodip.userservice.service.PostInfoMicroservice;

@RequestMapping("/posts")
@RestController
public class UserPostsController {
	@Autowired
	private PostInfoMicroservice postInfo;
	
	@Value("${microservices.posts.name}")
	private String postsMicroService;

	@GetMapping("")
	/** HystrixCommand annotation is only applicable on the method which 
	 * is called by spring bean 
	 * i.e. in this case getPosts is a method of a RestController bean **/
	public ResponseEntity<List<Post>> getPosts() {
		return postInfo.getAllPosts();
	}
}

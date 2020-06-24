package com.refl3xn.DiscussionForum.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.refl3xn.DiscussionForum.entity.Comment;
import com.refl3xn.DiscussionForum.entity.Post;
import com.refl3xn.DiscussionForum.entity.User;
import com.refl3xn.DiscussionForum.service.CommentService;
import com.refl3xn.DiscussionForum.service.PostService;
import com.refl3xn.DiscussionForum.service.UserService;

@Controller
@RequestMapping("/data")
public class DataController {
	private PostService postService;
	private CommentService commentService;
	private UserService userService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> list = postService.listPosts();
        return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
    }
	
	@GetMapping("/comments")
	public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> list = commentService.listComments();
        return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.listUsers();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }
}

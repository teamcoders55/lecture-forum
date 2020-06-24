package com.refl3xn.DiscussionForum.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.refl3xn.DiscussionForum.entity.Comment;

import com.refl3xn.DiscussionForum.service.CommentService;
import com.refl3xn.DiscussionForum.service.PostService;

import com.refl3xn.DiscussionForum.service.UserService;

@Controller
@RequestMapping("post")
public class PostController {
	private UserService userService;
	private PostService postService;
	private CommentService commentService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/{postId}")
	public String getDiscussions(@PathVariable Integer postId, Model model) {
		model.addAttribute("discussion", postService.findPost(postId));
		Comment newComment =  new Comment();
		newComment.setPostId(postId);
		model.addAttribute("newComment", newComment);
		return "post";
	}
	
	@PostMapping("/{postId}")
	public String postComment(@PathVariable Integer postId, @ModelAttribute Comment newComment, Principal principal) {
		if (!(newComment.getCommentText() == null)) {
			System.out.println(newComment.getPostId());
			commentService.saveNewComment(newComment, userService.findUserByEmail(principal.getName()));
		}
		return "redirect:" + String.valueOf(postId);
	}
	
	@PostMapping("/reply{commentId}")
	public String postReplyComment(@PathVariable(name = "commentId") Integer commentId, @ModelAttribute Comment newComment, Principal principal) {
		if (!(newComment.getCommentText() == null)) {
			Comment reply = new Comment(commentService.findComment(commentId).getPostId(), newComment.getCommentText(), newComment.getCommentId());
			commentService.saveNewComment(reply, userService.findUserByEmail(principal.getName()));
		}
		return "redirect:" + String.valueOf(commentService.findComment(commentId).getPostId());
	}
}

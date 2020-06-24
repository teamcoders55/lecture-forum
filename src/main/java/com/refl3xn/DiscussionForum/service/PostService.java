package com.refl3xn.DiscussionForum.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.refl3xn.DiscussionForum.entity.Post;
import com.refl3xn.DiscussionForum.entity.User;

public interface PostService {
	List<Post> listPosts();
	Post findPost(int postId);
	void savePost(Post post, User user);
	Page<Post> findPosts(Integer pageNo, Integer pageSize);
	Page<Post> findSearchedPosts(Integer pageNo, Integer pageSize, String search);
	void deletePost(int postId);
	void updatePost(Post post);
}

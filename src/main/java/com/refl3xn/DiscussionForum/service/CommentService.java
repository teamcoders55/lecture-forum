package com.refl3xn.DiscussionForum.service;

import java.util.List;

import com.refl3xn.DiscussionForum.entity.Comment;
import com.refl3xn.DiscussionForum.entity.User;

public interface CommentService {
	List<Comment> listComments();
	Comment findComment(int commentId);
	void saveNewComment(Comment comment, User user);
}

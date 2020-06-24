package com.refl3xn.DiscussionForum.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refl3xn.DiscussionForum.entity.Comment;
import com.refl3xn.DiscussionForum.entity.User;
import com.refl3xn.DiscussionForum.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
    public List<Comment> listComments() {
        List<Comment> list =  (List<Comment>) commentRepository.findAll();
//        System.out.println("Comment: " + list);
        return list;
    }

	@Override
	public void saveNewComment(Comment comment, User user) {
		Date date = new Date();
		comment.setUserId(user.getUserId());
		comment.setCreatedOn(new Timestamp(date.getTime()));
//		comment.setPostId(8);
		System.out.println("new comment : " + comment.getCommentText());
		commentRepository.save(comment);
	}

	@Override
	public Comment findComment(int commentId) {
		return commentRepository.findById(commentId).get();
	}
}

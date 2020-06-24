package com.refl3xn.DiscussionForum.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.refl3xn.DiscussionForum.comparators.PostComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.refl3xn.DiscussionForum.entity.Post;
import com.refl3xn.DiscussionForum.entity.User;
import com.refl3xn.DiscussionForum.repository.CommentRepository;
import com.refl3xn.DiscussionForum.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
    public List<Post> listPosts() {
        List<Post> list =  (List<Post>) postRepository.findAll();
        Collections.sort(list, new PostComparator());
        return list;
    }

	@Override
	public void savePost(Post post, User user) {
		Date date = new Date();
		post.setUserId(user.getUserId());
		post.setCreatedOn(new Timestamp(date.getTime()));
		post.setUpdatedOn(new Timestamp(date.getTime()));
		postRepository.save(post);
	}

	@Override
	public Post findPost(int postId) {
		return postRepository.findById(postId).get();
	}

	@Override
	public Page<Post> findPosts(Integer pageNo, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(--pageNo, pageSize, Direction.DESC, "createdOn"); 
		return postRepository.findAll(pageRequest);
	}

	@Override
	public Page<Post> findSearchedPosts(Integer pageNo, Integer pageSize, String search) {
		PageRequest pageRequest = PageRequest.of(--pageNo, pageSize, Direction.DESC, "createdOn");
		return postRepository.findBySubjectContaining(search, pageRequest);
	}

	@Override
	public void deletePost(int postId) {
		Post post = postRepository.findById(postId).get();
//		System.out.println(post.getComments().size());
//		for (int i=0;i<post.getComments().size();i++) {
//			for (int j=0;j<post.getComments().get(i).getReplyComments().size();j++) {
//				post.getComments().get(i).getReplyComments().get(j).setParentId(null);
//				commentRepository.save(post.getComments().get(i).getReplyComments().get(j));
//			}
//		}
		commentRepository.setParentIdFor(postId);
		commentRepository.deleteInBulkByPostId(postId);
		postRepository.deleteByPostID(postId);
	}

	@Override
	public void updatePost(Post post) {
		Date date = new Date();
		post.setUpdatedOn(new Timestamp(date.getTime()));
		postRepository.save(post);
	}

}

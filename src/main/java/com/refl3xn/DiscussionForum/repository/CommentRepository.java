package com.refl3xn.DiscussionForum.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refl3xn.DiscussionForum.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Modifying
	@Transactional
	@Query("delete from comments where post_id = ?1")
	void deleteInBulkByPostId(int postId);
	
	@Transactional
	void deleteByPostId(int postId);
	
	@Modifying
	@Transactional
	@Query("update comments set parent_id = null where post_id = ?1")
	void setParentIdFor(int postId);
	
	List<Comment> findAllByPostIdAndParentIdNull(int postId);
}

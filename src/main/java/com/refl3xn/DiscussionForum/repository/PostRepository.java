package com.refl3xn.DiscussionForum.repository;


import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refl3xn.DiscussionForum.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	@Modifying
	@Transactional
	@Query("delete from posts where post_id = ?1")
	void deleteByPostID(int postId);
	Page<Post> findAll(Pageable pageable);
	Page<Post> findBySubjectContaining(String subject, Pageable pageable);
}

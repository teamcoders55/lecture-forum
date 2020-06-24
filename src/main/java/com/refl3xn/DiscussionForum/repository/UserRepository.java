package com.refl3xn.DiscussionForum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refl3xn.DiscussionForum.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
}

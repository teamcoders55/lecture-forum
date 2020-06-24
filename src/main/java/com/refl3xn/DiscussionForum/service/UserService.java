package com.refl3xn.DiscussionForum.service;

import java.util.List;

import com.refl3xn.DiscussionForum.entity.User;

public interface UserService {
	List<User> listUsers();
	User findUser(int userId);
	User findUserByEmail(String email);
	void saveUser(User user);
	boolean isUserAlreadyPresent(User user);
	void updateUser(User user);
}

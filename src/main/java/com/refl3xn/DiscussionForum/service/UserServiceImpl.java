package com.refl3xn.DiscussionForum.service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.refl3xn.DiscussionForum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.refl3xn.DiscussionForum.entity.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public List<User> listUsers() {
		List<User> list =  (List<User>) userRepository.findAll();
		return list;
	}

	@Override
	public User findUser(int userId) {
		User user = userRepository.findById(userId).get();
		return user;
	}
	
	@Override
	public void saveUser(User user) {
		Date date = new Date();
		user.setCreatedBy("email");
		user.setUpdatedBy("email");
		user.setCreatedOn(new Timestamp(date.getTime()));
		user.setUpdatedOn(new Timestamp(date.getTime()));
		user.setPassword(encoder.encode(user.getPassword()));
		System.out.println(user.getName() + user.getEmail() + user.getPassword());
		user.setRole("SITE_USER");
		userRepository.save(user);
	}

	@Override
	public boolean isUserAlreadyPresent(User user) {
		boolean isUserAlreadyExists = false;
		User existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser != null){
			isUserAlreadyExists = true; 
		}
		return isUserAlreadyExists;
	}

	@Override
	public User findUserByEmail(String email) {
		User existingUser = userRepository.findByEmail(email);
		if(existingUser != null){
			return existingUser; 
		}
		return null;
	}

	@Override
	public void updateUser(User user) {
//		user.viewUserDetails();
		Date date = new Date();
		user.setUpdatedOn(new Timestamp(date.getTime()));
		userRepository.save(user);
	}
}

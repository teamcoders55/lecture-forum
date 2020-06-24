package com.refl3xn.DiscussionForum.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.refl3xn.DiscussionForum.entity.User;
import com.refl3xn.DiscussionForum.service.UserService;

@Controller
public class AdminController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> list = userService.listUsers();
		model.addAttribute("users", list);
		return "users";
	}

	@GetMapping("/edituser{userId}")
	public String getUser(@PathVariable(name = "userId") Integer id,Model model) {
		User user = userService.findUser(id);
		System.out.println("Users id: " + user.getName());
		model.addAttribute("user", user);
		return "edit_user_details";
	}
	
	@GetMapping("/editUserDetails")
	public String getEditUserDetails(Model model) {
		return "edit_user_details";
	}
}

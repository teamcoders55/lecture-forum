package com.refl3xn.DiscussionForum.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.refl3xn.DiscussionForum.entity.Post;

import com.refl3xn.DiscussionForum.entity.User;
import com.refl3xn.DiscussionForum.service.PostService;

import com.refl3xn.DiscussionForum.service.UserService;

@Controller
public class HomeController {
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/images";
	
	private UserService userService;
	private PostService postService;
	
	Integer editPostId = null;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping("deletepost")
	public String deletePost(@RequestParam(required = true) Integer postId) {
		postService.deletePost(postId);
		return "redirect:profile";
	}
	
	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		model.addAttribute("user", user);
		return "profile";
	}
	
	@PostMapping("/saveDetails")
	public String saveDetails(@ModelAttribute User user, Principal principal) {
		User userTemp = userService.findUserByEmail(principal.getName());
		userTemp.setName(user.getName());
		userService.updateUser(userTemp);
		return "redirect:profile";
	}
	
	@PostMapping("/profile")
	public String upload(Model model, @RequestParam("files") MultipartFile[] files, Principal principal) {
		StringBuilder fileNames = new StringBuilder();
		for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename());
			String newFileNameTemp[] = file.getOriginalFilename().split("\\.");
			String newFileName = String.valueOf(userService.findUserByEmail(principal.getName()).getUserId()) + ".jpg";
			Path fileNameAndPath = Paths.get(uploadDirectory, newFileName);
			fileNames.append(String.valueOf(userService.findUserByEmail(principal.getName()).getUserId()) + " ");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());
		return "redirect:profile";
	}
	
	@GetMapping("/userProfile")
	public String getUserProfile(@RequestParam(required = true) Integer userId, Model model, Principal principal) {
		if (userId == userService.findUserByEmail(principal.getName()).getUserId()) {
			return "redirect:profile";
		}
		model.addAttribute("user", userService.findUser(userId));
		return "user_profile";
	}
	
	@GetMapping("/discussions")
	public String getDiscussions(@RequestParam(required = false, defaultValue = "1") Integer pageNo, @RequestParam(required = false, defaultValue = "5") Integer pageSize, @RequestParam(required = false, defaultValue = "") String searchFor, Model model) {
		if (searchFor.equals("")) {
		model.addAttribute("discussions", postService.findPosts(pageNo, pageSize));
		model.addAttribute("searchResult", 0);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchFor", searchFor);
		} else {
			model.addAttribute("discussions", postService.findSearchedPosts(pageNo, pageSize, searchFor));
			System.out.println(postService.findPosts(pageNo, pageSize).getTotalPages());
			model.addAttribute("searchResult", 1);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("searchFor", searchFor);
		}
		return "discussions";
	}
	
	@GetMapping("/addDiscussion")
	public String getAddDiscussion(Model model) {
		model.addAttribute("post", new Post());
		return "add_discussion";
	}
	
	@PostMapping("/addDiscussion")
	public String discussionSubmit(@ModelAttribute @Valid Post post, BindingResult bindingResult, Principal principal, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "add_discussion";
		} else {
			postService.savePost(post, userService.findUserByEmail(principal.getName()));
			return "redirect:discussions";
		}
	}
	
	@GetMapping("/editPost")
	public String getEditDiscussion(@RequestParam(required = true) Integer postId, Model model) {
		model.addAttribute("post", postService.findPost(postId));
		editPostId = postId;
		return "edit_discussion";
	}
	
	@PostMapping("/editPost")
	public String editDiscussionSubmit(@ModelAttribute @Valid Post post, BindingResult bindingResult, Principal principal, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "edit_discussion";
		} else {
//			System.out.println(editPostId + " " + post.getSubject() + " " + post.getDescription() + " " + post.getTags() + " " + post.getUserId() + " " + post.getCreatedOn() + " " + post.getUpdatedOn());
			Post editPost = postService.findPost(editPostId);
			editPost.setSubject(post.getSubject());
			editPost.setDescription(post.getDescription());
			editPost.setTags(post.getTags());
			postService.updatePost(editPost);
//			postService.savePost(post, userService.findUserByEmail(principal.getName()));
			return "redirect:profile";
		}
	}
	
	@GetMapping("/signin")
	public String getSignin(Model model) {
		model.addAttribute("user", new User());
		return "signin";
	}
	
	@GetMapping("/signup")
	public ModelAndView getSignup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signup");
		return modelAndView;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signupSubmit(@ModelAttribute @Valid User user, BindingResult bindingResult, ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signup");
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("successMessage", "Please correct the errors in form!");
			modelMap.addAttribute("bindingResult", bindingResult);
		} else if (userService.isUserAlreadyPresent(user)) {
			modelAndView.addObject("successMessage", "user already exists!");
		} else {
			System.out.println("User created succesfully");
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User is registered successfully!");
			modelAndView.setViewName("signin");
		}
		modelAndView.addObject("user", new User());
		return modelAndView;
	}
}

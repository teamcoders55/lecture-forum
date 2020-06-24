package com.refl3xn.DiscussionForum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import static com.refl3xn.DiscussionForum.controllers.HomeController.uploadDirectory;

@SpringBootApplication
public class DiscussionForumApplication {

	public static void main(String[] args) {
		new File(uploadDirectory).mkdir();
		SpringApplication.run(DiscussionForumApplication.class, args);
	}

}

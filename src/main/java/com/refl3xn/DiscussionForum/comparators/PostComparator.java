package com.refl3xn.DiscussionForum.comparators;

import java.util.Comparator;

import com.refl3xn.DiscussionForum.entity.Post;

public class PostComparator implements Comparator<Post> {

	@Override
	public int compare(Post o1, Post o2) {
		if (o1.getCreatedOn().compareTo(o2.getCreatedOn()) < 1) {
			return 1;
		} else {
			return -1;
		}
	}

}

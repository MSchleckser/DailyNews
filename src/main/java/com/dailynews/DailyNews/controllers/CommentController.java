package com.dailynews.DailyNews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("article")
public class CommentController {

	@RequestMapping("/{articleId}")
	public String displayComments(){
		return "comment";
	}

}

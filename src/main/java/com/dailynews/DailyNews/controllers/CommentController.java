package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.comments.Comment.Comment;
import com.dailynews.DailyNews.models.comments.Comment.CommentDao;
import com.dailynews.DailyNews.models.comments.CommentContainer;
import com.dailynews.DailyNews.models.comments.CommentContainerDao;
import com.dailynews.DailyNews.models.rssfeeds.article.Article;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.io.StringWriter;
import java.util.Optional;

@Controller
@RequestMapping("article")
public class CommentController {

	@Autowired
	private CommentContainerDao ccDao;

	@Autowired
	private UserDao uDao;

	@Autowired
	private CommentDao cDao;

	@RequestMapping("")
	public String displayComments(Model model) {
		model.addAttribute("title", "Comments");
		return "comments";
	}

	@RequestMapping("rest/comments/{articleId}")
	@ResponseBody
	public String getArticleData(@PathVariable int articleId){
		System.out.println(articleId);

		Optional<CommentContainer> optionalContainer = ccDao.findById(articleId);

		if(optionalContainer.isPresent() == false)
			return "Article not found";

		CommentContainer container = optionalContainer.get();
		return convertArticleToXml(container);
	}

	@RequestMapping(value = "CreateComment", method = RequestMethod.POST)
	@ResponseBody
	public String createComment(HttpServletRequest request, HttpSession session){
		if(request.getParameter("body") == null || request.getParameter("ArticleId") == null)
			return "Bad post request)";

		Integer id = null;

		if((id = (Integer)session.getAttribute("userId")) == null)
			return "not logged in";

		Optional<User> optionalUser = uDao.findById(id);

		if(!optionalUser.isPresent())
			return "Bad Login, could not post";

		User u = optionalUser.get();

		Comment comment = new Comment();

		comment.setUser(u);
		comment.setCommentBody(request.getParameter("body"));

		CommentContainer commentContainer = getCommentContainer(Integer.parseInt(request.getParameter("ArticleId")));

		comment.setCommentContainer(commentContainer);
		commentContainer.getComments().add(comment);
		cDao.save(comment);
		ccDao.save(commentContainer);

		return Integer.toString(comment.getId());
	}

	@RequestMapping(value = "removeComment")
	@ResponseBody
	public String removeComment(HttpServletRequest request, HttpSession session){
		if((session.getAttribute("role").equals("")))
			return	"You do not have the permissions to do that!";

		if(request.getParameter("id") == null)
			return "Malformed request";

		Integer id = Integer.parseInt(request.getParameter("id"));

		Optional<Comment> comment = cDao.findById(id);

		if(!comment.isPresent())
			return "could not find the comment";

		cDao.delete(comment.get());

		return "success";
	}

	private CommentContainer getCommentContainer(Integer id){
		Optional<CommentContainer> optionalCommentContainer = ccDao.findById(id);
		return optionalCommentContainer.get();
	}

	private String convertArticleToXml(CommentContainer container){
		StringWriter writer = new StringWriter();
		writer.append("<Container>");
		writer.append("<ID>" + container.getContainerID() + "</ID>\n");
		writer.append("<Title>" + container.getArticleTitle() + "</Title>\n");
		writer.append("<Publisher>" + container.getArticlePublisher() + "</Publisher>\n");
		fetchComments(container, writer);
		writer.append("</Container>");
		return writer.toString();
	}

	private void fetchComments(CommentContainer container, StringWriter writer){
		writer.append("<Comments>\n");

		for(Comment comment : container.getComments()){
			System.out.println(comment.getId());
			fetchComment(comment, writer);
		}

		writer.append("</Comments>\n");
	}

	private void fetchComment(Comment comment, StringWriter writer){

		writer.append("<Comment>\n");
		writer.append("<ID>" + comment.getId() + "</ID>\n");
		writer.append("<User>" + comment.getUser().getUsername() + "</User>\n");
		writer.append("<Body>" + comment.getCommentBody() + "</Body>\n");
		writer.append("</Comment>\n");
	}
}

package blogProject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.CommentService;
import blogProject.service.LikeyService;
import blogProject.service.PostsService;
import blogProject.service.SubjectService;
import blogProject.vo.Comment;
import blogProject.vo.Posts;
import blogProject.vo.Subject;
import blogProject.vo.postsAndMember;

@WebServlet("/PostsDetail")
public class PostsDetail extends HttpServlet {
	private PostsService postsService;
	private CommentService commentService;
	private LikeyService likeyService;
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		int currentPage = 1;
		if(request.getParameter("currentPage")!=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		postsService = new PostsService();
		postsAndMember pnm = postsService.getPostsOne(postsNo);
		commentService = new CommentService();
		Map<String, Object> map = commentService.getCommentListAll(postsNo, currentPage);
		request.setAttribute("pnm", pnm);
		request.setAttribute("commentList", map.get("list"));
		request.setAttribute("page", map.get("page"));
		
		likeyService = new LikeyService();
		/*
		 * int likeCount = likeyService.countLikey(postsNo); int notLikeCount =
		 * likeyService.countNotLikey(postsNo); request.setAttribute("likeCount",
		 * likeCount); request.setAttribute("notLikeCount", notLikeCount);
		 */
		Map<String, Object> likeyMap = likeyService.countLikey(postsNo);
		request.setAttribute("likeyMap", likeyMap);
		
		subjectService = new SubjectService();
		ArrayList<Subject> subjectList = subjectService.getSubjectListAll();
		request.setAttribute("subjectListAll", subjectList);
		request.getRequestDispatcher("/WEB-INF/views/postsDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

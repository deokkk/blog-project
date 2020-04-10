package blogProject.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.PostsService;
import blogProject.service.SubjectService;
import blogProject.vo.Member;
import blogProject.vo.Posts;
import blogProject.vo.Subject;
import blogProject.vo.postsAndMember;

@WebServlet("/UpdatePosts")
public class UpdatePosts extends HttpServlet {
	private PostsService postsService;
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		String memberId = request.getParameter("memberId");
		if(((Member)request.getSession().getAttribute("loginMember")).getMemberId().equals(memberId) && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			postsService = new PostsService();
			postsAndMember posts = postsService.getPostsOne(postsNo);
			request.setAttribute("posts", posts);
			subjectService = new SubjectService();
			ArrayList<Subject> subjectListAll = subjectService.getSubjectListAll();
			request.setAttribute("subjectListAll", subjectListAll);
			request.getRequestDispatcher("/WEB-INF/views/updatePosts.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		String memberId = request.getParameter("memberId");
		String postsTitle = request.getParameter("postsTitle");
		String subjectName = request.getParameter("subjectName");
		String postsContent = request.getParameter("postsContent");
		Posts posts = new Posts();
		posts.setMemberId(memberId);
		posts.setPostsContent(postsContent);
		posts.setPostsNo(postsNo);
		posts.setPostsTitle(postsTitle);
		posts.setSubjectName(subjectName);
		postsService = new PostsService();
		postsService.updatePosts(posts);
		response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
	}

}

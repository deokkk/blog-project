package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.CommentService;
import blogProject.vo.Member;

@WebServlet("/RemoveComment")
public class RemoveComment extends HttpServlet {
	private CommentService commentService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		String memberId = request.getParameter("memberId");
		if(((Member)request.getSession().getAttribute("loginMember")).getMemberId().equals(memberId)) {
			commentService = new CommentService();
			commentService.removeComment(commentNo);
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
		} else {
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

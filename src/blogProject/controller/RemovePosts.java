package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.PostsService;
import blogProject.vo.Member;

@WebServlet("/RemovePosts")
public class RemovePosts extends HttpServlet {
	private PostsService postsService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		String memberId = request.getParameter("memberId");
		if(((Member)request.getSession().getAttribute("loginMember")).getMemberId().equals(memberId)) {
			postsService = new PostsService();
			postsService.removePosts(postsNo);
			response.sendRedirect(request.getContextPath()+"/Home");
		} else {
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

package blogProject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.PostsService;
import blogProject.vo.Posts;

@WebServlet("/PostsList")
public class PostsList extends HttpServlet {
	private PostsService postsService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		postsService = new PostsService();
		int currentPage = 1;
		if(request.getParameter("currentPage")!=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		String subjectName = request.getParameter("subjectName");
		System.out.println(subjectName + "<-----subjectName");
		Map<String, Object> map = postsService.getPostsList(subjectName, currentPage);
		request.setAttribute("postsList", map.get("list"));
		request.setAttribute("page", map.get("page"));
		request.setAttribute("subjectName", subjectName);
		request.getRequestDispatcher("/WEB-INF/views/postsList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

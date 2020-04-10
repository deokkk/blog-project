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
import blogProject.service.SubjectService;
import blogProject.vo.Member;
import blogProject.vo.Subject;

@WebServlet("/PostsListAll")
public class PostsListAll extends HttpServlet {
	private PostsService postsService;
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember") != null && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			postsService = new PostsService();
			int currentPage = 1;
			if(request.getParameter("currentPage")!=null) {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			Map<String, Object> map = postsService.getPostsListAll(currentPage);
			request.setAttribute("postsListAll", map.get("list"));
			request.setAttribute("page", map.get("page"));
			subjectService = new SubjectService();
			ArrayList<Subject> subjectList = subjectService.getSubjectListAll();
			request.setAttribute("subjectListAll", subjectList);
			request.getRequestDispatcher("/WEB-INF/views/postsListAll.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

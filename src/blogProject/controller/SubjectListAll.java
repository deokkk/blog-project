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

@WebServlet("/SubjectListAll")
public class SubjectListAll extends HttpServlet {
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember") != null && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			subjectService = new SubjectService();
			int currentPage = 1;
			if(request.getParameter("currentPage")!=null) {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			Map<String, Object> map = subjectService.getSubjectListAll(currentPage);
			request.setAttribute("subjectList", map.get("list"));
			request.setAttribute("page", map.get("page"));
			ArrayList<Subject> subjectListAll = subjectService.getSubjectListAll();
			request.setAttribute("subjectListAll", subjectListAll);
			request.getRequestDispatcher("/WEB-INF/views/subjectList.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

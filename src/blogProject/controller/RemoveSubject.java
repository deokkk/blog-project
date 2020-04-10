package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.SubjectService;
import blogProject.vo.Member;

@WebServlet("/RemoveSubject")
public class RemoveSubject extends HttpServlet {
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subjectName = request.getParameter("subjectName");
		if(((Member)request.getSession().getAttribute("loginMember"))!=null && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			subjectService = new SubjectService();
			subjectService.removeSubject(subjectName);
			response.sendRedirect(request.getContextPath()+"/SubjectListAll");
			return;
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

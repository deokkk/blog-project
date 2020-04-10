package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import blogProject.dao.SubjectDao;
import blogProject.service.SubjectService;
import blogProject.vo.Member;

@WebServlet("/AddSubject")
public class AddSubject extends HttpServlet {
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getSession().getAttribute("loginMember") != null && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			request.getRequestDispatcher("/WEB-INF/views/insertSubject.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subjectName = request.getParameter("subjectName");
		System.out.println(subjectName + " <-- subjectName");
		subjectService = new SubjectService();
		subjectService.addSubject(subjectName);
		response.sendRedirect(request.getContextPath()+"/Home");
	}

}

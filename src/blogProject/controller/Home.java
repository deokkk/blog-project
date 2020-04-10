package blogProject.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.dao.SubjectDao;
import blogProject.service.SubjectService;
import blogProject.vo.Subject;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private SubjectService subjectService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		subjectService = new SubjectService();
		ArrayList<Subject> subjectList = subjectService.getSubjectListAll();
		System.out.println(subjectList.size() + " <---subject size");
		
		ServletContext application = getServletContext();
		application.setAttribute("subjectListAll", subjectList);
		
		//request.setAttribute("subjectListAll", subjectList);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.MemberService;
import blogProject.service.MemberidService;
import blogProject.vo.Member;

@WebServlet("/AddMember")
public class AddMember extends HttpServlet {
	private MemberService memberService;
	private MemberidService memberidService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember")!=null) {
			response.sendRedirect(request.getContextPath()+"/Home");
		} else {
			request.getRequestDispatcher("/WEB-INF/views/insertMember.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember")!=null) {
			response.sendRedirect(request.getContextPath()+"/Home");
		} else {
			String memberId = request.getParameter("memberId");
			String memberPw = request.getParameter("memberPw");
			String memberPw2 = request.getParameter("memberPw2");
			if(memberPw.equals(memberPw2)) {
				memberidService = new MemberidService();
				memberService = new MemberService();
				if(memberService.seachId(memberId)) {
					Member member = new Member();
					member.setMemberId(memberId);
					member.setMemberPw(memberPw);
					
					memberService.addMember(member);
					response.sendRedirect(request.getContextPath()+"/Login");
				} else {
					request.setAttribute("duplication", "y");
					request.getRequestDispatcher("/WEB-INF/views/insertMember.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("inconsistent", "y");
				request.getRequestDispatcher("/WEB-INF/views/insertMember.jsp").forward(request, response);
			}
		}
	}
}

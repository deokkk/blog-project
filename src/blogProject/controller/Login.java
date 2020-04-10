package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import blogProject.dao.MemberDao;
import blogProject.service.MemberService;
import blogProject.vo.Member;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private MemberService memberService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember")==null) {
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		memberService = new MemberService();
		Member returnMember = memberService.getMemberOne(member);
		if(returnMember == null) {
			response.sendRedirect(request.getContextPath()+"/Login");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", returnMember);
			response.sendRedirect(request.getContextPath()+"/Home");
		}
	}

}

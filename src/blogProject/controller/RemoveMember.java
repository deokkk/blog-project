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

@WebServlet("/RemoveMember")
public class RemoveMember extends HttpServlet {
	private MemberService memberService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember")==null) {
			response.sendRedirect(request.getContextPath()+"/Home");
		} else {
			request.setAttribute("state", "delete");
			request.getRequestDispatcher("/WEB-INF/views/confirmPw.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RemoveMember.doPost()");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String memberId = ((Member)session.getAttribute("loginMember")).getMemberId();
		String memberPw = request.getParameter("memberPw");
		System.out.println("id: " + memberId+", pw: "+memberPw);
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		memberService = new MemberService();
		int success = memberService.removeMember(member);
		if(success==1) {
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/Home");
		} else {
			response.sendRedirect(request.getContextPath()+"/MemberDetail");
			//request.setAttribute("state", "delete");
			//request.getRequestDispatcher("/WEB-INF/views/confirmPw.jsp").forward(request, response);
			return;
		}
		
	}

}

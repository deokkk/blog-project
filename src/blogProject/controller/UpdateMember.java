package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import blogProject.service.MemberService;
import blogProject.service.MemberidService;
import blogProject.vo.Member;

@WebServlet("/UpdateMember")
public class UpdateMember extends HttpServlet {
	private MemberService memberService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember")==null) {
			response.sendRedirect(request.getContextPath()+"/Home");
		} else {
			request.setAttribute("state", "update");
			request.getRequestDispatcher("/WEB-INF/views/confirmPw.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		memberService = new MemberService();
		if(request.getParameter("change") == null) {
			HttpSession session = request.getSession();
			String memberId = ((Member)session.getAttribute("loginMember")).getMemberId();
			String memberPw = request.getParameter("memberPw");
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			Member returnMember = memberService.getMemberOne(memberId);
			if(returnMember.getMemberPw().equals(memberPw)) {
				request.getRequestDispatcher("/WEB-INF/views/updateMember.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath()+"/MemberDetail");
			}
		} else {
			String memberId = request.getParameter("memberId");
			String memberPw = request.getParameter("memberPw");
			String memberPw2 = request.getParameter("memberPw2");
			if(memberPw.equals(memberPw2)) {
				Member member = new Member();
				member.setMemberId(memberId);
				member.setMemberPw(memberPw);
				memberService.updateMember(member);
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath()+"/Login");
			} else {
				request.setAttribute("inconsistent", "y");
				request.getRequestDispatcher("/WEB-INF/views/updateMember.jsp").forward(request, response);
			}
		}
	}

}

package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import blogProject.service.LikeyService;
import blogProject.service.MemberService;
import blogProject.vo.Member;

@WebServlet("/MemberDetail")
public class MemberDetail extends HttpServlet {
	private MemberService memberService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember")==null) {
			response.sendRedirect(request.getContextPath()+"/Home");
			return;
		} else {
			System.out.println("MemberDetail");
			memberService = new MemberService();
			Member member = memberService.getMemberOne(((Member)session.getAttribute("loginMember")).getMemberId());
			request.setAttribute("member", member);
			request.getRequestDispatcher("/WEB-INF/views/memberDetail.jsp").forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

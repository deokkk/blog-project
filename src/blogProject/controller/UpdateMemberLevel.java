package blogProject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.MemberService;
import blogProject.vo.Member;

@WebServlet("/UpdateMemberLevel")
public class UpdateMemberLevel extends HttpServlet {
	private MemberService memberService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		memberService = new MemberService();
		if(request.getSession().getAttribute("loginMember") != null && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			if(request.getParameter("memberId") != null) {
				String memberId = request.getParameter("memberId");
				Member member = memberService.getMemberOne(memberId);
				request.setAttribute("member", member);
				request.getRequestDispatcher("/WEB-INF/views/updateMemberLevelForm.jsp").forward(request, response);
			} else {
				int currentPage = 1;
				if(request.getParameter("currentPage")!=null) {
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
				Map<String, Object> map = memberService.selectMemberListAll(currentPage);
				request.setAttribute("memberList", map.get("list"));
				request.setAttribute("page", map.get("page"));
				request.getRequestDispatcher("/WEB-INF/views/updateMemberLevel.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		int memberLevel = Integer.parseInt(request.getParameter("memberLevel"));
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberLevel(memberLevel);
		memberService = new MemberService();
		memberService.updateMemberLevel(member);
		response.sendRedirect(request.getContextPath()+"/UpdateMemberLevel");
	}
}

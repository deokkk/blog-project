package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.LikeyService;
import blogProject.service.SubjectService;
import blogProject.vo.Likey;
import blogProject.vo.Member;

@WebServlet("/AddLikey")
public class AddLikey extends HttpServlet {
	private LikeyService likeyService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		System.out.println(postsNo + " <--postsNo");
		int likeyCk = Integer.parseInt(request.getParameter("likeyCk"));
		System.out.println(likeyCk + " <------likeyCk");
		if(request.getSession().getAttribute("loginMember")==null) {
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
			return;
		} else {
			Likey likey = new Likey();
			likey.setPostsNo(postsNo);
			likey.setMemberId(((Member)request.getSession().getAttribute("loginMember")).getMemberId());
			likey.setLikeyCk(likeyCk);
			likeyService = new LikeyService();
			likeyService.addLikey(likey);
			
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
